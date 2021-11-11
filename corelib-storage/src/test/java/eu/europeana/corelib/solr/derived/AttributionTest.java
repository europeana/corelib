package eu.europeana.corelib.solr.derived;

import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import eu.europeana.corelib.solr.entity.ContextualClassImpl;
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

    private static final String LANGAWARE_MAP_VALUE_1 = "Default Value";
    private static final String LANGAWARE_MAP_VALUE_2 = "English";
    private static final String LANGAWARE_MAP_VALUE_3 = "Other-English-lang";
    private static final String LANGAWARE_MAP_VALUE_4 = "Dutch";
    private static final String LANGAWARE_MAP_VALUE_5 = "Hindi";

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

    private List<? extends ContextualClass> mockEntity() {
        List<ContextualClass> entity = new ArrayList<>();
        // initiate based on entityType for now we check two type Agent and organisations
        ContextualClass object = new ContextualClassImpl();
        entity.add(object);
        //first entity : not present in uri maps
        object.setAbout("http://repository.olympicmuseum-not-present");
        object.setPrefLabel(new HashMap<>());
        object.getPrefLabel().put("en", new ArrayList<>());
        object.getPrefLabel().get("en").add("Olympic Museum of Thessaloniki");
        object.getPrefLabel().put("el", new ArrayList<>());
        object.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");

        //second entity : with two language preflabel.
        object = new ContextualClassImpl();
        entity.add(object);

        object.setAbout(CREATOR_VALUE_2);
        object.setPrefLabel(new HashMap<>());
        object.getPrefLabel().put("en", new ArrayList<>());
        object.getPrefLabel().get("en").add("Weenenk");
        object.getPrefLabel().get("en").add("Olympic Museum"); //another en value. But it should pick only one
        object.getPrefLabel().put("hn", new ArrayList<>());
        object.getPrefLabel().get("hn").add("वीनेंक");

        //third entity:  without english language
        object = new ContextualClassImpl();
        entity.add(object);

        object.setAbout(CREATOR_VALUE_3);
        object.setPrefLabel(new HashMap<>());
        object.getPrefLabel().put("el", new ArrayList<>());
        object.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        object.getPrefLabel().get("el").add("Olympic Museum"); //another el value. But it should pick only one

        //fouth entity with multiple other languages
        object = new ContextualClassImpl();
        entity.add(object);

        object.setAbout(CREATOR_VALUE_5);
        object.setPrefLabel(new HashMap<>());
        object.getPrefLabel().put("el", new ArrayList<>());
        object.getPrefLabel().get("el").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        object.getPrefLabel().get("el").add("Olympic Museum"); //another el value. But it should pick only one
        object.getPrefLabel().put("hn", new ArrayList<>());
        object.getPrefLabel().get("hn").add("वीनेंक");
        object.getPrefLabel().get("hn").add("Olympic Museum"); //another hn value. But it should pick only one

        //fifth entity without preflabel
        object = new ContextualClassImpl();
        entity.add(object);
        object.setAbout(CREATOR_VALUE_7);

        //sixth agent with no preflabel but with altlabel
        object = new ContextualClassImpl();
        entity.add(object);
        object.setAbout(CREATOR_VALUE_8);
        object.setAltLabel(new HashMap<>());
        object.getAltLabel().put("def", new ArrayList<>());
        object.getAltLabel().get("def").add("Ολυμπιακό μουσείο Θεσσαλονίκης");
        object.getAltLabel().get("def").add("Olympic Museum");
        object.getAltLabel().put("en", new ArrayList<>());
        object.getAltLabel().get("en").add("Olympic Museum");
        object.getAltLabel().get("en").add("वीनेंक");

        return entity;
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
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertEquals(1, attribution.getCreator().size());
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
    }

    // creator value is URI and it is present in the agents. It should pick the "en" preflabel if present
    @Test
    public void testCreator_isUriWithAgentsWithEnglishLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_2);
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertEquals(1, attribution.getCreator().size());
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_2_LABEL));
    }

    // creator value is URI and it is present in the agents. It should pick the other language preflabel if present
    @Test
    public void testCreator_isUriWithAgentsWithNoEnglishLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_3);
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertEquals(1, attribution.getCreator().size());
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_3_LABEL));
    }

    // creator value is URI and it is present in the agents. It should pick the first language present in the prefLabel
    @Test
    public void testCreator_isUriWithAgentsWithMultipleOtherLanguage() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_5);
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_5_LABEL));
        Assert.assertEquals(1, attribution.getCreator().size());
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
    }

    // empty prefLabel and altLabel check
    @Test
    public void testCreator_isUriWithAgentsWithNoPrefLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_7);
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertEquals(1, attribution.getCreator().size());
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
    }

    // empty prefLabel but altLabel available
    @Test
    public void testCreator_isUriWithAgentsWithAltLabel() throws IOException {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_8);
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertEquals(1, attribution.getCreator().size());
        Assert.assertTrue(attribution.getCreator().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getCreator().get("").contains("Olympic Museum"));
    }

    // creator map empty check
    @Test
    public void test_emptyCreatorMap() throws IOException {
        Attribution attribution = new Attribution();
        Assert.assertTrue(creatorMap.isEmpty());
        attribution.setCreator(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, null));
        Assert.assertTrue(attribution.getCreator().isEmpty());
    }

    @Test
    public void test_createCreatorTitleMap() {
        Map<String, List<String>> resultMap ;

        Map<String, List<String>> langAwareMap = new HashMap<>();
        List<String > valueList = new ArrayList<>();
        //adding def
        valueList.add(LANGAWARE_MAP_VALUE_1);
        valueList.add("https://urivalue");
        valueList.add(LANGAWARE_MAP_VALUE_2);
        langAwareMap.put("def", valueList);
        resultMap = attributionConverter.createCreatorTitleMap(langAwareMap);

        Assert.assertTrue(! resultMap.isEmpty());
        Assert.assertTrue(resultMap.get("").contains(LANGAWARE_MAP_VALUE_1) && resultMap.get("").contains(LANGAWARE_MAP_VALUE_2) && resultMap.get("").contains("https://urivalue"));

        //add other language "de"
        valueList = new ArrayList<>();
        valueList.add(LANGAWARE_MAP_VALUE_4); //unique
        valueList.add(LANGAWARE_MAP_VALUE_4); //duplicate
        valueList.add(LANGAWARE_MAP_VALUE_2); // already present in def
        langAwareMap.put("de", valueList);
        resultMap.clear();
        resultMap = attributionConverter.createCreatorTitleMap(langAwareMap);

        Assert.assertTrue(resultMap.containsKey("de"));
        Assert.assertTrue(resultMap.get("de").contains(LANGAWARE_MAP_VALUE_4));

        // adding other language starting with "en".
        valueList = new ArrayList<>();
        valueList.add(LANGAWARE_MAP_VALUE_2); // already present in result map in def
        valueList.add(LANGAWARE_MAP_VALUE_5); // unique value
        valueList.add(LANGAWARE_MAP_VALUE_3); // unique value
        langAwareMap.put("en-gb", valueList);
        resultMap.clear();
        resultMap = attributionConverter.createCreatorTitleMap(langAwareMap);

        Assert.assertTrue(resultMap.containsKey("en-gb"));
        Assert.assertTrue(resultMap.get("en-gb").contains(LANGAWARE_MAP_VALUE_3) && resultMap.get("en-gb").contains(LANGAWARE_MAP_VALUE_5));

        //adding "en"
        valueList = new ArrayList<>();
        valueList.add(LANGAWARE_MAP_VALUE_2); //value already present in resultMap in def
        valueList.add(LANGAWARE_MAP_VALUE_2); // duplicate
        valueList.add(LANGAWARE_MAP_VALUE_5); //unique value
        langAwareMap.put("en", valueList);
        resultMap.clear();
        resultMap = attributionConverter.createCreatorTitleMap(langAwareMap);

        Assert.assertTrue(resultMap.containsKey("en"));
        Assert.assertTrue(resultMap.get("en").contains(LANGAWARE_MAP_VALUE_5));
    }

    @Test
    public void test_OrganisationLabelWithEdmLang() {
        Attribution attribution = new Attribution();
        mockCreatorMap(CREATOR_VALUE_2);
        attribution.setProvider(attributionConverter.checkLabelInMaps(mockEntity(), creatorMap, "hn"));
        Assert.assertEquals(1, attribution.getProvider().size());
        Assert.assertTrue(attribution.getProvider().get("").contains(CREATOR_VALUE_4));
        Assert.assertTrue(attribution.getProvider().get("").contains(CREATOR_VALUE_5_LABEL));
    }
}
