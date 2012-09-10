package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.jibx.Alternative;
import eu.europeana.corelib.definitions.jibx.ConformsTo;
import eu.europeana.corelib.definitions.jibx.Contributor;
import eu.europeana.corelib.definitions.jibx.Coverage;
import eu.europeana.corelib.definitions.jibx.Created;
import eu.europeana.corelib.definitions.jibx.Creator;
import eu.europeana.corelib.definitions.jibx.CurrentLocation;
import eu.europeana.corelib.definitions.jibx.Date;
import eu.europeana.corelib.definitions.jibx.Description;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.EuropeanaProxy;
import eu.europeana.corelib.definitions.jibx.EuropeanaType;
import eu.europeana.corelib.definitions.jibx.Extent;
import eu.europeana.corelib.definitions.jibx.Format;
import eu.europeana.corelib.definitions.jibx.HasFormat;
import eu.europeana.corelib.definitions.jibx.HasPart;
import eu.europeana.corelib.definitions.jibx.HasVersion;
import eu.europeana.corelib.definitions.jibx.Identifier;
import eu.europeana.corelib.definitions.jibx.IsFormatOf;
import eu.europeana.corelib.definitions.jibx.IsNextInSequence;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.IsReferencedBy;
import eu.europeana.corelib.definitions.jibx.IsReplacedBy;
import eu.europeana.corelib.definitions.jibx.IsRequiredBy;
import eu.europeana.corelib.definitions.jibx.IsVersionOf;
import eu.europeana.corelib.definitions.jibx.Issued;
import eu.europeana.corelib.definitions.jibx.Language;
import eu.europeana.corelib.definitions.jibx.Medium;
import eu.europeana.corelib.definitions.jibx.Provenance;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.Publisher;
import eu.europeana.corelib.definitions.jibx.References;
import eu.europeana.corelib.definitions.jibx.Relation;
import eu.europeana.corelib.definitions.jibx.Replaces;
import eu.europeana.corelib.definitions.jibx.Requires;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Source;
import eu.europeana.corelib.definitions.jibx.Spatial;
import eu.europeana.corelib.definitions.jibx.Subject;
import eu.europeana.corelib.definitions.jibx.TableOfContents;
import eu.europeana.corelib.definitions.jibx.Temporal;
import eu.europeana.corelib.definitions.jibx.Title;
import eu.europeana.corelib.definitions.jibx.Type;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.ProxyFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class ProxyFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testProxy() throws MalformedURLException, IOException {
		// The fields of the proxy come from the ProvidedCHO
		ProxyType proxy = createProxyFields();
		testMongo(proxy);
		testSolr(proxy);
	}

	private void testSolr(ProxyType proxy) throws MalformedURLException,
			IOException {
		SolrInputDocument solrDocument = new SolrInputDocument();
		try {
			solrDocument = new ProxyFieldInput().createProxySolrFields(proxy,
					solrDocument);
			assertEquals(proxy.getAbout(),
					solrDocument.getFieldValue(EdmLabel.ORE_PROXY.toString()));
			assertEquals(proxy.getCurrentLocation().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.PROXY_EDM_CURRENT_LOCATION
									.toString()));
			assertEquals(
					proxy.getIsNextInSequence().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.PROXY_EDM_IS_NEXT_IN_SEQUENCE
									.toString()));
			assertEquals(proxy.getType().toString(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_EDM_TYPE
							.toString()));
			List<EuropeanaType.Choice> dcterms = proxy.getChoiceList();
			for(EuropeanaType.Choice choice:dcterms){
				if(choice.ifAlternative())
				assertEquals(
						choice.getAlternative().getString(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_ALTERNATIVE
										.toString()));
				if(choice.ifConformsTo())
				assertEquals(
						choice.getConformsTo().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_CONFORMS_TO
										.toString()));
				if(choice.ifCreated())
				assertEquals(choice.getCreated().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_CREATED
										.toString()));
				if(choice.ifExtent())
				assertEquals(choice.getExtent().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_EXTENT
										.toString()));
				if(choice.ifHasFormat())
				assertEquals(
						choice.getHasFormat().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_HAS_FORMAT
										.toString()));
				if(choice.ifHasPart())
				assertEquals(choice.getHasPart().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_HAS_PART
										.toString()));
				if(choice.ifHasVersion())
				assertEquals(
						choice.getHasVersion().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_HAS_VERSION
										.toString()));
				if(choice.ifIsFormatOf())
				assertEquals(
						choice.getIsFormatOf().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_FORMAT_OF
										.toString()));
				if(choice.ifIsPartOf())
				assertEquals(
						choice.getIsPartOf().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_PART_OF
										.toString()));
				if(choice.ifIsReferencedBy())
				assertEquals(
						choice.getIsReferencedBy().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_REFERENCED_BY
										.toString()));
				if(choice.ifIsReplacedBy())
				assertEquals(
						choice.getIsReplacedBy().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_REPLACED_BY
										.toString()));
				if(choice.ifIsRequiredBy())
				assertEquals(
						choice.getIsRequiredBy().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_REQUIRED_BY
										.toString()));
				if(choice.ifIssued())
				assertEquals(choice.getIssued().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_ISSUED
										.toString()));
				if(choice.ifIsVersionOf())
				assertEquals(
						choice.getIsVersionOf().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_IS_VERSION_OF
										.toString()));
				if(choice.ifMedium())
				assertEquals(choice.getMedium().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_MEDIUM
										.toString()));
				if(choice.ifProvenance())
				assertEquals(
						choice.getProvenance().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_PROVENANCE
										.toString()));
				if(choice.ifReferences())
				assertEquals(
						choice.getReferences().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_REFERENCES
										.toString()));
				if(choice.ifReplaces())
				assertEquals(choice.getReplaces().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_REPLACES
										.toString()));
				if(choice.ifRequires())
				assertEquals(choice.getRequires().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_REQUIRES
										.toString()));
				if(choice.ifSpatial())
				assertEquals(choice.getSpatial().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_SPATIAL
										.toString()));
				if(choice.ifTableOfContents())
				assertEquals(
						choice.getTableOfContents().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_TABLE_OF_CONTENTS
										.toString()));
				if(choice.ifTemporal())
				assertEquals(choice.getTemporal().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DCTERMS_TEMPORAL
										.toString()));
				if(choice.ifContributor())
				assertEquals(choice.getContributor().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DC_CONTRIBUTOR
										.toString()));
				if(choice.ifCoverage())
				assertEquals(choice.getCoverage().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_COVERAGE
								.toString()));
				if(choice.ifCreator())
				assertEquals(choice.getCreator().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_CREATOR
								.toString()));
				if(choice.ifDate())
				assertEquals(choice.getDate().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_DATE
								.toString()));
				if(choice.ifDescription())
				assertEquals(choice.getDescription().getResource(),
						solrDocument
								.getFieldValue(EdmLabel.PROXY_DC_DESCRIPTION
										.toString()));
				if(choice.ifFormat())
				assertEquals(choice.getFormat().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_FORMAT
								.toString()));
				if(choice.ifIdentifier())
				assertEquals(choice.getIdentifier().getString(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_IDENTIFIER
								.toString()));
				if(choice.ifLanguage())
				assertEquals(choice.getLanguage().getString(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_LANGUAGE
								.toString()));
				if(choice.ifPublisher())
				assertEquals(choice.getPublisher().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_PUBLISHER
								.toString()));
				if(choice.ifRelation())
				assertEquals(choice.getRelation().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_RELATION
								.toString()));
				if(choice.ifRights())
				assertEquals(choice.getRights().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_RIGHTS
								.toString()));
				if(choice.ifSource())
				assertEquals(choice.getSource().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_SOURCE
								.toString()));
				if(choice.ifSubject())
				assertEquals(choice.getSubject().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_SUBJECT
								.toString()));
				if(choice.ifTitle())
				assertEquals(choice.getTitle().getString(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_TITLE
								.toString()));
				if(choice.ifType())
				assertEquals(choice.getType().getResource(),
						solrDocument.getFieldValue(EdmLabel.PROXY_DC_TYPE
								.toString()));

			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testMongo(ProxyType proxy) {
		ProxyImpl mongoProxy = new ProxyImpl();
		try {
			mongoProxy = new ProxyFieldInput().createProxyMongoFields(
					mongoProxy, proxy, mongoServer);
			assertEquals(proxy.getAbout(), mongoProxy.getAbout());
			assertEquals(proxy.getType().toString(), mongoProxy.getEdmType()
					.toString());
			assertEquals(proxy.getIsNextInSequence().getResource(),
					mongoProxy.getEdmIsNextInSequence());
			List<EuropeanaType.Choice> dcterms = proxy.getChoiceList();
			for (EuropeanaType.Choice choice : dcterms) {
				if (choice.ifAlternative()) {
					assertEquals(choice.getAlternative().getString(),
							mongoProxy.getDctermsAlternative().values()
									.iterator().next());
				}
				if (choice.ifConformsTo()) {
					assertEquals(choice.getConformsTo().getResource(),
							mongoProxy.getDctermsConformsTo().values()
									.iterator().next());
				}
				if (choice.ifCreated()) {
					assertEquals(choice.getCreated().getResource(), mongoProxy
							.getDctermsCreated().values().iterator().next());
				}
				if (choice.ifExtent()) {
					assertEquals(choice.getExtent().getResource(), mongoProxy
							.getDctermsExtent().values().iterator().next());
				}
				if (choice.ifHasFormat()) {
					assertEquals(choice.getHasFormat().getResource(),
							mongoProxy.getDctermsHasFormat().values()
									.iterator().next());
				}
				if (choice.ifHasPart()) {
					assertEquals(choice.getHasPart().getResource(), mongoProxy
							.getDctermsHasPart().values().iterator().next());
				}
				if (choice.ifHasVersion()) {
					assertEquals(choice.getHasVersion().getResource(),
							mongoProxy.getDctermsHasVersion().values()
									.iterator().next());
				}
				if (choice.ifIsFormatOf()) {
					assertEquals(choice.getIsFormatOf().getResource(),
							mongoProxy.getDctermsIsFormatOf().values()
									.iterator().next());
				}
				if (choice.ifIsPartOf()) {
					assertEquals(choice.getIsPartOf().getResource(), mongoProxy
							.getDctermsIsPartOf().values().iterator().next());
				}
				if (choice.ifIsReferencedBy()) {
					assertEquals(choice.getIsReferencedBy().getResource(),
							mongoProxy.getDctermsIsReferencedBy().values()
									.iterator().next());
				}
				if (choice.ifIsReplacedBy()) {
					assertEquals(choice.getIsReplacedBy().getResource(),
							mongoProxy.getDctermsIsReplacedBy().values()
									.iterator().next());
				}
				if (choice.ifIsRequiredBy()) {
					assertEquals(choice.getIsRequiredBy().getResource(),
							mongoProxy.getDctermsIsRequiredBy().values()
									.iterator().next());
				}
				if (choice.ifIssued()) {
					assertEquals(choice.getIssued().getResource(), mongoProxy
							.getDctermsIssued().values().iterator().next());
				}
				if (choice.ifIsVersionOf()) {
					assertEquals(choice.getIsVersionOf().getResource(),
							mongoProxy.getDctermsIsVersionOf().values()
									.iterator().next());
				}
				if (choice.ifMedium()) {
					assertEquals(choice.getMedium().getResource(), mongoProxy
							.getDctermsMedium().values().iterator().next());
				}
				if (choice.ifProvenance()) {
					assertEquals(choice.getProvenance().getResource(),
							mongoProxy.getDctermsProvenance().values()
									.iterator().next());
				}
				if (choice.ifReferences()) {
					assertEquals(choice.getReferences().getResource(),
							mongoProxy.getDctermsReferences().values()
									.iterator().next());
				}
				if (choice.ifReplaces()) {
					assertEquals(choice.getReplaces().getResource(), mongoProxy
							.getDctermsReplaces().values().iterator().next());
				}
				if (choice.ifRequires()) {
					assertEquals(choice.getRequires().getResource(), mongoProxy
							.getDctermsRequires().values().iterator().next());
				}
				if (choice.ifSpatial()) {
					assertEquals(choice.getSpatial().getResource(), mongoProxy
							.getDctermsSpatial().values().iterator().next());
				}
				if (choice.ifTableOfContents()) {
					assertEquals(choice.getTableOfContents().getResource(),
							mongoProxy.getDctermsTOC().values().iterator()
									.next());
				}
				if (choice.ifTemporal()) {
					assertEquals(choice.getTemporal().getResource(), mongoProxy
							.getDctermsTemporal().values().iterator().next());
				}
				if (choice.ifContributor()) {
					assertEquals(choice.getContributor().getResource(),
							mongoProxy.getDcContributor().values().iterator()
									.next());
				}
				if (choice.ifCoverage()) {
					assertEquals(choice.getCoverage().getResource(), mongoProxy
							.getDcCoverage().values().iterator().next());
				}
				if(choice.ifCreator()){
				assertEquals(choice.getCreator().getResource(),
						mongoProxy.getDcCreator().values().iterator().next());
				}
				if(choice.ifDate()){
				assertEquals(choice.getDate().getResource(), mongoProxy
						.getDcDate().values().iterator().next());
				}
				if(choice.ifDescription()){
				assertEquals(choice.getDescription().getResource(),
						mongoProxy.getDcDescription().values().iterator()
								.next());
				}
				if(choice.ifFormat()){
				assertEquals(choice.getFormat().getResource(),
						mongoProxy.getDcFormat().values().iterator().next());
				}
				if(choice.ifIdentifier()){
				assertEquals(choice.getIdentifier().getString(),
						mongoProxy.getDcIdentifier().values().iterator().next());
				}
				if(choice.ifLanguage()){
				assertEquals(choice.getLanguage().getString(),
						mongoProxy.getDcLanguage().values().iterator().next());
				}
				if(choice.ifPublisher()){
				assertEquals(choice.getPublisher().getResource(),
						mongoProxy.getDcPublisher().values().iterator().next());
				}
				if(choice.ifRelation()){
				assertEquals(choice.getRelation().getResource(),
						mongoProxy.getDcRelation().values().iterator().next());
				}
				if(choice.ifRights()){
				assertEquals(choice.getRights().getResource(),
						mongoProxy.getDcRights().values().iterator().next());
				}
				if(choice.ifSource()){
				assertEquals(choice.getSource().getResource(),
						mongoProxy.getDcSource().values().iterator().next());
				}
				if(choice.ifSubject()){
				assertEquals(choice.getSubject().getResource(),
						mongoProxy.getDcSubject().values().iterator().next());
				}
				if(choice.ifTitle()){
				assertEquals(choice.getTitle().getString(), mongoProxy
						.getDcTitle().values().iterator().next());
				}
				if(choice.ifType()){
				assertEquals(choice.getType().getResource(), mongoProxy
						.getDcType().values().iterator().next());
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ProxyType createProxyFields() {
		ProxyType proxy = new ProxyType();
		proxy.setAbout("test about");
		CurrentLocation currentLocation = new CurrentLocation();
		currentLocation.setResource("test current location");
		IsNextInSequence isNextInSequence = new IsNextInSequence();
		isNextInSequence.setResource("test is next in sequence");
		proxy.setIsNextInSequence(isNextInSequence);
		EuropeanaProxy europeanaProxy = new EuropeanaProxy();
		europeanaProxy.setEuropeanaProxy(true);
		proxy.setEuropeanaProxy(europeanaProxy);
		proxy.setCurrentLocation(currentLocation);
		proxy.setType(EdmType.IMAGE);
		proxy.setChoiceList(createEuropeanaTermsList());

		return proxy;
	}

	private List<EuropeanaType.Choice> createEuropeanaTermsList() {
		List<EuropeanaType.Choice> dctermsList = new ArrayList<EuropeanaType.Choice>();

		EuropeanaType.Choice choiceAlternative = new EuropeanaType.Choice();
		Alternative alternative = new Alternative();
		alternative.setString("test alternative");
		choiceAlternative.setAlternative(alternative);
		dctermsList.add(choiceAlternative);
		EuropeanaType.Choice choiceConformsTo = new EuropeanaType.Choice();
		ConformsTo conformsTo = new ConformsTo();
		conformsTo.setResource("test conforms to");
		choiceConformsTo.setConformsTo(conformsTo);
		dctermsList.add(choiceConformsTo);
		EuropeanaType.Choice choiceCreated = new EuropeanaType.Choice();
		Created created = new Created();
		created.setResource("test created");
		choiceCreated.setCreated(created);
		dctermsList.add(choiceCreated);
		EuropeanaType.Choice choiceExtent = new EuropeanaType.Choice();
		Extent extent = new Extent();
		extent.setResource("test extent");
		choiceExtent.setExtent(extent);
		dctermsList.add(choiceExtent);
		EuropeanaType.Choice choiceHasFormat = new EuropeanaType.Choice();
		HasFormat hasFormat = new HasFormat();
		hasFormat.setResource("test hasFormat");
		choiceHasFormat.setHasFormat(hasFormat);
		dctermsList.add(choiceHasFormat);
		EuropeanaType.Choice choiceHasPart = new EuropeanaType.Choice();
		HasPart hasPart = new HasPart();
		hasPart.setResource("test hasPart");
		choiceHasPart.setHasPart(hasPart);
		dctermsList.add(choiceHasPart);
		EuropeanaType.Choice choiceHasVersion = new EuropeanaType.Choice();
		HasVersion hasVersion = new HasVersion();
		hasVersion.setResource("test hasVersion");
		choiceHasVersion.setHasVersion(hasVersion);
		dctermsList.add(choiceHasVersion);
		EuropeanaType.Choice choiceIsFormatOf = new EuropeanaType.Choice();
		IsFormatOf isFormatOf = new IsFormatOf();
		isFormatOf.setResource("test isFormatOf");
		choiceIsFormatOf.setIsFormatOf(isFormatOf);
		dctermsList.add(choiceIsFormatOf);
		EuropeanaType.Choice choiceIsPartOf = new EuropeanaType.Choice();
		IsPartOf isPartOf = new IsPartOf();
		isPartOf.setResource("test isPartOf");
		choiceIsPartOf.setIsPartOf(isPartOf);
		dctermsList.add(choiceIsPartOf);
		EuropeanaType.Choice choiceIsReferencedBy = new EuropeanaType.Choice();
		IsReferencedBy isReferencedBy = new IsReferencedBy();
		isReferencedBy.setResource("test isReferencedBy");
		choiceIsReferencedBy.setIsReferencedBy(isReferencedBy);
		dctermsList.add(choiceIsReferencedBy);
		EuropeanaType.Choice choiceIsReplacedBy = new EuropeanaType.Choice();
		IsReplacedBy isReplacedBy = new IsReplacedBy();
		isReplacedBy.setResource("test isReplacedBy");
		choiceIsReplacedBy.setIsReplacedBy(isReplacedBy);
		dctermsList.add(choiceIsReplacedBy);
		EuropeanaType.Choice choiceIsRequiredBy = new EuropeanaType.Choice();
		IsRequiredBy isRequiredBy = new IsRequiredBy();
		isRequiredBy.setResource("test isRequiredBy");
		choiceIsRequiredBy.setIsRequiredBy(isRequiredBy);
		dctermsList.add(choiceIsRequiredBy);
		EuropeanaType.Choice choiceIssued = new EuropeanaType.Choice();
		Issued issued = new Issued();
		issued.setResource("test issued");
		choiceIssued.setIssued(issued);
		dctermsList.add(choiceIssued);
		EuropeanaType.Choice choiceIsVersionOf = new EuropeanaType.Choice();
		IsVersionOf isVersionOf = new IsVersionOf();
		isVersionOf.setResource("test isVersionOf");
		choiceIsVersionOf.setIsVersionOf(isVersionOf);
		dctermsList.add(choiceIsVersionOf);
		EuropeanaType.Choice choiceMedium = new EuropeanaType.Choice();
		Medium medium = new Medium();
		medium.setResource("test medium");
		choiceMedium.setMedium(medium);
		dctermsList.add(choiceMedium);
		EuropeanaType.Choice choiceProvenance = new EuropeanaType.Choice();
		Provenance provenance = new Provenance();
		provenance.setResource("test provenance");
		choiceProvenance.setProvenance(provenance);
		dctermsList.add(choiceProvenance);
		EuropeanaType.Choice choiceReferences = new EuropeanaType.Choice();
		References references = new References();
		references.setResource("test references");
		choiceReferences.setReferences(references);
		dctermsList.add(choiceReferences);
		EuropeanaType.Choice choiceReplaces = new EuropeanaType.Choice();
		Replaces replaces = new Replaces();
		replaces.setResource("test replaces");
		choiceReplaces.setReplaces(replaces);
		dctermsList.add(choiceReplaces);
		EuropeanaType.Choice choiceRequires = new EuropeanaType.Choice();
		Requires requires = new Requires();
		requires.setResource("test requires");
		choiceRequires.setRequires(requires);
		dctermsList.add(choiceRequires);
		EuropeanaType.Choice choiceSpatial = new EuropeanaType.Choice();
		Spatial spatial = new Spatial();
		spatial.setResource("test spatial");
		choiceSpatial.setSpatial(spatial);
		dctermsList.add(choiceSpatial);
		EuropeanaType.Choice choiceTableOfContents = new EuropeanaType.Choice();
		TableOfContents tableOfContents = new TableOfContents();
		tableOfContents.setResource("test TOC");
		choiceTableOfContents.setTableOfContents(tableOfContents);
		dctermsList.add(choiceTableOfContents);
		EuropeanaType.Choice choiceTemporal = new EuropeanaType.Choice();
		Temporal temporal = new Temporal();
		temporal.setResource("temporal");
		choiceTemporal.setTemporal(temporal);
		dctermsList.add(choiceTemporal);
		EuropeanaType.Choice choiceContributor = new EuropeanaType.Choice();
		Contributor contributor = new Contributor();
		contributor.setResource("test contributor");
		choiceContributor.setContributor(contributor);
		dctermsList.add(choiceContributor);
		EuropeanaType.Choice choiceCoverage = new EuropeanaType.Choice();
		Coverage coverage = new Coverage();
		coverage.setResource("test coverage");
		choiceCoverage.setCoverage(coverage);
		dctermsList.add(choiceCoverage);
		EuropeanaType.Choice choiceCreator = new EuropeanaType.Choice();
		Creator creator = new Creator();
		creator.setResource("test creator");
		choiceCreator.setCreator(creator);
		dctermsList.add(choiceCreator);
		EuropeanaType.Choice choiceDate = new EuropeanaType.Choice();
		Date date = new Date();
		date.setResource("test date");
		choiceDate.setDate(date);
		dctermsList.add(choiceDate);
		EuropeanaType.Choice choiceDescription = new EuropeanaType.Choice();
		Description description = new Description();
		description.setResource("test description");
		choiceDescription.setDescription(description);
		dctermsList.add(choiceDescription);
		EuropeanaType.Choice choiceFormat = new EuropeanaType.Choice();
		Format format = new Format();
		format.setResource("test format");
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
		publisher.setResource("test publisher");
		choicePublisher.setPublisher(publisher);
		dctermsList.add(choicePublisher);
		EuropeanaType.Choice choiceRelation = new EuropeanaType.Choice();
		Relation relation = new Relation();
		relation.setResource("test relation");
		choiceRelation.setRelation(relation);
		dctermsList.add(choiceRelation);
		EuropeanaType.Choice choiceRights = new EuropeanaType.Choice();
		Rights rights = new Rights();
		rights.setResource("test rights");
		choiceRights.setRights(rights);
		dctermsList.add(choiceRights);
		EuropeanaType.Choice choiceSource = new EuropeanaType.Choice();
		Source source = new Source();
		source.setResource("test source");
		choiceSource.setSource(source);
		dctermsList.add(choiceSource);
		EuropeanaType.Choice choiceSubject = new EuropeanaType.Choice();
		Subject subject = new Subject();
		subject.setResource("test subject");
		choiceSubject.setSubject(subject);
		dctermsList.add(choiceSubject);
		EuropeanaType.Choice choiceTitle = new EuropeanaType.Choice();
		Title title = new Title();
		title.setString("test title");
		choiceTitle.setTitle(title);
		dctermsList.add(choiceTitle);
		EuropeanaType.Choice choiceType = new EuropeanaType.Choice();
		Type type = new Type();
		type.setResource("test type");
		choiceType.setType(type);

		dctermsList.add(choiceType);
		return dctermsList;
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
