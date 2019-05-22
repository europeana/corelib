package eu.europeana.corelib.web.service;

import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.users.Role;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.email.impl.EmailBuilderImpl;
import eu.europeana.corelib.web.exception.EmailServiceException;
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

import javax.annotation.Resource;
import java.util.Date;
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
    public void testSendForgotPassword() throws EmailServiceException {
        User user = createFakeUser("testSendToken", "testSendForgotPassword@testSendForgotPassword.eu");
        emailService.sendForgotPassword(user, "testSendToken.eu/test.html");
        assertEquals(1, wiser.getMessages().size());
    }

    @Test(expected = EmailServiceException.class)
    public void testSendForgotPasswordException() throws EmailServiceException {
        User user = createFakeUser("testSendToken", "testSendForgotPassword@testSendForgotPassword.eu");
        emailService.sendForgotPassword(user, null);
    }

    @Test
    public void testSendFeedback() throws EmailServiceException {
        emailService.sendFeedback("testSendFeedback@testSendFeedback.eu", "Feedback Test");
        assertEquals(2, wiser.getMessages().size());
    }

    @Test(expected = EmailServiceException.class)
    public void testSendFeedbackException() throws EmailServiceException {
        emailService.sendFeedback("testSendFeedback@testSendFeedback.eu", null);
    }

    @Test(expected = EmailServiceException.class)
    public void testInvalidEmailTemplate() throws EmailServiceException {
        EmailBuilder builder = applicationContext.getBean(EmailBuilderImpl.class);
        builder.setEmailFrom("doesNotExists@europeana.eu");
        builder.setSubject("doesNotExists");
        // next line will trigger an EmailServiceException as it must be a valid template tag
        builder.setTemplate("doesNotExists");
    }

    private User createFakeUser(final String username, final String email) {
        User userMock = mock(User.class);
        when(userMock.getId()).thenReturn(NumberUtils.LONG_ONE);
        when(userMock.getUserName()).thenReturn(username);
        when(userMock.getRole()).thenReturn(Role.ROLE_USER);
        when(userMock.getRegistrationDate()).thenReturn(new Date());
        when(userMock.getEmail()).thenReturn(email);
        when(userMock.getLanguageSearchApplied()).thenReturn(Boolean.TRUE);

        return userMock;
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
