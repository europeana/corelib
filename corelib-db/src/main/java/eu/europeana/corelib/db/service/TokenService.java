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

package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;

/**
 * Service with dedicated Token related actions.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.entity.Token
 */
public interface TokenService extends AbstractService<Token> {

	public static final int MAX_TOKEN_AGE = 1000 * 60 * 60 * 6;

	/**
	 * Creates a new email token
	 * 
	 * @param email
	 * @return The created token.
	 * @throws DatabaseException
	 *             When there is no valid email address provided
	 */
	Token create(String email) throws DatabaseException;

	/**
	 * Create a random Token String with 32 characters
	 * 
	 * @return The created token
	 */
	String createRandomToken();

}
