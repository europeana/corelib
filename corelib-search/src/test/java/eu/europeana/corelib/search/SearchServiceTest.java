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

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.loader.ContentLoader;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.search.SearchService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-solr-context.xml", "/corelib-solr-test.xml"})
public class SearchServiceTest {

    private static int no_of_tests = 0;
    private static LoadingStatus dataLoaded = LoadingStatus.NOT_LOADED;
    private static int testCount = 0;

    private static final ContentLoader contentLoader = ContentLoader.getInstance();

    @Resource(name = "corelib_solr_searchService")
    private SearchService searchService;

    @Resource(name = "corelib_solr_mongoServer")
    private EdmMongoServer mongoServer;

    @Resource(name = "corelib_solr_mongoServer_id")
    private EuropeanaIdMongoServer idServer;

    @Resource(name = "corelib_solr_solrEmbedded")
    private EmbeddedSolrServer solrServer;

    @BeforeClass
    public static void setupClass() {
        for (Method method : SearchServiceTest.class.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType().equals(org.junit.Test.class)) {
                    no_of_tests++;
                }
            }
        }
        String COLLECTION = "src/test/resources/records.zip";
        contentLoader.readRecords(COLLECTION);
    }

    @Before
    public void loadTestData() {
        if (dataLoaded == LoadingStatus.NOT_LOADED) {
            dataLoaded = LoadingStatus.LOADING;
            System.out.println("LOADING TEST DATA...");
            try {
//                    IMongodConfig conf = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//                            .net(new Net(port, Network.localhostIsIPv6()))
//                            .build();

//                    MongodStarter runtime = MongodStarter.getDefaultInstance();
//
//                    MongodExecutable mongodExecutable = runtime.prepare(conf);
//                    mongodExecutable.start();
//                    Mongo mongo = new Mongo("localhost", port);
//                    EdmMongoServer mongoDBServer = new EdmMongoServerImpl(mongo, "europeana_test",
//                            "", "");
//                    idServer = new EuropeanaIdMongoServerImpl(mongo, "europeana_id_test", "", "");
//                    mongoDBServer.getDatastore().getDB().dropDatabase();
//                    idServer.createDatastore();
//                    searchService.setEdmMongoServer(mongoDBServer);
//                    searchService.setEuropeanaIdMongoServer(idServer);
//                    searchService.setLogger(Logger.getLogger(SearchServiceImpl.class));


                try {
                    Assert.assertTrue("records failed to load...", contentLoader.parse(mongoServer, solrServer) == 0);
                    contentLoader.commit(solrServer);
//                        Thread.sleep(5000);
                    dataLoaded = LoadingStatus.LOADED;
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
            System.out.println("TEST DATA LOADED...");
        }
    }

    @After
    public void removeTestData() throws InterruptedException {
        System.out.println("@AFTER "+testCount+" OF "+no_of_tests);
        if (testCount == no_of_tests) {
            System.out.println("CLEANING TEST DATA...");
            try {
//                dataLoaded = false;
                solrServer.deleteByQuery("*:*");
                solrServer.commit();
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testSpellCheck() throws SolrTypeException {
        System.out.println("TEST testSpellCheck");
        testCount++;
        Query query = new Query("musi");
        query.setDefaultSolrFacets();
        ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
        Assert.assertNull(results.getSpellcheck());
    }

//    @Test
//    @Ignore // TODO Deprecate?
//    public void findSuggestions() throws SolrTypeException {
//        testCount++;
//
//        List<Term> terms = searchService.suggestions("model mod", 10, "title");
//        Assert.assertEquals(2, terms.size());
//        Assert.assertEquals("Title", terms.get(0).getField());
//        Assert.assertEquals(1, terms.get(0).getFrequency());
//        Assert.assertEquals("modell der dulcitone mechanik", terms.get(0).getTerm());
//    }

	/*
    @Test
	public void findAllTest() throws SolrTypeException {
		testCount++;
		Assert.assertTrue("Data not loaded succesfull...", dataLoaded);
		ResultSet<BriefBean> results = searchService.calculateTag(BriefBean.class,
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
		ResultSet<BriefBean> results = searchService.calculateTag(BriefBean.class,
				query);
		Assert.assertNotNull("Did not got any results", results);
		Assert.assertTrue("Did not return expected amount of results: "
				+ results.getResultSize(), results.getResultSize() == 195);

	}
	 */

    @Test
    public void testFindById() throws MongoDBException, MongoRuntimeException, SolrTypeException, Neo4JException {
        System.out.println("TEST testFindById");
        testCount++;
        Query query = new Query("*:*");
        query.setDefaultSolrFacets();
        ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
        assertFalse("No results given back... ", results.getResults().isEmpty());
        FullBean fBean = searchService.findById(results.getResults().get(0).getId(), true, 4000);
        assertNotNull(fBean);
    }

    @Test
    public void testFindMoreLikeThis() throws SolrTypeException, SolrServerException {
        System.out.println("TEST testFindMoreLikeThis");
        testCount++;
        Query query = new Query("*:*");
        query.setDefaultSolrFacets();
        ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
        assertFalse("No results given back... ", results.getResults().isEmpty());
        List<BriefBean> mlt = searchService.findMoreLikeThis(results.getResults().get(0).getId());
        assertNotNull(mlt);
        Assert.assertEquals(10, mlt.size());
    }

    @Test
    public void testGestLastSolrUpdate() throws SolrServerException, IOException {
        System.out.println("TEST testGestLastSolrUpdate");
        testCount++;
        assertNotNull(searchService.getLastSolrUpdate());
    }

    @Test
    public void testSitemap() throws SolrTypeException {
        System.out.println("TEST testSitemap");
        testCount++;
        assertNotNull(searchService.sitemap(BriefBean.class, new Query("*:*")));
    }

    @Test
    public void testSeeAlso() {
        System.out.println("TEST testSeeAlso");
        testCount++;
        List<String> queries = new ArrayList<>();
        queries.add("DATA_PROVIDER:*");
        assertNotNull(searchService.seeAlso(queries));
    }

//    @Test TODO: fix this test...
    public void testResolve() {
        System.out.println("TEST testResolve");
        testCount++;
        Query query = new Query("*:*");
        ResultSet<BriefBean> results;
        try {
            results = searchService.search(BriefBean.class, query);
            String newId = results.getResults().get(0).getId();
            String oldId = "test_id";
            EuropeanaId eId = new EuropeanaId();
            eId.setNewId(newId);
            eId.setOldId(oldId);
//            Mongo mongo = new Mongo("localhost", port);
//            idServer = new EuropeanaIdMongoServerImpl(mongo, "europeana_id_test", "", "");
//            idServer.createDatastore();
            idServer.saveEuropeanaId(eId);
//            searchService.setEuropeanaIdMongoServer(idServer);
            assertNotNull(searchService.resolve("test_id", false));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Should never happen:" + e.getMessage());
        }

    }

    enum LoadingStatus {NOT_LOADED, LOADING, LOADED}

}
