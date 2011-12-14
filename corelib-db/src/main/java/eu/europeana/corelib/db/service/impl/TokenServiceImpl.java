/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.europeana.corelib.db.service.impl;

import java.util.Calendar;

import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;

public class TokenServiceImpl extends AbstractServiceImpl<Token> implements
		TokenService {

	@Override
	public Token create(String email) {
		Token token = new Token();
		token.setCreated(Calendar.getInstance().getTimeInMillis());
		token.setEmail(email);
		token.setToken("");
		return dao.insert(token);
	}

}
