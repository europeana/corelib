package eu.europeana.corelib.search;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.loader.ContentLoader;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.web.exception.EuropeanaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
// TODO JV this test is ignored until the Mongo and Solar versions are updated. Then the Metis
// indexing library should be used to save the required test records (see ContentLoader class).
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-solr-context.xml", "/corelib-solr-test.xml"})
public class SearchServiceTest {

    private static final Logger LOG = LogManager.getLogger(SearchServiceTest.class);

    private static final ContentLoader CONTENT_LOADER = ContentLoader.getInstance();

    private enum LoadingStatus {NOT_LOADED, LOADING, LOADED}

    private static int no_of_tests = 0;
    private static LoadingStatus dataLoaded = LoadingStatus.NOT_LOADED;
    private static int testCount = 0;

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
        CONTENT_LOADER.readRecords(COLLECTION);
    }

    @Before
    public void loadTestData() {
        if (dataLoaded == LoadingStatus.NOT_LOADED) {
            dataLoaded = LoadingStatus.LOADING;
            LOG.info("LOADING TEST DATA...");
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
                    Assert.assertTrue("records failed to load...", CONTENT_LOADER.parse(mongoServer, solrServer) == 0);
                    CONTENT_LOADER.commit(solrServer);
//                        Thread.sleep(5000);
                    dataLoaded = LoadingStatus.LOADED;
                } catch (Exception e) {
                    LOG.error("Error loading records", e);
                } finally {
                    if (CONTENT_LOADER != null) {
                        CONTENT_LOADER.cleanFiles();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOG.info("TEST DATA LOADED...");
        }
    }

    @After
    public void removeTestData() throws InterruptedException {
        LOG.info("@AFTER "+testCount+" OF "+no_of_tests);
        if (testCount == no_of_tests) {
            LOG.info("CLEANING TEST DATA...");
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
    public void testSpellCheck() throws EuropeanaException {
        LOG.info("TEST testSpellCheck");
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
    public void testFindById() throws EuropeanaException {
        LOG.info("TEST testFindById");
        testCount++;
        Query query = new Query("*:*");
        query.setDefaultSolrFacets();
        ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
        assertFalse("No results given back... ", results.getResults().isEmpty());
        FullBean fBean = searchService.findById(results.getResults().get(0).getId(), true);
        assertNotNull(fBean);
    }

    @Test
    @Deprecated
    public void testFindMoreLikeThis() throws EuropeanaException {
        LOG.info("TEST testFindMoreLikeThis");
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
    public void testGestLastSolrUpdate() throws EuropeanaException {
        LOG.info("TEST testGestLastSolrUpdate");
        testCount++;
        assertNotNull(searchService.getLastSolrUpdate());
    }

    @Test
    public void testSeeAlso() {
        LOG.info("TEST testSeeAlso");
        testCount++;
        List<String> queries = new ArrayList<>();
        queries.add("DATA_PROVIDER:*");
        assertNotNull(searchService.seeAlso(queries));
    }

    /**
     * Tests whether setting a sort returns in a valid query
     * @throws SolrTypeException
     */
    @Test
    public void testSort() throws EuropeanaException {
        LOG.info("TEST testSort");
        testCount++;
        Query query = new Query("keyboard");

        query.setSort("score descending timestamp_created asc+timestamp_update,COMPLETENESS");
        ResultSet<BriefBean> results = searchService.search(BriefBean.class, query);
        assertNotNull(results);
        assertTrue(results.getResultSize() > 0);
    }

//    @Test TODO: fix this test...
    public void testResolve() throws EuropeanaException  {
        LOG.info("TEST testResolve");
        testCount++;
        Query query = new Query("*:*");
        ResultSet<BriefBean> results;
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
    }



}
