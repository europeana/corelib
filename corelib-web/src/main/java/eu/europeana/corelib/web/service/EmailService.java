/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.web.service;

import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.web.exception.EmailServiceException;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EmailService {
	
	void sendToken(Token token, String url) throws EmailServiceException;
	
	void sendForgotPassword(User user, String url) throws EmailServiceException;
	
	void sendFeedback(String email, String feedback) throws EmailServiceException;
	
}
