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

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml", "/corelib-db-test.xml" })
public class TokenServiceTest {

	@Resource(name = "corelib_db_tokenDao")
	private Dao<Token> dao;

	@Resource(name = "corelib_db_tokenService")
	private TokenService tokenService;

	@Test
	public void testCreate() {
		dao.deleteAll();
		Token token = tokenService.create("test@europeana.eu");
		if (token == null) {
			fail("Unable to create token");
		}
		if (StringUtils.isBlank(token.getId())) {
			fail("No token generated");
		}
		if (StringUtils.length(token.getId()) != DatabaseDefinition.FIELDSIZE_TOKEN) {
			fail("Token did not have the required length, current length="
					+ StringUtils.length(token.getId()));
		}
		if (token.getCreated() <= 0) {
			fail("No valid creation date (long) set, current value="
					+ token.getCreated());
		}
	}

	@Test
	public void testCreateRandomToken() {
		String token = tokenService.createRandomToken();
		if (StringUtils.isBlank(token)) {
			fail("Did not give back succesfully a random token");
		}
		if (StringUtils.length(token) != DatabaseDefinition.FIELDSIZE_TOKEN) {
			fail("Token did not have the required length");
		}
	}

}
