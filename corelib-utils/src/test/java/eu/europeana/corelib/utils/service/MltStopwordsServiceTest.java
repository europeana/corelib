package eu.europeana.corelib.utils.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * MLT Stopwords service tests
 * @author Peter.Kiraly@kb.nl
 *
 * @deprecated July 2017 MoreLikeThis / SimilarItems for records is no longer being used (and doesn't work properly)
 */
@Deprecated
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-utils-context.xml", "/corelib-utils-test.xml" })
public class MltStopwordsServiceTest {

	@Resource
	private MltStopwordsService mltStopwordsService;

	/**
	 * Testing the service's check() method
	 */
	@Test
	public void check() {
		assertTrue("image should be in list", mltStopwordsService.check("image"));
		assertTrue("text should be in list", mltStopwordsService.check("text"));
		assertTrue("video should be in list", mltStopwordsService.check("video"));
		assertTrue("VIDEO should be in list", mltStopwordsService.check("VIDEO"));
		assertTrue("sound should be in list", mltStopwordsService.check("sound"));
		assertTrue("stillimage should be in list", mltStopwordsService.check("stillimage"));
		assertTrue("Stillimage should be in list", mltStopwordsService.check("Stillimage"));
		assertTrue("still image should be in list", mltStopwordsService.check("still image"));

		assertFalse("Angel should not be in list", mltStopwordsService.check("Angel"));
		assertFalse("Saint should not be in list", mltStopwordsService.check("Saint"));
	}

}
