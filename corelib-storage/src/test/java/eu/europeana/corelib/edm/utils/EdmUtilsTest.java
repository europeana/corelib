package eu.europeana.corelib.edm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import eu.europeana.metis.schema.jibx.ColorSpaceType;
import eu.europeana.metis.schema.jibx.RDF;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests both the EdmUtils and EdmWebResourceUtils classes.
 * Note that at the moment we only test a very limited part of both
 *
 * @author Patrick Ehlert
 * Created on 11-03-2019
 */
public class EdmUtilsTest {


    // TODO at the moment there are many places in the code where we assume at least certain data is present
    // 2019-03-11 below is a minimal fullbean that should work for conversion to RDF
    private static FullBeanImpl minimalFullBean = getMinimalFullBeanMock();
    static FullBeanImpl getMinimalFullBeanMock() {
        FullBeanImpl bean = new FullBeanImpl();

        bean.setProxies(new ArrayList<>());
        ProxyImpl proxy = new ProxyImpl();
        proxy.setAbout("/proxy/provider/1234/test_5678");
        proxy.setProxyFor("/item/1234/test_5678");
        proxy.setEdmType(DocType.IMAGE.getEnumNameValue());
        proxy.setProxyIn(new String [] {"/aggregation/provider/1234/test_5678"});
        bean.getProxies().add(proxy);

        bean.setProvidedCHOs(new ArrayList<>());
        ProvidedCHOImpl providedCHO = new ProvidedCHOImpl();
        providedCHO.setAbout("/item/1234/test_5678");
        bean.getProvidedCHOs().add(providedCHO);

        bean.setAggregations(new ArrayList<>());
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setAbout("/aggregation/provider/1234/test_5678");
        bean.getAggregations().add(aggregation);

        List<WebResourceImpl> webResources = new ArrayList<>();
        aggregation.setWebResources(webResources);

        EuropeanaAggregationImpl europeanaAggregation = new EuropeanaAggregationImpl();
        europeanaAggregation.setAbout("/aggregation/europeana/1234/test_5678");
        bean.setEuropeanaAggregation(europeanaAggregation);

        bean.setEuropeanaCollectionName(new String[] { "1234_test"});

        return bean;
    }

    @Test
    public void testToRdfMinimalBean() {
        RDF rdf = EdmUtils.toRDF(minimalFullBean);
        assertNotNull(rdf);
    }

    /**
     * Test if colorSpace information is converted to RDF properly
     */
    @Test
    public void testToRdfColorSpace() {
        // first we create a bean with a webresource with a 'normal' color space type
        ColorSpaceType expected = ColorSpaceType.LC_HAB;
        FullBeanImpl bean = minimalFullBean;

        ImageMetaInfoImpl imageInfo = new ImageMetaInfoImpl();
        imageInfo.setColorSpace(expected.xmlValue());

        WebResourceMetaInfoImpl wrInfo = new WebResourceMetaInfoImpl("test color space", imageInfo, null, null, null);

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
}
