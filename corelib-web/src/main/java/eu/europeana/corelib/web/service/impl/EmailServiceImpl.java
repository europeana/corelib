package eu.europeana.corelib.web.service.impl;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.web.service.EmailService
 */
public abstract class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LogManager.getLogger(EmailServiceImpl.class);

    @Resource
    private JavaMailSenderImpl mailSender;

    @Override
    public void sendActivationToken(Token token, String apiHost) throws EmailServiceException {
        if ((token == null)
                || StringUtils.isBlank(token.getToken())
                || StringUtils.isBlank(token.getEmail())
                || StringUtils.isBlank(apiHost)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        String url = apiHost + "/user/activate/" + token.getEmail() + "/" + token.getToken();
        Map<String, Object> model = new HashMap<>();
        model.put("url", url);
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("activation"); // see corelib_web_emailConfigs
        builder.setEmailTo(token.getEmail());
        mailSender.send(builder);
        LOG.info("Sent token ({}) and URL ({}) to {}", token.getToken(), url, token.getEmail());
    }

    @Override
    public void sendApiKeys(ApiKey apiKey) throws EmailServiceException {
        if (apiKey == null) {
            LOG.error("Problem with sendApiKeys: apiKey is null");
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("apiKey", apiKey);
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("apikeys"); // see corelib_web_emailConfigs
        builder.setEmailTo(apiKey.getEmail());
        mailSender.send(builder);
        LOG.info("Sent API details to {}", apiKey.getEmail());
    }

    @Override
    public void sendNewPasswordToken(Token token, String apiHost, String salutation) throws EmailServiceException {
        if ((token == null)
                || StringUtils.isBlank(token.getToken())
                || StringUtils.isBlank(token.getEmail())
                || StringUtils.isBlank(apiHost)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        String url = apiHost + "/user/password/" + token.getEmail() + "/" + token.getToken();
        Map<String, Object> model = new HashMap<>();
        model.put("url", url);
        model.put("salutation", salutation);
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("forgotPassword"); // see corelib_web_emailConfigs
        builder.setEmailTo(token.getEmail());
        mailSender.send(builder);
        LOG.info("Sent password reset url ({}) to {}", url, token.getEmail());
    }

    @Override
    public void sendPasswordResetConfirmation(User user, String salutation) throws EmailServiceException {
        if ((user == null) || (user.getId() == null)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("salutation", salutation);
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("resetPasswordConfirm"); // see corelib_web_emailConfigs
        builder.setEmailTo(user.getEmail());
        mailSender.send(builder);
        LOG.info("Sent password reset confirmation email to ({})", user.getEmail());
    }

    //TODO: remove because it's implemented in sedNewPasswordToken ?
    @Override
    public void sendForgotPassword(final User user, final String url) throws EmailServiceException {
        if ((user == null) || (user.getId() == null) || StringUtils.isBlank(url)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        sendForgotPassword(user.getEmail(), url);
    }


    //TODO: remove because it's implemented in sedNewPasswordToken ?
    @Override
    public void sendForgotPassword(final String email, final String url) throws EmailServiceException {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(url)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("url", url);
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("forgotPassword");
        builder.setEmailTo(email);
        mailSender.send(builder);
        LOG.info("Sent forgot password (URL={}) to {}", url, email);
    }

    @Override
    public void sendFeedback(String email, String feedback) throws EmailServiceException {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(feedback)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("email", email);
        model.put("feedback", feedback);

        // one email to organisation
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("userFeedback");
        mailSender.send(builder);

        // and one email to user
        builder.setTemplate("userFeedbackConfirm");
        builder.setEmailTo(email);
        mailSender.send(builder);
        LOG.info("Sent feedback of {}", email);
    }

    @Override
    public void sendException(String subject, String body)
            throws EmailServiceException {
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(body)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("subject", subject);
        model.put("body", body);

        // one email to organisation
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("exception");
        builder.setSubject(subject);
        mailSender.send(builder);
    }

    /**
     * This method will be handled by Spring Framework.
     * No implementation needed
     *
     * @return a instance of EmailBuilder
     */
    protected abstract EmailBuilder createEmailBuilder();
}
