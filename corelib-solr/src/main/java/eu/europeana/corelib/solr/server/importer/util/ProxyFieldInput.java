/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
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
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 * Constructor for the Proxy Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class ProxyFieldInput {

	
	private ProxyFieldInput(){
		
	}
	/**
	 * Create a SolrInputDocument with the Proxy fields filled in
	 * 
	 * @param providedCHO
	 *            The JiBX ProvidedCHO Entity
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The altered SolrInputDocument with the Proxy fields filled in
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SolrInputDocument createProxySolrFields(
			ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument)
			throws InstantiationException, IllegalAccessException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY.toString(),
				providedCHO.getAbout());
		solrInputDocument.addField(EdmLabel.EDM_TYPE.toString(), SolrUtils
				.exists(EdmType.class, (providedCHO.getType())).toString());
		solrInputDocument.addField(
				EdmLabel.EDM_CURRENT_LOCATION.toString(),
				SolrUtils.exists(ResourceType.class,
						(providedCHO.getCurrentLocation())).getResource());

		solrInputDocument.addField(EdmLabel.ORE_PROXY_FOR.toString(),
				SolrUtils.exists(String.class, providedCHO.getAbout()));

		// Retrieve the dcterms namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCTermsType.Choice> dcTermsList = providedCHO
				.getChoiceList();
		if (dcTermsList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCTermsType.Choice choice : dcTermsList) {
					solrInputDocument.addField(EdmLabel.DCTERMS_ALTERNATIVE
							.toString(), choice.getAlternative().getString());
					solrInputDocument.addField(EdmLabel.DCTERMS_CONFORMS_TO
							.toString(), choice.getConformsTo().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_CREATED
							.toString(), choice.getCreated().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_EXTENT
							.toString(), choice.getExtent().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_HAS_FORMAT
							.toString(), choice.getHasFormat().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_HAS_PART
							.toString(), choice.getHasPart().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_HAS_VERSION
							.toString(), choice.getHasVersion().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_IS_FORMAT_OF
							.toString(), choice.getIsFormatOf().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_IS_PART_OF
							.toString(), choice.getIsPartOf().getResource());
					solrInputDocument.addField(
							EdmLabel.DCTERMS_IS_REFERENCED_BY.toString(),
							choice.getIsReferencedBy().getResource());
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_IS_REPLACED_BY.toString(),
									choice.getIsReplacedBy().getResource());
					solrInputDocument
							.addField(
									EdmLabel.DCTERMS_IS_REQUIRED_BY.toString(),
									choice.getIsRequiredBy().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_ISSUED
							.toString(), choice.getIssued().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_IS_VERSION_OF
							.toString(), choice.getIsVersionOf().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_MEDIUM
							.toString(), choice.getMedium().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_PROVENANCE
							.toString(), choice.getProvenance().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_REFERENCES
							.toString(), choice.getReferences().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_REPLACES
							.toString(), choice.getReplaces().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_REQUIRES
							.toString(), choice.getRequires().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_SPATIAL
							.toString(), choice.getSpatial().getResource());
					solrInputDocument.addField(
							EdmLabel.DCTERMS_TABLE_OF_CONTENTS.toString(),
							choice.getTableOfContents().getResource());
					solrInputDocument.addField(EdmLabel.DCTERMS_TEMPORAL
							.toString(), choice.getTemporal().getResource());
			}
		}

		// Retrieve the dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCType.Choice> dcList = providedCHO
				.getChoiceList1s();
		if (dcList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCType.Choice choice : dcList) {
					solrInputDocument.addField(EdmLabel.DC_CONTRIBUTOR
							.toString(), choice.getContributor().getResource());
					solrInputDocument.addField(EdmLabel.DC_COVERAGE.toString(),
							choice.getCoverage().getResource());
					solrInputDocument.addField(EdmLabel.DC_CREATOR.toString(),
							choice.getCreator().getResource());
					solrInputDocument.addField(EdmLabel.DC_DATE.toString(),
							choice.getDate().getResource());
					solrInputDocument.addField(EdmLabel.DC_DESCRIPTION
							.toString(), choice.getDescription().getResource());
					solrInputDocument.addField(EdmLabel.DC_FORMAT.toString(),
							choice.getFormat().getResource());
					solrInputDocument.addField(EdmLabel.DC_IDENTIFIER
							.toString(), choice.getIdentifier().getString());
					solrInputDocument.addField(EdmLabel.DC_LANGUAGE.toString(),
							choice.getLanguage().getString());
					solrInputDocument.addField(
							EdmLabel.DC_PUBLISHER.toString(), choice
									.getPublisher().getResource());
					solrInputDocument.addField(EdmLabel.DC_RELATION.toString(),
							choice.getRelation().getResource());
					solrInputDocument.addField(EdmLabel.PRX_DC_RIGHTS
							.toString(), choice.getRights().getResource());
					solrInputDocument.addField(EdmLabel.DC_SOURCE.toString(),
							choice.getSource().getResource());
					solrInputDocument.addField(EdmLabel.DC_SUBJECT.toString(),
							choice.getSubject().getResource());
					solrInputDocument.addField(EdmLabel.DC_TITLE.toString(),
							choice.getTitle().getString());
					solrInputDocument.addField(EdmLabel.DC_TYPE.toString(),
							choice.getType().getResource());
			}
		}
		return solrInputDocument;
	}

	/**
	 * Construct the fields of a Proxy MongoDB Entity. The entity is
	 * instantiated when reading the ProvidedCHO
	 * 
	 * @param mongoProxy
	 *            The Proxy MongoDB Entity to save or update
	 * @param providedCHO
	 *            The ProvidedCHO JiBX Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the entity to
	 * @return The MongoDB Proxy Entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
			ProvidedCHOType providedCHO, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		mongoProxy.setAbout(providedCHO.getAbout());

		mongoProxy.setEdmCurrentLocation(SolrUtils.exists(ResourceType.class,
				(providedCHO.getCurrentLocation())).getResource());
		mongoProxy.setEdmType(DocType.get(SolrUtils.exists(EdmType.class,
				(providedCHO.getType())).toString()));

		mongoProxy.setProxyFor(SolrUtils.exists(String.class,
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

					alternatives.add(dcTerm.getAlternative().getString());
					conformsTo.add(dcTerm.getConformsTo().getResource());
					created.add(dcTerm.getCreated().getResource());
					extent.add(dcTerm.getExtent().getResource());
					hasFormat.add(dcTerm.getHasFormat().getResource());
					hasPart.add(dcTerm.getHasPart().getResource());
					hasVersion.add(dcTerm.getHasVersion().getResource());
					isFormatOf.add(dcTerm.getIsFormatOf().getResource());
					isPartOf.add(dcTerm.getIsPartOf().getResource());
					isReferencedBy.add(dcTerm.getIsReferencedBy().getResource());
					isReplacedBy.add(dcTerm.getIsReplacedBy().getResource());
					isRequiredBy.add(dcTerm.getIsRequiredBy().getResource());
					issued.add(dcTerm.getIssued().getResource());
					isVersionOf.add(dcTerm.getIsVersionOf().getResource());
					medium.add(dcTerm.getMedium().getResource());
					provenance.add(dcTerm.getProvenance().getResource());
					references.add(dcTerm.getReferences().getResource());
					replaces.add(dcTerm.getReplaces().getResource());
					requires.add(dcTerm.getRequires().getResource());
					spatial.add(dcTerm.getSpatial().getResource());
					tableOfContents
							.add(dcTerm.getTableOfContents().getResource());
					temporal.add(dcTerm.getTemporal().getResource());
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
					contributor.add(dc.getContributor().getResource());
					coverage.add(dc.getCoverage().getResource());
					creator.add(dc.getCreator().getResource());
					date.add(dc.getDate().getResource());
					description.add(dc.getDescription().getResource());
					format.add(dc.getFormat().getResource());
					identifier.add(dc.getIdentifier().getString());
					language.add(dc.getLanguage().getString());
					publisher.add(dc.getPublisher().getResource());
					relation.add(dc.getRelation().getResource());
					rights.add(dc.getRights().getResource());
					source.add(dc.getSource().getResource());
					subject.add(dc.getSubject().getResource());
					title.add(dc.getTitle().getString());
					type.add(dc.getType().getResource());
			}
		}
		mongoProxy.setDctermsAlternative(alternatives
				.toArray(new String[alternatives.size()]));
		mongoProxy.setDctermsConformsTo(conformsTo
				.toArray(new String[conformsTo.size()]));
		mongoProxy
				.setDctermsCreated(created.toArray(new String[created.size()]));
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
		if (mongoServer.searchByAbout(ProxyImpl.class, mongoProxy.getAbout()) != null) {
			MongoUtils.updateProxy(mongoProxy, mongoServer);
		} else {
		mongoServer.getDatastore().save(mongoProxy);
		}
		return mongoProxy;
	}

	/**
	 * Set the ProxyIn field after the aggregations are created
	 * 
	 * @param proxy
	 *            The MongoDB proxy Entity
	 * @param aggregation
	 *            The JiBX Aggregation Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the Entity
	 * @return The proxy with the proxyIn field
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */

	public static ProxyImpl addProxyForMongo(ProxyImpl proxy,
			Aggregation aggregation, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		proxy.setProxyIn(SolrUtils.exists(String.class, aggregation.getAbout()));
		return proxy;
	}

	/**
	 * Set the ProxyIn field for a SolrInputDocument
	 * 
	 * @param aggregation
	 *            The JiBX Aggregation Entity
	 * @param solrInputDocument
	 *            The SolrInputDocument
	 * @return The SolrInputDocument with the ProxyIn field
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SolrInputDocument addProxyForSolr(Aggregation aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY_IN.toString(),
				SolrUtils.exists(String.class, aggregation.getAbout()));
		return solrInputDocument;
	}
	
	public static void deleteProxyFromMongo(String about, MongoDBServer mongoServer){
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
