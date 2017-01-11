package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.enums.RecordType;
import eu.europeana.corelib.db.entity.nosql.ApiLog;
import eu.europeana.corelib.utils.DateIntervalUtils;
import eu.europeana.corelib.utils.model.DateInterval;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
public class ApiLogServiceTest {

    String apiKey = "testKey";

    @Resource
    private ApiLogService apiLogService;

    @Resource(name = "corelib_db_apiLogDao")
    NosqlDao<ApiLog, String> apiLogDao;

    /**
     * Initialise the testing session
     *
     * @throws IOException
     */
    @Before
    public void setup() throws IOException {
        apiLogDao.getCollection().drop();
    }

    /**
     * Cleaning the testing session's data
     *
     * @throws IOException
     */
    @After
    public void tearDown() throws IOException {
        apiLogDao.getCollection().drop();
    }

    @Test
    public void testCountByApiKeyByInterval() throws CloneNotSupportedException {
        DateInterval interval = DateIntervalUtils.getToday();

        long count = apiLogService.countByIntervalAndApiKey(interval, apiKey);
        assertNotNull(count);
        assertEquals(0, count);

        apiLogService.logApiRequest(apiKey, "paris", RecordType.SEARCH, "standard");
        apiLogService.logApiRequest(apiKey, "berlin", RecordType.SEARCH, "standard");

        // Give the system some time to process the requests;
        // rather than force the system to sleep, use a time in the future
        DateTime dt = new DateTime().plusMillis(100);

        // the interval contains the end date, which was before insertions, so we have to refresh it.
        interval.setEnd(dt.toDate());
        long count2 = apiLogService.countByIntervalAndApiKey(interval, apiKey);
        assertNotNull(count2);
        assertEquals(2, count2);
    }

    @Test
    public void testCountByApiKey() {
        long count = apiLogService.countByApiKey(apiKey);
        assertNotNull(count);
        assertEquals(0, count);

        apiLogService.logApiRequest(apiKey, "paris", RecordType.SEARCH, "standard");
        apiLogService.logApiRequest(apiKey, "berlin", RecordType.SEARCH, "standard");

        count = apiLogService.countByApiKey(apiKey);
        assertNotNull(count);
        assertEquals(2, count);
    }

    @Test
    public void testFindByApiKey() {
        List<ApiLog> logs = apiLogService.findByApiKey(apiKey);
        assertNotNull(logs);
        assertEquals(0, logs.size());

        apiLogService.logApiRequest(apiKey, "paris", RecordType.SEARCH, "standard");
        apiLogService.logApiRequest(apiKey, "berlin", RecordType.SEARCH, "standard");

        logs = apiLogService.findByApiKey(apiKey);
        assertNotNull(logs);
        assertEquals(2, logs.size());
    }

    @Test
    public void testCountByInterval() {
        DateInterval interval = DateIntervalUtils.getToday();

        long count = apiLogService.countByInterval(interval);
        assertNotNull(count);
        assertEquals(0, count);

        apiLogService.logApiRequest(apiKey, "paris", RecordType.SEARCH, "standard");
        apiLogService.logApiRequest(apiKey, "berlin", RecordType.SEARCH, "standard");

        DateTime dt = new DateTime().plusMillis(100); // add some time to avoid racing condition problems
        interval.setEnd(dt.toDate());
        long count2 = apiLogService.countByInterval(interval);
        assertNotNull(count2);
        assertEquals(2, count2);
    }
}
