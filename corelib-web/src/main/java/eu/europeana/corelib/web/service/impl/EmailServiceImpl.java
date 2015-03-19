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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.mail.javamail.JavaMailSender;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.logging.Log;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.web.service.EmailService
 */
public abstract class EmailServiceImpl implements EmailService {
	
	@Log
	private Logger log;

	@Resource
	private JavaMailSender mailSender;

	@Resource
	private MessageSource messageSource;

	/**
	 * Sends a token to user as part of registration confirmation
	 * 
	 * @param token
	 *   The token to send to the user
	 * @param url
	 *   The URL of registration confirm page
	 */
	@Override
	public void sendToken(final Token token, final String url) throws EmailServiceException {
		if ( (token == null)
			|| StringUtils.isBlank(token.getToken())
			|| StringUtils.isBlank(token.getEmail())
			|| StringUtils.isBlank(url)) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token", token.getToken());
		
		model.put("url", url.replace("//", "/"));
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("register"); // see corelib_web_emailConfigs
		builder.setEmailTo(token.getEmail());
		mailSender.send(builder);
		log.info(String.format("Sent token (%s) and URL (%s) to %s", token.getToken(), url, token.getEmail()));
	}

	/**
	 * Sends a token to user as part of registration confirmation
	 * 
	 * @param token
	 *   The token to send to the user
	 * @param url
	 *   The URL of registration confirm page
	 */
	@Override
	public void sendApiToken(final Token token, final String url) throws EmailServiceException {
		if ( (token == null)
			|| StringUtils.isBlank(token.getToken())
			|| StringUtils.isBlank(token.getEmail())
			|| StringUtils.isBlank(url)) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token", token.getToken());
		model.put("url", url);
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("registerApi"); // see corelib_web_emailConfigs
		builder.setEmailTo(token.getEmail());
		mailSender.send(builder);
		log.info(String.format("Sent token (%s) and URL (%s) to %s", token.getToken(), url, token.getEmail()));
	}

	/**
	 * Sends a token to user as part of registration confirmation
	 * 
	 * @param token
	 *   The token to send to the user
	 * @param url
	 *   The URL of registration confirm page
	 */
	@Override
	public void sendRegisterNotify(final User user) throws EmailServiceException {
		if (user == null) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("registerNotify"); // see corelib_web_emailConfigs
		// builder.setEmailTo(token.getEmail());
		mailSender.send(builder);
		log.info(String.format("Sent user registratiom (%s)", user.getEmail()));
	}

	/**
	 * Sends email to the site administrator about an API registration
	 */
	@Override
	public void sendRegisterApiNotifyAdmin(final User user) throws EmailServiceException {
		if (user == null) {
			log.error("Problem with sendRegisterApiNotifyAdmin: user is null");
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", user);
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("registerApiNotifyAdmin"); // see corelib_web_emailConfigs
		mailSender.send(builder);
		log.info(String.format("Sent notification of API registratiom (%s)", user.getEmail()));
	}

	/**
	 * Sends email to the user about the details of API registration
	 */
	@Override
	public void sendRegisterApiNotifyUser(final ApiKey apiKey, Locale locale) throws EmailServiceException {
		if (apiKey == null) {
			log.error("Problem with sendRegisterApiNotifyUser: apiKey is null");
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apiKey", apiKey);

		// adding translatable message keys
		String prefix = "register_api_notify_user_";
		String suffix = "_t";
		String[] labels = new String[] {
				"line1", "line2", "apikey_label", "privatekey_label", "line3a",
				"line3b", "line4", "line5", "line6a", "line6b", "line7"
		};
		for (String label : labels) {
			try {
				String value = messageSource.getMessage(prefix + label + suffix, null, locale);
				model.put(label, value);
				String html = value;
				if (StringUtils.contains(html, "http://") || StringUtils.contains(html, "https://")) {
					html = html.replaceAll("(https?://[^ ,]+)", "<a href=\"$1\" target=\"_blank\">$1</a>");
				}
				if (StringUtils.contains(html, "@")) {
					html = html.replaceAll("([^ ,]+@[^ ,]+)", "<a href=\"mailto:$1\" target=\"_blank\">$1</a>");
				}
				model.put(label + "_html", html);
			} catch (NoSuchMessageException e) {
				log.error(String.format("Label %s is not defined", label));
			}
		}

		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("registerApiNotifyUser"); // see corelib_web_emailConfigs
		builder.setEmailTo(apiKey.getUser().getEmail());
		mailSender.send(builder);
		log.info(String.format("Sent API details to %s", apiKey.getUser().getEmail()));
	}

	/**
	 * Sends and email to user in case of forgotting password. It contains a link where the user can reset his password.
	 * 
	 * @param user
	 *   The user object
	 * @param url
	 *   The URL of the password reset page
	 */
	@Override
	public void sendForgotPassword(final User user, final String url) throws EmailServiceException {
		if ((user == null) || (user.getId() == null) || StringUtils.isBlank(url)) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		sendForgotPassword(user.getEmail(), url);
	}

	/**
	 * Sends and email to user in case of forgotting password. It contains a link where the user can reset his password.
	 * 
	 * @param user
	 *   The user object
	 * @param url
	 *   The URL of the password reset page
	 */
	@Override
	public void sendForgotPassword(final String email, final String url) throws EmailServiceException {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(url)) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("forgotPassword");
		builder.setEmailTo(email);
		mailSender.send(builder);
		log.info(String.format("Sent forgot password (URL=%s) to %s", url, email));
	}

	/**
	 * Sends the user's feedback to the site admin, and sends an thanking email to the user
	 * 
	 * @param email
	 *   The user's email address
	 * @param feedback
	 *   The user's feedback
	 */
	@Override
	public void sendFeedback(String email, String feedback) throws EmailServiceException {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(feedback)) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
		Map<String, Object> model = new HashMap<String, Object>();
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

	/**
	 * Sends exception to the site admin
	 */
	@Override
	public void sendException(String subject, String body)
			throws EmailServiceException {
		if (StringUtils.isBlank(subject) || StringUtils.isBlank(body)) {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}

		Map<String, Object> model = new HashMap<String, Object>();
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