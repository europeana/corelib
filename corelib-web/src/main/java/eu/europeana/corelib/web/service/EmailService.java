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

package eu.europeana.corelib.web.service;

import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.web.exception.EmailServiceException;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EmailService {

	void sendToken(Token token, String url) throws EmailServiceException;

	void sendApiToken(Token token, String url) throws EmailServiceException;

	void sendRegisterNotify(User user) throws EmailServiceException;

	void sendForgotPassword(User user, String url) throws EmailServiceException;

	void sendForgotPassword(String email, String url) throws EmailServiceException;

	void sendFeedback(String email, String feedback) throws EmailServiceException;

	void sendException(String subject, String body) throws EmailServiceException;

	void sendRegisterApiNotifyAdmin(User user) throws EmailServiceException;

	void sendRegisterApiNotifyUser(ApiKey apiKey) throws EmailServiceException;
}
