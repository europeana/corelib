package eu.europeana.corelib.edm.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.ThreeDMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ChangeLogImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.PersistentIdentifierImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.metis.schema.jibx.ColorSpaceType;
import eu.europeana.metis.schema.jibx.IntendedUsage;
import eu.europeana.metis.schema.jibx.Language;
import eu.europeana.metis.schema.jibx.RDF;
import eu.europeana.metis.schema.jibx.SeeAlso;
import eu.europeana.metis.schema.jibx.Temporal;
import eu.europeana.metis.schema.jibx.WebResourceType;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * Tests both the EdmUtils and EdmWebResourceUtils classes.
 * Note that at the moment we only test a very limited part of both
 *
 * @author Patrick Ehlert
 * Created on 11-03-2019
 */
public class EdmUtilsTest {

    public static final String PID = "/pid.json";

    private static FullBeanImpl getMinimalFullBean() {
        FullBeanImpl bean = new FullBeanImpl();

//        bean.setAbout("/test/minimalbean_1");
//
//        bean.setProxies(new ArrayList<>());
//        ProxyImpl proxy = new ProxyImpl();
//        proxy.setAbout("/proxy/provider" + bean.getAbout());
//        proxy.setEuropeanaProxy(false);
//        proxy.setEdmType("IMAGE");
//        bean.setProxies(List.of(proxy));


        // EdmUtils code assumes there is always a EuropeanaAggregation
        // For marshalling to EDM, JIBX requires EuropeanaAggregation to have aggregatedCHO, edmCountry (with a proper
        // supported country) and language (also with proper value)
        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAggregatedCHO("/item/1234/test_5678"); // required
        europeanaAggregation.setEdmCountry(createSimpleHashMap("def", "Poland")); // required
        europeanaAggregation.setEdmLanguage(createSimpleHashMap("def", "pl")); // required
        europeanaAggregation.setWasGeneratedBy("http://data.europeana.eu/provenance/europeana");
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
        aggregation.setWasGeneratedBy("http://data.europeana.eu/provenance/europeana");
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
        RDF rdf = EdmUtils.toRDF(getMinimalFullBean());
        System.out.println(rdf);
        assertNotNull(rdf);
    }

