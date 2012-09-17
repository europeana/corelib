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