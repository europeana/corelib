package eu.europeana.corelib.web.email.model;

/**
 * Container of email-related configurations.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class EmailConfig {

	private String template;

	private String emailFrom;

	private String emailTo;

	private String emailCc;

	private String subject;

	/**
	 * GETTERS & SETTTERS
	 */

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}
}