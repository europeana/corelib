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

package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;
import eu.europeana.corelib.definitions.db.entity.relational.Token;

/**
 * Service with dedicated Token related actions.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.entity.relational.TokenImpl
 */
public interface TokenService extends AbstractService<Token> {

	// 6 hours
	int MAX_TOKEN_AGE = 1000 * 60 * 60 * 6;

	/**
	 * Creates a new email token
	 * 
	 * @param email
	 * @return The created token.
	 * @throws DatabaseException
	 *             When there is no valid email address provided
	 */
	Token create(String email, String redirect) throws DatabaseException;

	/**
	 * Create a random Token String with 32 characters
	 * 
	 * @return The created token
	 */
	String createRandomToken();
}
