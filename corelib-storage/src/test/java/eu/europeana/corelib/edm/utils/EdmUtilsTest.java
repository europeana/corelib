package eu.europeana.corelib.edm.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europeana.corelib.definitions.edm.entity.PersistentIdentifier;
import eu.europeana.corelib.solr.entity.*;
import eu.europeana.metis.schema.jibx.ColorSpaceType;
import eu.europeana.metis.schema.jibx.RDF;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests both the EdmUtils and EdmWebResourceUtils classes.
 * Note that at the moment we only test a very limited part of both
 *
 * @author Patrick Ehlert
 * Created on 11-03-2019
 */
public class EdmUtilsTest {

    public static final String PID = "/pid.json";
    private static FullBeanImpl minimalFullBean = getMinimalFullBean();

    private static FullBeanImpl getMinimalFullBean() {
        FullBeanImpl bean = new FullBeanImpl();

        // EdmUtils code assumes there is always a EuropeanaAggregation
        // For marshalling to EDM, JIBX requires EuropeanaAggregation to have aggregatedCHO, edmCountry (with a proper
        // supported country) and language (also with proper value)
        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAggregatedCHO("/item/1234/test_5678"); // required
        europeanaAggregation.setEdmCountry(createSimpleHashMap("def", "Poland")); // required
        europeanaAggregation.setEdmLanguage(createSimpleHashMap("def", "pl")); // required
        bean.setEuropeanaAggregation(europeanaAggregation);

        return bean;
    }

    private static FullBeanImpl getTombstoneFullBean() {
        FullBeanImpl bean = getMinimalFullBean();

        // A tombstone record typically has about, provider aggregation, europeana aggregation with changelog,
        // europeanaCompleteness and provider proxy
        String about = "/test/tombstone";
        bean.setAbout(about);

        ChangeLogImpl changeLog = new ChangeLogImpl();
        changeLog.setType("Delete");
        changeLog.setContext("http://data.europeana.eu/vocabulary/depublicationReason/sourceRemoval");
        changeLog.setEndTime(new Date(1729502835000L));
        bean.getEuropeanaAggregation().setChangeLog(List.of(changeLog));
        bean.getEuropeanaAggregation().setEdmPreview("https://mymuseum.org/images/pretty-picture.jpg");
        bean.getEuropeanaAggregation().setEdmLandingPage("https://www.europeana.eu/item/test/tombstone");

        // For marshalling to EDM, JIBX requires aggregations to have aggregatedCHO, edmProvider, edmRights
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setAbout("/aggregation/provider" + about);
        aggregation.setAggregatedCHO("/item/1234/test_5678"); // required, not empty or space
        aggregation.setEdmIsShownBy("https://mymuseum.org/images/pretty-picture.jpg");
        aggregation.setEdmIsShownAt("https://mymuseum.org/images/pretty-picture.jpg");
        aggregation.setEdmObject("https://mymuseum.org/images/pretty-picture.jpg");
        aggregation.setEdmProvider(createSimpleHashMap("def", "http://data.europeana.eu/organization/1234")); // required, map with at least 1 entry
        aggregation.setEdmDataProvider(createSimpleHashMap("def", "http://data.europeana.eu/organization/5678"));
        aggregation.setEdmRights(createSimpleHashMap("def", "Open")); // required, map with at least 1 entry
        bean.setAggregations(List.of(aggregation));

        // For marshalling to EDM, JIBX requires proxies to have edmType
        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout("/proxy/provider" + about);
        proxy.setDcIdentifier(createSimpleHashMap("def", "myId"));
        proxy.setDcRights(createSimpleHashMap("def", "Open"));
        proxy.setDcTitle(createSimpleHashMap("nl","Dit is een title"));
        proxy.setEdmType("IMAGE"); // required
        proxy.setEuropeanaProxy(false);
        bean.setProxies(List.of(proxy));

        bean.setEuropeanaCompleteness(0);
        bean.setTimestampCreated(new Date());
        bean.setTimestampUpdated(new Date());

        return bean;
    }

