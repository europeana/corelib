package eu.europeana.corelib.web.email;

import java.util.Map;

import org.springframework.mail.javamail.MimeMessagePreparator;

import eu.europeana.corelib.web.exception.EmailServiceException;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EmailBuilder extends MimeMessagePreparator {

	void setTemplate(String template) throws EmailServiceException;

	void setModel(Map<String, Object> model);

	void setEmailTo(String emailTo);

	void setSubject(String subject);

	void setEmailFrom(String emailFrom);
}