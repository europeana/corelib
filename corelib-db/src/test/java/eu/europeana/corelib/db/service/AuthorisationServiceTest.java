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

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.definitions.db.entity.User;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml", "/corelib-db-test.xml" })
public class AuthorisationServiceTest {
	
	@Resource
	private UserService userService;

	@Resource
	private TokenService tokenService;
	
	@Resource
	private AuthorisationService authorisationService;
	
	private static final String APIKEY = "TESTKEY";
	private boolean keyCreated = false;
	
	@Before
	public void setupKey() throws DatabaseException {
		if (!keyCreated) {
			Token token = tokenService.create("apikey@europeana.eu");
			User user = userService.create(token.getToken(), "apikey", "apikey");
			user.setApiKey(APIKEY);
			userService.store(user);
			keyCreated = true;
		}
	}


}
