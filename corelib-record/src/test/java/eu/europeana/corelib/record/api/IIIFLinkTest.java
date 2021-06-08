package eu.europeana.corelib.record.api;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class IIIFLinkTest {

    private static final String MANIFEST_BASE_URL = "iiif.europeana.eu";
    private static final String PRESENTATION_PATH = "/presentation";
    private static final String MANIFEST_PATH = "/manifest";
    private static final String API2_BASE_URL = "api-test.eanadev.org";

    /**
     * Test if generate the expected dctermsIsReferencedBy value for webresources that should get it
     */
    @Test
    public void testAddManifestNoApiBaseUrl() {
        String recordId = "/test/bean1";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        MockBeanUtils.addWebResource(fb,
                MockBeanUtils.generateWebResource("https://some.webresource.eu", "image/jpeg"));

        IIIFLink.addReferencedBy(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        assert(fb.getAggregations().get(0).getWebResources().size() > 0);
        for (WebResource wr : fb.getAggregations().get(0).getWebResources()) {
            assertEquals(MANIFEST_BASE_URL + PRESENTATION_PATH + recordId + MANIFEST_PATH, wr.getDctermsIsReferencedBy()[0]);
        }
    }

    /**
     * Test if generate the expected dctermsIsReferencedBy value for webresources that should get it, if we also
     * want the value to have an extra recordUrl= parameter with the api base url.
     */
    @Test
    public void testAddManifestWithApiBaseUrl() {
        String recordId = "/test/bean2";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        MockBeanUtils.addWebResource(fb,
                MockBeanUtils.generateWebResource("https://some.webresource.eu", "application/xhtml+xml"));

        IIIFLink.addReferencedBy(fb, true, API2_BASE_URL, MANIFEST_BASE_URL);
        assert(fb.getAggregations().get(0).getWebResources().size() > 0);
        for (WebResource wr : fb.getAggregations().get(0).getWebResources()) {
            assertEquals(MANIFEST_BASE_URL + PRESENTATION_PATH + recordId + MANIFEST_PATH + "?recordApi=https://"+ API2_BASE_URL,
                    wr.getDctermsIsReferencedBy()[0]);
        }
    }

    /**
     * Test if we don't add a dctermsIsReferencedBy value if there is one webresource with a mime-type that's in our blocked
     * mime-type list
     */
    @Test
    public void testAddManifestBlockedMimeType() {
        String recordId = "/test/bean3";

        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        MockBeanUtils.addWebResource(fb,
                MockBeanUtils.generateWebResource("https://some.webresource1.eu", "image/jpeg"));
        MockBeanUtils.addWebResource(fb,
                MockBeanUtils.generateWebResource("https://some.webresource2.eu", "application/pdf"));

        IIIFLink.addReferencedBy(fb, true, API2_BASE_URL, MANIFEST_BASE_URL);
        assert(fb.getAggregations().get(0).getWebResources().size() > 0);
        for (WebResource wr : fb.getAggregations().get(0).getWebResources()) {
            assertNull(wr.getDctermsIsReferencedBy());
        }
    }

    /**
     * Test if we don't add a dctermsIsReferencedBy value if a particular webresource already has a dcTermsIsReferenced
     * value
     */
    @Test
    public void testAddManifestAlreadyHasValue() {
        String recordId = "/test/bean4";
        String resourceId = "https://some.webresource.with.preset.value";
        String[] presetValue = new String[]{"Hi! I'm a dctermsReferenceByValue!"};


        FullBean fb = MockBeanUtils.mockMinimalBean(recordId);
        WebResourceImpl wri = MockBeanUtils.generateWebResource(resourceId, "video/mp4");
        wri.setDctermsIsReferencedBy(presetValue);
        MockBeanUtils.addWebResource(fb, wri);
        MockBeanUtils.addWebResource(fb,
                MockBeanUtils.generateWebResource("https://some.webresource2.eu", "image/jpeg"));

        IIIFLink.addReferencedBy(fb, false, API2_BASE_URL, MANIFEST_BASE_URL);
        assert(fb.getAggregations().get(0).getWebResources().size() > 0);
        for (WebResource wr : fb.getAggregations().get(0).getWebResources()) {
            String value = wr.getDctermsIsReferencedBy()[0];
            if (wr.getAbout().equals(resourceId)) {
                assertEquals(presetValue[0], value);
            } else {
                assertEquals(MANIFEST_BASE_URL + PRESENTATION_PATH + recordId + MANIFEST_PATH, value);
            }
        }
    }
}
