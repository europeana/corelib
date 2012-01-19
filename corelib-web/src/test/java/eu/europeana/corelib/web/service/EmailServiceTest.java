package eu.europeana.corelib.web.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;

import eu.europeana.corelib.definitions.db.entity.SavedItem;
import eu.europeana.corelib.definitions.db.entity.SavedSearch;
import eu.europeana.corelib.definitions.db.entity.SocialTag;
import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.definitions.users.Role;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.email.impl.EmailBuilderImpl;
import eu.europeana.corelib.web.exception.EmailServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class EmailServiceTest extends AbstractJUnit4SpringContextTests {
	
	public static int SERVER_PORT = 2500;

    private Wiser wiser;
    
    @Resource
    private EmailService emailService;
    
	@Before
	public void setUp() throws Exception {
		wiser = new Wiser();
		wiser.setPort(SERVER_PORT);
		wiser.start();

		reconfigureMailSenders(applicationContext, 2500);
	}

	@After
	public void tearDown() throws Exception {
		wiser.stop();
	}
	
	@Test
	public void testSendToken() throws EmailServiceException {
		Token token = createFakeToken("testSendToken","testSendToken@testSendToken.eu");
		emailService.sendToken(token, "testSendToken.eu/test.html");
		assertEquals(1, wiser.getMessages().size());
	}
	
	@Test
	public void testSendForgotPassword() throws EmailServiceException {
		User user = createFakeUser("testSendToken","testSendForgotPassword@testSendForgotPassword.eu");
		emailService.sendForgotPassword(user, "testSendToken.eu/test.html");
		assertEquals(1, wiser.getMessages().size());
	}

	@Test
	public void testSendFeedback() throws EmailServiceException {
		emailService.sendFeedback("testSendFeedback@testSendFeedback.eu", "Feedback Test");
		assertEquals(2, wiser.getMessages().size());
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
			public Date getCreated() {
				return new Date();
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
