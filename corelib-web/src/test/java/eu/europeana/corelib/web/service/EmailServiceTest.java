/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.web.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;
import eu.europeana.corelib.definitions.db.entity.relational.SavedSearch;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.users.Role;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.email.impl.EmailBuilderImpl;
import eu.europeana.corelib.web.exception.EmailServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class EmailServiceTest extends AbstractJUnit4SpringContextTests {

	public static int SERVER_PORT = 2500;

	private static Wiser wiser;

	private boolean runOnce = false;

	@Resource
	private EmailService emailService;

	@BeforeClass
	public static void setup() {
		// sometimes the service does not completely stops...
		if ( (wiser != null) && wiser.getServer().isRunning()){
			wiser.getServer().stop();
			wiser.stop();
		}
		wiser = new Wiser();
		wiser.setPort(SERVER_PORT);
		wiser.start();
	}

	@Before
	public void clear() throws Exception {
		if (!runOnce) {
			reconfigureMailSenders(applicationContext, 2500);
			runOnce = true;
		}
		wiser.getMessages().clear();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		wiser.stop();
	}

	@Test
	public void testSendToken() throws EmailServiceException {
		Token token = createFakeToken("testSendToken","testSendToken@testSendToken.eu");
		emailService.sendToken(token, "testSendToken.eu/test.html");
		assertEquals(1, wiser.getMessages().size());
	}

	@Test(expected=EmailServiceException.class)
	public void testSendTokenException() throws EmailServiceException {
		emailService.sendToken(null, "testSendToken.eu/test.html");
	}

	@Test
	public void testSendForgotPassword() throws EmailServiceException {
		User user = createFakeUser("testSendToken","testSendForgotPassword@testSendForgotPassword.eu");
		emailService.sendForgotPassword(user, "testSendToken.eu/test.html");
		assertEquals(1, wiser.getMessages().size());
	}

	@Test(expected=EmailServiceException.class)
	public void testSendForgotPasswordException() throws EmailServiceException {
		User user = createFakeUser("testSendToken","testSendForgotPassword@testSendForgotPassword.eu");
		emailService.sendForgotPassword(user, null);
	}

	@Test
	public void testSendFeedback() throws EmailServiceException {
		emailService.sendFeedback("testSendFeedback@testSendFeedback.eu", "Feedback Test");
		assertEquals(2, wiser.getMessages().size());
	}

	@Test(expected=EmailServiceException.class)
	public void testSendFeedbackException() throws EmailServiceException {
		emailService.sendFeedback("testSendFeedback@testSendFeedback.eu", null);
	}

	@Test(expected=EmailServiceException.class)
	public void testInvalidEmailTemplate() throws EmailServiceException {
		EmailBuilder builder = applicationContext.getBean(EmailBuilderImpl.class);
		builder.setEmailFrom("doesNotExists@europeana.eu");
		builder.setSubject("doesNotExists");
		// next line will trigger an EmailServiceException as it must be a valid template tag
		builder.setTemplate("doesNotExists");
	}

	@SuppressWarnings("serial")
	private Token createFakeToken(final String tokenString, final String emailAddress) {
		return new Token() {
			@Override
			public String getId() {
				return tokenString;
			}

			@Override
			public void setToken(String token) {}

			@Override
			public void setEmail(String email) {}

			@Override
			public void setCreated(Date created) {}

			@Override
			public String getToken() {
				return getId();
			}

			@Override
			public String getEmail() {
				return emailAddress;
			}

			@Override
			public long getCreated() {
				return new Date().getTime();
			}
		};
	}

	@SuppressWarnings("serial")
	private User createFakeUser(final String username, final String email) {
		return new User() {

			@Override
			public Long getId() {
				return NumberUtils.LONG_ONE;
			}

			@Override
			public void setUserName(String userName) {}

			@Override
			public void setRole(Role role) {}

			@Override
			public void setRegistrationDate(Date registrationDate) {}

			@Override
			public void setPassword(String password) {}

			@Override
			public void setLastLogin(Date lastLogin) {}

			@Override
			public void setEmail(String email) {}

			@Override
			public void setApiKey(String apiKey) {}

			@Override
			public String getUserName() {
				return username;
			}

			@Override
			public Set<SocialTag> getSocialTags() {
				return null;
			}

			@Override
			public Set<SavedSearch> getSavedSearches() {
				return null;
			}

			@Override
			public Set<SavedItem> getSavedItems() {
				return null;
			}

			@Override
			public Role getRole() {
				return Role.ROLE_USER;
			}

			@Override
			public Date getRegistrationDate() {
				return new Date();
			}

			@Override
			public String getPassword() {
				return null;
			}

			@Override
			public Date getLastLogin() {
				return null;
			}

			@Override
			public String getEmail() {
				return email;
			}

			@Override
			public String getApiKey() {
				return null;
			}

			@Override
			public Set<ApiKey> getApiKeys() {
				return null;
			}

		
			@Override
			public String getFirstName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setFirstName(String firstName) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getLastName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setLastName(String lastName) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getCompany() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getCountry() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setCompany(String company) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setCountry(String country) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getPhone() {
				return null;
			}

			@Override
			public void setPhone(String phone) {}

			@Override
			public String getAddress() {
				return null;
			}

			@Override
			public void setAddress(String address) {}

			@Override
			public String getWebsite() {
				return null;
			}

			@Override
			public void setWebsite(String website) {}

			@Override
			public String getFieldOfWork() {
				return null;
			}

			@Override
			public void setFieldOfWork(String fieldOfWork) {}
		};
	}

	private static void reconfigureMailSenders(ApplicationContext applicationContext, int port) {
		Map<String, JavaMailSenderImpl> ofType =
				applicationContext.getBeansOfType(org.springframework.mail.javamail.JavaMailSenderImpl.class);

		for (Map.Entry<String, JavaMailSenderImpl> bean : ofType.entrySet()) {
			JavaMailSenderImpl mailSender = bean.getValue();
			mailSender.setHost("localhost");
			mailSender.setPort(port);
		}
	}
}
