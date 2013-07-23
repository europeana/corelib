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
package eu.europeana.corelib.utils.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Opt-out service tests
 * @author Peter.Kiraly@ kb.nl
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-utils-context.xml", "/corelib-utils-test.xml" })
public class OptOutServiceTest {
	
	@Resource
	private OptOutService optOutService;
	
	@Test
	public void testCheckByEuropeanaId() {
	}

	@Test
	public void checkByCollectionId() {
		assertTrue("CollectionId 90801 should be in list", optOutService.check("/90801/testrecordid"));
		assertTrue("CollectionId 08501 should be in list", optOutService.check("/08501/testrecordid"));
		assertTrue("CollectionId 92066 should be in list", optOutService.check("92066"));
		assertTrue("CollectionId 08556 should be in list", optOutService.check("08556"));
		assertFalse("CollectionId 00000 should not be in list", optOutService.check("/00000/testrecordid"));
		assertFalse("CollectionId 11111 should not be in list", optOutService.check("11111"));

		String url = "http://www.europeana.eu/portal/record/%s/testrecordid";
		assertTrue("CollectionId 90801 should be in list", optOutService.check(String.format(url, "90801")));
		assertTrue("CollectionId 08501 should be in list", optOutService.check(String.format(url, "08501")));
		assertFalse("CollectionId 11111 should not be in list", optOutService.check(String.format(url, "11111")));
	}
	
}
