package eu.europeana.corelib.web.service;

import static org.junit.Assert.fail;

import java.util.Map;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class EmailServiceTest extends AbstractJUnit4SpringContextTests {
	
	public static int SERVER_PORT = 2500;

    private Wiser wiser;
    
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
	public void test() {
		fail("Not yet implemented");
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
