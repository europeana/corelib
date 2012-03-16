package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.jibx.DCTermsType;
import eu.europeana.corelib.definitions.jibx.DCTermsType.Choice;
import eu.europeana.corelib.definitions.jibx.DCType;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.server.importer.util.ProxyFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class ProxyFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private MongoDBServer mongoServer;

	@Test
	public void testProxy() {
		// The fields of the proxy come from the ProvidedCHO
		ProvidedCHOType proxy = createProxyFields();
		testMongo(proxy);
		testSolr(proxy);
	}

	private void testSolr(ProvidedCHOType proxy) {
		SolrInputDocument solrDocument = new SolrInputDocument();
		try {
			solrDocument = ProxyFieldInput.createProxySolrFields(proxy,
					solrDocument);
			assertEquals(proxy.getAbout(),
					solrDocument.getFieldValue(EdmLabel.ORE_PROXY.toString()));
			assertEquals(proxy.getCurrentLocation().getResource(),
					solrDocument.getFieldValue(EdmLabel.EDM_CURRENT_LOCATION
							.toString()));
			assertEquals(proxy.getType().toString(),
					solrDocument.getFieldValue(EdmLabel.EDM_TYPE.toString()));
			List<DCTermsType.Choice> dcterms = proxy.getChoiceList();
			assertEquals(dcterms.get(0).getAlternative().getString(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_ALTERNATIVE
							.toString()));
			assertEquals(dcterms.get(0).getConformsTo().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_CONFORMS_TO
							.toString()));
			assertEquals(dcterms.get(0).getCreated().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_CREATED
							.toString()));
			assertEquals(dcterms.get(0).getExtent().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_EXTENT
							.toString()));
			assertEquals(dcterms.get(0).getHasFormat().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_HAS_FORMAT
							.toString()));
			assertEquals(dcterms.get(0).getHasPart().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_HAS_PART
							.toString()));
			assertEquals(dcterms.get(0).getHasVersion().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_HAS_VERSION
							.toString()));
			assertEquals(dcterms.get(0).getIsFormatOf().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_IS_FORMAT_OF
							.toString()));
			assertEquals(dcterms.get(0).getIsPartOf().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_IS_PART_OF
							.toString()));
			assertEquals(dcterms.get(0).getIsReferencedBy().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.DCTERMS_IS_REFERENCED_BY
									.toString()));
			assertEquals(dcterms.get(0).getIsReplacedBy().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_IS_REPLACED_BY
							.toString()));
			assertEquals(dcterms.get(0).getIsRequiredBy().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_IS_REQUIRED_BY
							.toString()));
			assertEquals(dcterms.get(0).getIssued().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_ISSUED
							.toString()));
			assertEquals(dcterms.get(0).getIsVersionOf().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_IS_VERSION_OF
							.toString()));
			assertEquals(dcterms.get(0).getMedium().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_MEDIUM
							.toString()));
			assertEquals(dcterms.get(0).getProvenance().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_PROVENANCE
							.toString()));
			assertEquals(dcterms.get(0).getReferences().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_REFERENCES
							.toString()));
			assertEquals(dcterms.get(0).getReplaces().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_REPLACES
							.toString()));
			assertEquals(dcterms.get(0).getRequires().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_REQUIRES
							.toString()));
			assertEquals(dcterms.get(0).getSpatial().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_SPATIAL
							.toString()));
			assertEquals(dcterms.get(0).getTableOfContents().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.DCTERMS_TABLE_OF_CONTENTS
									.toString()));
			assertEquals(dcterms.get(0).getTemporal().getResource(),
					solrDocument.getFieldValue(EdmLabel.DCTERMS_TEMPORAL
							.toString()));

			List<DCType.Choice> dc = proxy.getChoiceList1s();
			assertEquals(dc.get(0).getContributor().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_CONTRIBUTOR
							.toString()));
			assertEquals(dc.get(0).getCoverage().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_COVERAGE.toString()));
			assertEquals(dc.get(0).getCreator().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_CREATOR.toString()));
			assertEquals(dc.get(0).getDate().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_DATE.toString()));
			assertEquals(dc.get(0).getDescription().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_DESCRIPTION
							.toString()));
			assertEquals(dc.get(0).getFormat().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_FORMAT.toString()));
			assertEquals(dc.get(0).getIdentifier().getString(),
					solrDocument.getFieldValue(EdmLabel.DC_IDENTIFIER
							.toString()));
			assertEquals(dc.get(0).getLanguage().getString(),
					solrDocument.getFieldValue(EdmLabel.DC_LANGUAGE.toString()));
			assertEquals(
					dc.get(0).getPublisher().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_PUBLISHER.toString()));
			assertEquals(dc.get(0).getRelation().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_RELATION.toString()));
			assertEquals(dc.get(0).getRights().getResource(),
					solrDocument.getFieldValue(EdmLabel.PRX_DC_RIGHTS
							.toString()));
			assertEquals(dc.get(0).getSource().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_SOURCE.toString()));
			assertEquals(dc.get(0).getSubject().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_SUBJECT.toString()));
			assertEquals(dc.get(0).getTitle().getString(),
					solrDocument.getFieldValue(EdmLabel.DC_TITLE.toString()));
			assertEquals(dc.get(0).getType().getResource(),
					solrDocument.getFieldValue(EdmLabel.DC_TYPE.toString()));

			// continue from here
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testMongo(ProvidedCHOType proxy) {
		ProxyImpl mongoProxy = new ProxyImpl();
		try {
			mongoProxy = ProxyFieldInput.createProxyMongoFields(mongoProxy,
					proxy, mongoServer);
			assertEquals(proxy.getAbout(), mongoProxy.getAbout());
			assertEquals(proxy.getType().toString(), mongoProxy.getEdmType()
					.toString());
			List<Choice> dcterms = proxy.getChoiceList();
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

			List<DCType.Choice> dc = proxy.getChoiceList1s();
			assertEquals(dc.get(0).getContributor().getResource(),
					mongoProxy.getDcContributor()[0]);
			assertEquals(dc.get(0).getCoverage().getResource(),
					mongoProxy.getDcCoverage()[0]);
			assertEquals(dc.get(0).getCreator().getResource(),
					mongoProxy.getDcCreator()[0]);
			assertEquals(dc.get(0).getDate().getResource(),
					mongoProxy.getDcDate()[0]);
			assertEquals(dc.get(0).getDescription().getResource(),
					mongoProxy.getDcDescription()[0]);
			assertEquals(dc.get(0).getFormat().getResource(),
					mongoProxy.getDcFormat()[0]);
			assertEquals(dc.get(0).getIdentifier().getString(),
					mongoProxy.getDcIdentifier()[0]);
			assertEquals(dc.get(0).getLanguage().getString(),
					mongoProxy.getDcLanguage()[0]);
			assertEquals(dc.get(0).getPublisher().getResource(),
					mongoProxy.getDcPublisher()[0]);
			assertEquals(dc.get(0).getRelation().getResource(),
					mongoProxy.getDcRelation()[0]);
			assertEquals(dc.get(0).getRights().getResource(),
					mongoProxy.getDcRights()[0]);
			assertEquals(dc.get(0).getSource().getResource(),
					mongoProxy.getDcSource()[0]);
			assertEquals(dc.get(0).getSubject().getResource(),
					mongoProxy.getDcSubject()[0]);
			assertEquals(dc.get(0).getTitle().getString(),
					mongoProxy.getDcTitle()[0]);
			assertEquals(dc.get(0).getType().getResource(),
					mongoProxy.getDcType()[0]);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ProvidedCHOType createProxyFields() {
		ProvidedCHOType proxy = new ProvidedCHOType();
		proxy.setAbout("test about");
		ResourceType currentLocation = new ResourceType();
		currentLocation.setResource("test current location");
		proxy.setCurrentLocation(currentLocation);
		proxy.setType(EdmType.IMAGE);
		proxy.setChoiceList(createDcTermsList());
		proxy.setChoiceList1s(createDcList());
		return proxy;
	}

	private List<DCType.Choice> createDcList() {
		List<DCType.Choice> dcList = new ArrayList<DCType.Choice>();
		DCType.Choice dc = new DCType.Choice();
		ResourceOrLiteralType contributor = new ResourceOrLiteralType();
		contributor.setResource("test contributor");
		dc.setContributor(contributor);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType coverage = new ResourceOrLiteralType();
		coverage.setResource("test coverage");
		dc.setCoverage(coverage);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType creator = new ResourceOrLiteralType();
		creator.setResource("test creator");
		dc.setCreator(creator);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType date = new ResourceOrLiteralType();
		date.setResource("test date");
		dc.setDate(date);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType description = new ResourceOrLiteralType();
		description.setResource("test description");
		dc.setDescription(description);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType format = new ResourceOrLiteralType();
		format.setResource("test format");
		dc.setFormat(format);
		dc.clearChoiceList1Select();
		LiteralType identifier = new LiteralType();
		identifier.setString("test identifier");
		dc.setIdentifier(identifier);
		dc.clearChoiceList1Select();
		LiteralType language = new LiteralType();
		language.setString("test language");
		dc.setLanguage(language);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType publisher = new ResourceOrLiteralType();
		publisher.setResource("test publisher");
		dc.setPublisher(publisher);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType relation = new ResourceOrLiteralType();
		relation.setResource("test relation");
		dc.setRelation(relation);
		dc.clearChoiceList1Select();
		Rights1 rights = new Rights1();
		rights.setResource("test rights");
		dc.setRights(rights);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType source = new ResourceOrLiteralType();
		source.setResource("test source");
		dc.setSource(source);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType subject = new ResourceOrLiteralType();
		subject.setResource("test subject");
		dc.setSubject(subject);
		dc.clearChoiceList1Select();
		LiteralType title = new LiteralType();
		title.setString("test title");
		dc.setTitle(title);
		dc.clearChoiceList1Select();
		ResourceOrLiteralType type = new ResourceOrLiteralType();
		type.setResource("test type");
		dc.setType(type);
		dcList.add(dc);
		return dcList;
	}

	private List<Choice> createDcTermsList() {
		List<Choice> dctermsList = new ArrayList<Choice>();

		Choice dcterms = new Choice();
		LiteralType alternative = new LiteralType();
		alternative.setString("test alternative");
		dcterms.setAlternative(alternative);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType conformsTo = new ResourceOrLiteralType();
		conformsTo.setResource("test conforms to");
		dcterms.setConformsTo(conformsTo);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType created = new ResourceOrLiteralType();
		created.setResource("test created");
		dcterms.setCreated(created);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType extent = new ResourceOrLiteralType();
		extent.setResource("test extent");
		dcterms.setExtent(extent);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType hasFormat = new ResourceOrLiteralType();
		hasFormat.setResource("test hasFormat");
		dcterms.setHasFormat(hasFormat);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType hasPart = new ResourceOrLiteralType();
		hasPart.setResource("test hasPart");
		dcterms.setHasPart(hasPart);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType hasVersion = new ResourceOrLiteralType();
		hasVersion.setResource("test hasVersion");
		dcterms.setHasVersion(hasVersion);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType isFormatOf = new ResourceOrLiteralType();
		isFormatOf.setResource("test isFormatOf");
		dcterms.setIsFormatOf(isFormatOf);
		dcterms.clearChoiceListSelect();
		IsPartOf isPartOf = new IsPartOf();
		isPartOf.setResource("test isPartOf");
		dcterms.setIsPartOf(isPartOf);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType isReferencedBy = new ResourceOrLiteralType();
		isReferencedBy.setResource("test isReferencedBy");
		dcterms.setIsReferencedBy(isReferencedBy);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType isReplacedBy = new ResourceOrLiteralType();
		isReplacedBy.setResource("test isReplacedBy");
		dcterms.setIsReplacedBy(isReplacedBy);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType isRequiredBy = new ResourceOrLiteralType();
		isRequiredBy.setResource("test isRequiredBy");
		dcterms.setIsRequiredBy(isRequiredBy);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType issued = new ResourceOrLiteralType();
		issued.setResource("test issued");
		dcterms.setIssued(issued);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType isVersionOf = new ResourceOrLiteralType();
		isVersionOf.setResource("test isVersionOf");
		dcterms.setIsVersionOf(isVersionOf);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType medium = new ResourceOrLiteralType();
		medium.setResource("test medium");
		dcterms.setMedium(medium);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType provenance = new ResourceOrLiteralType();
		provenance.setResource("test provenance");
		dcterms.setProvenance(provenance);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType references = new ResourceOrLiteralType();
		references.setResource("test references");
		dcterms.setReferences(references);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType replaces = new ResourceOrLiteralType();
		replaces.setResource("test replaces");
		dcterms.setReplaces(replaces);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType requires = new ResourceOrLiteralType();
		requires.setResource("test requires");
		dcterms.setRequires(requires);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType spatial = new ResourceOrLiteralType();
		spatial.setResource("test spatial");
		dcterms.setSpatial(spatial);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType tableOfContents = new ResourceOrLiteralType();
		tableOfContents.setResource("test TOC");
		dcterms.setTableOfContents(tableOfContents);
		dcterms.clearChoiceListSelect();
		ResourceOrLiteralType temporal = new ResourceOrLiteralType();
		temporal.setResource("temporal");
		dcterms.setTemporal(temporal);
		dctermsList.add(dcterms);
		return dctermsList;
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
