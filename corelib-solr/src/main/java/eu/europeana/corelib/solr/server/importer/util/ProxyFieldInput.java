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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
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

	public ProxyFieldInput() {

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
	public SolrInputDocument createProxySolrFields(ProxyType proxy,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException, MalformedURLException, IOException {
		solrInputDocument.addField(EdmLabel.ORE_PROXY.toString(),
				proxy.getAbout());
		solrInputDocument.addField(EdmLabel.PROVIDER_EDM_TYPE.toString(),
				SolrUtils.exists(EdmType.class, (proxy.getType())).toString());
		solrInputDocument.addField(
				EdmLabel.PROXY_EDM_CURRENT_LOCATION.toString(),
				SolrUtils.exists(ResourceType.class,
						(proxy.getCurrentLocation())).getResource());
		solrInputDocument.addField(
				EdmLabel.PROXY_EDM_IS_NEXT_IN_SEQUENCE.toString(),
				SolrUtils.exists(ResourceType.class,
						(proxy.getIsNextInSequence())).getResource());
		solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_FOR.toString(),
				SolrUtils.exists(String.class, proxy.getAbout()));
		if (proxy.getEuropeanaProxy() != null) {
			solrInputDocument.addField(EdmLabel.EDM_ISEUROPEANA_PROXY
					.toString(), proxy.getEuropeanaProxy().isEuropeanaProxy());
		}

		// Retrieve the dcterms and dc namespace fields
		List<eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice> europeanaTypeList = proxy
				.getChoiceList();
		if (europeanaTypeList != null) {
			for (eu.europeana.corelib.definitions.jibx.EuropeanaType.Choice choice : europeanaTypeList) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, choice.getAlternative(),
						EdmLabel.PROXY_DCTERMS_ALTERNATIVE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getConformsTo(),
						EdmLabel.PROXY_DCTERMS_CONFORMS_TO);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getCreated(),
						EdmLabel.PROXY_DCTERMS_CREATED);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getExtent(),
						EdmLabel.PROXY_DCTERMS_EXTENT);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getHasFormat(),
						EdmLabel.PROXY_DCTERMS_HAS_FORMAT);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getHasPart(),
						EdmLabel.PROXY_DCTERMS_HAS_PART);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getHasVersion(),
						EdmLabel.PROXY_DCTERMS_HAS_VERSION);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIsFormatOf(),
						EdmLabel.PROXY_DCTERMS_IS_FORMAT_OF);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIsPartOf(),
						EdmLabel.PROXY_DCTERMS_IS_PART_OF);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIsReferencedBy(),
						EdmLabel.PROXY_DCTERMS_IS_REFERENCED_BY);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIsReplacedBy(),
						EdmLabel.PROXY_DCTERMS_IS_REPLACED_BY);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIsRequiredBy(),
						EdmLabel.PROXY_DCTERMS_IS_REQUIRED_BY);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIssued(),
						EdmLabel.PROXY_DCTERMS_ISSUED);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getIsVersionOf(),
						EdmLabel.PROXY_DCTERMS_IS_VERSION_OF);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getMedium(),
						EdmLabel.PROXY_DCTERMS_MEDIUM);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getProvenance(),
						EdmLabel.PROXY_DCTERMS_PROVENANCE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getReferences(),
						EdmLabel.PROXY_DCTERMS_REFERENCES);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getReplaces(),
						EdmLabel.PROXY_DCTERMS_REPLACES);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getRequires(),
						EdmLabel.PROXY_DCTERMS_REQUIRES);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getSpatial(),
						EdmLabel.PROXY_DCTERMS_SPATIAL);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getTableOfContents(),
						EdmLabel.PROXY_DCTERMS_TABLE_OF_CONTENTS);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getTemporal(),
						EdmLabel.PROXY_DCTERMS_TEMPORAL);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getContributor(),
						EdmLabel.PROXY_DC_CONTRIBUTOR);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getCoverage(),
						EdmLabel.PROXY_DC_COVERAGE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getCreator(),
						EdmLabel.PROXY_DC_CREATOR);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getDate(),
						EdmLabel.PROXY_DC_DATE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getDescription(),
						EdmLabel.PROXY_DC_DESCRIPTION);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getFormat(),
						EdmLabel.PROXY_DC_FORMAT);
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, choice.getIdentifier(),
						EdmLabel.PROXY_DC_IDENTIFIER);
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, choice.getLanguage(),
						EdmLabel.PROXY_DC_LANGUAGE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getPublisher(),
						EdmLabel.PROXY_DC_PUBLISHER);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getRelation(),
						EdmLabel.PROXY_DC_RELATION);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getRights(),
						EdmLabel.PROXY_DC_RIGHTS);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getSource(),
						EdmLabel.PROXY_DC_SOURCE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getSubject(),
						EdmLabel.PROXY_DC_SUBJECT);
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, choice.getTitle(),
						EdmLabel.PROXY_DC_TITLE);
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, choice.getType(),
						EdmLabel.PROXY_DC_TYPE);
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
	public  ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
			ProxyType proxy, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException,
			MalformedURLException, IOException {

		mongoProxy.setAbout(proxy.getAbout());
		// mongoProxy.setId(new ObjectId());
		if (proxy.getEuropeanaProxy() != null) {
			mongoProxy.setEuropeanaProxy(proxy.getEuropeanaProxy()
					.isEuropeanaProxy());
		}
		mongoProxy.setEdmCurrentLocation(SolrUtils.exists(ResourceType.class,
				(proxy.getCurrentLocation())).getResource());
		mongoProxy.setEdmIsNextInSequence(SolrUtils.exists(ResourceType.class,
				(proxy.getIsNextInSequence())).getResource());
		mongoProxy.setEdmType(DocType.get(SolrUtils.exists(EdmType.class,
				(proxy.getType())).toString()));

		mongoProxy
				.setProxyFor(SolrUtils.exists(String.class, proxy.getAbout()));
		mongoProxy.setEdmHasMet(MongoUtils.createLiteralMapFromList(proxy
				.getHasMetList()));
		mongoProxy.setEdmHasType(MongoUtils
				.createResourceOrLiteralMapFromList(proxy.getHasTypeList()));
		mongoProxy.setEdmIncorporates(SolrUtils.resourceListToArray(proxy
				.getIncorporateList()));
		mongoProxy.setEdmIsDerivativeOf(SolrUtils.resourceListToArray(proxy
				.getIsDerivativeOfList()));
		mongoProxy
				.setEdmIsRelatedTo(MongoUtils
						.createResourceOrLiteralMapFromList(proxy
								.getIsRelatedToList()));
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
				if (europeanaType.ifAlternative()) {
					if (mongoProxy.getDctermsAlternative() == null) {
						mongoProxy.setDctermsAlternative(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getAlternative(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsAlternative();
						tempMap.putAll(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getAlternative(),tempMap.size()));
						mongoProxy.setDctermsAlternative(tempMap);
					}
				}
				if (europeanaType.ifConformsTo()) {
					if (mongoProxy.getDctermsConformsTo() == null) {
						mongoProxy
								.setDctermsConformsTo(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getConformsTo(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsConformsTo();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getConformsTo(),tempMap.size()));
						mongoProxy.setDctermsConformsTo(tempMap);
					}
				}
				if (europeanaType.ifCreated()) {
					if (mongoProxy.getDctermsCreated() == null) {
						mongoProxy
								.setDctermsCreated(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getCreated(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsCreated();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getCreated(),tempMap.size()));
						mongoProxy.setDctermsCreated(tempMap);
					}
				}
				if (europeanaType.ifExtent()) {
					if (mongoProxy.getDctermsExtent() == null) {
						mongoProxy
								.setDctermsExtent(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getExtent(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsExtent();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getExtent(),tempMap.size()));
						mongoProxy.setDctermsExtent(tempMap);
					}
				}
				if (europeanaType.ifHasFormat()) {
					if (mongoProxy.getDctermsHasFormat() == null) {
						mongoProxy
								.setDctermsHasFormat(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getHasFormat(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsHasFormat();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getHasFormat(),tempMap.size()));
						mongoProxy.setDctermsHasFormat(tempMap);
					}
				}
				if (europeanaType.ifHasPart()) {
					if (mongoProxy.getDctermsHasPart() == null) {
						mongoProxy
								.setDctermsHasPart(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getHasPart(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsHasPart();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getHasPart(),tempMap.size()));
						mongoProxy.setDctermsHasPart(tempMap);
					}
				}
				if (europeanaType.ifHasVersion()) {
					if (mongoProxy.getDctermsHasVersion() == null) {
						mongoProxy
								.setDctermsHasVersion(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getHasVersion(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsHasVersion();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getHasVersion(),tempMap.size()));
						mongoProxy.setDctermsHasVersion(tempMap);
					}
				}

				if (europeanaType.ifIsFormatOf()) {
					if (mongoProxy.getDctermsIsFormatOf() == null) {
						mongoProxy
								.setDctermsIsFormatOf(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIsFormatOf(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIsFormatOf();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIsFormatOf(),tempMap.size()));
						mongoProxy.setDctermsIsFormatOf(tempMap);
					}
				}

				if (europeanaType.ifIsPartOf()) {
					if (mongoProxy.getDctermsIsPartOf() == null) {
						mongoProxy
								.setDctermsIsPartOf(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIsPartOf(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIsPartOf();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIsPartOf(),tempMap.size()));
						mongoProxy.setDctermsIsPartOf(tempMap);
					}
				}
				if (europeanaType.ifIsReferencedBy()) {
					if (mongoProxy.getDctermsIsReferencedBy() == null) {
						mongoProxy
								.setDctermsIsReferencedBy(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIsReferencedBy(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIsReferencedBy();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIsReferencedBy(),tempMap.size()));
						mongoProxy.setDctermsIsReferencedBy(tempMap);
					}
				}
				if (europeanaType.ifIsReplacedBy()) {
					if (mongoProxy.getDctermsIsReplacedBy() == null) {
						mongoProxy
								.setDctermsIsReplacedBy(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIsReplacedBy(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIsReplacedBy();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIsReplacedBy(),tempMap.size()));
						mongoProxy.setDctermsIsReplacedBy(tempMap);
					}
				}

				if (europeanaType.ifIsRequiredBy()) {
					if (mongoProxy.getDctermsIsRequiredBy() == null) {
						mongoProxy
								.setDctermsIsRequiredBy(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIsRequiredBy(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIsRequiredBy();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIsRequiredBy(),tempMap.size()));
						mongoProxy.setDctermsIsRequiredBy(tempMap);
					}
				}
				if (europeanaType.ifIssued()) {
					if (mongoProxy.getDctermsIssued() == null) {
						mongoProxy
								.setDctermsIssued(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIssued(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIssued();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIssued(),tempMap.size()));
						mongoProxy.setDctermsIssued(tempMap);
					}
				}

				if (europeanaType.ifIsVersionOf()) {
					if (mongoProxy.getDctermsIsVersionOf() == null) {
						mongoProxy
								.setDctermsIsVersionOf(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getIsVersionOf(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsIsVersionOf();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getIsVersionOf(),tempMap.size()));
						mongoProxy.setDctermsIsVersionOf(tempMap);
					}
				}

				if (europeanaType.ifMedium()) {
					if (mongoProxy.getDctermsMedium() == null) {
						mongoProxy
								.setDctermsMedium(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getMedium(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsMedium();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getMedium(),tempMap.size()));
						mongoProxy.setDctermsMedium(tempMap);
					}
				}

				if (europeanaType.ifProvenance()) {
					if (mongoProxy.getDctermsProvenance() == null) {
						mongoProxy
								.setDctermsProvenance(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getProvenance(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsProvenance();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getProvenance(),tempMap.size()));
						mongoProxy.setDctermsProvenance(tempMap);
					}
				}
				if (europeanaType.ifReferences()) {
					if (mongoProxy.getDctermsReferences() == null) {
						mongoProxy
								.setDctermsReferences(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getReferences(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsReferences();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getReferences(),tempMap.size()));
						mongoProxy.setDctermsReferences(tempMap);
					}
				}

				if (europeanaType.ifReplaces()) {
					if (mongoProxy.getDctermsReplaces() == null) {
						mongoProxy
								.setDctermsReplaces(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getReplaces(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsReplaces();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getReplaces(),tempMap.size()));
						mongoProxy.setDctermsReplaces(tempMap);
					}
				}

				if (europeanaType.ifRequires()) {
					if (mongoProxy.getDctermsRequires() == null) {
						mongoProxy
								.setDctermsRequires(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getRequires(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsRequires();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getRequires(),tempMap.size()));
						mongoProxy.setDctermsRequires(tempMap);
					}
				}
				if (europeanaType.ifSpatial()) {
					if (mongoProxy.getDctermsSpatial() == null) {
						mongoProxy
								.setDctermsSpatial(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getSpatial(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsSpatial();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getSpatial(),tempMap.size()));
						mongoProxy.setDctermsSpatial(tempMap);
					}
				}
				if (europeanaType.ifTableOfContents()) {
					if (mongoProxy.getDctermsTOC() == null) {
						mongoProxy
								.setDctermsTOC(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getTableOfContents(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsTOC();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getTableOfContents(),tempMap.size()));
						mongoProxy.setDctermsTOC(tempMap);
					}
				}
				if (europeanaType.ifTemporal()) {
					if (mongoProxy.getDctermsTemporal() == null) {
						mongoProxy
								.setDctermsTemporal(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getTemporal(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDctermsTemporal();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getTemporal(),tempMap.size()));
						mongoProxy.setDctermsTemporal(tempMap);
					}
				}
				if (europeanaType.ifContributor()) {
					if (mongoProxy.getDcContributor() == null) {
						mongoProxy
								.setDcContributor(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getContributor(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcContributor();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getContributor(),tempMap.size()));
						mongoProxy.setDcContributor(tempMap);
					}
				}
				if (europeanaType.ifCoverage()) {
					if (mongoProxy.getDcCoverage() == null) {
						mongoProxy
								.setDcCoverage(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getCoverage(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcCoverage();

						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getCoverage(),tempMap.size()));
						mongoProxy.setDcCoverage(tempMap);
					}
				}

				if (europeanaType.ifCreator()) {
					if (mongoProxy.getDcCreator() == null) {
						mongoProxy
								.setDcCreator(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getCreator(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcCreator();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getCreator(),tempMap.size()));
						mongoProxy.setDcCreator(tempMap);
					}
				}
				if (europeanaType.ifDate()) {
					if (mongoProxy.getDcDate() == null) {
						mongoProxy
								.setDcDate(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getDate(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcDate();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getDate(),tempMap.size()));
						mongoProxy.setDcDate(tempMap);
					}
				}

				if (europeanaType.ifDescription()) {
					if (mongoProxy.getDcDescription() == null) {
						mongoProxy
								.setDcDescription(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getDescription(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcDescription();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getDescription(),tempMap.size()));
						mongoProxy.setDcDescription(tempMap);
					}
				}
				if (europeanaType.ifFormat()) {
					if (mongoProxy.getDcFormat() == null) {
						mongoProxy
								.setDcFormat(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getFormat(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcFormat();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getFormat(),tempMap.size()));
						mongoProxy.setDcFormat(tempMap);
					}
				}

				if (europeanaType.ifIdentifier()) {
					if (mongoProxy.getDcIdentifier() == null) {
						mongoProxy.setDcIdentifier(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getIdentifier(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcIdentifier();
						tempMap.putAll(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getIdentifier(),tempMap.size()));
						mongoProxy.setDcIdentifier(tempMap);
					}
				}
				if (europeanaType.ifLanguage()) {
					if (mongoProxy.getDcLanguage() == null) {
						mongoProxy.setDcLanguage(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getLanguage(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcLanguage();
						tempMap.putAll(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getLanguage(),tempMap.size()));
						mongoProxy.setDcLanguage(tempMap);
					}
				}
				if (europeanaType.ifPublisher()) {
					if (mongoProxy.getDcPublisher() == null) {
						mongoProxy
								.setDcPublisher(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getPublisher(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcPublisher();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getPublisher(),tempMap.size()));
						mongoProxy.setDcPublisher(tempMap);
					}
				}

				if (europeanaType.ifRelation()) {
					if (mongoProxy.getDcRelation() == null) {
						mongoProxy
								.setDcRelation(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getRelation(),0));
					} else {
						Map<String, String> tempMap = mongoProxy
								.getDcRelation();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getRelation(),tempMap.size()));
						mongoProxy.setDcRelation(tempMap);
					}
				}
				if (europeanaType.ifRights()) {
					if (mongoProxy.getDcRights() == null) {
						mongoProxy
								.setDcRights(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getRights(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcRights();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getRights(),tempMap.size()));
						mongoProxy.setDcRights(tempMap);
					}
				}

				if (europeanaType.ifSource()) {
					if (mongoProxy.getDcSource() == null) {
						mongoProxy
								.setDcSource(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getSource(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcSource();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getSource(),tempMap.size()));
						mongoProxy.setDcSource(tempMap);
					}
				}

				if (europeanaType.ifSubject()) {
					if (mongoProxy.getDcSubject() == null) {
						mongoProxy
								.setDcSubject(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getSubject(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcSubject();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getSubject(),tempMap.size()));
						mongoProxy.setDcSubject(tempMap);
					}
				}

				if (europeanaType.ifTitle()) {
					if (mongoProxy.getDcTitle() == null) {
						mongoProxy.setDcTitle(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getTitle(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcTitle();
						tempMap.putAll(MongoUtils
								.createLiteralMapFromString(europeanaType
										.getTitle(),tempMap.size()));
						mongoProxy.setDcTitle(tempMap);
					}
				}
				if (europeanaType.ifType()) {
					if (mongoProxy.getDcType() == null) {
						mongoProxy
								.setDcType(MongoUtils
										.createResourceOrLiteralMapFromString(europeanaType
												.getType(),0));
					} else {
						Map<String, String> tempMap = mongoProxy.getDcType();
						tempMap.putAll(MongoUtils
								.createResourceOrLiteralMapFromString(europeanaType
										.getType(),tempMap.size()));
						mongoProxy.setDcType(tempMap);
					}
				}
			}
		}
		 if (((EdmMongoServer) mongoServer).searchByAbout(ProxyImpl.class,
			 mongoProxy.getAbout()) != null) {
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

	public ProxyImpl addProxyForMongo(ProxyImpl proxy,
			Aggregation aggregation, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		if (proxy.getProxyIn() == null) {
			proxy.setProxyIn(new String[] { SolrUtils.exists(String.class,
					aggregation.getAbout()) });
		} else {
			String[] tempProxy = proxy.getProxyIn();
			List<String> tempList = new ArrayList<String>(
					Arrays.asList(tempProxy));
			tempList.add(SolrUtils.exists(String.class, aggregation.getAbout()));
			proxy.setProxyIn(tempList.toArray(new String[tempList.size()]));
		}
		proxy.setEuropeanaProxy(false);
		return proxy;
	}

	public  ProxyImpl addEuropeanaProxyForMongo(ProxyImpl proxy,
			EuropeanaAggregationType aggregation, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {

		proxy.setProxyIn(new String[] { SolrUtils.exists(String.class,
				aggregation.getAbout()) });
		proxy.setEuropeanaProxy(true);
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
	public SolrInputDocument addProxyForSolr(Aggregation aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {
		solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_IN.toString(),
				SolrUtils.exists(String.class, aggregation.getAbout()));
		solrInputDocument.addField(EdmLabel.EDM_ISEUROPEANA_PROXY.toString(),
				false);
		return solrInputDocument;
	}

	public  SolrInputDocument addProxyForSolr(
			EuropeanaAggregationType aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {
		solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_IN.toString(),
				SolrUtils.exists(String.class, aggregation.getAbout()));
		solrInputDocument.addField(EdmLabel.EDM_ISEUROPEANA_PROXY.toString(),
				true);
		return solrInputDocument;
	}

	public void deleteProxyFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(ProxyImpl.class, about, mongoServer);
	}
}
