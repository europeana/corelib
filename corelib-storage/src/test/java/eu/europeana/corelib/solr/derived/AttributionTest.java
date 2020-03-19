package eu.europeana.corelib.solr.derived;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributionTest {

    private static final String HTML_SNIPPET_FILE = "/htmlsnippet.txt";
    private static final String TEXT_SNIPPET_FILE = "/textsnippet.txt";
    private static final String HTML_CSS_SOURCE   = "https://style.europeana.eu/attribution/style.css";

    private static final String CREATOR_VALUE_1  = "http://repository.olympicmuseum-thessaloniki.org/vocabularies/organizations/index.php?tema=3";
    private static final String CREATOR_VALUE_2   = "http://repository.olympicmuseum-thessaloniki.org/vocabularies/olympic_creator/index.php?tema=test";
    private static final String CREATOR_VALUE_3   = "http://repository.olympicmuseum-thessaloniki.org/vocabularies/olympic_creator/index.php?tema=test1";
    private static final String CREATOR_VALUE_4   = "Europeana Creator test";
    private static final String CREATOR_VALUE_5   = "https://test-multiple-language-map";

    private static final String CREATOR_VALUE_2_LABEL  = "Weenenk";
    private static final String CREATOR_VALUE_3_LABEL  = "Ολυμπιακό μουσείο Θεσσαλονίκης";
    private static final String CREATOR_VALUE_5_LABEL  = "वीनेंक";


    private static AttributionSnippet attributionSnippet;
    private static AttributionConverter attributionConverter;

    @Before
    public void setUp(){
        attributionSnippet = Mockito.spy(AttributionSnippet.class);
        attributionConverter = Mockito.spy(AttributionConverter.class);
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
        map = new HashMap<>();
        map.put("", "Europe, India");
        attribution.setCountry(map);
        attribution.setRightsStatement("http://rightsstatements.org/vocab/InC-OW-EU/1.0/");
        attribution.setRightsLabel("In Copyright - EU Orphan Work");
        attribution.setRightsIcon(new String[]{"cc-by", "cc-to", "cc-me"});
        attribution.setCcDeprecatedOn(null);
        attribution.setLandingPage("https://www.europeana.eu/portal/record/142/UEDIN_214.html");
        return attribution;
    }

    private static Attribution mockCreator(String creatorValue) {
        Attribution attribution = new Attribution();
        Map<String, String> map = new HashMap<>();
        map.put("", creatorValue);
        attribution.setCreator(map);
        return attribution;
   }
       public List<Agent>  mockAgent() {
        List<Agent> agents = new ArrayList<>();
        AgentImpl agent = new AgentImpl();
        agents.add(agent);
        //first agent : not present in creator
        agent.setAbout("http://repository.olympicmuseum-not-present");
        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("en", new ArrayList<>());
        agent.getPrefLabel().get("en").add("Olympic Museum of Thessaloniki");
        agent.getPrefLabel().put("el", new ArrayList<>());
        agent.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");

        //second agent : present in creator with two langusge preflabel.
        agent = new AgentImpl();
        agents.add(agent);

        agent.setAbout(CREATOR_VALUE_2);
        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("en", new ArrayList<>());
        agent.getPrefLabel().get("en").add("Weenenk");
        agent.getPrefLabel().put("hn", new ArrayList<>());
        agent.getPrefLabel().get("hn").add("वीनेंक");

        //third agent: present in creator without english language
        agent = new AgentImpl();
        agents.add(agent);

        agent.setAbout(CREATOR_VALUE_3);
        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("el", new ArrayList<>());
        agent.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");

        //fouth agent with multiple other languages
        agent = new AgentImpl();
        agents.add(agent);

        agent.setAbout(CREATOR_VALUE_5);
        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("el", new ArrayList<>());
        agent.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        agent.getPrefLabel().put("hn", new ArrayList<>());
        agent.getPrefLabel().get("hn").add("वीनेंक");

           return agents;
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

    // creator value is URI and it is NOT present in the agents. hence it should not create a Creator Tag (attribution.getCreator should be empty)
    @Test
    public void testCreator_isUriWithNoAgents() throws IOException {
        Attribution attribution= mockCreator(CREATOR_VALUE_1);
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_1));
        attributionConverter.checkCreatorLabel(attribution, mockAgent());
        Assert.assertTrue(attribution.getCreator().isEmpty());
    }

    // creator value is URI and it is present in the agents. It should pick the "en" preflabel if present
    @Test
    public void testCreator_isUriWithAgentsWithEnglishLabel() throws IOException {
        Attribution attribution= mockCreator(CREATOR_VALUE_2);
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_2));
        attributionConverter.checkCreatorLabel(attribution, mockAgent());
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_2_LABEL));
        Assert.assertTrue(attribution.getCreator().size() == 1);
    }

    // creator value is URI and it is present in the agents. It should pick the other language preflabel if present
    @Test
    public void testCreator_isUriWithAgentsWithNoEnglishLabel() throws IOException {
        Attribution attribution= mockCreator(CREATOR_VALUE_3);
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_3));
        attributionConverter.checkCreatorLabel(attribution, mockAgent());
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_3_LABEL));
        Assert.assertTrue(attribution.getCreator().size() == 1);

    }

    // creator value is URI and it is present in the agents. It should pick the first language present in the prefLabel
    @Test
    public void testCreator_isUriWithAgentsWithMultipleOtherLanguage() throws IOException {
        Attribution attribution= mockCreator(CREATOR_VALUE_5);
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_5));
        attributionConverter.checkCreatorLabel(attribution, mockAgent());
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_5_LABEL));
        Assert.assertTrue(attribution.getCreator().size() == 1);
    }

    // creator value is not URI. It should not modify the attribution.getCreator()
    @Test
    public void testCreator_isNotURI() throws IOException {
        Attribution attribution= mockCreator(CREATOR_VALUE_4);
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_4));
        attributionConverter.checkCreatorLabel(attribution, mockAgent());
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().size() == 1);

    }

    // test multiple creator value. To test if there is any Java concurrent exception in the code or any other exception.
    @Test
    public void testCreator_multipleCreator() throws IOException {
        Attribution attribution = new Attribution();
        Map<String, String> map = new HashMap<>();
        map.put("1", CREATOR_VALUE_1);  // not present in agent
        map.put("2", CREATOR_VALUE_2);  // present in agent with english label
        map.put("3", CREATOR_VALUE_3);  //present in agent with NO english label
        map.put("", CREATOR_VALUE_4); // Not a URI present with "" key
        map.put("5", CREATOR_VALUE_5); //present in agent with multiple language label
        attribution.setCreator(map);
        attributionConverter.checkCreatorLabel(attribution, mockAgent());
        Assert.assertTrue(!attribution.getCreator().containsValue(CREATOR_VALUE_1));
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_2_LABEL));
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_3_LABEL));
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().containsValue(CREATOR_VALUE_5_LABEL));
        Assert.assertTrue(attribution.getCreator().size() == 4);

    }
}
