package eu.europeana.corelib.utils;

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Patrick Ehlert
 * Created on 06-09-2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml"})
public class EuropeanaAggregationTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void testLandingPageFromAbout() {
        String testId = "/12345/abcde";
        EuropeanaAggregation ea = new EuropeanaAggregationImpl();
        ea.setAbout("/aggregation/europeana" + testId);
        assertEquals("http://localhost:8081/portal/record"+testId+".html", ea.getEdmLandingPage());
    }

    @Test
    public void testLandingPageFromAggregatedCHO() {
        String testId = "/12345/abcde";
        EuropeanaAggregation ea = new EuropeanaAggregationImpl();
        ea.setAggregatedCHO(testId);
        assertEquals("http://localhost:8081/portal/record"+testId+".html", ea.getEdmLandingPage());
    }

    @Test
    public void testLandingPageIncorrect() {
        String testId = "/12345";
        EuropeanaAggregation ea = new EuropeanaAggregationImpl();
        ea.setAggregatedCHO(testId);
        assertNull(ea.getEdmLandingPage());
    }

}
