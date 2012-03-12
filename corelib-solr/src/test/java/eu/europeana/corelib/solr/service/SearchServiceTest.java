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

package eu.europeana.corelib.solr.service;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.solr.ContentLoader;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.MongoDBServer;


/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.solr.service.SearchService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-solr-context.xml", "/corelib-solr-test.xml"})
public class SearchServiceTest {

	private static String COLLECTION = "src/test/resources/records-test.zip";
	
	@Resource()
	private SearchService searchService;
	
	private static MongoDBServer mongoDBServer;
	
	private static SolrServer solrServer;
	
	@BeforeClass
	public static void loadTestData() {
		ApplicationContext context = new ClassPathXmlApplicationContext( "/corelib-solr-context.xml", "/corelib-solr-test.xml" );
		solrServer = context.getBean("corelib_solr_solrEmbedded", SolrServer.class);
		mongoDBServer =  context.getBean("corelib_solr_mongoServer", MongoDBServer.class);

		ContentLoader contentLoader = null;
		try {
			contentLoader = ContentLoader.getInstance(mongoDBServer, solrServer);
			contentLoader.readRecords(COLLECTION);
			Assert.assertTrue("records failed to load...",contentLoader.parse() == 0);
			contentLoader.commit();
		} finally {
			if (contentLoader != null) {
				contentLoader.cleanFiles();
			}
		}
	}
	
	@Test
	public void findAllTest() throws SolrTypeException {
		ResultSet<BriefBean> results = searchService.search(BriefBean.class, new Query("*:*"));
		Assert.assertTrue("Did not return expected amount of results: " + results.getResultSize(), results.getResultSize() == 205);
		
	}
	
	@AfterClass
	public static void removeTestData() {
		// doesn't need to delete solr data, as it is written in temp directory.
		mongoDBServer.getDatastore().getDB().dropDatabase();
	}

}
