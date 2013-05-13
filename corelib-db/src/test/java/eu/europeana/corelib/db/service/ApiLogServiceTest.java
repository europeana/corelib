package eu.europeana.corelib.db.service;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.enums.RecordType;
import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.service.ApiLogService;
import eu.europeana.corelib.utils.DateIntervalUtils;
import eu.europeana.corelib.utils.model.DateInterval;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
public class ApiLogServiceTest {

	@Resource private ApiLogService apiLogService;

	@Resource(name = "corelib_db_apiLogDao")
	NosqlDao<ImageCache, String> apiLogDao;

	/**
	 * Initialise the testing session
	 * 
	 * @throws IOException
	 */
	@Before
	public void setup() throws IOException {
		apiLogDao.getCollection().drop();
	}

	@Test
	public void testCountByApiKeyByInterval() {
		String apiKey = "api2demo";
		apiLogService.logApiRequest(apiKey, "paris", RecordType.SEARCH, "standard");
		apiLogService.logApiRequest(apiKey, "berlin", RecordType.SEARCH, "standard");

		DateInterval interval = DateIntervalUtils.getToday();
		long count = apiLogService.countByApiKeyByInterval(apiKey, interval);
		assertNotNull(count);
		assertEquals(2, count);
	}
}
