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
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.solr.ContentLoader;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.solr.service.impl.SearchServiceImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServerImpl;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.solr.service.SearchService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class SearchServiceTest {

	private static String COLLECTION = "src/test/resources/records.zip";
	private MongodExecutable mongodExecutable = null;
	private int port = 10000;

	@Resource
	private TestSearchService searchService;

	private EdmMongoServer mongoDBServer;
	private EuropeanaIdMongoServer idServer;

	@Resource(name = "corelib_solr_solrEmbedded")
	private EmbeddedSolrServer solrServer;

	private static boolean dataLoaded = false;

	private static int testCount = 0;

	private static int no_of_tests = 0;

	@Before
	public void loadTestData() {
		if (!dataLoaded) {
			try {
				
				MongodConfig conf = new MongodConfig(Version.V2_0_7, port,
						false);

				MongodStarter runtime = MongodStarter.getDefaultInstance();

				mongodExecutable = runtime.prepare(conf);
				mongodExecutable.start();
				Mongo mongo = new Mongo("localhost", port);
				mongoDBServer = new EdmMongoServerImpl(mongo, "europeana_test",
						"", "");
				idServer = new EuropeanaIdMongoServerImpl(mongo, "europeana_id_test", "", "");
				mongoDBServer.getDatastore().getDB().dropDatabase();
				idServer.createDatastore();
				searchService.setEdmMongoServer(mongoDBServer);
				searchService.setEuropeanaIdMongoServer(idServer);
				searchService.setLogger(Logger.getLogger(SearchServiceImpl.class));
				for (Method method : this.getClass().getMethods()) {
					for (Annotation annotation : method.getAnnotations()) {
						if (annotation.annotationType().equals(
								org.junit.Test.class)) {
							no_of_tests++;
						}
					}
				}

				System.out.println("LOADING TEST DATA...");
				ContentLoader contentLoader = null;
				try {
					contentLoader = ContentLoader.getInstance(mongoDBServer,
							solrServer);
					contentLoader.readRecords(COLLECTION);
					Assert.assertTrue("records failed to load...",
							contentLoader.parse() == 0);
					contentLoader.commit();
					dataLoaded = true;
				} catch (Exception e) {

					e.printStackTrace();
				} finally {
					if (contentLoader != null) {
						contentLoader.cleanFiles();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void findSuggestions() throws SolrTypeException {
		testCount++;
		
		List<Term> terms = searchService.suggestions("model mod", 10, "title");
		Assert.assertEquals(terms.size(), 10);
		Assert.assertEquals(terms.get(0).getField(), "Title");
		Assert.assertEquals(terms.get(0).getFrequency(), 3);
		Assert.assertEquals(terms.get(0).getTerm(),
				"modell moderner pianinomechanik");
	}

	/*
	@Test
	public void findAllTest() throws SolrTypeException {
		testCount++;
		Assert.assertTrue("Data not loaded succesfull...", dataLoaded);
		ResultSet<BriefBean> results = searchService.search(BriefBean.class,
				new Query("*:*"));
		Assert.assertNotNull("Did not got any results", results);
		Assert.assertTrue("Did not return expected amount of results: "
				+ results.getResultSize(), results.getResultSize() == 250);
		Assert.assertTrue("Did not return expected facet list: "
				+ results.getFacetFields().size(), results.getFacetFields()
				.size() == 8);
	}
	@Test
	public void findAllWithTextFilterTest() throws SolrTypeException {
		testCount++;
		Query query = new Query("*:*");

		query.setRefinements(new String[] { "text:mus*" });
		ResultSet<BriefBean> results = searchService.search(BriefBean.class,
				query);
		Assert.assertNotNull("Did not got any results", results);
		Assert.assertTrue("Did not return expected amount of results: "
				+ results.getResultSize(), results.getResultSize() == 195);

	}
	 */

	@Test
	public void testSpellCheck() throws SolrTypeException {
		testCount++;
		Query query = new Query("musi");
		ResultSet<BriefBean> results = searchService.search(BriefBean.class,
				query);

		Assert.assertNotNull(results.getSpellcheck());

	}

	@Test
	public void testFindById() throws MongoDBException,SolrTypeException {
		testCount++;
		Query query = new Query("*:*");
		ResultSet<BriefBean> results = searchService.search(BriefBean.class,
				query);
		FullBean fBean = searchService.findById(results.getResults().get(0)
				.getId(), true);
		Assert.assertNotNull(fBean);

	}

	@Test
	public void testFindMoreLikeThis() throws SolrTypeException,
			SolrServerException {
		testCount++;
		Query query = new Query("*:*");
		ResultSet<BriefBean> results = searchService.search(BriefBean.class,
				query);
		List<BriefBean> mlt = searchService.findMoreLikeThis(results
				.getResults().get(0).getId());
		Assert.assertNotNull(mlt);
		Assert.assertEquals(10, mlt.size());
	}

	@Test
	public void testGestLastSolrUpdate() throws SolrServerException,
			IOException {
		testCount++;
		Assert.assertNotNull(searchService.getLastSolrUpdate());
	}

	@Test
	public void testSitemap() throws SolrTypeException {
		testCount++;
		Assert.assertNotNull(searchService.sitemap(BriefBean.class, new Query(
				"*:*")));
	}

	@Test
	public void testSeeAlso() {
		testCount++;
		List<String> queries = new ArrayList<String>();
		queries.add("DATA_PROVIDER:*");
		Assert.assertNotNull(searchService.seeAlso(queries));
	}

	@Test
	public void testResolve(){
		testCount++;
		Query query = new Query("*:*");
		ResultSet<BriefBean> results;
		try {
			results = searchService.search(BriefBean.class,
					query);
			String newId = results.getResults().get(0)
					.getId();
			String oldId = "test_id";
			EuropeanaId eId = new EuropeanaId();
			eId.setNewId(newId);
			eId.setOldId(oldId);
			Mongo mongo = new Mongo("localhost", port);
			idServer = new EuropeanaIdMongoServerImpl(mongo, "europeana_id_test", "", "");
			idServer.createDatastore();
			idServer.saveEuropeanaId(eId);
			
			Assert.assertNotNull(searchService.resolve("test_id", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@After
	public void removeTestData() {
		if (testCount == no_of_tests) {
			System.out.println("CLEANING TEST DATA...");

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
