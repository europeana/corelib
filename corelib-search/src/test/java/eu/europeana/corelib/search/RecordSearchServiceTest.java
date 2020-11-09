package eu.europeana.corelib.search;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.record.BaseUrlWrapper;
import eu.europeana.corelib.record.DataSourceWrapper;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.search.loader.ContentLoader;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;
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

import static org.junit.Assert.*;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.search.SearchService
 */
// TODO JV this test is ignored until the Mongo and Solar versions are updated. Then the Metis
// indexing library should be used to save the required test records (see ContentLoader class).

// TODO PE April 2020: This unit test requires major refactoring. It's not a good idea to combine solr and mongo
// stuff into a single unit test.
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-solr-context.xml", "/corelib-mongo-context.xml", "/corelib-solr-test.xml"})
public class RecordSearchServiceTest {

    private static final Logger LOG = LogManager.getLogger(RecordSearchServiceTest.class);

    private static final ContentLoader CONTENT_LOADER = ContentLoader.getInstance();

    private enum LoadingStatus {NOT_LOADED, LOADING, LOADED}

    private static int no_of_tests = 0;
    private static LoadingStatus dataLoaded = LoadingStatus.NOT_LOADED;
    private static int testCount = 0;

    @Resource(name = "corelib_solr_searchService")
    private SearchService searchService;

    @Resource(name = "corelib_record_recordService")
    private RecordService recordService;

    @Resource(name = "corelib_record_mongoServer")
    private RecordDao recordDao;

    @Resource(name = "metis_redirect_mongo")
    private RecordRedirectDao mongoIdServer;

    @Resource(name = "corelib_solr_solrEmbedded")
    private EmbeddedSolrServer solrServer;

    @BeforeClass
    public static void setupClass() {
        for (Method method : RecordSearchServiceTest.class.getMethods()) {
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
//                    RecordDao mongoDBServer = new RecordDaoImpl(mongo, "europeana_test",
//                            "", "");
//                    idServer = new EuropeanaIdMongoServerImpl(mongo, "europeana_id_test", "", "");
//                    mongoDBServer.getDatastore().getDB().dropDatabase();
//                    idServer.createDatastore();
//                    searchService.setRecordDao(mongoDBServer);
//                    searchService.setEuropeanaIdMongoServer(idServer);
//                    searchService.setLogger(LogManager.getLogger(SearchServiceImpl.class));


                try {
                    Assert.assertTrue("records failed to load...", CONTENT_LOADER.parse(recordDao, solrServer) == 0);
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
        ResultSet<BriefBean> results = searchService.search(solrServer, BriefBean.class, query);
        Assert.assertNull(results.getSpellcheck());
    }

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
        ResultSet<BriefBean> results = searchService.search(solrServer, BriefBean.class, query);
        assertFalse("No results given back... ", results.getResults().isEmpty());
        FullBean fBean = recordService.findById(new DataSourceWrapper(recordDao, mongoIdServer), results.getResults().get(0).getId(), new BaseUrlWrapper("", "",""));
        assertNotNull(fBean);
    }

    @Test
    public void testGetLastSolrUpdate() throws EuropeanaException {
        LOG.info("TEST testGestLastSolrUpdate");
        testCount++;
        assertNotNull(searchService.getLastSolrUpdate(solrServer));
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
        ResultSet<BriefBean> results = searchService.search(solrServer,BriefBean.class, query);
        assertNotNull(results);
        assertTrue(results.getResultSize() > 0);
    }

}
