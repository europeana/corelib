package eu.europeana.corelib.solr.derived;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.solr.entity.AgentImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
    private static final String HTML_CSS_SOURCE = "https://style.europeana.eu/attribution/style.css";

    private static final String CREATOR_VALUE_1 = "http://repository.olympicmuseum-thessaloniki.org/vocabularies/organizations/index.php?tema=3";
    private static final String CREATOR_VALUE_2 = "http://repository.olympicmuseum-thessaloniki.org/vocabularies/olympic_creator/index.php?tema=test";
    private static final String CREATOR_VALUE_3 = "http://repository.olympicmuseum-thessaloniki.org/vocabularies/olympic_creator/index.php?tema=test1";
    private static final String CREATOR_VALUE_4 = "Europeana Creator test";
    private static final String CREATOR_VALUE_5 = "https://test-multiple-language-map";
    private static final String CREATOR_VALUE_6 = "Europeana -Creator test"; // to check duplicates with punctuation
    private static final String CREATOR_VALUE_7 = "http://europeana-test-empty-preflabel";
    private static final String CREATOR_VALUE_8 = "http://europeana-test-altlabel";

    private static final String CREATOR_VALUE_2_LABEL = "Weenenk";
    private static final String CREATOR_VALUE_3_LABEL = "Ολυμπιακό μουσείο Θεσσαλονίκης";
    private static final String CREATOR_VALUE_5_LABEL = "वीनेंक";

    private Map<String, List<String>> creatorMap;


    private static AttributionSnippet attributionSnippet;
    private static AttributionConverter attributionConverter;

    @Before
    public void setUp() {
        attributionSnippet = Mockito.spy(AttributionSnippet.class);
        attributionConverter = Mockito.spy(AttributionConverter.class);
        creatorMap = new HashMap<>();
    }

    private static Attribution createAttr() {
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

    private void mockCreatorMap(String value) {
        creatorMap.put("", new ArrayList<>());
        creatorMap.get("").add(CREATOR_VALUE_4);
        creatorMap.get("").add(CREATOR_VALUE_6);
        creatorMap.get("").add("#agent_CourtoisNephewElder");
        creatorMap.get("").add(value);
    }

    private static Attribution mockCreator(String creatorValue) {
        Attribution attribution = new Attribution();
        Map<String, String> map = new HashMap<>();
        map.put("", creatorValue);
        attribution.setCreator(map);
        return attribution;
    }

    public List<Agent> mockAgent() {
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
        agent.getPrefLabel().get("en").add("Olympic Museum"); //another en value. But it should pick only one
        agent.getPrefLabel().put("hn", new ArrayList<>());
        agent.getPrefLabel().get("hn").add("वीनेंक");

        //third agent: present in creator without english language
        agent = new AgentImpl();
        agents.add(agent);

        agent.setAbout(CREATOR_VALUE_3);
        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("el", new ArrayList<>());
        agent.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        agent.getPrefLabel().get("el").add("Olympic Museum"); //another el value. But it should pick only one


        //fouth agent with multiple other languages
        agent = new AgentImpl();
        agents.add(agent);

        agent.setAbout(CREATOR_VALUE_5);
        agent.setPrefLabel(new HashMap<>());
        agent.getPrefLabel().put("el", new ArrayList<>());
        agent.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        agent.getPrefLabel().get("el").add("Olympic Museum"); //another el value. But it should pick only one
        agent.getPrefLabel().put("hn", new ArrayList<>());
        agent.getPrefLabel().get("hn").add("वीनेंक");
        agent.getPrefLabel().get("hn").add("Olympic Museum"); //another hn value. But it should pick only one

        //fifth agent without preflabel
        agent = new AgentImpl();
        agents.add(agent);
        agent.setAbout(CREATOR_VALUE_7);

        //sixth agent with no preflabel but with altlabel
        agent = new AgentImpl();
        agents.add(agent);
        agent.setAbout(CREATOR_VALUE_8);
        agent.setAltLabel(new HashMap<>());
        agent.getAltLabel().put("def", new ArrayList<>());
        agent.getAltLabel().get("def").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        agent.getAltLabel().get("def").add("Olympic Museum");
        agent.getAltLabel().put("en", new ArrayList<>());
        agent.getAltLabel().get("en").add("Olympic Museum");
        agent.getAltLabel().get("en").add("वीनेंक");

        return agents;
    }

    @Test
    public void testHtmlSnippet() throws IOException {
        attributionSnippet.assembleHtmlSnippet(createAttr(), HTML_CSS_SOURCE);
        String htmlSnippet = attributionSnippet.getHtmlSnippet();
        InputStream stream = AttributionTest.class.getResourceAsStream(HTML_SNIPPET_FILE);
        String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
        Assert.assertTrue(!htmlSnippet.isEmpty());
        Assert.assertEquals(expectedOutput.length(), htmlSnippet.length());
        Assert.assertEquals(expectedOutput, htmlSnippet);
    }

    @Test
    public void testTextSnippet() throws IOException {
        attributionSnippet.assembleTextSnippet(createAttr());
        String textSnippet = attributionSnippet.getTextSnippet();
        InputStream stream = AttributionTest.class.getResourceAsStream(TEXT_SNIPPET_FILE);
        String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
        Assert.assertTrue(!textSnippet.isEmpty());
        Assert.assertEquals(expectedOutput.length(), textSnippet.length());
        Assert.assertEquals(expectedOutput, textSnippet);
    }

    // creator value is URI and it is NOT present in the agents. It should only contain the default value CREATOR_VALUE_4
    @Test
    public void testCreator_isUriWithNoAgents() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_1);
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().size() == 1);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
    }

    // creator value is URI and it is present in the agents. It should pick the "en" preflabel if present
    @Test
    public void testCreator_isUriWithAgentsWithEnglishLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_2);
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().size() == 1);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_2_LABEL));
    }

    // creator value is URI and it is present in the agents. It should pick the other language preflabel if present
    @Test
    public void testCreator_isUriWithAgentsWithNoEnglishLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_3);
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().size() == 1);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_3_LABEL));
    }

    // creator value is URI and it is present in the agents. It should pick the first language present in the prefLabel
    @Test
    public void testCreator_isUriWithAgentsWithMultipleOtherLanguage() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_5);
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_5_LABEL));
        Assert.assertTrue(attribution.getCreator().size() == 1);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
    }

    // empty prefLabel and altLabel check
    @Test
    public void testCreator_isUriWithAgentsWithNoPrefLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_7);
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().size() == 1);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
    }

    // empty prefLabel but altLabel available
    @Test
    public void testCreator_isUriWithAgentsWithAltLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_8);
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().size() == 1);
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().get("").contains("Olympic Museum"));
    }

    // creator map empty check
    @Test
    public void test_emptyCreatorMap() throws IOException {
        Attribution attribution = new Attribution();
        Assert.assertTrue(creatorMap.isEmpty());
        attributionConverter.checkCreatorLabel(attribution, mockAgent(), creatorMap);
        Assert.assertTrue(attribution.getCreator().isEmpty());
    }


}
