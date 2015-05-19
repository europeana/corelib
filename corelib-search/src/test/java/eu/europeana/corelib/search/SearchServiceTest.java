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

package eu.europeana.corelib.search;

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
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.lookup.impl.EuropeanaIdMongoServerImpl;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.search.impl.SearchServiceImpl;
import eu.europeana.corelib.search.loader.ContentLoader;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.search.SearchService
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
				IMongodConfig conf = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				        .net(new Net(port, Network.localhostIsIPv6()))
				        .build();

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
		Assert.assertEquals(terms.size(), 2);
		Assert.assertEquals(terms.get(0).getField(), "Title");
		Assert.assertEquals(terms.get(0).getFrequency(), 1);
		Assert.assertEquals(terms.get(0).getTerm(),
				"modell der dulcitone mechanik");
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

		Assert.assertNull(results.getSpellcheck());

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
		List<String> queries = new ArrayList<>();
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
			} catch (SolrServerException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
