package eu.europeana.corelib.edm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import eu.europeana.corelib.edm.model.schemaorg.CreativeWork;
import eu.europeana.corelib.edm.model.schemaorg.SchemaOrgConstants;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import eu.europeana.corelib.edm.model.schemaorg.ContextualEntity;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-web-context.xml", "/corelib-web-test.xml"})
public class SchemaOrgUtilsTest {

    private static final String FULL_BEAN_FILE = "/schemaorg/fullbean.json";
    private static final String EDMORGANIZATION_FILE = "/schemaorg/edmorganization.json";
    private static final String DATEFORMATCHECK_FILE = "/schemaorg/dateformat.json";

    /**
     * Test schema.org generation and serialization
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void toSchemaOrgTest() throws IOException, URISyntaxException {
        FullBeanImpl bean = MockFullBean.mock();
        String output = SchemaOrgUtils.toSchemaOrg(bean);
        Assert.assertNotNull(output);
        //Used for testing purposes, use the following call to update the expected output whenever the serialization is updated
        //writeToFile(output);
        try (InputStream stream = getClass().getResourceAsStream(FULL_BEAN_FILE)) {
            String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
            //we cannot string compare until the ordering of properties is implemented
            //still, a fast indication that the output was changed will be indicated through the length of the string
            assertEquals(expectedOutput.length(), output.length());
        }
    }

    @Test
    public void toSchemaOrgEdmOrganizationTest() throws IOException {
        String output;
        ContextualClass organization = MockEdmOrganization.getEdmOrganization();
        ContextualEntity thingObject = SchemaOrgTypeFactory.createContextualEntity(organization);
        SchemaOrgUtils.processEntity(organization, thingObject);
        JsonLdSerializer serializer = new JsonLdSerializer();
        output = serializer.serialize(thingObject);
        Assert.assertNotNull(output);

        try (InputStream stream = getClass().getResourceAsStream(EDMORGANIZATION_FILE)) {
            String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
            assertEquals(expectedOutput.length(), output.length());
            // If fails, then the field order definition misses some fields
            assertEquals(expectedOutput, output);
        }
    }

    @Test
    public void testAddDateProperty() throws Exception {
        String output;
        FullBeanImpl bean = new FullBeanImpl();
        MockFullBean.setTimespans(bean);
        List<TimespanImpl> timespans = bean.getTimespans();
        CreativeWork object = new CreativeWork();

        //temporal coverage
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "1992-03-12"); //1
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "1824-1828"); //invalid
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "2002"); //2
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "18-02-1585"); //invalid
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "2017-07-26T01:00:00.000Z"); //3
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "http://semium.org/time/1977"); //4
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "http://semium.org/time/19xx"); //5
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "http://semium.org/time/1901"); //6
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "http://semium.org/time/1901"); //duplicate
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", "23 march"); //invalid
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, true, "def", "23 testing invalid"); //7
        SchemaOrgUtils.processDateValue(object, SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, timespans, false, "def", ""); //invalid

        assertNotNull(object.getTemporalCoverage());
        assertEquals(7, object.getTemporalCoverage().size());
        JsonLdSerializer serializer = new JsonLdSerializer();
        output = serializer.serialize(object);
        assertNotNull(output);
        try (InputStream stream = getClass().getResourceAsStream(DATEFORMATCHECK_FILE)) {
            String expectedOutput = IOUtils.toString(stream, StandardCharsets.UTF_8);
            assertEquals(expectedOutput, output);
        }
    }

    void writeToFile(String output) throws IOException, URISyntaxException {
        URI outputFilePath = getClass().getResource(FULL_BEAN_FILE).toURI();
        File outputFile = new File(outputFilePath);
        FileUtils.write(outputFile, output, StandardCharsets.UTF_8);
    }

}