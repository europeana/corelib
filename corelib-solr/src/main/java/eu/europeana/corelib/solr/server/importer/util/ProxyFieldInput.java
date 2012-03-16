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

	private ProxyFieldInput() {

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
				if (choice.getAlternative() != null) {
					solrInputDocument.addField(EdmLabel.DCTERMS_ALTERNATIVE
							.toString(), choice.getAlternative().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_CONFORMS_TO, choice.getConformsTo());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_CREATED, choice.getCreated());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_EXTENT, choice.getExtent());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_HAS_FORMAT, choice.getHasFormat());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_HAS_PART, choice.getHasPart());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_HAS_VERSION, choice.getHasVersion());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_IS_FORMAT_OF, choice.getIsFormatOf());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_IS_PART_OF, choice.getIsPartOf());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_IS_REFERENCED_BY, choice.getIsReferencedBy());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_IS_REPLACED_BY, choice.getIsReplacedBy());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_IS_REQUIRED_BY, choice.getIsRequiredBy());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_ISSUED, choice.getIssued());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_IS_VERSION_OF, choice.getIsVersionOf());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_MEDIUM, choice.getMedium());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_PROVENANCE, choice.getProvenance());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_REFERENCES, choice.getReferences());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_REPLACES, choice.getReplaces());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_REQUIRES, choice.getRequires());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_SPATIAL, choice.getSpatial());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_TABLE_OF_CONTENTS, choice.getTableOfContents());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DCTERMS_TEMPORAL, choice.getTemporal());
			}
		}

		// Retrieve the dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.DCType.Choice> dcList = providedCHO
				.getChoiceList1s();
		if (dcList != null) {
			for (eu.europeana.corelib.definitions.jibx.DCType.Choice choice : dcList) {
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_CONTRIBUTOR, choice.getContributor());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_COVERAGE, choice.getCoverage());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_CREATOR, choice.getCreator());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_DATE, choice.getDate());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_DESCRIPTION, choice.getDescription());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_FORMAT, choice.getFormat());
				if (choice.getIdentifier() != null) {
					solrInputDocument.addField(EdmLabel.DC_IDENTIFIER
							.toString(), choice.getIdentifier().getString());
				}
				if (choice.getLanguage() != null) {
					solrInputDocument.addField(EdmLabel.DC_LANGUAGE.toString(),
							choice.getLanguage().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_PUBLISHER, choice.getPublisher());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_RELATION, choice.getRelation());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.PRX_DC_RIGHTS, choice.getRights());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_SOURCE, choice.getSource());
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_SUBJECT, choice.getSubject());
				if (choice.getTitle() != null) {
					solrInputDocument.addField(EdmLabel.DC_TITLE.toString(),
							choice.getTitle().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument, EdmLabel.DC_TYPE, choice.getType());

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

				if (dcTerm.getAlternative() != null) {
					alternatives.add(dcTerm.getAlternative().getString());
				}
				SolrUtils.addResourceOrLiteralType(conformsTo, dcTerm.getConformsTo());
				SolrUtils.addResourceOrLiteralType(created, dcTerm.getCreated());
				SolrUtils.addResourceOrLiteralType(extent, dcTerm.getExtent());
				SolrUtils.addResourceOrLiteralType(hasFormat, dcTerm.getHasFormat());
				SolrUtils.addResourceOrLiteralType(hasPart, dcTerm.getHasPart());
				SolrUtils.addResourceOrLiteralType(hasVersion, dcTerm.getHasVersion());
				SolrUtils.addResourceOrLiteralType(isFormatOf, dcTerm.getIsFormatOf());
				SolrUtils.addResourceOrLiteralType(isPartOf, dcTerm.getIsPartOf());
				SolrUtils.addResourceOrLiteralType(isReferencedBy, dcTerm.getIsReferencedBy());
				SolrUtils.addResourceOrLiteralType(isReplacedBy, dcTerm.getIsReplacedBy());
				SolrUtils.addResourceOrLiteralType(isRequiredBy, dcTerm.getIsRequiredBy());
				SolrUtils.addResourceOrLiteralType(issued, dcTerm.getIssued());
				SolrUtils.addResourceOrLiteralType(isVersionOf, dcTerm.getIsVersionOf());
				SolrUtils.addResourceOrLiteralType(medium, dcTerm.getMedium());
				SolrUtils.addResourceOrLiteralType(provenance, dcTerm.getProvenance());
				SolrUtils.addResourceOrLiteralType(references, dcTerm.getReferences());
				SolrUtils.addResourceOrLiteralType(replaces, dcTerm.getReplaces());
				SolrUtils.addResourceOrLiteralType(requires, dcTerm.getRequires());
				SolrUtils.addResourceOrLiteralType(spatial, dcTerm.getSpatial());
				SolrUtils.addResourceOrLiteralType(tableOfContents, dcTerm.getTableOfContents());
				SolrUtils.addResourceOrLiteralType(temporal, dcTerm.getTemporal());
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
				SolrUtils.addResourceOrLiteralType(contributor, dc.getContributor());
				SolrUtils.addResourceOrLiteralType(coverage, dc.getCoverage());
				SolrUtils.addResourceOrLiteralType(creator, dc.getCreator());
				SolrUtils.addResourceOrLiteralType(date, dc.getDate());
				SolrUtils.addResourceOrLiteralType(description, dc.getDescription());
				SolrUtils.addResourceOrLiteralType(format, dc.getFormat());
				if (dc.getIdentifier() != null) {
					identifier.add(dc.getIdentifier().getString());
				}
				if (dc.getLanguage() != null) {
					language.add(dc.getLanguage().getString());
				}
				SolrUtils.addResourceOrLiteralType(publisher, dc.getPublisher());
				SolrUtils.addResourceOrLiteralType(relation, dc.getRelation());
				SolrUtils.addResourceOrLiteralType(rights, dc.getRights());
				SolrUtils.addResourceOrLiteralType(source, dc.getSource());
				SolrUtils.addResourceOrLiteralType(subject, dc.getSubject());
				if (dc.getTitle() != null) {
					title.add(dc.getTitle().getString());
				}
				SolrUtils.addResourceOrLiteralType(type, dc.getType());
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

	public static void deleteProxyFromMongo(String about,
			MongoDBServer mongoServer) {
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
