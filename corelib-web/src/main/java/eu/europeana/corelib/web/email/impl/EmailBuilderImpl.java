package eu.europeana.corelib.web.email.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.email.model.EmailConfig;
import eu.europeana.corelib.web.exception.EmailServiceException;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class EmailBuilderImpl implements EmailBuilder {

	private static final String TEMPLATE_NAME_AFFIX_TEXT = ".txt.vm";
	private static final String TEMPLATE_NAME_AFFIX_HTML = ".html.vm";

	private String emailTo;

	private String emailFrom;

	private String emailCc;

	private String subject;

	private Map<String, Object> model;

	@Resource
	private VelocityEngine engine;

	@Resource(name = "corelib_web_emailConfigs")
	private Map<String, EmailConfig> configs;

	private EmailConfig config;

	@Override
	public void prepare(MimeMessage mimeMessage) throws MessagingException {

		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

		if (StringUtils.contains(emailTo, ",")) {
			message.setTo(StringUtils.split(emailTo, ","));
		} else {
			message.setTo(emailTo);
		}
		if (StringUtils.isNotBlank(emailCc)) {
			message.addCc(emailCc);
		}
		message.setFrom(emailFrom);
		message.setSubject(subject);

		String text = VelocityEngineUtils.mergeTemplateIntoString(engine,
			config.getTemplate() + TEMPLATE_NAME_AFFIX_TEXT, "UTF-8", model);

		String html = VelocityEngineUtils.mergeTemplateIntoString(engine,
			config.getTemplate() + TEMPLATE_NAME_AFFIX_HTML, model);

		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setText(text);

		BodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(html, "text/html");

		message.getMimeMultipart().addBodyPart(textPart);
		message.getMimeMultipart().addBodyPart(htmlPart);
	}

	@Override
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	@Override
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}


	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	@Override
	public void setTemplate(String template) throws EmailServiceException {
		if (configs.containsKey(template)) {
			this.config = configs.get(template);
			this.emailTo = config.getEmailTo();
			this.emailFrom = config.getEmailFrom();
			this.emailCc = config.getEmailCc();
			this.subject = config.getSubject();
		} else {
			throw new EmailServiceException(ProblemType.INVALIDARGUMENTS);
		}
	}
}
