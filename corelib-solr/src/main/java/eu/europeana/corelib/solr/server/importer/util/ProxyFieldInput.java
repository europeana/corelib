package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.DCTermsType.Choice;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.SolrUtil;

public class ProxyFieldInput {
	public static SolrInputDocument createProxySolrFields(
			ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument)
			throws InstantiationException, IllegalAccessException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY.toString(),
				providedCHO.getAbout());
		solrInputDocument.addField(EdmLabel.EDM_TYPE.toString(), SolrUtil
				.exists(EdmType.class, (providedCHO.getType())).toString());
		solrInputDocument.addField(
				EdmLabel.EDM_CURRENT_LOCATION.toString(),
				SolrUtil.exists(ResourceType.class,
						(providedCHO.getCurrentLocation())).getResource());

		solrInputDocument.addField(EdmLabel.ORE_PROXY_FOR.toString(),
				SolrUtil.exists(String.class, providedCHO.getAbout()));

		// Retrieve the dcterms namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCTermsType.Choice> dcTermsList = providedCHO
				.getChoiceList();
		if(dcTermsList!=null){
		for (eu.europeana.corelib.definitions.jibx.DCTermsType.Choice choice : dcTermsList) {
			if (choice.ifAlternative()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_ALTERNATIVE
						.toString(), choice.getAlternative().getString());
			}
			if (choice.ifConformsTo()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_CONFORMS_TO
						.toString(), choice.getConformsTo().getResource());
			}
			if (choice.ifCreated()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_CREATED.toString(),
						choice.getCreated().getResource());
			}
			if (choice.ifExtent()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_EXTENT.toString(),
						choice.getExtent().getResource());
			}
			if (choice.ifHasFormat()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_HAS_FORMAT
						.toString(), choice.getHasFormat().getResource());
			}
			if (choice.ifHasPart()) {
				solrInputDocument.addField(
						EdmLabel.DCTERMS_HAS_PART.toString(), choice
								.getHasPart().getResource());
			}
			if (choice.ifHasVersion()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_HAS_VERSION
						.toString(), choice.getHasVersion().getResource());
			}
			if (choice.ifIsFormatOf()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_IS_FORMAT_OF
						.toString(), choice.getIsFormatOf().getResource());
			}
			if (choice.ifIsPartOf()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_IS_PART_OF
						.toString(), choice.getIsPartOf().getResource());
			}
			if (choice.ifIsReferencedBy()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_IS_REFERENCED_BY
						.toString(), choice.getIsReferencedBy().getResource());
			}
			if (choice.ifIsReplacedBy()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_IS_REPLACED_BY
						.toString(), choice.getIsReplacedBy().getResource());
			}
			if (choice.ifIsRequiredBy()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_IS_REQUIRED_BY
						.toString(), choice.getIsRequiredBy().getResource());
			}
			if (choice.ifIssued()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_ISSUED.toString(),
						choice.getIssued().getResource());
			}
			if (choice.ifIsVersionOf()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_IS_VERSION_OF
						.toString(), choice.getIsVersionOf().getResource());
			}
			if (choice.ifMedium()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_MEDIUM.toString(),
						choice.getMedium().getResource());
			}
			if (choice.ifProvenance()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_PROVENANCE
						.toString(), choice.getProvenance().getResource());
			}
			if (choice.ifReferences()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_REFERENCES
						.toString(), choice.getReferences().getResource());
			}
			if (choice.ifReplaces()) {
				solrInputDocument.addField(
						EdmLabel.DCTERMS_REPLACES.toString(), choice
								.getReplaces().getResource());
			}
			if (choice.ifRequires()) {
				solrInputDocument.addField(
						EdmLabel.DCTERMS_REQUIRES.toString(), choice
								.getRequires().getResource());
			}
			if (choice.ifSpatial()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_SPATIAL.toString(),
						choice.getSpatial().getResource());
			}
			if (choice.ifTableOfContents()) {
				solrInputDocument.addField(EdmLabel.DCTERMS_TABLE_OF_CONTENTS
						.toString(), choice.getTableOfContents().getResource());
			}
			if (choice.ifTemporal()) {
				solrInputDocument.addField(
						EdmLabel.DCTERMS_TEMPORAL.toString(), choice
								.getTemporal().getResource());
			}
		}
		}
		
		// Retrieve the dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCType.Choice> dcList = providedCHO
				.getChoiceList1s();
		if(dcList!=null){
		for (eu.europeana.corelib.definitions.jibx.DCType.Choice choice : dcList) {
			if (choice.ifContributor()) {
				solrInputDocument.addField(EdmLabel.DC_CONTRIBUTOR.toString(),
						choice.getContributor().getResource());
			}
			if (choice.ifCoverage()) {
				solrInputDocument.addField(EdmLabel.DC_COVERAGE.toString(),
						choice.getCoverage().getResource());
			}
			if (choice.ifCreator()) {
				solrInputDocument.addField(EdmLabel.DC_CREATOR.toString(),
						choice.getCreator().getResource());
			}
			if (choice.ifDate()) {
				solrInputDocument.addField(EdmLabel.DC_DATE.toString(), choice
						.getDate().getResource());
			}
			if (choice.ifDescription()) {
				solrInputDocument.addField(EdmLabel.DC_DESCRIPTION.toString(),
						choice.getDescription().getResource());
			}
			if (choice.ifFormat()) {
				solrInputDocument.addField(EdmLabel.DC_FORMAT.toString(),
						choice.getFormat().getResource());
			}
			if (choice.ifIdentifier()) {
				solrInputDocument.addField(EdmLabel.DC_IDENTIFIER.toString(),
						choice.getIdentifier().getString());
			}
			if (choice.ifLanguage()) {
				solrInputDocument.addField(EdmLabel.DC_LANGUAGE.toString(),
						choice.getLanguage().getString());
			}
			if (choice.ifPublisher()) {
				solrInputDocument.addField(EdmLabel.DC_PUBLISHER.toString(),
						choice.getPublisher().getResource());
			}
			if (choice.ifRelation()) {
				solrInputDocument.addField(EdmLabel.DC_RELATION.toString(),
						choice.getRelation().getResource());
			}
			if (choice.ifRights()) {
				solrInputDocument.addField(EdmLabel.PRX_DC_RIGHTS.toString(),
						choice.getRights().getResource());
			}

			if (choice.ifSource()) {
				solrInputDocument.addField(EdmLabel.DC_SOURCE.toString(),
						choice.getSource().getResource());
			}

			if (choice.ifSubject()) {
				solrInputDocument.addField(EdmLabel.DC_SUBJECT.toString(),
						choice.getSubject().getResource());
			}

			if (choice.ifTitle()) {
				solrInputDocument.addField(EdmLabel.DC_TITLE.toString(), choice
						.getTitle().getString());
			}

			if (choice.ifType()) {
				solrInputDocument.addField(EdmLabel.DC_TYPE.toString(), choice
						.getType().getResource());
			}
		}
		}
		return solrInputDocument;
	}

	public static ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
			ProvidedCHOType providedCHO, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		mongoProxy.setAbout(providedCHO.getAbout());

		mongoProxy.setEdmCurrentLocation(SolrUtil.exists(ResourceType.class,
				(providedCHO.getCurrentLocation())).getResource());
		mongoProxy.setEdmType(DocType.get(SolrUtil.exists(EdmType.class,
				(providedCHO.getType())).toString()));

		mongoProxy.setProxyFor(SolrUtil.exists(String.class,
				providedCHO.getAbout()));

		List<String> alternatives = new ArrayList<String>();
		List<String> conformsTo = new ArrayList<String>();
		List<String> created = new ArrayList<String>();
		List<String> extent = new ArrayList<String>();
		List<String> hasFormat = new ArrayList<String>();
		List<String> hasPart = new ArrayList<String>();
		List<String> hasVersion = new ArrayList<String>();
		List<String> isFormatOf = new ArrayList<String>();
		List<String> isPartOf = new ArrayList<String>();
		List<String> isReferencedBy = new ArrayList<String>();
		List<String> isReplacedBy = new ArrayList<String>();
		List<String> isRequiredBy = new ArrayList<String>();
		List<String> issued = new ArrayList<String>();
		List<String> isVersionOf = new ArrayList<String>();
		List<String> medium = new ArrayList<String>();
		List<String> provenance = new ArrayList<String>();
		List<String> references = new ArrayList<String>();
		List<String> replaces = new ArrayList<String>();
		List<String> requires = new ArrayList<String>();
		List<String> spatial = new ArrayList<String>();
		List<String> tableOfContents = new ArrayList<String>();
		List<String> temporal = new ArrayList<String>();

		List<Choice> dcTermsList = providedCHO.getChoiceList();
		List<eu.europeana.corelib.definitions.jibx.DCType.Choice> dcList = providedCHO
				.getChoiceList1s();

		if (dcTermsList != null) {
			for (Choice dcTerm : dcTermsList) {

				if (dcTerm.ifAlternative()) {
					alternatives.add(dcTerm.getAlternative().getString());
				}
				if (dcTerm.ifConformsTo()) {
					conformsTo.add(dcTerm.getConformsTo().getString());
				}
				if (dcTerm.ifCreated()) {
					created.add(dcTerm.getCreated().getString());
				}
				if (dcTerm.ifExtent()) {
					extent.add(dcTerm.getExtent().getString());
				}
				if (dcTerm.ifHasFormat()) {
					hasFormat.add(dcTerm.getHasFormat().getString());
				}
				if (dcTerm.ifHasPart()) {
					hasPart.add(dcTerm.getHasPart().getString());
				}
				if (dcTerm.ifHasVersion()) {
					hasVersion.add(dcTerm.getHasVersion().getString());
				}
				if (dcTerm.ifIsFormatOf()) {
					isFormatOf.add(dcTerm.getIsFormatOf().getString());
				}
				if (dcTerm.ifIsPartOf()) {
					isPartOf.add(dcTerm.getIsPartOf().getString());
				}
				if (dcTerm.ifIsReferencedBy()) {
					isReferencedBy.add(dcTerm.getIsReferencedBy().getString());
				}
				if (dcTerm.ifIsReplacedBy()) {
					isReplacedBy.add(dcTerm.getIsReplacedBy().getString());
				}
				if (dcTerm.ifIsRequiredBy()) {
					isRequiredBy.add(dcTerm.getIsRequiredBy().getString());
				}
				if (dcTerm.ifIssued()) {
					issued.add(dcTerm.getIssued().getString());
				}
				if (dcTerm.ifIsVersionOf()) {
					isVersionOf.add(dcTerm.getIsVersionOf().getString());
				}
				if (dcTerm.ifMedium()) {
					medium.add(dcTerm.getMedium().getString());
				}
				if (dcTerm.ifProvenance()) {
					provenance.add(dcTerm.getProvenance().getString());
				}
				if (dcTerm.ifReferences()) {
					references.add(dcTerm.getReferences().getString());
				}
				if (dcTerm.ifReplaces()) {
					replaces.add(dcTerm.getReplaces().getString());
				}
				if (dcTerm.ifRequires()) {
					requires.add(dcTerm.getRequires().getString());
				}
				if (dcTerm.ifSpatial()) {
					spatial.add(dcTerm.getSpatial().getString());
				}
				if (dcTerm.ifTableOfContents()) {
					tableOfContents
							.add(dcTerm.getTableOfContents().getString());
				}
				if (dcTerm.ifTemporal()) {
					temporal.add(dcTerm.getTemporal().getString());
				}
			}
		}
		List<String> contributor = new ArrayList<String>();
		List<String> coverage = new ArrayList<String>();
		List<String> creator = new ArrayList<String>();
		List<String> date = new ArrayList<String>();
		List<String> description = new ArrayList<String>();
		List<String> format = new ArrayList<String>();
		List<String> identifier = new ArrayList<String>();
		List<String> language = new ArrayList<String>();
		List<String> publisher = new ArrayList<String>();
		List<String> relation = new ArrayList<String>();
		List<String> rights = new ArrayList<String>();
		List<String> source = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		List<String> title = new ArrayList<String>();
		List<String> type = new ArrayList<String>();

		if (dcList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCType.Choice dc : dcList) {
				if (dc.ifContributor()) {
					contributor.add(dc.getContributor().getString());
				}
				if (dc.ifCoverage()) {
					coverage.add(dc.getCoverage().getString());
				}
				if (dc.ifCreator()) {
					creator.add(dc.getCreator().getString());
				}
				if (dc.ifDate()) {
					date.add(dc.getDate()	.getString());
				}
				if (dc.ifDescription()) {
					description.add(dc.getDescription().getString());
				}
				if (dc.ifFormat()) {
					format.add(dc.getFormat().getString());
				}
				if (dc.ifIdentifier()) {
					identifier.add(dc.getIdentifier().getString());
				}
				if (dc.ifLanguage()) {
					language.add(dc.getLanguage().getString());
				}
				if (dc.ifPublisher()) {
					publisher.add(dc.getPublisher().getString());
				}
				if (dc.ifRelation()) {
					relation.add(dc.getRelation().getString());
				}
				if (dc.ifRights()) {
					rights.add(dc.getRights().getString());
				}

				if (dc.ifSource()) {
					source.add(dc.getSource().getString());
				}

				if (dc.ifSubject()) {
					subject.add(dc.getSubject().getString());
				}

				if (dc.ifTitle()) {
					title.add(dc.getTitle().getString());
				}

				if (dc.ifType()) {
					type.add(dc.getType().getString());
				}
			}
		}
		mongoProxy.setDctermsAlternative(alternatives
				.toArray(new String[alternatives.size()]));
		mongoProxy.setDctermsConformsTo(conformsTo
				.toArray(new String[conformsTo.size()]));
		mongoProxy.setDctermsCreated(created.toArray(new String[created.size()]));
		mongoProxy.setDctermsExtent(extent.toArray(new String[extent.size()]));
		mongoProxy.setDctermsHasFormat(hasFormat.toArray(new String[hasFormat
				.size()]));
		mongoProxy
				.setDctermsHasPart(hasPart.toArray(new String[hasPart.size()]));
		mongoProxy.setDctermsHasVersion(hasVersion
				.toArray(new String[hasVersion.size()]));
		mongoProxy.setDctermsIsFormatOf(isFormatOf
				.toArray(new String[isFormatOf.size()]));
		mongoProxy.setDctermsIsPartOf(isPartOf.toArray(new String[isPartOf
				.size()]));
		mongoProxy.setDctermsIsReferencedBy(isReferencedBy
				.toArray(new String[isReferencedBy.size()]));
		mongoProxy.setDctermsIsReplacedBy(isReplacedBy
				.toArray(new String[isReplacedBy.size()]));
		mongoProxy.setDctermsIsRequiredBy(isRequiredBy
				.toArray(new String[isRequiredBy.size()]));
		mongoProxy.setDctermsIssued(issued.toArray(new String[issued.size()]));
		mongoProxy.setDctermsIsVersionOf(isVersionOf
				.toArray(new String[isVersionOf.size()]));
		mongoProxy.setDctermsMedium(medium.toArray(new String[medium.size()]));
		mongoProxy.setDctermsProvenance(provenance
				.toArray(new String[provenance.size()]));
		mongoProxy.setDctermsReferences(references
				.toArray(new String[references.size()]));
		mongoProxy.setDctermsReplaces(replaces.toArray(new String[replaces
				.size()]));
		mongoProxy.setDctermsRequires(requires.toArray(new String[requires
				.size()]));
		mongoProxy
				.setDctermsSpatial(spatial.toArray(new String[spatial.size()]));
		mongoProxy.setDctermsTemporal(temporal.toArray(new String[temporal
				.size()]));
		mongoProxy.setDctermsTOC(tableOfContents
				.toArray(new String[tableOfContents.size()]));

		mongoProxy.setDcContributor(contributor.toArray(new String[contributor
				.size()]));
		mongoProxy.setDcCoverage(coverage.toArray(new String[coverage.size()]));
		mongoProxy.setDcCreator(creator.toArray(new String[creator.size()]));
		mongoProxy.setDcDate(date.toArray(new String[date.size()]));
		mongoProxy.setDcDescription(description.toArray(new String[description
				.size()]));
		mongoProxy.setDcFormat(format.toArray(new String[format.size()]));
		mongoProxy.setDcIdentifier(identifier.toArray(new String[identifier
				.size()]));
		mongoProxy.setDcLanguage(language.toArray(new String[language.size()]));
		mongoProxy
				.setDcPublisher(publisher.toArray(new String[publisher.size()]));
		mongoProxy.setDcRelation(relation.toArray(new String[relation.size()]));
		mongoProxy.setDcRights(rights.toArray(new String[rights.size()]));
		mongoProxy.setDcSource(source.toArray(new String[source.size()]));
		mongoProxy.setDcSubject(subject.toArray(new String[subject.size()]));
		mongoProxy.setDcTitle(title.toArray(new String[title.size()]));
		mongoProxy.setDcType(type.toArray(new String[type.size()]));
		try{
		mongoServer.getDatastore().save(mongoProxy);
		}
		catch(Exception e)
		{
			//IF THAT HAPPENS THERE ARE NO UNIQUE IDENTIFIERS
		}
		return mongoProxy;
	}

	public static ProxyImpl addProxyForMongo(ProxyImpl proxy,
			Aggregation aggregation, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		//
		proxy.setProxyIn(SolrUtil.exists(String.class, aggregation.getAbout()));
		return proxy;
	}

	public static SolrInputDocument addProxyForSolr(Aggregation aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY_IN.toString(),
				SolrUtil.exists(String.class, aggregation.getAbout()));
		return solrInputDocument;
	}
}
