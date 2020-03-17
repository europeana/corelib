package eu.europeana.corelib.solr.entity;

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
        assertEquals("https://www.europeana.eu/portal/record"+testId+".html", ea.getEdmLandingPage());
    }

    @Test
    public void testLandingPageFromAggregatedCHO() {
        String testId = "/12345/abcde";
        EuropeanaAggregation ea = new EuropeanaAggregationImpl();
        ea.setAggregatedCHO(testId);
        assertEquals("https://www.europeana.eu/portal/record"+testId+".html", ea.getEdmLandingPage());
    }


    @Test
    public void testLandingPageSetExternal() {
        String testId = "/12345/abcde";
        EuropeanaAggregation ea = new EuropeanaAggregationImpl();
        ea.setAggregatedCHO(testId);
        String newValue = "https://www-test.eanadev.org/api/v2/record/1/2.json";
        ea.setEdmLandingPage(newValue);
        assertEquals(newValue, ea.getEdmLandingPage());
    }

    @Test
    public void testLandingPageIncorrect() {
        String testId = "/12345";
        EuropeanaAggregation ea = new EuropeanaAggregationImpl();
        ea.setAggregatedCHO(testId);
        assertNull(ea.getEdmLandingPage());
    }

}
