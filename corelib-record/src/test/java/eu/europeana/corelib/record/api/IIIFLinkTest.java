package eu.europeana.corelib.record.api;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IIIFLinkTest {

    private static final String MANIFEST_BASE_URL = "iiif.europeana.eu";
    private static final String PRESENTATION_PATH = "/presentation";
    private static final String MANIFEST_PATH = "/manifest";
    private static final String API2_BASE_URL = "api-test.eanadev.org";

    private String expectedIIIFUrl(String recordId) {
        return MANIFEST_BASE_URL + PRESENTATION_PATH + recordId + MANIFEST_PATH;
    }

    /**
     * Test if we generate the proper dctermsIsReferencedBy value for webresources that should get it (based on
     * edmIsShownBy)
     */
    @Test
    public void testAddManifestNoApiBaseUrl() {
        String recordId = "/test/bean1";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        String edmIsShownBy = fb.getAggregations().get(0).getEdmIsShownBy();
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource(edmIsShownBy, "image/jpeg"));

        IIIFLink.addReferencedByAndManifestResources(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);

        verifyManifestUrlAndResources(fb, 2, expectedIIIFUrl(recordId));
    }

    /**
     * Test if we generate the proper dctermsIsReferencedBy value for webresources that should get it (based on hasView),
     * if we also want the value to have an extra recordUrl= parameter with the api base url.
     */
    @Test
    public void testAddManifestWithApiBaseUrl() {
        String recordId = "/test/bean2";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        String id = "https://some.webresource.eu";
        fb.getAggregations().get(0).setHasView(new String[] {id});
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource(id, "audio/vorbis"));

        IIIFLink.addReferencedByAndManifestResources(fb, true, API2_BASE_URL, MANIFEST_BASE_URL);
        verifyManifestUrlAndResources(fb, 2, expectedIIIFUrl(recordId) + "?recordApi=https://"+ API2_BASE_URL);
    }

    /**
     *  Test if we don't add a dcTermsIsReferencedBy value if the webresources are not edmIsShownBy or hasView
     */
    @Test
    public void testNotAddManifest() {
        String recordId = "/test/bean3";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        Aggregation agg = fb.getAggregations().get(0);
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource("I'm a webresource id", "image/jpeg"));
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource("Me too!", "video/mp4"));

        IIIFLink.addReferencedByAndManifestResources(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        assertNull(agg.getWebResources().get(0).getDctermsIsReferencedBy());
        assertNull(agg.getWebResources().get(1).getDctermsIsReferencedBy());
        assertEquals(2, agg.getWebResources().size());
    }

    /**
     * Test if we don't add any dcTermsIsReferencedBy value for invalid mimetypes web resources
     */
    @Test
    public void testNotAddManifestBlockedMimeType() {
        String recordId = "/test/bean4";
        String hasViewId = "https://some.webresource.eu";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        Aggregation agg = fb.getAggregations().get(0);
        String edmIsShownBy = fb.getAggregations().get(0).getEdmIsShownBy();
        agg.setHasView(new String[] {hasViewId});
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource(edmIsShownBy, "image/jpeg"));
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource(hasViewId, "text/html"));

        IIIFLink.addReferencedByAndManifestResources(fb, true, API2_BASE_URL, MANIFEST_BASE_URL);
        assertEquals(3, agg.getWebResources().size());
        checkManifestLink(agg.getWebResources(), edmIsShownBy, true);
        checkManifestLink(agg.getWebResources(), hasViewId, false);
    }


    /**
     * Test if we don't add any dctermsIsReferencedBy values if a particular webresource already has a dcTermsIsReferenced
     * value
     */
    @Test
    public void testNotAddManifestAlreadyHasNonEuropeanaValue() {
        String recordId = "/test/bean5";
        String hasViewId = "https://some.webresource.eu";
        String[] presetValue = new String[]{"Hi! I'm a dctermsReferenceByValue from a provider!"};

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        Aggregation agg = fb.getAggregations().get(0);
        String edmIsShownBy = agg.getEdmIsShownBy();
        agg.setHasView(new String[] {hasViewId});
        // create 2 resources, one for edmIsShownBy and 1 for hasView
        MockBeanUtils.addWebResource(fb, MockBeanUtils.generateWebResource(edmIsShownBy, "video/mp4"));
        WebResourceImpl wri = MockBeanUtils.generateWebResource(hasViewId, "image/jpeg");
        wri.setDctermsIsReferencedBy(presetValue);
        MockBeanUtils.addWebResource(fb, wri);

        IIIFLink.addReferencedByAndManifestResources(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        assertEquals(2, agg.getWebResources().size());
        for (WebResource wr : agg.getWebResources()) {
            String[] value = wr.getDctermsIsReferencedBy();
            if (wr.getAbout().equals(hasViewId)) {
                assertEquals(presetValue[0], value[0]);
            } else {
                assertNull(value);
            }
        }
    }

    /**
     * Test if we overwrite a dcTermsIsReferencedBy value if it starts with http(s)://iiif.europeana.eu
     */
    @Test
    public void testAddManifestAlreadyHasEuropeanaValue() {
        String recordId = "/test/bean6";
        String edmIsShownBy = "https://some.webresource.with.preset.value";
        String[] presetValue = new String[]{"https://iiif.europeana.eu/Hi! I'm a preset invalid url. I will be replaced"};

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        Aggregation agg = fb.getAggregations().get(0);
        agg.setEdmIsShownBy(edmIsShownBy);
        WebResourceImpl wri = MockBeanUtils.generateWebResource(edmIsShownBy, "video/mp4");
        wri.setDctermsIsReferencedBy(presetValue);
        MockBeanUtils.addWebResource(fb, wri);

        IIIFLink.addReferencedByAndManifestResources(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        verifyManifestUrlAndResources(fb, 2, expectedIIIFUrl(recordId));
    }

    /**
     * Test if we add a dcTermsIsReferencedBy value for EU screen audio or video items
     */
    @Test
    public void testAddManifestEuScreen() {
        String recordId = "/test/bean6";
        String edmIsShownAt = "http://www.euscreen.eu/some/resource";

        // first we try with a non A/V item
        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        fb.getProxies().get(0).setEdmType("IMAGE");
        Aggregation agg = fb.getAggregations().get(0);
        agg.setEdmIsShownAt(edmIsShownAt);
        agg.setEdmIsShownBy(null);
        WebResourceImpl wri = MockBeanUtils.generateWebResource(edmIsShownAt, "video/mp4");
        MockBeanUtils.addWebResource(fb, wri);

        IIIFLink.addReferencedByAndManifestResources(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        assertNull(agg.getWebResources().get(0).getDctermsIsReferencedBy());

        // now we try again as an A/V item
        fb.getProxies().get(0).setEdmType("VIDEO");
        IIIFLink.addReferencedByAndManifestResources(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        verifyManifestUrlAndResources(fb, 2, expectedIIIFUrl(recordId));
    }

    private void verifyManifestUrlAndResources(FullBean fb, int expectedSize, String expectedIIIFUrl) {
        // check the size of the web resources = existing + newly added manifest resources
        assertEquals(expectedSize, fb.getAggregations().get(0).getWebResources().size());

        // Verify if the existing web resources is updated with DctermsIsReferencedBy value
        // and manifest web resource is added with expectedIIIFUrl
        for (WebResource wr : fb.getAggregations().get(0).getWebResources()) {
            if (StringUtils.equals(wr.getRdfType(), IIIFLink.MANIFEST_RDF_TYPE) && wr.getDctermsIsReferencedBy() == null) {
                assertEquals(expectedIIIFUrl, wr.getAbout());
            } else {
                assertEquals(expectedIIIFUrl, wr.getDctermsIsReferencedBy()[0]);
            }
        }
    }

    private void checkManifestLink(List<? extends WebResource> webResourceList, String wrToCheck, boolean manifestLinkPresent) {
        for (WebResource wr : webResourceList) {
            if(StringUtils.equals(wr.getAbout(), wrToCheck)) {
                if (manifestLinkPresent) {
                    assertNotNull(wr.getDctermsIsReferencedBy());
                } else {
                    assertNull(wr.getDctermsIsReferencedBy());
                }
            }
        }
    }
}
