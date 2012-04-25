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
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

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
			ProvidedCHOType providedCHO, EdmMongoServer mongoServer)
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
		mongoProxy.setDctermsAlternative(StringArrayUtils.toArray(alternatives));
		mongoProxy.setDctermsConformsTo(StringArrayUtils.toArray(conformsTo));
		mongoProxy.setDctermsCreated(StringArrayUtils.toArray(created));
		mongoProxy.setDctermsExtent(StringArrayUtils.toArray(extent));
		mongoProxy.setDctermsHasFormat(StringArrayUtils.toArray(hasFormat));
		mongoProxy.setDctermsHasPart(StringArrayUtils.toArray(hasPart));
		mongoProxy.setDctermsHasVersion(StringArrayUtils.toArray(hasVersion));
		mongoProxy.setDctermsIsFormatOf(StringArrayUtils.toArray(isFormatOf));
		mongoProxy.setDctermsIsPartOf(StringArrayUtils.toArray(isPartOf));
		mongoProxy.setDctermsIsReferencedBy(StringArrayUtils.toArray(isReferencedBy));
		mongoProxy.setDctermsIsReplacedBy(StringArrayUtils.toArray(isReplacedBy));
		mongoProxy.setDctermsIsRequiredBy(StringArrayUtils.toArray(isRequiredBy));
		mongoProxy.setDctermsIssued(StringArrayUtils.toArray(issued));
		mongoProxy.setDctermsIsVersionOf(StringArrayUtils.toArray(isVersionOf));
		mongoProxy.setDctermsMedium(StringArrayUtils.toArray(medium));
		mongoProxy.setDctermsProvenance(StringArrayUtils.toArray(provenance));
		mongoProxy.setDctermsReferences(StringArrayUtils.toArray(references));
		mongoProxy.setDctermsReplaces(StringArrayUtils.toArray(replaces));
		mongoProxy.setDctermsRequires(StringArrayUtils.toArray(requires));
		mongoProxy.setDctermsSpatial(StringArrayUtils.toArray(spatial));
		mongoProxy.setDctermsTemporal(StringArrayUtils.toArray(temporal));
		mongoProxy.setDctermsTOC(StringArrayUtils.toArray(tableOfContents));
		mongoProxy.setDcContributor(StringArrayUtils.toArray(contributor));
		mongoProxy.setDcCoverage(StringArrayUtils.toArray(coverage));
		mongoProxy.setDcCreator(StringArrayUtils.toArray(creator));
		mongoProxy.setDcDate(StringArrayUtils.toArray(date));
		mongoProxy.setDcDescription(StringArrayUtils.toArray(description));
		mongoProxy.setDcFormat(StringArrayUtils.toArray(format));
		mongoProxy.setDcIdentifier(StringArrayUtils.toArray(identifier));
		mongoProxy.setDcLanguage(StringArrayUtils.toArray(language));
		mongoProxy.setDcPublisher(StringArrayUtils.toArray(publisher));
		mongoProxy.setDcRelation(StringArrayUtils.toArray(relation));
		mongoProxy.setDcRights(StringArrayUtils.toArray(rights));
		mongoProxy.setDcSource(StringArrayUtils.toArray(source));
		mongoProxy.setDcSubject(StringArrayUtils.toArray(subject));
		mongoProxy.setDcTitle(StringArrayUtils.toArray(title));
		mongoProxy.setDcType(StringArrayUtils.toArray(type));
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
			Aggregation aggregation, EdmMongoServer mongoServer)
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
			EdmMongoServer mongoServer) {
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
