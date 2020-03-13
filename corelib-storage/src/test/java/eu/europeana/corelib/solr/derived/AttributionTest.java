package eu.europeana.corelib.solr.derived;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AttributionTest {

    private static final String HTML_SNIPPET_FILE = "/htmlsnippet.txt";
    private static final String TEXT_SNIPPET_FILE = "/textsnippet.txt";
    private static final String HTML_CSS_SOURCE   = "https://style.europeana.eu/attribution/style.css";

    private static AttributionSnippet attributionSnippet;

    @Before
    public void setUp(){
        attributionSnippet = Mockito.spy(AttributionSnippet.class);
    }

    private static Attribution createAttr(){
        Attribution attribution = new Attribution();
        attribution.setItemUri("http://data.europeana.eu/item/142/UEDIN_214");
        Map<String, String> map = new HashMap<>();
        map.put("", "Trombone whelk. Pitch nominal: B?");
        attribution.setTitle(map);
        map = new HashMap<>();
        map.put("", "Courtois nephew elder; #agent_CourtoisNephewElder");
        attribution.setCreator(map);
        map = new HashMap<>();
        map.put("", "1940; 1930");
        attribution.setDate(map);
        map = new HashMap<>();
        map.put("en", "University of Edinburgh");
        attribution.setProvider(map);
        attribution.setProviderUrl("http://www.mimo-db.eu/UEDIN/214");
        attribution.setCountry("europe, India");
        attribution.setRightsStatement("http://rightsstatements.org/vocab/InC-OW-EU/1.0/");
        attribution.setRightsLabel("In Copyright - EU Orphan Work");
        attribution.setRightsIcon(new String[]{"cc-by", "cc-to", "cc-me"});
        attribution.setCcDeprecatedOn(null);
        attribution.setLandingPage("https://www.europeana.eu/portal/record/142/UEDIN_214.html");
        return attribution;
    }

    @Test
    public void testHtmlSnippet() throws IOException {
       attributionSnippet.assembleHtmlSnippet(createAttr(), HTML_CSS_SOURCE);
       String htmlSnippet= attributionSnippet.getHtmlSnippet();
       InputStream stream = AttributionTest.class.getResourceAsStream(HTML_SNIPPET_FILE);
       String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
       Assert.assertTrue(!htmlSnippet.isEmpty());
       Assert.assertEquals(expectedOutput.length(), htmlSnippet.length());
       Assert.assertEquals(expectedOutput, htmlSnippet);
   }

    @Test
    public void testTextSnippet() throws IOException {
        attributionSnippet.assembleTextSnippet(createAttr());
        String textSnippet= attributionSnippet.getTextSnippet();
        InputStream stream = AttributionTest.class.getResourceAsStream(TEXT_SNIPPET_FILE);
        String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
        Assert.assertTrue(!textSnippet.isEmpty());
        Assert.assertEquals(expectedOutput.length(), textSnippet.length());
        Assert.assertEquals(expectedOutput, textSnippet);
    }

}
