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

package eu.europeana.corelib.db.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.entity.relational.TokenImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.definitions.exception.ProblemType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.service.TokenService
 */
@Transactional
public class TokenServiceImpl extends AbstractServiceImpl<Token> implements
		TokenService {

	/**
	 * Overriding the findByID() method to handle expiration
	 */
	@Override
	public Token findByID(Serializable id) {
		Token token;
		try {
			token = super.findByID(id);
		} catch (DatabaseException e) {
			return null;
		}
		if (token != null) {
			long age = System.currentTimeMillis() - token.getCreated().getTime();
	        if (age <= MAX_TOKEN_AGE) {
	        	return token;
	        }
	        dao.delete(token);
		}
		return null;
	}
	
	@Override
	public Token create(String email) throws DatabaseException {
		if (StringUtils.isBlank(email)) {
			throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
		}
		TokenImpl token = new TokenImpl();
		token.setCreated(new Date());
		token.setEmail(email);
		token.setToken(createRandomToken());
		return dao.insert(token);
	}
	
	@Override
	public String createRandomToken() {
		String token = UUID.randomUUID().toString();
		return StringUtils.remove(token, "-");
	}

}
