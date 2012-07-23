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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
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
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static SolrInputDocument createProxySolrFields(ProxyType proxy,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException, MalformedURLException, IOException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY.toString(),
				proxy.getAbout());
		solrInputDocument.addField(EdmLabel.PROVIDER_EDM_TYPE.toString(),
				SolrUtils.exists(EdmType.class, (proxy.getType())).toString());
		solrInputDocument.addField(
				EdmLabel.PROXY_EDM_CURRENT_LOCATION_LAT.toString(),
				SolrUtils.exists(ResourceType.class,
						(proxy.getCurrentLocation())).getResource());
		solrInputDocument.addField(
				EdmLabel.PROXY_EDM_CURRENT_LOCATION_LONG.toString(),
				SolrUtils.exists(ResourceType.class,
						(proxy.getCurrentLocation())).getResource());
		solrInputDocument.addField(
				EdmLabel.PROXY_EDM_IS_NEXT_IN_SEQUENCE.toString(),
				SolrUtils.exists(ResourceType.class,
						(proxy.getIsNextInSequence())).getResource());
		solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_FOR.toString(),
				SolrUtils.exists(String.class, proxy.getAbout()));
		solrInputDocument.addField(EdmLabel.EDM_ISEUROPEANA_PROXY.toString(), proxy.getEuropeanaProxy().isEuropeanaProxy());

		// Retrieve the dcterms and dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> europeanaTypeList = proxy
				.getChoiceList();
		if (europeanaTypeList != null) {
			for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice choice : europeanaTypeList) {
				if (choice.getAlternative() != null) {
					solrInputDocument.addField(
							EdmLabel.PROXY_DCTERMS_ALTERNATIVE.toString(),
							choice.getAlternative().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_CONFORMS_TO,
						choice.getConformsTo());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_CREATED, choice.getCreated());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_EXTENT, choice.getExtent());
			
				SolrUtils
				.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_HAS_FORMAT,
						choice.getHasFormat());
				SolrUtils
						.addResourceOrLiteralType(solrInputDocument,
								EdmLabel.PROXY_DCTERMS_HAS_PART,
								choice.getHasPart());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_HAS_VERSION,
						choice.getHasVersion());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_IS_FORMAT_OF,
						choice.getIsFormatOf());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_IS_PART_OF,
						choice.getIsPartOf());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_IS_REFERENCED_BY,
						choice.getIsReferencedBy());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_IS_REPLACED_BY,
						choice.getIsReplacedBy());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_IS_REQUIRED_BY,
						choice.getIsRequiredBy());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_ISSUED, choice.getIssued());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_IS_VERSION_OF,
						choice.getIsVersionOf());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_MEDIUM, choice.getMedium());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_PROVENANCE,
						choice.getProvenance());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_REFERENCES,
						choice.getReferences());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_REPLACES,
						choice.getReplaces());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_REQUIRES,
						choice.getRequires());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_SPATIAL, choice.getSpatial());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_TABLE_OF_CONTENTS,
						choice.getTableOfContents());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DCTERMS_TEMPORAL,
						choice.getTemporal());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_CONTRIBUTOR,
						choice.getContributor());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_COVERAGE, choice.getCoverage());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_CREATOR, choice.getCreator());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_DATE, choice.getDate());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_DESCRIPTION,
						choice.getDescription());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_FORMAT, choice.getFormat());
				if (choice.getIdentifier() != null) {
					solrInputDocument.addField(EdmLabel.PROXY_DC_IDENTIFIER
							.toString(), choice.getIdentifier().getString());
				}
				if (choice.getLanguage() != null) {
					solrInputDocument.addField(EdmLabel.PROXY_DC_LANGUAGE
							.toString(), choice.getLanguage().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_PUBLISHER, choice.getPublisher());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_RELATION, choice.getRelation());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_RIGHTS, choice.getRights());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_SOURCE, choice.getSource());
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_SUBJECT, choice.getSubject());
				if (choice.getTitle() != null) {
					solrInputDocument.addField(EdmLabel.PROXY_DC_TITLE
							.toString(), choice.getTitle().getString());
				}
				SolrUtils.addResourceOrLiteralType(solrInputDocument,
						EdmLabel.PROXY_DC_TYPE, choice.getType());
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
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
			ProxyType proxy, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException,
			MalformedURLException, IOException {

		mongoProxy.setAbout(proxy.getAbout());
		mongoProxy.setId(new ObjectId());
		mongoProxy.setEuropeanaProxy(proxy.getEuropeanaProxy().isEuropeanaProxy());
		mongoProxy.setEdmCurrentLocation(SolrUtils.exists(ResourceType.class,
				(proxy.getCurrentLocation())).getResource());
		mongoProxy.setEdmIsNextInSequence(SolrUtils.exists(ResourceType.class,
				(proxy.getIsNextInSequence())).getResource());
		mongoProxy.setEdmType(DocType.get(SolrUtils.exists(EdmType.class,
				(proxy.getType())).toString()));

		mongoProxy
				.setProxyFor(SolrUtils.exists(String.class, proxy.getAbout()));
		mongoProxy.setEdmHasMet(SolrUtils.literalListToArray(proxy
				.getHasMetList()));
		mongoProxy.setEdmHasType(SolrUtils.resourceOrLiteralListToArray(proxy
				.getHasTypeList()));
		mongoProxy.setEdmIncorporates(SolrUtils.resourceListToArray(proxy
				.getIncorporateList()));
		mongoProxy.setEdmIsDerivativeOf(SolrUtils.resourceListToArray(proxy
				.getIsDerivativeOfList()));
		mongoProxy.setEdmIsRelatedTo(SolrUtils
				.resourceOrLiteralListToArray(proxy.getIsRelatedToList()));
		if (proxy.getIsRepresentationOf() != null) {
			mongoProxy.setEdmIsRepresentationOf(proxy.getIsRepresentationOf()
					.getResource());
		}
		mongoProxy.setEdmIsSimilarTo(SolrUtils.resourceListToArray(proxy
				.getIsSimilarToList()));
		mongoProxy.setEdmRealizes(SolrUtils.resourceListToArray(proxy
				.getRealizeList()));
		mongoProxy.setEdmIsSuccessorOf(SolrUtils.resourceListToArray(proxy
				.getIsSuccessorOfList()));
		List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> europeanaTypeList = proxy
				.getChoiceList();
		if (europeanaTypeList != null) {
			for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice europeanaType : europeanaTypeList) {
				if (europeanaType.getAlternative() != null) {
					mongoProxy
							.setDctermsAlternative(new String[] { europeanaType
									.getAlternative().getString() });
				}
				mongoProxy
						.setDctermsConformsTo(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getConformsTo()));
				mongoProxy.setDctermsCreated(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getCreated()));
				mongoProxy.setDctermsExtent(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getExtent()));
				mongoProxy
						.setDctermsHasFormat(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getHasFormat()));
				mongoProxy.setDctermsHasPart(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getHasPart()));
				mongoProxy
						.setDctermsHasVersion(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getHasVersion()));
				mongoProxy
						.setDctermsIsFormatOf(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getIsFormatOf()));
				mongoProxy.setDctermsIsPartOf(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getIsPartOf()));
				mongoProxy.setDctermsIsReferencedBy(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getIsReferencedBy()));
				mongoProxy.setDctermsIsReplacedBy(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getIsReplacedBy()));
				mongoProxy.setDctermsIsRequiredBy(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getIsRequiredBy()));
				mongoProxy.setDctermsIssued(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getIssued()));
				mongoProxy.setDctermsIsVersionOf(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getIsVersionOf()));
				mongoProxy.setDctermsMedium(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getMedium()));
				mongoProxy
						.setDctermsProvenance(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getProvenance()));
				mongoProxy
						.setDctermsReferences(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getReferences()));
				mongoProxy.setDctermsReplaces(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getReplaces()));
				mongoProxy.setDctermsRequires(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getRequires()));
				mongoProxy.setDctermsSpatial(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getSpatial()));
				mongoProxy.setDctermsTOC(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getTableOfContents()));
				mongoProxy.setDctermsTemporal(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getTemporal()));
				mongoProxy.setDcContributor(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getContributor()));
				mongoProxy.setDcCoverage(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getCoverage()));
				mongoProxy.setDcCreator(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getCreator()));
				mongoProxy.setDcDate(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getDate()));
				mongoProxy.setDcDescription(SolrUtils
						.resourceOrLiteralToArray(europeanaType
								.getDescription()));
				mongoProxy.setDcFormat(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getFormat()));
				if (europeanaType.getIdentifier() != null) {
					mongoProxy.setDcIdentifier(new String[] { europeanaType
							.getIdentifier().getString() });
				}
				if (europeanaType.getLanguage() != null) {
					mongoProxy.setDcLanguage(new String[] { europeanaType
							.getLanguage().getString() });
				}
				mongoProxy
						.setDcPublisher(SolrUtils
								.resourceOrLiteralToArray(europeanaType
										.getPublisher()));
				mongoProxy.setDcRelation(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getRelation()));
				mongoProxy.setDcRights(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getRights()));
				mongoProxy.setDcSource(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getSource()));
				mongoProxy.setDcSubject(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getSubject()));
				if (europeanaType.getTitle() != null) {
					mongoProxy.setDcTitle(new String[] { europeanaType
							.getTitle().getString() });
				}
				mongoProxy.setDcType(SolrUtils
						.resourceOrLiteralToArray(europeanaType.getType()));
			}
		}
		//if (((EdmMongoServer) mongoServer).searchByAbout(ProxyImpl.class,
	//			mongoProxy.getAbout()) != null) {
		//	MongoUtils.updateProxy(mongoProxy, mongoServer);
	//	} else {
			mongoServer.getDatastore().save(mongoProxy);
	//	}
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
			Aggregation aggregation, MongoServer mongoServer)
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
		solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_IN.toString(),
				SolrUtils.exists(String.class, aggregation.getAbout()));
		return solrInputDocument;
	}

	public static void deleteProxyFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