    @Test
    public void testToEdmMinimalBean() {
        try (StringWriter writer = new StringWriter()) {
            EdmUtils.toEDM(getMinimalFullBean(), writer, true);
            assertNotNull(writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToRdfTombstoneBean() {
        RDF rdf = EdmUtils.toRDF(getTombstoneFullBean());
        assertNotNull(rdf);
    }

    @Test
    public void testToEdmTombstoneBean() {
        try (StringWriter writer = new StringWriter()) {
            EdmUtils.toEDM(getTombstoneFullBean(), writer, true);
            assertNotNull(writer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if colorSpace information is converted to RDF properly
     */
    @Test
    public void testToRdfColorSpace() {
        FullBeanImpl bean = getMinimalFullBean();

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

        RDF rdf = EdmUtils.toRDF(bean);
        assertEquals(expected, rdf.getWebResourceList().get(0).getHasColorSpace().getHasColorSpace());

        // second we change to an unknown color space type
        imageInfo.setColorSpace("this is an unknown color for testing purposes");
        rdf = EdmUtils.toRDF(bean);
        assertNull(rdf.getWebResourceList().get(0).getHasColorSpace());

        // finally we change to an empty color space type
        imageInfo.setColorSpace(null);
        rdf = EdmUtils.toRDF(bean);
        assertNull(rdf.getWebResourceList().get(0).getHasColorSpace());
    }

    @Test
    public void testPID() throws IOException {
        FullBeanImpl bean = getPIDBean();
        // check all three 3 pids reference/literals are loaded
        assertEquals(3, bean.getProxies().getFirst().getPIDS().size());
        RDF rdf = EdmUtils.toRDF(bean);

        assertEquals(3, rdf.getProxyList().getFirst().getPidList().size()); // all three are added in proxies
        assertEquals(2, rdf.getPersistentIdentifierList().size()); // two PIDs (only reference ones are added)
        assertEquals("#pid_1", rdf.getPersistentIdentifierList().get(0).getAbout());
        assertEquals("#pid_3", rdf.getPersistentIdentifierList().get(1).getAbout());
    }

    private FullBeanImpl getPIDBean() throws IOException {
        FullBeanImpl bean = getMinimalFullBean();
        ObjectMapper mapper = new ObjectMapper();
        List<PersistentIdentifierImpl> pids = mapper.readValue(
                getJsonStringInput(PID),
                mapper.getTypeFactory().constructCollectionType(List.class, PersistentIdentifierImpl.class));

        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout("/proxy/provider" + bean.getAbout());
        proxy.setEuropeanaProxy(false);
        proxy.setPIDS(pids);
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

  @Test
  public void testToRdf3DFields() {
      FullBeanImpl bean = getMinimalFullBean();
      bean.setAggregations(new ArrayList<>());
      AggregationImpl aggregation = new AggregationImpl();
      aggregation.setAbout("/aggregation/provider/2468/test_1357");
      bean.getAggregations().add(aggregation);

      WebResourceImpl webResource = getWebResourceWith3DInfo();

      List<WebResourceImpl> webResources = new ArrayList<>();
      webResources.add(webResource);
      bean.getAggregations().getFirst().setWebResources(webResources);

      RDF rdf = EdmUtils.toRDF(bean);
      WebResourceType wrResult = rdf.getWebResourceList().getFirst();

      assertWebResourceTypeWith3DInfo(wrResult);
  }

  private static void assertWebResourceTypeWith3DInfo(WebResourceType wrResult) {
    assertEquals("/2468/test_1357", wrResult.getAbout());
    assertEquals(4096L, wrResult.getPointCount().getInteger().longValue());
    assertEquals(2048L, wrResult.getPolygonCount().getInteger().longValue());
    assertEquals(8192L, wrResult.getVertexCount().getInteger().longValue());
    assertEquals(100L, wrResult.getGaussianCount().getInteger().longValue());
    assertArrayEquals(new String[]{"UK", "US"},
        wrResult.getLanguageList().stream().map(Language::getString).toArray());
    assertArrayEquals(new String[]{"2019-09-11T08:10:18.452Z", "2019-09-23T08:10:18.452Z"},
        wrResult.getTemporalList().stream().map(Temporal::getString).toArray());
    assertEquals("https://cv.iptc.org/newscodes/digitalsourcetype/digitalCapture",
        wrResult.getDigitalSourceType().getResource());
    assertArrayEquals(new String[]{"http://data.europeana.eu/vocabulary/usageArea/Knowledge"},
        wrResult.getIntendedUsageList().stream().map(IntendedUsage::getResource).toArray());
    assertArrayEquals(new String[]{"http://data_partner.org/the_paradata", "http://data_partner.org/the_metahuman"},
        wrResult.getSeeAlsoList().stream().map(SeeAlso::getResource).toArray());
  }

  private static WebResourceImpl getWebResourceWith3DInfo() {
      ThreeDMetaInfoImpl threeDInfo = new ThreeDMetaInfoImpl();
      threeDInfo.setFileSize(256L);
      threeDInfo.setPointCount(4096L);
      threeDInfo.setPolygonCount(2048L);
      threeDInfo.setVertexCount(8192L);
      threeDInfo.setGaussianCount(100L);
      threeDInfo.setMimeType("model/x.stl-ascii");

      WebResourceMetaInfoImpl wrThreeDInfo = new WebResourceMetaInfoImpl("test three d", null, null, null, null, threeDInfo);
      WebResourceImpl webResource = new WebResourceImpl();
      webResource.setAbout("/2468/test_1357");
      webResource.setDcLanguage(Map.of("en", List.of("UK", "US")));
      webResource.setDctermsTemporal(Map.of("en", List.of("2019-09-11T08:10:18.452Z", "2019-09-23T08:10:18.452Z")));
      webResource.setSchemaDigitalSourceType("https://cv.iptc.org/newscodes/digitalsourcetype/digitalCapture");
      webResource.setEdmIntendedUsage(new String[]{"http://data.europeana.eu/vocabulary/usageArea/Knowledge"});
      webResource.setRdfsSeeAlso(new String[]{"http://data_partner.org/the_paradata", "http://data_partner.org/the_metahuman"});
      webResource.setWebResourceMetaInfo(wrThreeDInfo);
      return webResource;
  }
}
