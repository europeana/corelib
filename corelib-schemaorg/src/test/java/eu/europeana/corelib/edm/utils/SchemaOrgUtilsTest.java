package eu.europeana.corelib.edm.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import eu.europeana.corelib.definitions.EuropeanaStaticUrl;
import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import eu.europeana.corelib.edm.model.schemaorg.*;
import eu.europeana.corelib.utils.DateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-web-context.xml", "/corelib-web-test.xml"})
public class SchemaOrgUtilsTest {

    private static final String   FULL_BEAN_FILE              = "/schemaorg/fullbean.json";
    private static final String   EDMORGANIZATION_FILE        = "/schemaorg/edmorganization.json";

    private static final String TEMPORAL_COVERAGE_TIMESPAN_1  = "1901-01-01T00:19:32/2000-12-31T01:00:00";
    private static final String TEMPORAL_COVERAGE_TIMESPAN_2  = "1901-01-01/1902-01-01";
    private static final String TIMESTAMP_CREATED_VALUE       = DateUtils.format(MockBeanConstants.TIMESTAMP_CREATED_DATE);
    private static final String VIDEO_OBJECT                  = "VideoObject";
    private static final String IMAGE_OBJECT                  = "ImageObject";

    /**
     * Test schema.org serialization
     *
     * @throws IOException
     */
    @Test
    public void toSchemaOrg_Serialization() throws IOException {
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

    /**
     * Test schema.org response each fields
     */
    @Test
    public void testSchemaOrg_Response() {
        FullBeanImpl bean = MockFullBean.mock();
        List<Thing> schemaResponse = new ArrayList<>();
        Thing object = SchemaOrgTypeFactory.createObject(bean);
        schemaResponse.add(object);
        SchemaOrgUtils.getSchemaOrg(bean, schemaResponse, object);

        int testedObjects = 0;
        checkMainObject(object);
        testedObjects++; // one object tested
        testedObjects += checkLinkedObjects(schemaResponse, object);

        // check if all objects are tested
        assertTrue("All objects are not tested", testedObjects == schemaResponse.size());
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

    private static void checkMainObject(Thing object) {
        // id, url, sameAs, thumbnailUrl
        assertEquals(object.getId(), MockBeanConstants.URL_PREFIX + MockBeanConstants.ABOUT);
        assertEquals(object.getUrl().get(0).toString(), EuropeanaStaticUrl.EUROPEANA_PORTAL_URL + MockBeanConstants.ABOUT);
        assertEquals(MockBeanConstants.EDM_IS_SHOWN_AT, object.getSameAs().get(0).toString());
        assertEquals(MockBeanConstants.EDM_PREVIEW, object.getProperty(SchemaOrgConstants.PROPERTY_THUMBNAIL_URL).get(0).toString());

        //about
        for (String about : SchemaOrgTestUtilities.getResourceOrReference(SchemaOrgConstants.PROPERTY_ABOUT, object)) {
            assertTrue(StringUtils.equals(about, MockBeanConstants.DC_TYPE_1) ||
                    StringUtils.equals(about, MockBeanConstants.DC_COVERAGE_ABOUT) ||
                    StringUtils.equals(about, MockBeanConstants.DC_TYPE_2));
        }
        //creator
        for (String creator : SchemaOrgTestUtilities.getResourceOrReference(SchemaOrgConstants.PROPERTY_CREATOR, object)) {
            assertTrue(StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_1) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_2) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_3) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_4) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_5) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_6) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_7) ||
                    StringUtils.equals(creator, MockBeanConstants.DC_CREATOR_8));
        }

        //dateCreated
        assertEquals(MockBeanConstants.DC_TERMS_CREATED, object.getProperty(SchemaOrgConstants.PROPERTY_DATE_CREATED).get(0).toString());

        //TemporalCoverage
        List<String> temporalCoverageList = new ArrayList<>();
        temporalCoverageList.addAll(SchemaOrgTestUtilities.getReference(SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, object));
        temporalCoverageList.addAll(SchemaOrgTestUtilities.getText(SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, object));
        for (String temporalCoverage : temporalCoverageList) {
            assertTrue(StringUtils.equals(MockBeanConstants.DC_COVERAGE_TEMPORAL, temporalCoverage) ||
                    StringUtils.equals(TEMPORAL_COVERAGE_TIMESPAN_1, temporalCoverage) ||
                    StringUtils.equals(TEMPORAL_COVERAGE_TIMESPAN_2, temporalCoverage) ||
                    StringUtils.equals(MockBeanConstants.DC_TERMS_TEMPORAL_4, temporalCoverage));
        }

        //provider
        for (String provider : SchemaOrgTestUtilities.getResourceOrReference(SchemaOrgConstants.PROPERTY_PROVIDER, object)) {
            assertTrue(StringUtils.equals( MockBeanConstants.EDM_PROVIDER_1, provider) ||
                    StringUtils.equals(MockBeanConstants.EDM_PROVIDER_2, provider));
        }

        //name
        assertTrue(StringUtils.equals(MockBeanConstants.DC_TITLE, SchemaOrgTestUtilities.getMultilingualString(SchemaOrgConstants.PROPERTY_NAME, object).get(0)));

        //description
        assertTrue(StringUtils.equals( MockBeanConstants.DC_DESCRIPTION, SchemaOrgTestUtilities.getMultilingualString(SchemaOrgConstants.PROPERTY_DESCRIPTION, object).get(0)));

        //alternate name
        assertTrue(StringUtils.equals(MockBeanConstants.DC_ALTERNATIVE, SchemaOrgTestUtilities.getMultilingualString(SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, object).get(0)));
    }

    private static int checkLinkedObjects(List<Thing> schemaResponse, Thing object) {
        int linkedObjectsTested =0;
        //associated media
        List<String> associatedMedia = SchemaOrgTestUtilities.getReference(SchemaOrgConstants.PROPERTY_ASSOCIATED_MEDIA, object);
        assertTrue(associatedMedia.size() == 2);

        int i = associatedMedia.size();
        int j = 0;
        for (Thing thing : schemaResponse) {
            //check the associated media objects : Only checking basic properties
            if (j < i) {
                if (StringUtils.equals(thing.getId(), associatedMedia.get(j))) {
                    if (StringUtils.equals(thing.getTypeName(), VIDEO_OBJECT)) {
                        //encoding format
                        assertEquals(MockBeanConstants.MIME_TYPE_VIDEO, thing.getProperty(SchemaOrgConstants.PROPERTY_ENCODING_FORMAT).get(0).toString());
                        //thumbnailUrl
                        assertEquals(MockBeanConstants.EDM_PREVIEW, thing.getProperty(SchemaOrgConstants.PROPERTY_THUMBNAIL_URL).get(0).toString());
                        //uploadDate
                        assertEquals(TIMESTAMP_CREATED_VALUE, thing.getProperty(SchemaOrgConstants.PROPERTY_UPLOAD_DATE).get(0).toString());
                    }
                    //encoding format for image
                    if (StringUtils.equals(thing.getTypeName(), IMAGE_OBJECT)) {
                        assertEquals(MockBeanConstants.MIME_TYPE_IMAGE, thing.getProperty(SchemaOrgConstants.PROPERTY_ENCODING_FORMAT).get(0).toString());
                    }
                    //contentUrl
                    assertEquals(thing.getProperty(SchemaOrgConstants.PROPERTY_CONTENT_URL).get(0).toString(), thing.getId());
                    j++;
                    linkedObjectsTested++;
                }

            } else {
                //remaining will be contextual entities. Will check error prone fields only
                //Note: SchemaOrg only contains Contextual Entities which are linked
                for (int k = 0; k < SchemaOrgUtils.linkedContextualEntities.size(); k++) {
                    if (StringUtils.equals(SchemaOrgUtils.linkedContextualEntities.get(k), thing.getId())) {
                        //agent Person check
                        if (StringUtils.equals(thing.getTypeName(), SchemaOrgTestUtilities.PERSON)) {
                            //id
                            assertEquals(MockBeanConstants.DC_CREATOR_6, thing.getId());
                            //birthDate
                            assertEquals(MockBeanConstants.BIRTH_DATE, thing.getProperty(SchemaOrgConstants.PROPERTY_BIRTH_DATE).get(0).toString());
                            //deathDate
                            assertEquals(MockBeanConstants.DEATH_DATE, thing.getProperty(SchemaOrgConstants.PROPERTY_DEATH_DATE).get(0).toString());
                            linkedObjectsTested++;

                        }
                        // Organization check
                        if (StringUtils.equals(thing.getTypeName(), SchemaOrgTestUtilities.ORANGANIZATION)) {
                            //id
                            assertEquals(MockBeanConstants.DC_CREATOR_8, thing.getId());
                            //dissolutionDate
                            assertEquals(MockBeanConstants.DISOLUTION_DATE, thing.getProperty(SchemaOrgConstants.PROPERTY_DISSOLUTION_DATE).get(0).toString());
                            linkedObjectsTested++;
                        }
                        //Place Object check
                        if (StringUtils.equals(thing.getTypeName(), SchemaOrgTestUtilities.PLACE)) {
                            //id
                            assertEquals( MockBeanConstants.DC_CREATOR_7, thing.getId());
                            //description
                            assertEquals( MockBeanConstants.PLACE_NOTE, SchemaOrgTestUtilities.getMultilingualString(SchemaOrgConstants.PROPERTY_DESCRIPTION, thing).get(0));
                            linkedObjectsTested++;

                        }
                        //Concept object
                        if (StringUtils.equals(thing.getTypeName(), SchemaOrgTestUtilities.THING)) {
                            //id
                            assertEquals(MockBeanConstants.DC_CREATOR_4, thing.getId());
                            //description
                            for (String description : SchemaOrgTestUtilities.getMultilingualString(SchemaOrgConstants.PROPERTY_DESCRIPTION, thing)) {
                                assertTrue(StringUtils.equals(description, MockBeanConstants.CONCEPT_NOTE_1) ||
                                        StringUtils.equals(description, MockBeanConstants.CONCEPT_NOTE_2));
                            }
                            linkedObjectsTested++;
                        }
                    }
                }
            }
        }
        return linkedObjectsTested;
    }

    void writeToFile(String output) throws IOException, URISyntaxException {
        URI outputFilePath = getClass().getResource(FULL_BEAN_FILE).toURI();
        File outputFile = new File(outputFilePath);
        FileUtils.write(outputFile, output, StandardCharsets.UTF_8);
    }

}