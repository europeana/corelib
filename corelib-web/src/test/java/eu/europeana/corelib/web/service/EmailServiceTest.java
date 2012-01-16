package eu.europeana.corelib.web.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

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

import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.web.exception.EmailServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class EmailServiceTest extends AbstractJUnit4SpringContextTests {
	
	public static int SERVER_PORT = 2500;

    private static Wiser wiser;
    
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
