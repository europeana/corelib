package eu.europeana.corelib.edm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import eu.europeana.corelib.edm.model.schemaorg.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml"})
public class SchemaOrgUtilsTest {

    static String FULL_BEAN_FILE                = "/schemaorg/fullbean.json";
    static String PROVIDEDCHO_FILE              = "/schemaorg/providedCHO.json";
    static String PROXIES_FILE                  = "/schemaorg/proxies.json";
    static String EUROPEANA_AGGREGATION_FILE    = "/schemaorg/europeanaAggregation.json";
    static String PLACES_FILE                   = "/schemaorg/places.json";
    static String EDMORGANIZATION_FILE          = "/schemaorg/edmorganization.json";
    static String CONCEPT_FILE                  = "/schemaorg/concept.json";
    /**
     * Test schema.org generation and serialization
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void toSchemaOrgTest() throws IOException, URISyntaxException {
        FullBeanImpl bean = MockFullBean.mock();
        String output = SchemaOrgUtils.toSchemaOrg(bean);
        Assert.assertNotNull(output);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
//        writeToFile(output);
        String expectedOutput = getResponseFromFile(FULL_BEAN_FILE);
        //we cannot string compare until the ordering of properties is implemented
        //still, a fast indication that the output was changed will be indicated through the length of the string
        assertEquals(expectedOutput.length(), output.length());
    }

    @Test
    public void toSchemaOrgEdmOrganizationTest() throws IOException {
        String output;
        ContextualClass organization = MockEdmOrganization.getEdmOrganization();
        ContextualEntity thingObject = SchemaOrgTypeFactory.createContextualEntity(organization);
        SchemaOrgUtils.processEntity(organization, thingObject);
        output = serialize(thingObject);
        Assert.assertNotNull(output);

        String expectedOutput = getResponseFromFile(EDMORGANIZATION_FILE);
        assertEquals(expectedOutput.length(), output.length());
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_MAIN_ENTITY_OF_PAGE));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_NAME));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_ALTERNATE_NAME));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_DESCRIPTION));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_LOGO));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_ADDRESS));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_IDENTIFIER));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_SAME_AS));

        // If fails, then the field order definition misses some fields
        assertEquals(expectedOutput, output);
    }

    @Test
    public void processProvidedCHOTest() throws Exception {
        FullBeanImpl bean = MockFullBean.mockProvidedCHO();
        Thing object = createObject(bean);
        SchemaOrgUtils.processProvidedCHO((CreativeWork)object, bean);
        String output = serialize(object);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
        //writeToFile(output);
        //check if the output is equal and has all the properties of schema.org for ProvidedCho
        String expectedOutput = getResponseFromFile(PROVIDEDCHO_FILE);
        assertEquals(output.length(), expectedOutput.length());
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_THUMBNAIL_URL));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_URL));
    }

    @Test
    public void processProxiesTest() throws Exception {
        FullBeanImpl bean = MockFullBean.mockProxies();
        Thing object = createObject(bean);
        SchemaOrgUtils.processProxies((CreativeWork)object, bean);
        String output = serialize(object);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
        //writeToFile(output);

        //check if the output is equal and has all the properties of schema.org for Proxies
        String expectedOutput = getResponseFromFile(PROXIES_FILE);
        assertEquals(output.length(), expectedOutput.length());
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_CONTRIBUTOR));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_ABOUT));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_CREATOR));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_DESCRIPTION));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_DATE_CREATED));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE));
    }

    @Test
    public void processEuropeanaAggregation() throws Exception {
        FullBeanImpl bean = MockFullBean.mockAggregation();
        Thing object = createObject(bean);
        List<Thing> list = new ArrayList<>();
        JsonLdSerializer serializer = new JsonLdSerializer();
        list.addAll(SchemaOrgUtils.processAggregations((CreativeWork)object, bean));
        String output = serializer.serialize(list);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
        //writeToFile(output);

        //check if the output is equal and has all the properties of schema.org for aggregation
        String expectedOutput = getResponseFromFile(EUROPEANA_AGGREGATION_FILE);
        assertEquals(output.length(), expectedOutput.length());
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_CONTENT_URL));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_LICENSE));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_HEIGHT));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_WIDTH));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_ENCODES_CREATIVE_WORK));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_ENCODING_FORMAT));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_CONTENT_SIZE));
    }

    @Test
    public void processPlaces() throws Exception {
        FullBeanImpl bean = MockFullBean.mockPlaces();
        Place placeObject = new Place();
        SchemaOrgUtils.processPlace(bean.getPlaces().get(0), placeObject);
        String output = serialize(placeObject);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
        //writeToFile(output);

        //check if the output is equal and has all the properties of schema.org for aggregation
        String expectedOutput = getResponseFromFile(PLACES_FILE);
        assertEquals(output.length(), expectedOutput.length());
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.TYPE_GEO_COORDINATES));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_LATITUDE));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_LONGITUDE));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_CONTAINS_PLACE));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE));
    }

    @Test
    public void processConcept() throws Exception {
        FullBeanImpl bean = MockFullBean.mockConcept();
        Concept conceptObject = new Concept();
        SchemaOrgUtils.processConcept(bean.getConcepts().get(0), conceptObject);
        String output = serialize(conceptObject);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
        //writeToFile(output);

        //check if the output is equal and has all the properties of schema.org for aggregation
        String expectedOutput = getResponseFromFile(CONCEPT_FILE);
        assertEquals(output.length(), expectedOutput.length());
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_NAME));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_DESCRIPTION));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_SAME_AS));
        assertTrue(StringUtils.contains(output, SchemaOrgConstants.PROPERTY_URL));
    }

    void writeToFile(String output, String fileName) throws IOException, URISyntaxException {
        URI outputFilePath = getClass().getResource(fileName).toURI();
        File outputFile = new File(outputFilePath);
        FileUtils.write(outputFile, output, StandardCharsets.UTF_8);
    }

    private  String serialize(Thing object) throws IOException {
        JsonLdSerializer serializer = new JsonLdSerializer();
        return serializer.serialize(object);
    }

    private Thing createObject(FullBeanImpl bean) {
        return SchemaOrgTypeFactory.createObject(bean);
    }

    public String getResponseFromFile(String fileName) throws IOException {
        try(InputStream stream = getClass().getResourceAsStream(fileName)) {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
    }
}