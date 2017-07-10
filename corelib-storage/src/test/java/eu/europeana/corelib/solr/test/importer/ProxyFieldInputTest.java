package eu.europeana.corelib.solr.test.importer;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.ProxyFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class ProxyFieldInputTest {

    @Test
    public void testProxy() throws IOException {
        // The fields of the proxy come from the ProvidedCHO
        ProxyType proxy = createProxyFields();
        testMongo(proxy);
        testSolr(proxy);
    }

    private void testSolr(ProxyType proxy) throws IOException {
        SolrInputDocument solrDocument = new SolrInputDocument();
        try {
            solrDocument = new ProxyFieldInput().createProxySolrFields(proxy, solrDocument);
            assertEquals(proxy.getAbout(), solrDocument.getFieldValue(EdmLabel.ORE_PROXY.toString()));
            assertEquals(proxy.getCurrentLocation().getString(),
                    solrDocument.getFieldValue(EdmLabel.PROXY_EDM_CURRENT_LOCATION.toString()));
            assertEquals(
                    proxy.getIsNextInSequenceList().get(0).getResource(),
                    solrDocument.getFieldValue(EdmLabel.PROXY_EDM_IS_NEXT_IN_SEQUENCE.toString()));
            assertEquals(proxy.getType().getType().toString(),
                    solrDocument.getFieldValue(EdmLabel.PROVIDER_EDM_TYPE.toString()));
            List<EuropeanaType.Choice> dcterms = proxy.getChoiceList();
            for (EuropeanaType.Choice choice : dcterms) {
                if (choice.ifAlternative())
                    assertEquals(
                            choice.getAlternative().getString(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_ALTERNATIVE.toString()));
                if (choice.ifConformsTo())
                    assertEquals(
                            choice.getConformsTo().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_CONFORMS_TO.toString()));
                if (choice.ifCreated())
                    assertEquals(choice.getCreated().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_CREATED.toString()));
                if (choice.ifExtent())
                    assertEquals(choice.getExtent().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_EXTENT.toString()));
                if (choice.ifHasFormat())
                    assertEquals(
                            choice.getHasFormat().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_HAS_FORMAT.toString()));
                if (choice.ifHasPart())
                    assertEquals(choice.getHasPart().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_HAS_PART.toString()));
                if (choice.ifHasVersion())
                    assertEquals(
                            choice.getHasVersion().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_HAS_VERSION.toString()));
                if (choice.ifIsFormatOf())
                    assertEquals(
                            choice.getIsFormatOf().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_FORMAT_OF.toString()));
                if (choice.ifIsPartOf())
                    assertEquals(
                            choice.getIsPartOf().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_PART_OF.toString()));
                if (choice.ifIsReferencedBy())
                    assertEquals(
                            choice.getIsReferencedBy().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_REFERENCED_BY.toString()));
                if (choice.ifIsReplacedBy())
                    assertEquals(
                            choice.getIsReplacedBy().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_REPLACED_BY.toString()));
                if (choice.ifIsRequiredBy())
                    assertEquals(
                            choice.getIsRequiredBy().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_REQUIRED_BY.toString()));
                if (choice.ifIssued())
                    assertEquals(choice.getIssued().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_ISSUED.toString()));
                if (choice.ifIsVersionOf())
                    assertEquals(
                            choice.getIsVersionOf().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_VERSION_OF
                                    .toString()));
                if (choice.ifMedium())
                    assertEquals(choice.getMedium().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_MEDIUM.toString()));
                if (choice.ifProvenance())
                    assertEquals(
                            choice.getProvenance().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_PROVENANCE.toString()));
                if (choice.ifReferences())
                    assertEquals(
                            choice.getReferences().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_REFERENCES.toString()));
                if (choice.ifReplaces())
                    assertEquals(choice.getReplaces().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_REPLACES.toString()));
                if (choice.ifRequires())
                    assertEquals(choice.getRequires().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_REQUIRES.toString()));
                if (choice.ifSpatial())
                    assertEquals(choice.getSpatial().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_SPATIAL.toString()));
                if (choice.ifTableOfContents())
                    assertEquals(
                            choice.getTableOfContents().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_TABLE_OF_CONTENTS.toString()));
                if (choice.ifTemporal())
                    assertEquals(choice.getTemporal().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DCTERMS_TEMPORAL.toString()));
                if (choice.ifContributor())
                    assertEquals(choice.getContributor().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_CONTRIBUTOR.toString()));
                if (choice.ifCoverage())
                    assertEquals(choice.getCoverage().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_COVERAGE.toString()));
                if (choice.ifCreator())
                    assertEquals(choice.getCreator().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_CREATOR.toString()));
                if (choice.ifDate())
                    assertEquals(choice.getDate().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_DATE.toString()));
                if (choice.ifDescription())
                    assertEquals(choice.getDescription().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_DESCRIPTION.toString()));
                if (choice.ifFormat())
                    assertEquals(choice.getFormat().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_FORMAT.toString()));
                if (choice.ifIdentifier())
                    assertEquals(choice.getIdentifier().getString(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_IDENTIFIER.toString()));
                if (choice.ifLanguage())
                    assertEquals(choice.getLanguage().getString(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_LANGUAGE.toString()));
                if (choice.ifPublisher())
                    assertEquals(choice.getPublisher().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_PUBLISHER.toString()));
                if (choice.ifRelation())
                    assertEquals(choice.getRelation().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_RELATION.toString()));
                if (choice.ifRights())
                    assertEquals(choice.getRights().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_RIGHTS.toString()));
                if (choice.ifSource())
                    assertEquals(choice.getSource().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_SOURCE.toString()));
                if (choice.ifSubject())
                    assertEquals(choice.getSubject().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_SUBJECT.toString()));
                if (choice.ifTitle())
                    assertEquals(choice.getTitle().getString(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_TITLE.toString()));
                if (choice.ifType())
                    assertEquals(choice.getType().getResource().getResource(),
                            solrDocument.getFieldValue(EdmLabel.PROXY_DC_TYPE.toString()));

            }
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void testMongo(ProxyType proxy) {

        ProxyImpl mongoProxy = new ProxyImpl();
        mongoProxy.setAbout(proxy.getAbout());

        EdmMongoServer mongoServerMock = mock(EdmMongoServer.class);
        Datastore datastoreMock = mock(Datastore.class);
        Query queryMock = mock(Query.class);

        when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
        when(datastoreMock.find(ProxyImpl.class)).thenReturn(queryMock);
        when(datastoreMock.save(mongoProxy)).thenReturn(null);
        when(queryMock.filter("about", proxy.getAbout())).thenReturn(queryMock);
        when(queryMock.get()).thenReturn(null);

        try {
            mongoProxy = new ProxyFieldInput().createProxyMongoFields(
                    mongoProxy, proxy);
            mongoServerMock.getDatastore().save(mongoProxy);
            assertEquals(proxy.getAbout(), mongoProxy.getAbout());
            assertEquals(proxy.getType().getType().toString(), mongoProxy.getEdmType()
                    .toString());
            assertEquals(proxy.getIsNextInSequenceList().size(),
                    mongoProxy.getEdmIsNextInSequence().length);
            //@TODO: Add actual content checking here
            List<EuropeanaType.Choice> dcterms = proxy.getChoiceList();
            for (EuropeanaType.Choice choice : dcterms) {
                if (choice.ifAlternative()) {
                    assertEquals(choice.getAlternative().getString(),
                            mongoProxy.getDctermsAlternative().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifConformsTo()) {
                    assertEquals(choice.getConformsTo().getResource().getResource(),
                            mongoProxy.getDctermsConformsTo().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifCreated()) {
                    assertEquals(choice.getCreated().getResource().getResource(), mongoProxy
                            .getDctermsCreated().values().iterator().next().get(0));
                }
                if (choice.ifExtent()) {
                    assertEquals(choice.getExtent().getResource().getResource(), mongoProxy
                            .getDctermsExtent().values().iterator().next().get(0));
                }
                if (choice.ifHasFormat()) {
                    assertEquals(choice.getHasFormat().getResource().getResource(),
                            mongoProxy.getDctermsHasFormat().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifHasPart()) {
                    assertEquals(choice.getHasPart().getResource().getResource(), mongoProxy
                            .getDctermsHasPart().values().iterator().next().get(0));
                }
                if (choice.ifHasVersion()) {
                    assertEquals(choice.getHasVersion().getResource().getResource(),
                            mongoProxy.getDctermsHasVersion().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifIsFormatOf()) {
                    assertEquals(choice.getIsFormatOf().getResource().getResource(),
                            mongoProxy.getDctermsIsFormatOf().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifIsPartOf()) {
                    assertEquals(choice.getIsPartOf().getResource().getResource(), mongoProxy
                            .getDctermsIsPartOf().values().iterator().next().get(0));
                }
                if (choice.ifIsReferencedBy()) {
                    assertEquals(choice.getIsReferencedBy().getResource().getResource(),
                            mongoProxy.getDctermsIsReferencedBy().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifIsReplacedBy()) {
                    assertEquals(choice.getIsReplacedBy().getResource().getResource(),
                            mongoProxy.getDctermsIsReplacedBy().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifIsRequiredBy()) {
                    assertEquals(choice.getIsRequiredBy().getResource().getResource(),
                            mongoProxy.getDctermsIsRequiredBy().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifIssued()) {
                    assertEquals(choice.getIssued().getResource().getResource(), mongoProxy
                            .getDctermsIssued().values().iterator().next().get(0));
                }
                if (choice.ifIsVersionOf()) {
                    assertEquals(choice.getIsVersionOf().getResource().getResource(),
                            mongoProxy.getDctermsIsVersionOf().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifMedium()) {
                    assertEquals(choice.getMedium().getResource().getResource(), mongoProxy
                            .getDctermsMedium().values().iterator().next().get(0));
                }
                if (choice.ifProvenance()) {
                    assertEquals(choice.getProvenance().getResource().getResource(),
                            mongoProxy.getDctermsProvenance().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifReferences()) {
                    assertEquals(choice.getReferences().getResource().getResource(),
                            mongoProxy.getDctermsReferences().values()
                                    .iterator().next().get(0));
                }
                if (choice.ifReplaces()) {
                    assertEquals(choice.getReplaces().getResource().getResource(), mongoProxy
                            .getDctermsReplaces().values().iterator().next().get(0));
                }
                if (choice.ifRequires()) {
                    assertEquals(choice.getRequires().getResource().getResource(), mongoProxy
                            .getDctermsRequires().values().iterator().next().get(0));
                }
                if (choice.ifSpatial()) {
                    assertEquals(choice.getSpatial().getResource().getResource(), mongoProxy
                            .getDctermsSpatial().values().iterator().next().get(0));
                }
                if (choice.ifTableOfContents()) {
                    assertEquals(choice.getTableOfContents().getResource().getResource(),
                            mongoProxy.getDctermsTOC().values().iterator()
                                    .next().get(0));
                }
                if (choice.ifTemporal()) {
                    assertEquals(choice.getTemporal().getResource().getResource(), mongoProxy
                            .getDctermsTemporal().values().iterator().next().get(0));
                }
                if (choice.ifContributor()) {
                    assertEquals(choice.getContributor().getResource().getResource(),
                            mongoProxy.getDcContributor().values().iterator()
                                    .next().get(0));
                }
                if (choice.ifCoverage()) {
                    assertEquals(choice.getCoverage().getResource().getResource(), mongoProxy
                            .getDcCoverage().values().iterator().next().get(0));
                }
                if (choice.ifCreator()) {
                    assertEquals(choice.getCreator().getResource().getResource(),
                            mongoProxy.getDcCreator().values().iterator().next().get(0));
                }
                if (choice.ifDate()) {
                    assertEquals(choice.getDate().getResource().getResource(), mongoProxy
                            .getDcDate().values().iterator().next().get(0));
                }
                if (choice.ifDescription()) {
                    assertEquals(choice.getDescription().getResource().getResource(),
                            mongoProxy.getDcDescription().values().iterator()
                                    .next().get(0));
                }
                if (choice.ifFormat()) {
                    assertEquals(choice.getFormat().getResource().getResource(),
                            mongoProxy.getDcFormat().values().iterator().next().get(0));
                }
                if (choice.ifIdentifier()) {
                    assertEquals(choice.getIdentifier().getString(),
                            mongoProxy.getDcIdentifier().values().iterator().next().get(0));
                }
                if (choice.ifLanguage()) {
                    assertEquals(choice.getLanguage().getString(),
                            mongoProxy.getDcLanguage().values().iterator().next().get(0));
                }
                if (choice.ifPublisher()) {
                    assertEquals(choice.getPublisher().getResource().getResource(),
                            mongoProxy.getDcPublisher().values().iterator().next().get(0));
                }
                if (choice.ifRelation()) {
                    assertEquals(choice.getRelation().getResource().getResource(),
                            mongoProxy.getDcRelation().values().iterator().next().get(0));
                }
                if (choice.ifRights()) {
                    assertEquals(choice.getRights().getResource().getResource(),
                            mongoProxy.getDcRights().values().iterator().next().get(0));
                }
                if (choice.ifSource()) {
                    assertEquals(choice.getSource().getResource().getResource(),
                            mongoProxy.getDcSource().values().iterator().next().get(0));
                }
                if (choice.ifSubject()) {
                    assertEquals(choice.getSubject().getResource().getResource(),
                            mongoProxy.getDcSubject().values().iterator().next().get(0));
                }
                if (choice.ifTitle()) {
                    assertEquals(choice.getTitle().getString(), mongoProxy
                            .getDcTitle().values().iterator().next().get(0));
                }
                if (choice.ifType()) {
                    assertEquals(choice.getType().getResource().getResource(), mongoProxy
                            .getDcType().values().iterator().next().get(0));
                }
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private ProxyType createProxyFields() {
        ProxyType proxy = new ProxyType();
        proxy.setAbout("test about");
        CurrentLocation currentLocation = new CurrentLocation();
        currentLocation.setString("test current location");
        IsNextInSequence isNextInSequence = new IsNextInSequence();
        isNextInSequence.setResource("test is next in sequence");
        List<IsNextInSequence> isNextInSequenceList = new ArrayList<>();
        isNextInSequenceList.add(isNextInSequence);
        proxy.setIsNextInSequenceList(isNextInSequenceList);
        EuropeanaProxy europeanaProxy = new EuropeanaProxy();
        europeanaProxy.setEuropeanaProxy(true);
        proxy.setEuropeanaProxy(europeanaProxy);
        proxy.setCurrentLocation(currentLocation);
        Type2 type = new Type2();
        type.setType(EdmType.IMAGE);
        proxy.setType(type);
        proxy.setChoiceList(createEuropeanaTermsList());
        ProxyFor pFor = new ProxyFor();
        pFor.setResource("test proxy for");

        proxy.setProxyFor(pFor);
        List<ProxyIn> pinList = new ArrayList<>();
        ProxyIn pin = new ProxyIn();
        pin.setResource("test proxy in");
        proxy.setProxyInList(pinList);
        return proxy;
    }

    private List<EuropeanaType.Choice> createEuropeanaTermsList() {
        List<EuropeanaType.Choice> dctermsList = new ArrayList<>();

        EuropeanaType.Choice choiceAlternative = new EuropeanaType.Choice();
        Alternative alternative = new Alternative();
        alternative.setString("test alternative");
        choiceAlternative.setAlternative(alternative);
        dctermsList.add(choiceAlternative);
        EuropeanaType.Choice choiceConformsTo = new EuropeanaType.Choice();
        ConformsTo conformsTo = new ConformsTo();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource conformsResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        conformsResource.setResource("test conforms to");
        conformsTo.setResource(conformsResource);
        choiceConformsTo.setConformsTo(conformsTo);
        dctermsList.add(choiceConformsTo);
        EuropeanaType.Choice choiceCreated = new EuropeanaType.Choice();

        Created created = new Created();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource createdResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        createdResource.setResource("test created");
        created.setResource(createdResource);
        choiceCreated.setCreated(created);
        dctermsList.add(choiceCreated);
        EuropeanaType.Choice choiceExtent = new EuropeanaType.Choice();

        Extent extent = new Extent();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource extentResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        extentResource.setResource("test extent");
        extent.setResource(extentResource);

        choiceExtent.setExtent(extent);
        dctermsList.add(choiceExtent);

        EuropeanaType.Choice choiceHasFormat = new EuropeanaType.Choice();
        HasFormat hasFormat = new HasFormat();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource choiceHasFormatResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        choiceHasFormatResource.setResource("test hasFormat");
        hasFormat.setResource(choiceHasFormatResource);

        choiceHasFormat.setHasFormat(hasFormat);
        dctermsList.add(choiceHasFormat);
        EuropeanaType.Choice choiceHasPart = new EuropeanaType.Choice();
        HasPart hasPart = new HasPart();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource hasPartResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        hasPartResource.setResource("test hasPart");
        hasPart.setResource(hasPartResource);
        choiceHasPart.setHasPart(hasPart);
        dctermsList.add(choiceHasPart);
        EuropeanaType.Choice choiceHasVersion = new EuropeanaType.Choice();

        HasVersion hasVersion = new HasVersion();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource hasVersionResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        hasVersionResource.setResource("test hasVersion");
        hasVersion.setResource(hasVersionResource);

        choiceHasVersion.setHasVersion(hasVersion);
        dctermsList.add(choiceHasVersion);
        EuropeanaType.Choice choiceIsFormatOf = new EuropeanaType.Choice();
        IsFormatOf isFormatOf = new IsFormatOf();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isFormatOfResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        isFormatOfResource.setResource("test isFormatOf");
        isFormatOf.setResource(isFormatOfResource);

        choiceIsFormatOf.setIsFormatOf(isFormatOf);
        dctermsList.add(choiceIsFormatOf);
        EuropeanaType.Choice choiceIsPartOf = new EuropeanaType.Choice();
        IsPartOf isPartOf = new IsPartOf();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isPartOfResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        isPartOfResource.setResource("test isPartOf");
        isPartOf.setResource(isPartOfResource);

        choiceIsPartOf.setIsPartOf(isPartOf);
        dctermsList.add(choiceIsPartOf);
        EuropeanaType.Choice choiceIsReferencedBy = new EuropeanaType.Choice();
        IsReferencedBy isReferencedBy = new IsReferencedBy();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isReferencedByResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        isReferencedByResource.setResource("test isReferencedBy");
        isReferencedBy.setResource(isReferencedByResource);


        choiceIsReferencedBy.setIsReferencedBy(isReferencedBy);
        dctermsList.add(choiceIsReferencedBy);
        EuropeanaType.Choice choiceIsReplacedBy = new EuropeanaType.Choice();
        IsReplacedBy isReplacedBy = new IsReplacedBy();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isReplacedByResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        isReplacedByResource.setResource("test isReplacedBy");
        isReplacedBy.setResource(isReplacedByResource);

        choiceIsReplacedBy.setIsReplacedBy(isReplacedBy);
        dctermsList.add(choiceIsReplacedBy);
        EuropeanaType.Choice choiceIsRequiredBy = new EuropeanaType.Choice();
        IsRequiredBy isRequiredBy = new IsRequiredBy();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isRequiredByResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        isRequiredByResource.setResource("test isRequiredBy");
        isRequiredBy.setResource(isRequiredByResource);

        choiceIsRequiredBy.setIsRequiredBy(isRequiredBy);
        dctermsList.add(choiceIsRequiredBy);
        EuropeanaType.Choice choiceIssued = new EuropeanaType.Choice();
        Issued issued = new Issued();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource issuedResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        issuedResource.setResource("test issued");
        issued.setResource(issuedResource);

        choiceIssued.setIssued(issued);
        dctermsList.add(choiceIssued);
        EuropeanaType.Choice choiceIsVersionOf = new EuropeanaType.Choice();
        IsVersionOf isVersionOf = new IsVersionOf();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isVersionOfResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        isVersionOfResource.setResource("test isVersionOf");
        isVersionOf.setResource(isVersionOfResource);

        choiceIsVersionOf.setIsVersionOf(isVersionOf);
        dctermsList.add(choiceIsVersionOf);
        EuropeanaType.Choice choiceMedium = new EuropeanaType.Choice();
        Medium medium = new Medium();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource mediumResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        mediumResource.setResource("test medium");
        medium.setResource(mediumResource);


        choiceMedium.setMedium(medium);
        dctermsList.add(choiceMedium);
        EuropeanaType.Choice choiceProvenance = new EuropeanaType.Choice();
        Provenance provenance = new Provenance();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource provenanceResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        provenanceResource.setResource("test provenance");
        provenance.setResource(provenanceResource);
        choiceProvenance.setProvenance(provenance);
        dctermsList.add(choiceProvenance);
        EuropeanaType.Choice choiceReferences = new EuropeanaType.Choice();

        References references = new References();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource referencesResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        referencesResource.setResource("test references");
        references.setResource(referencesResource);

        choiceReferences.setReferences(references);
        dctermsList.add(choiceReferences);
        EuropeanaType.Choice choiceReplaces = new EuropeanaType.Choice();

        Replaces replaces = new Replaces();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource replacesResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        replacesResource.setResource("test replaces");
        replaces.setResource(replacesResource);
        choiceReplaces.setReplaces(replaces);
        dctermsList.add(choiceReplaces);
        EuropeanaType.Choice choiceRequires = new EuropeanaType.Choice();

        Requires requires = new Requires();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource requiresResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        requiresResource.setResource("test requires");
        requires.setResource(requiresResource);
        choiceRequires.setRequires(requires);
        dctermsList.add(choiceRequires);
        EuropeanaType.Choice choiceSpatial = new EuropeanaType.Choice();

        Spatial spatial = new Spatial();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource spatialResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        spatialResource.setResource("test spatial");
        spatial.setResource(spatialResource);
        choiceSpatial.setSpatial(spatial);
        dctermsList.add(choiceSpatial);
        EuropeanaType.Choice choiceTableOfContents = new EuropeanaType.Choice();

        TableOfContents tableOfContents = new TableOfContents();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource tableOfContentsResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        tableOfContentsResource.setResource("test TOC");
        tableOfContents.setResource(tableOfContentsResource);

        choiceTableOfContents.setTableOfContents(tableOfContents);
        dctermsList.add(choiceTableOfContents);
        EuropeanaType.Choice choiceTemporal = new EuropeanaType.Choice();

        Temporal temporal = new Temporal();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource temporalResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        temporalResource.setResource("temporal");
        temporal.setResource(temporalResource);
        choiceTemporal.setTemporal(temporal);
        dctermsList.add(choiceTemporal);
        EuropeanaType.Choice choiceContributor = new EuropeanaType.Choice();

        Contributor contributor = new Contributor();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource contributorResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        contributorResource.setResource("test contributor");
        contributor.setResource(contributorResource);
        choiceContributor.setContributor(contributor);
        dctermsList.add(choiceContributor);
        EuropeanaType.Choice choiceCoverage = new EuropeanaType.Choice();

        Coverage coverage = new Coverage();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource coverageResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        coverageResource.setResource("test coverage");
        coverage.setResource(coverageResource);
        choiceCoverage.setCoverage(coverage);
        dctermsList.add(choiceCoverage);
        EuropeanaType.Choice choiceCreator = new EuropeanaType.Choice();

        Creator creator = new Creator();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource creatorResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        creatorResource.setResource("test creator");
        creator.setResource(creatorResource);
        choiceCreator.setCreator(creator);
        dctermsList.add(choiceCreator);
        EuropeanaType.Choice choiceDate = new EuropeanaType.Choice();

        Date date = new Date();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource dateResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        dateResource.setResource("test date");
        date.setResource(dateResource);
        choiceDate.setDate(date);
        dctermsList.add(choiceDate);
        EuropeanaType.Choice choiceDescription = new EuropeanaType.Choice();

        Description description = new Description();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource descriptionResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        descriptionResource.setResource("test description");
        description.setResource(descriptionResource);
        choiceDescription.setDescription(description);
        dctermsList.add(choiceDescription);
        EuropeanaType.Choice choiceFormat = new EuropeanaType.Choice();

        Format format = new Format();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource formatResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        formatResource.setResource("test format");
        format.setResource(formatResource);

        choiceFormat.setFormat(format);
        dctermsList.add(choiceFormat);
        EuropeanaType.Choice choiceIdentifier = new EuropeanaType.Choice();
        Identifier identifier = new Identifier();
        identifier.setString("test identifier");
        choiceIdentifier.setIdentifier(identifier);
        dctermsList.add(choiceIdentifier);
        EuropeanaType.Choice choiceLanguage = new EuropeanaType.Choice();
        Language language = new Language();
        language.setString("test language");
        choiceLanguage.setLanguage(language);
        dctermsList.add(choiceLanguage);
        EuropeanaType.Choice choicePublisher = new EuropeanaType.Choice();

        Publisher publisher = new Publisher();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource publisherResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        publisherResource.setResource("test publisher");
        publisher.setResource(publisherResource);
        choicePublisher.setPublisher(publisher);
        dctermsList.add(choicePublisher);
        EuropeanaType.Choice choiceRelation = new EuropeanaType.Choice();

        Relation relation = new Relation();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource relationResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        relationResource.setResource("test relation");
        relation.setResource(relationResource);

        choiceRelation.setRelation(relation);
        dctermsList.add(choiceRelation);
        EuropeanaType.Choice choiceRights = new EuropeanaType.Choice();

        Rights rights = new Rights();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource rightsResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        rightsResource.setResource("test rights");
        rights.setResource(rightsResource);
        choiceRights.setRights(rights);
        dctermsList.add(choiceRights);
        EuropeanaType.Choice choiceSource = new EuropeanaType.Choice();

        Source source = new Source();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource sourceResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        sourceResource.setResource("test source");
        source.setResource(sourceResource);

        choiceSource.setSource(source);
        dctermsList.add(choiceSource);
        EuropeanaType.Choice choiceSubject = new EuropeanaType.Choice();

        Subject subject = new Subject();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource subjectResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        subjectResource.setResource("test subject");
        subject.setResource(subjectResource);

        choiceSubject.setSubject(subject);
        dctermsList.add(choiceSubject);
        EuropeanaType.Choice choiceTitle = new EuropeanaType.Choice();
        Title title = new Title();
        title.setString("test title");
        choiceTitle.setTitle(title);
        dctermsList.add(choiceTitle);
        EuropeanaType.Choice choiceType = new EuropeanaType.Choice();

        Type type = new Type();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource typeResource =
                new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        typeResource.setResource("test type");
        type.setResource(typeResource);
        choiceType.setType(type);

        dctermsList.add(choiceType);
        return dctermsList;
    }

}
