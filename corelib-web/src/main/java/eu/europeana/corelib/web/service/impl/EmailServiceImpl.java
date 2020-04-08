package eu.europeana.corelib.web.service.impl;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.web.service.EmailService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.MailException;
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
    public void sendApiKeys(ApiKey apiKey) throws EmailServiceException {
        if (apiKey == null) {
            LOG.error("Problem with sendApiKeys: apiKey is null");
            throw new EmailServiceException(ProblemType.INVALID_ARGUMENTS);
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
    public void sendException(String subject, String body) throws EmailServiceException {
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(body)) {
            throw new EmailServiceException(ProblemType.INVALID_ARGUMENTS);
        }
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("subject", subject);
            model.put("body", body);

            // one email to organisation
            EmailBuilder builder = createEmailBuilder();
            builder.setModel(model);
            builder.setTemplate("exception");
            builder.setSubject(subject);
            mailSender.send(builder);
        } catch (MailException me) {
            throw new EmailServiceException(ProblemType.MAIL_ERROR, me);
        }
    }

    /**
     * This method will be handled by Spring Framework.
     * No implementation needed
     *
     * @return a instance of EmailBuilder
     */
    protected abstract EmailBuilder createEmailBuilder();
}
