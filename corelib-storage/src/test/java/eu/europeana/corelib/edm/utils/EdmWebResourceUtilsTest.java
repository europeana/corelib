package eu.europeana.corelib.edm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import eu.europeana.corelib.definitions.edm.model.metainfo.ImageOrientation;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.ThreeDMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.metis.schema.jibx.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdmWebResourceUtilsTest {

    @Test
    public void testCreateWebResourcesWithEmpty() {
        // Given
        RDF rdf = new RDF();
        AggregationImpl aggregation = new AggregationImpl();
        aggregation.setWebResources(new ArrayList<>());

        // When
        EdmWebResourceUtils.createWebResources(rdf, aggregation, true);

        // Then
        assertNotNull(rdf.getWebResourceList());
        assertTrue(rdf.getWebResourceList().isEmpty());
    }

    @Test
    public void testCreateWebResourcesWithExisting() {
        // Given
        RDF rdf = new RDF();
        List<WebResourceType> existingResources = new ArrayList<>();
        WebResourceType existing = new WebResourceType();
        existing.setAbout("existing");
        List<IsRepresentationOf> existingIsRepresentationOfList = new ArrayList<>();
        existingIsRepresentationOfList.add(new IsRepresentationOf());
        existingIsRepresentationOfList.get(0).setResource("existingRepresentation1");
        existing.setIsRepresentationOfList(existingIsRepresentationOfList);
        existingResources.add(existing);
        rdf.setWebResourceList(existingResources);

        AggregationImpl aggregation = new AggregationImpl();
        WebResourceImpl webResource = new WebResourceImpl();
        webResource.setAbout("new");
        webResource.setEdmIsRepresentationOf(Set.of("representation1", "representation2").toArray(new String[0]));
        aggregation.setWebResources(Collections.singletonList(webResource));

        // When
        EdmWebResourceUtils.createWebResources(rdf, aggregation, true);

        // Then
        assertEquals(2, rdf.getWebResourceList().size());
        assertEquals(Set.of("existing", "new"),
            rdf.getWebResourceList()
               .stream()
               .map(AboutType::getAbout)
               .collect(Collectors.toSet()));
        List<IsRepresentationOf> isRepresentationOfList =
            rdf.getWebResourceList()
               .stream()
                .map(WebResourceType::getIsRepresentationOfList)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        assertEquals(Set.of("existingRepresentation1", "representation1", "representation2"),
            isRepresentationOfList.stream()
                                  .map(ResourceType::getResource)
                                  .collect(Collectors.toSet()));
    }

    @Test
    public void testCreateWebResourcesMapping() {
        // Given
        RDF rdf = new RDF();
        AggregationImpl aggregation = new AggregationImpl();
        
        WebResourceImpl webResource = new WebResourceImpl();
        webResource.setAbout("http://example.com/res1");
        
        Map<String, List<String>> dcDescription = new HashMap<>();
        dcDescription.put("en", Collections.singletonList("Description"));
        webResource.setDcDescription(dcDescription);
        
        webResource.setRdfType("http://example.com/type");
        webResource.setSvcsHasService(new String[]{"http://example.com/service"});
        webResource.setDctermsIsReferencedBy(new String[]{"http://example.com/ref", "literal ref"});

        WebResourceMetaInfoImpl webResourceMetaInfo = new WebResourceMetaInfoImpl();
        ImageMetaInfoImpl imageMetaInfo = new ImageMetaInfoImpl();
        imageMetaInfo.setWidth(1920);
        imageMetaInfo.setHeight(1080);
        imageMetaInfo.setMimeType("image/jpeg");
        imageMetaInfo.setFileSize(1024L);
        imageMetaInfo.setColorSpace("sRGB");
        imageMetaInfo.setOrientation(ImageOrientation.LANDSCAPE);
        imageMetaInfo.setColorPalette(new String[]{"FFFFFF"});
        webResourceMetaInfo.setImageMetaInfo(imageMetaInfo);

        ThreeDMetaInfoImpl threeDMetaInfo = new ThreeDMetaInfoImpl();
        threeDMetaInfo.setPointCount(200L);
        threeDMetaInfo.setPolygonCount(50L);
        threeDMetaInfo.setVertexCount(200L);
        threeDMetaInfo.setGaussianCount(100L);
        webResourceMetaInfo.setThreeDMetaInfo(threeDMetaInfo);
        webResource.setWebResourceMetaInfo(webResourceMetaInfo);
        aggregation.setWebResources(Collections.singletonList(webResource));

        // When
        EdmWebResourceUtils.createWebResources(rdf, aggregation, true);

        // Then
        assertEquals(1, rdf.getWebResourceList().size());
        WebResourceType webResourceType = rdf.getWebResourceList().get(0);
        
        assertEquals("http://example.com/res1", webResourceType.getAbout());
        assertEquals(1, webResourceType.getDescriptionList().size());
        assertEquals("Description", webResourceType.getDescriptionList().get(0).getString());
        
        assertNotNull(webResourceType.getType());
        assertEquals("http://example.com/type", webResourceType.getType().getResource());
        
        assertEquals(1, webResourceType.getHasServiceList().size());
        assertEquals("http://example.com/service", webResourceType.getHasServiceList().get(0).getResource());
        
        assertEquals(2, webResourceType.getIsReferencedByList().size());
        assertEquals("http://example.com/ref", webResourceType.getIsReferencedByList().get(0).getResource().getResource());
        assertEquals("literal ref", webResourceType.getIsReferencedByList().get(1).getString());
        
        assertEquals(1, webResourceType.getComponentColorList().size());
        assertEquals("FFFFFF", webResourceType.getComponentColorList().get(0).getString());
        
        assertEquals("landscape", webResourceType.getOrientation().getString());
        assertNotNull(webResourceType.getHasColorSpace());
        assertEquals(1080L, webResourceType.getHeight().getLong());
        assertEquals(1920L, webResourceType.getWidth().getLong());
        assertEquals(1024L, webResourceType.getFileByteSize().getLong());
        assertEquals("image/jpeg", webResourceType.getHasMimeType().getHasMimeType());
        assertEquals(200L, webResourceType.getPointCount().getInteger().longValue());
        assertEquals(50L, webResourceType.getPolygonCount().getInteger().longValue());
        assertEquals(200L, webResourceType.getVertexCount().getInteger().longValue());
        assertEquals(100L, webResourceType.getGaussianCount().getInteger().longValue());
    }

    @Test
    public void testCreateWebResourcesDisabledPreserveIdentifiers() {
        // Given
        RDF rdf = new RDF();
        AggregationImpl aggregation = new AggregationImpl();
        WebResourceImpl webResource = new WebResourceImpl();
        webResource.setAbout("http://example.com/res1");
        webResource.setIsNextInSequence("/item/example/type");
        aggregation.setWebResources(Collections.singletonList(webResource));

        // When
        EdmWebResourceUtils.createWebResources(rdf, aggregation, false);

        // Then
        assertEquals(1, rdf.getWebResourceList().size());
        WebResourceType webResourceType = rdf.getWebResourceList().get(0);
        assertNotNull(webResourceType.getIsNextInSequence());
        assertEquals("http://example.com/res1", webResourceType.getAbout());
        assertEquals("http://data.europeana.eu/item/example/type", webResourceType.getIsNextInSequence().getResource());
    }
}
