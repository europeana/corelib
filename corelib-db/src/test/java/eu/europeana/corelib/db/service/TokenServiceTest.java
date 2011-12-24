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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml", "/corelib-db-test.xml" })
public class TokenServiceTest {

	@Resource(name = "corelib_db_tokenDao")
	private Dao<Token> dao;

	@Resource
	private TokenService tokenService;

	/**
	 * Clears database tables before every test and at the end
	 */
	@Before
	public void clearBetweenTest() {
		dao.deleteAll();
	}

	@Test
	public void testCreateRandomToken() {
		String token = tokenService.createRandomToken();
		assertNotNull("No token generated", StringUtils.trimToNull(token));
		assertEquals("Token did not have the required length, current length=" + StringUtils.length(token),
				DatabaseDefinition.FIELDSIZE_TOKEN, StringUtils.length(token));
	}

	@Test
	public void testCreate() throws DatabaseException {
		Token token = tokenService.create("test@europeana.eu");
		assertNotNull("Unable to create token", token);
		token = tokenService.findByID(token.getToken());
		assertNotNull("Unable to retrieve created token", token);
		assertNotNull("No token generated", StringUtils.trimToNull(token.getId()));
		assertNotNull("No valid creation date set", token.getCreated());
	}

	@Test(expected=DatabaseException.class)
	public void testCreateInvalidEmail() throws DatabaseException {
		tokenService.create(" ");
		fail("This line should never be reached!!!");
	}
	
	@Test
	public void testExpiredToken() throws DatabaseException {
		Calendar expired = Calendar.getInstance();
		expired.add(Calendar.MILLISECOND, -TokenService.MAX_TOKEN_AGE);
		
		Token token = tokenService.create("test@europeana.eu");
		assertNotNull("Unable to create token", token);
		// change date to expired date
		token.setCreated(expired.getTime());
		tokenService.store(token);
		assertNull("Token not flagged as expired", tokenService.findByID(token.getToken()));
	}

}
