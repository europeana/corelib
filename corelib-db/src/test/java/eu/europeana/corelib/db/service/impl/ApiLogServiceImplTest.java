package eu.europeana.corelib.db.service.impl;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.entity.enums.RecordType;
import eu.europeana.corelib.db.service.ApiLogService;
import eu.europeana.corelib.utils.DateIntervalUtils;
import eu.europeana.corelib.utils.model.DateInterval;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
public class ApiLogServiceImplTest {

	@Resource private ApiLogService apiLogService;

	@Test
	public void testCountByApiKeyByInterval() {
		String apiKey = "api2demo";
		apiLogService.logApiRequest(apiKey, "paris", RecordType.SEARCH, "standard");

		DateInterval interval = DateIntervalUtils.getToday();
		long count = apiLogService.countByApiKeyByInterval(apiKey, interval);
		assertNotNull(count);
		assertEquals(1, count);
	}
}
