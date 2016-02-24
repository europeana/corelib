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

package eu.europeana.corelib.web.service.impl;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.logging.Log;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.web.service.EmailService
 */
public abstract class EmailServiceImpl implements EmailService {

    @Log
    private Logger log;

    @Resource
    private JavaMailSender mailSender;

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
        log.info(String.format("Sent token (%s) and URL (%s) to %s", token.getToken(), url, token.getEmail()));
    }

    @Override
    public void sendApiKeys(ApiKey apiKey) throws EmailServiceException {
        if (apiKey == null) {
            log.error("Problem with sendApiKeys: apiKey is null");
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("apiKey", apiKey);
        EmailBuilder builder = createEmailBuilder();
        builder.setModel(model);
        builder.setTemplate("apikeys"); // see corelib_web_emailConfigs
        builder.setEmailTo(apiKey.getEmail());
        mailSender.send(builder);
        log.info(String.format("Sent API details to %s", apiKey.getEmail()));
    }

    @Override
    public void sendForgotPassword(final User user, final String url) throws EmailServiceException {
        if ((user == null) || (user.getId() == null) || StringUtils.isBlank(url)) {
            throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
        }
        sendForgotPassword(user.getEmail(), url);
    }


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
        log.info(String.format("Sent forgot password (URL=%s) to %s", url, email));
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
        log.info(String.format("Sent feedback of %s", email));
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
