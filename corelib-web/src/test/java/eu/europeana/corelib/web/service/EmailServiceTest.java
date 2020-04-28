package eu.europeana.corelib.web.service;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.email.impl.EmailBuilderImpl;
import eu.europeana.corelib.web.exception.EmailServiceException;
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

import javax.annotation.Resource;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-web-context.xml", "/corelib-web-test.xml"})
public class EmailServiceTest extends AbstractJUnit4SpringContextTests {

    public static int SERVER_PORT = 2500;

    private static Wiser wiser;

    private boolean runOnce = false;

    @Resource(name = "corelib_web_emailService")
    private EmailService emailService;

    @BeforeClass
    public static void setup() {
        // sometimes the service does not completely stops...
        if ((wiser != null) && wiser.getServer().isRunning()) {
            wiser.getServer().stop();
            wiser.stop();
        }
        wiser = new Wiser();
        wiser.setPort(SERVER_PORT);
        wiser.start();
    }

    @Before
    public void clear() {
        if (!runOnce) {
            reconfigureMailSenders(applicationContext, 2500);
            runOnce = true;
        }
        wiser.getMessages().clear();
    }

    @AfterClass
    public static void tearDown() {
        wiser.stop();
    }

    @Test
    public void testSendApiKeys() throws EmailServiceException {
        ApiKey fakeKey = createFakeApiKey("testKey", "testPrivateKey", "Test", "Case", "unit@test.com");
        emailService.sendApiKeys(fakeKey);
        assertEquals(2, wiser.getMessages().size()); // 2 because we always send a copy to ourselves
    }

    @Test(expected = EmailServiceException.class)
    public void testInvalidEmailTemplate() throws EmailServiceException {
        EmailBuilder builder = applicationContext.getBean(EmailBuilderImpl.class);
        builder.setEmailFrom("doesNotExists@europeana.eu");
        builder.setSubject("doesNotExists");
        // next line will trigger an EmailServiceException as it must be a valid template tag
        builder.setTemplate("doesNotExists");
    }

    private ApiKey createFakeApiKey(final String publicKey, final String privateKey,
                                    final String firstName, final String lastName, final String emailAddress) {
        ApiKey keyMock = mock(ApiKey.class);
        when(keyMock.getId()).thenReturn(publicKey);
        when(keyMock.getPrivateKey()).thenReturn(privateKey);
        when(keyMock.getFirstName()).thenReturn(firstName);
        when(keyMock.getLastName()).thenReturn(lastName);
        when(keyMock.getEmail()).thenReturn(emailAddress);
        return keyMock;
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