    private static void appendPIDInBean() {

    }

    private static HashMap createSimpleHashMap(String key, String value) {
        HashMap<String, List<String>> map = new HashMap<>();
        map.put(key, List.of(value));
        return map;
    }

    @Test
    public void testToRdfMinimalBean() {
        RDF rdf = EdmUtils.toRDF(minimalFullBean);
        assertNotNull(rdf);
    }

    @Test
    public void testToEdmMinimalBean() {
        String edmOut = EdmUtils.toEDM(minimalFullBean);
        assertNotNull(edmOut);
    }

    @Test
    public void testToRdfTombstoneBean() {
        RDF rdf = EdmUtils.toRDF(getTombstoneFullBean());
        assertNotNull(rdf);
    }

    @Test
    public void testToEdmTombstoneBean() {
        String edmOut = EdmUtils.toEDM(getTombstoneFullBean());
        assertNotNull(edmOut);
    }

    /**
     * Test if colorSpace information is converted to RDF properly
     */
    @Test
    public void testToRdfColorSpace() {
        FullBeanImpl bean = minimalFullBean;

        bean.setAggregations(new ArrayList<>());
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setAbout("/aggregation/provider/1234/test_5678");
        bean.getAggregations().add(aggregation);

        // first we create a bean with a webresource with a 'normal' color space type
        ColorSpaceType expected = ColorSpaceType.LC_HAB;

        ImageMetaInfoImpl imageInfo = new ImageMetaInfoImpl();
        imageInfo.setColorSpace(expected.xmlValue());

        WebResourceMetaInfoImpl wrInfo = new WebResourceMetaInfoImpl("test color space", imageInfo, null, null, null, null);

        WebResourceImpl webResource = new WebResourceImpl();
        webResource.setWebResourceMetaInfo(wrInfo);

        List<WebResourceImpl> webResources = new ArrayList<>();
        webResources.add(webResource);
        bean.getAggregations().get(0).setWebResources(webResources);

        RDF rdf = EdmUtils.toRDF(minimalFullBean);
        assertEquals(expected, rdf.getWebResourceList().get(0).getHasColorSpace().getHasColorSpace());

        // second we change to an unknown color space type
        imageInfo.setColorSpace("this is an unknown color for testing purposes");
        rdf = EdmUtils.toRDF(minimalFullBean);
        assertNull(rdf.getWebResourceList().get(0).getHasColorSpace());

        // finally we change to an empty color space type
        imageInfo.setColorSpace(null);
        rdf = EdmUtils.toRDF(minimalFullBean);
        assertNull(rdf.getWebResourceList().get(0).getHasColorSpace());
    }

    @Test
    public void testPID() throws IOException {
        FullBeanImpl bean = getPIDBean();
        RDF rdf = EdmUtils.toRDF(bean);
        System.out.println(rdf);
    }


    private FullBeanImpl getPIDBean() throws IOException {
        FullBeanImpl bean = minimalFullBean;
        ObjectMapper mapper = new ObjectMapper();
        List<PersistentIdentifierImpl> pids = mapper.readValue(
                getJsonStringInput(PID),
                mapper.getTypeFactory().constructCollectionType(List.class, PersistentIdentifierImpl.class));

        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout("/proxy/provider" + bean.getAbout());
        proxy.setEuropeanaProxy(false);
        proxy.setPID(pids);
        bean.setProxies(List.of(proxy));
        return bean;
    }


    /**
     * This method extracts JSON content from a file
     *
     * @param resource
     * @return JSON string
     * @throws IOException
     */
    protected String getJsonStringInput(String resource) throws IOException {

        try (InputStream resourceAsStream = getClass().getResourceAsStream(resource)) {
            List<String> lines = IOUtils.readLines(resourceAsStream, StandardCharsets.UTF_8);
            StringBuilder out = new StringBuilder();
            for (String line : lines) {
                out.append(line);
            }
            return out.toString();
        }
    }
}
