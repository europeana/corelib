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

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.solr.ContentLoader;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.EdmMongoServer;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.solr.service.SearchService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class SearchServiceTest {

	private static String COLLECTION = "src/test/resources/records-test.zip";

	@Resource
	private SearchService searchService;

	@Resource(name="corelib_solr_mongoServer")
	private EdmMongoServer mongoDBServer;

	@Resource(name = "corelib_solr_solrEmbedded")
	private SolrServer solrServer;

	private static boolean dataLoaded = false;

	private static int testCount = 0;

	@Before
	public void loadTestData() {
		if (!dataLoaded) {
			System.out.println("LOADING TEST DATA...");
			ContentLoader contentLoader = null;
			try {
				contentLoader = ContentLoader.getInstance(mongoDBServer, solrServer);
				contentLoader.readRecords(COLLECTION);
				Assert.assertTrue("records failed to load...", contentLoader.parse() == 0);
				contentLoader.commit();
				dataLoaded = true;
			} catch (Exception e) {
				e.printStackTrace();
				//System.out.println(e.printStackTrace());
			} finally {
				if (contentLoader != null) {
					contentLoader.cleanFiles();
				}
			}
		}
	}

	@Test
	public void findSuggestions() throws SolrTypeException{
		List<Term> terms  = searchService.suggestions("kleine p", 1);
		Assert.assertEquals(terms.size(),1);
		Assert.assertEquals(terms.get(0).getField(),"Title");
		Assert.assertEquals(terms.get(0).getFrequency(),1);
		Assert.assertEquals(terms.get(0).getTerm(),"kleine pauke");
	}
	
	@Test
	public void findAllTest() throws SolrTypeException {
		testCount++;
		Assert.assertTrue("Data not loaded succesfull...", dataLoaded);
		ResultSet<BriefBean> results = searchService.search(BriefBean.class, new Query("*:*"));
		Assert.assertNotNull("Did not got any results", results);
		Assert.assertTrue("Did not return expected amount of results: " + results.getResultSize(),
				results.getResultSize() == 205);
		Assert.assertTrue("Did not return expected facet list: " + results.getFacetFields().size(),
				results.getFacetFields().size() == 7);
	}

	@Test
	public void findAllWithTextFilterTest() throws SolrTypeException {
		testCount++;
		Query query = new Query("*:*");
		
		query.setRefinements(new String[]{"text:drums"});
		ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
		Assert.assertNotNull("Did not got any results", results);
		Assert.assertTrue("Did not return expected amount of results: " + results.getResultSize(),
				results.getResultSize() == 2);
		
	}
	
	@Test
	public void testSpellCheck() throws SolrTypeException{
		Query query = new Query("musi");
		ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
		Assert.assertNotNull(results.getSpellcheck());
		
	}
	
	@After
	public void removeTestData() {
		if (testCount == 2) {
			System.out.println("CLEANING TEST DATA...");
			mongoDBServer.getDatastore().getDB().dropDatabase();
			try {
				dataLoaded = false;
				solrServer.deleteByQuery("*:*");
				solrServer.commit();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
