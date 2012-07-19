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
import eu.europeana.corelib.definitions.jibx.RDF;
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
			solrDocument = ProxyFieldInput.createProxySolrFields(proxy,
					solrDocument, null, true);
			assertEquals(proxy.getAbout(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_ORE_PROXY.toString()));
			assertEquals(proxy.getCurrentLocation().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_EDM_CURRENT_LOCATION_LAT
							.toString()));
			assertEquals(proxy.getIsNextInSequence().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_EDM_IS_NEXT_IN_SEQUENCE
							.toString()));
			assertEquals(proxy.getType().toString(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_EDM_TYPE.toString()));
			List<EuropeanaType.Choice> dcterms = proxy.getChoiceList();
			assertEquals(dcterms.get(0).getAlternative().getString(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_ALTERNATIVE
							.toString()));
			assertEquals(dcterms.get(0).getConformsTo().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_CONFORMS_TO
							.toString()));
			assertEquals(dcterms.get(0).getCreated().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_CREATED
							.toString()));
			assertEquals(dcterms.get(0).getExtent().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_EXTENT
							.toString()));
			assertEquals(dcterms.get(0).getHasFormat().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_HAS_FORMAT
							.toString()));
			assertEquals(dcterms.get(0).getHasPart().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_HAS_PART
							.toString()));
			assertEquals(dcterms.get(0).getHasVersion().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_HAS_VERSION
							.toString()));
			assertEquals(dcterms.get(0).getIsFormatOf().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_IS_FORMAT_OF
							.toString()));
			assertEquals(dcterms.get(0).getIsPartOf().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_IS_PART_OF
							.toString()));
			assertEquals(dcterms.get(0).getIsReferencedBy().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.PROVIDER_DCTERMS_IS_REFERENCED_BY
									.toString()));
			assertEquals(dcterms.get(0).getIsReplacedBy().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_IS_REPLACED_BY
							.toString()));
			assertEquals(dcterms.get(0).getIsRequiredBy().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_IS_REQUIRED_BY
							.toString()));
			assertEquals(dcterms.get(0).getIssued().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_ISSUED
							.toString()));
			assertEquals(dcterms.get(0).getIsVersionOf().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_IS_VERSION_OF
							.toString()));
			assertEquals(dcterms.get(0).getMedium().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_MEDIUM
							.toString()));
			assertEquals(dcterms.get(0).getProvenance().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_PROVENANCE
							.toString()));
			assertEquals(dcterms.get(0).getReferences().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_REFERENCES
							.toString()));
			assertEquals(dcterms.get(0).getReplaces().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_REPLACES
							.toString()));
			assertEquals(dcterms.get(0).getRequires().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_REQUIRES
							.toString()));
			assertEquals(dcterms.get(0).getSpatial().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_SPATIAL
							.toString()));
			assertEquals(dcterms.get(0).getTableOfContents().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.PROVIDER_DCTERMS_TABLE_OF_CONTENTS
									.toString()));
			assertEquals(dcterms.get(0).getTemporal().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DCTERMS_TEMPORAL
							.toString()));

		
			assertEquals(dcterms.get(0).getContributor().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_CONTRIBUTOR
							.toString()));
			assertEquals(dcterms.get(0).getCoverage().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_COVERAGE.toString()));
			assertEquals(dcterms.get(0).getCreator().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_CREATOR.toString()));
			assertEquals(dcterms.get(0).getDate().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_DATE.toString()));
			assertEquals(dcterms.get(0).getDescription().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_DESCRIPTION
							.toString()));
			assertEquals(dcterms.get(0).getFormat().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_FORMAT.toString()));
			assertEquals(dcterms.get(0).getIdentifier().getString(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_IDENTIFIER
							.toString()));
			assertEquals(dcterms.get(0).getLanguage().getString(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_LANGUAGE.toString()));
			assertEquals(
					dcterms.get(0).getPublisher().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_PUBLISHER.toString()));
			assertEquals(dcterms.get(0).getRelation().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_RELATION.toString()));
			assertEquals(dcterms.get(0).getRights().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_RIGHTS
							.toString()));
			assertEquals(dcterms.get(0).getSource().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_SOURCE.toString()));
			assertEquals(dcterms.get(0).getSubject().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_SUBJECT.toString()));
			assertEquals(dcterms.get(0).getTitle().getString(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_TITLE.toString()));
			assertEquals(dcterms.get(0).getType().getResource(),
					solrDocument.getFieldValue(EdmLabel.PROVIDER_DC_TYPE.toString()));

			// continue from here
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
			mongoProxy = ProxyFieldInput.createProxyMongoFields(mongoProxy,
					proxy, mongoServer, new RDF(), true);
			assertEquals(proxy.getAbout(), mongoProxy.getAbout());
			assertEquals(proxy.getType().toString(), mongoProxy.getEdmType()
					.toString());
			assertEquals(proxy.getIsNextInSequence().getResource(),
					mongoProxy.getEdmIsNextInSequence());
			List<EuropeanaType.Choice> dcterms = proxy.getChoiceList();
			assertEquals(dcterms.get(0).getAlternative().getString(),
					mongoProxy.getDctermsAlternative()[0]);
			assertEquals(dcterms.get(0).getConformsTo().getResource(),
					mongoProxy.getDctermsConformsTo()[0]);
			assertEquals(dcterms.get(0).getCreated().getResource(),
					mongoProxy.getDctermsCreated()[0]);
			assertEquals(dcterms.get(0).getExtent().getResource(),
					mongoProxy.getDctermsExtent()[0]);
			assertEquals(dcterms.get(0).getHasFormat().getResource(),
					mongoProxy.getDctermsHasFormat()[0]);
			assertEquals(dcterms.get(0).getHasPart().getResource(),
					mongoProxy.getDctermsHasPart()[0]);
			assertEquals(dcterms.get(0).getHasVersion().getResource(),
					mongoProxy.getDctermsHasVersion()[0]);
			assertEquals(dcterms.get(0).getIsFormatOf().getResource(),
					mongoProxy.getDctermsIsFormatOf()[0]);
			assertEquals(dcterms.get(0).getIsPartOf().getResource(),
					mongoProxy.getDctermsIsPartOf()[0]);
			assertEquals(dcterms.get(0).getIsReferencedBy().getResource(),
					mongoProxy.getDctermsIsReferencedBy()[0]);
			assertEquals(dcterms.get(0).getIsReplacedBy().getResource(),
					mongoProxy.getDctermsIsReplacedBy()[0]);
			assertEquals(dcterms.get(0).getIsRequiredBy().getResource(),
					mongoProxy.getDctermsIsRequiredBy()[0]);
			assertEquals(dcterms.get(0).getIssued().getResource(),
					mongoProxy.getDctermsIssued()[0]);
			assertEquals(dcterms.get(0).getIsVersionOf().getResource(),
					mongoProxy.getDctermsIsVersionOf()[0]);
			assertEquals(dcterms.get(0).getMedium().getResource(),
					mongoProxy.getDctermsMedium()[0]);
			assertEquals(dcterms.get(0).getProvenance().getResource(),
					mongoProxy.getDctermsProvenance()[0]);
			assertEquals(dcterms.get(0).getReferences().getResource(),
					mongoProxy.getDctermsReferences()[0]);
			assertEquals(dcterms.get(0).getReplaces().getResource(),
					mongoProxy.getDctermsReplaces()[0]);
			assertEquals(dcterms.get(0).getRequires().getResource(),
					mongoProxy.getDctermsRequires()[0]);
			assertEquals(dcterms.get(0).getSpatial().getResource(),
					mongoProxy.getDctermsSpatial()[0]);
			assertEquals(dcterms.get(0).getTableOfContents().getResource(),
					mongoProxy.getDctermsTOC()[0]);
			assertEquals(dcterms.get(0).getTemporal().getResource(),
					mongoProxy.getDctermsTemporal()[0]);

			assertEquals(dcterms.get(0).getContributor().getResource(),
					mongoProxy.getDcContributor()[0]);
			assertEquals(dcterms.get(0).getCoverage().getResource(),
					mongoProxy.getDcCoverage()[0]);
			assertEquals(dcterms.get(0).getCreator().getResource(),
					mongoProxy.getDcCreator()[0]);
			assertEquals(dcterms.get(0).getDate().getResource(),
					mongoProxy.getDcDate()[0]);
			assertEquals(dcterms.get(0).getDescription().getResource(),
					mongoProxy.getDcDescription()[0]);
			assertEquals(dcterms.get(0).getFormat().getResource(),
					mongoProxy.getDcFormat()[0]);
			assertEquals(dcterms.get(0).getIdentifier().getString(),
					mongoProxy.getDcIdentifier()[0]);
			assertEquals(dcterms.get(0).getLanguage().getString(),
					mongoProxy.getDcLanguage()[0]);
			assertEquals(dcterms.get(0).getPublisher().getResource(),
					mongoProxy.getDcPublisher()[0]);
			assertEquals(dcterms.get(0).getRelation().getResource(),
					mongoProxy.getDcRelation()[0]);
			assertEquals(dcterms.get(0).getRights().getResource(),
					mongoProxy.getDcRights()[0]);
			assertEquals(dcterms.get(0).getSource().getResource(),
					mongoProxy.getDcSource()[0]);
			assertEquals(dcterms.get(0).getSubject().getResource(),
					mongoProxy.getDcSubject()[0]);
			assertEquals(dcterms.get(0).getTitle().getString(),
					mongoProxy.getDcTitle()[0]);
			assertEquals(dcterms.get(0).getType().getResource(),
					mongoProxy.getDcType()[0]);
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
		proxy.setCurrentLocation(currentLocation);
		proxy.setType(EdmType.IMAGE);
		proxy.setChoiceList(createEuropeanaTermsList());
		
		return proxy;
	}


	private List<EuropeanaType.Choice> createEuropeanaTermsList() {
		List<EuropeanaType.Choice> dctermsList = new ArrayList<EuropeanaType.Choice>();

		EuropeanaType.Choice dcterms = new EuropeanaType.Choice();
		Alternative alternative = new Alternative();
		alternative.setString("test alternative");
		dcterms.setAlternative(alternative);
		dcterms.clearChoiceListSelect();
		ConformsTo conformsTo = new ConformsTo();
		conformsTo.setResource("test conforms to");
		dcterms.setConformsTo(conformsTo);
		dcterms.clearChoiceListSelect();
		Created created = new Created();
		created.setResource("test created");
		dcterms.setCreated(created);
		dcterms.clearChoiceListSelect();
		Extent extent = new Extent();
		extent.setResource("test extent");
		dcterms.setExtent(extent);
		dcterms.clearChoiceListSelect();
		HasFormat hasFormat = new HasFormat();
		hasFormat.setResource("test hasFormat");
		dcterms.setHasFormat(hasFormat);
		dcterms.clearChoiceListSelect();
		HasPart hasPart = new HasPart();
		hasPart.setResource("test hasPart");
		dcterms.setHasPart(hasPart);
		dcterms.clearChoiceListSelect();
		HasVersion hasVersion = new HasVersion();
		hasVersion.setResource("test hasVersion");
		dcterms.setHasVersion(hasVersion);
		dcterms.clearChoiceListSelect();
		IsFormatOf isFormatOf = new IsFormatOf();
		isFormatOf.setResource("test isFormatOf");
		dcterms.setIsFormatOf(isFormatOf);
		dcterms.clearChoiceListSelect();
		IsPartOf isPartOf = new IsPartOf();
		isPartOf.setResource("test isPartOf");
		dcterms.setIsPartOf(isPartOf);
		dcterms.clearChoiceListSelect();
		IsReferencedBy isReferencedBy = new IsReferencedBy();
		isReferencedBy.setResource("test isReferencedBy");
		dcterms.setIsReferencedBy(isReferencedBy);
		dcterms.clearChoiceListSelect();
		IsReplacedBy isReplacedBy = new IsReplacedBy();
		isReplacedBy.setResource("test isReplacedBy");
		dcterms.setIsReplacedBy(isReplacedBy);
		dcterms.clearChoiceListSelect();
		IsRequiredBy isRequiredBy = new IsRequiredBy();
		isRequiredBy.setResource("test isRequiredBy");
		dcterms.setIsRequiredBy(isRequiredBy);
		dcterms.clearChoiceListSelect();
		Issued issued = new Issued();
		issued.setResource("test issued");
		dcterms.setIssued(issued);
		dcterms.clearChoiceListSelect();
		IsVersionOf isVersionOf = new IsVersionOf();
		isVersionOf.setResource("test isVersionOf");
		dcterms.setIsVersionOf(isVersionOf);
		dcterms.clearChoiceListSelect();
		Medium medium = new Medium();
		medium.setResource("test medium");
		dcterms.setMedium(medium);
		dcterms.clearChoiceListSelect();
		Provenance provenance = new Provenance();
		provenance.setResource("test provenance");
		dcterms.setProvenance(provenance);
		dcterms.clearChoiceListSelect();
		References references = new References();
		references.setResource("test references");
		dcterms.setReferences(references);
		dcterms.clearChoiceListSelect();
		Replaces replaces = new Replaces();
		replaces.setResource("test replaces");
		dcterms.setReplaces(replaces);
		dcterms.clearChoiceListSelect();
		Requires requires = new Requires();
		requires.setResource("test requires");
		dcterms.setRequires(requires);
		dcterms.clearChoiceListSelect();
		Spatial spatial = new Spatial();
		spatial.setResource("test spatial");
		dcterms.setSpatial(spatial);
		dcterms.clearChoiceListSelect();
		TableOfContents tableOfContents = new TableOfContents();
		tableOfContents.setResource("test TOC");
		dcterms.setTableOfContents(tableOfContents);
		dcterms.clearChoiceListSelect();
		Temporal temporal = new Temporal();
		temporal.setResource("temporal");
		dcterms.setTemporal(temporal);
		Contributor contributor = new Contributor();
		contributor.setResource("test contributor");
		dcterms.setContributor(contributor);
		dcterms.clearChoiceListSelect();
		Coverage coverage = new Coverage();
		coverage.setResource("test coverage");
		dcterms.setCoverage(coverage);
		dcterms.clearChoiceListSelect();
		Creator creator = new Creator();
		creator.setResource("test creator");
		dcterms.setCreator(creator);
		dcterms.clearChoiceListSelect();
		Date date = new Date();
		date.setResource("test date");
		dcterms.setDate(date);
		dcterms.clearChoiceListSelect();
		Description description = new Description();
		description.setResource("test description");
		dcterms.setDescription(description);
		dcterms.clearChoiceListSelect();
		Format format = new Format();
		format.setResource("test format");
		dcterms.setFormat(format);
		dcterms.clearChoiceListSelect();
		Identifier identifier = new Identifier();
		identifier.setString("test identifier");
		dcterms.setIdentifier(identifier);
		dcterms.clearChoiceListSelect();
		Language language = new Language();
		language.setString("test language");
		dcterms.setLanguage(language);
		dcterms.clearChoiceListSelect();
		Publisher publisher = new Publisher();
		publisher.setResource("test publisher");
		dcterms.setPublisher(publisher);
		dcterms.clearChoiceListSelect();
		Relation relation = new Relation();
		relation.setResource("test relation");
		dcterms.setRelation(relation);
		dcterms.clearChoiceListSelect();
		Rights rights = new Rights();
		rights.setResource("test rights");
		dcterms.setRights(rights);
		dcterms.clearChoiceListSelect();
		Source source = new Source();
		source.setResource("test source");
		dcterms.setSource(source);
		dcterms.clearChoiceListSelect();
		Subject subject = new Subject();
		subject.setResource("test subject");
		dcterms.setSubject(subject);
		dcterms.clearChoiceListSelect();
		Title title = new Title();
		title.setString("test title");
		dcterms.setTitle(title);
		dcterms.clearChoiceListSelect();
		Type type = new Type();
		type.setResource("test type");
		dcterms.setType(type);
		
		dctermsList.add(dcterms);
		return dctermsList;
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
