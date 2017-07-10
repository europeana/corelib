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
package eu.europeana.corelib.edm.server.importer.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasMet;
import eu.europeana.corelib.definitions.jibx.HasType;
import eu.europeana.corelib.definitions.jibx.IsNextInSequence;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx.Year;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.ProxyImpl;

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
   * @param proxy
   * @param solrInputDocument The SolrInputDocument to alter
   * @return The altered SolrInputDocument with the Proxy fields filled in
   */
  public SolrInputDocument createProxySolrFields(ProxyType proxy,
      SolrInputDocument solrInputDocument) throws InstantiationException,
      IllegalAccessException, IOException {
    solrInputDocument.addField(EdmLabel.ORE_PROXY.toString(),
        proxy.getAbout());
    solrInputDocument.addField(
        EdmLabel.PROVIDER_EDM_TYPE.toString(),
        SolrUtils.exists(String.class,
            (proxy.getType().getType().xmlValue())).toString());

    if (proxy.getCurrentLocation() != null) {
        solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
        solrInputDocument, proxy.getCurrentLocation(),
        EdmLabel.PROXY_EDM_CURRENT_LOCATION);
    }

    List<IsNextInSequence> seqList = proxy.getIsNextInSequenceList();
    if (seqList != null) {
      for (IsNextInSequence val : seqList) {
        solrInputDocument.addField(
            EdmLabel.PROXY_EDM_IS_NEXT_IN_SEQUENCE.toString(),
            val.getResource());
      }
    }
    solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_FOR.toString(),
        SolrUtils.exists(ResourceType.class, proxy.getProxyFor())
            .getResource());
    solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_IN.toString(),
        SolrUtils.resourceListToArray(proxy.getProxyInList()));
    if (proxy.getEuropeanaProxy() != null) {
      solrInputDocument.addField(EdmLabel.EDM_ISEUROPEANA_PROXY
          .toString(), proxy.getEuropeanaProxy().isEuropeanaProxy());
    }
    if (proxy.getYearList() != null) {
      for (Year year : proxy.getYearList()) {
        solrInputDocument = SolrUtils.addFieldFromLiteral(
            solrInputDocument, year, EdmLabel.PROXY_EDM_YEAR);
      }
    }
    if (proxy.getHasTypeList() != null) {
      for (HasType ht : proxy.getHasTypeList()) {
        solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
            solrInputDocument, ht, EdmLabel.PROXY_EDM_HAS_TYPE);
      }
    }
    if (proxy.getHasMetList() != null) {
      for (HasMet ht : proxy.getHasMetList()) {
        solrInputDocument = SolrUtils.addFieldFromResource(
            solrInputDocument, ht, EdmLabel.PROXY_EDM_HAS_MET);
      }
    }
    if (proxy.getHasTypeList() != null) {
      for (HasType ht : proxy.getHasTypeList()) {
        solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
            solrInputDocument, ht, EdmLabel.PROXY_EDM_HAS_TYPE);
      }
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
   * Set the ProxyIn field for a SolrInputDocument
   *
   * @param aggregation The JiBX Aggregation Entity
   * @param solrInputDocument The SolrInputDocument
   * @return The SolrInputDocument with the ProxyIn field
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

  /**
   * Create the EuropeanaProxy fields in a solr document
   *
   * @param aggregation The EuropeanaAggregation linked to this proxy
   * @param solrInputDocument The solrdocument to append the fields to
   */
  public SolrInputDocument addProxyForSolr(
      EuropeanaAggregationType aggregation,
      SolrInputDocument solrInputDocument) throws InstantiationException,
      IllegalAccessException {
    solrInputDocument.addField(EdmLabel.PROXY_ORE_PROXY_IN.toString(),
        SolrUtils.exists(String.class, aggregation.getAbout()));
    solrInputDocument.addField(EdmLabel.EDM_ISEUROPEANA_PROXY.toString(),
        true);
    return solrInputDocument;
  }

  /**
   * Construct the fields of a Proxy MongoDB Entity. The entity is
   * instantiated when reading the ProvidedCHO
   *
   * @param mongoProxy The Proxy MongoDB Entity to save or update
   * @param proxy
   * @return The MongoDB Proxy Entity
   */
  public ProxyImpl createProxyMongoFields(ProxyImpl mongoProxy,
      ProxyType proxy) throws InstantiationException,
      IllegalAccessException, IOException {

    mongoProxy.setAbout(proxy.getAbout());
    // mongoProxy.setId(new ObjectId());
    if (proxy.getEuropeanaProxy() != null) {
      mongoProxy.setEuropeanaProxy(proxy.getEuropeanaProxy()
          .isEuropeanaProxy());
    }
    mongoProxy.setEdmCurrentLocation(MongoUtils
        .createResourceOrLiteralMapFromString(proxy
            .getCurrentLocation()));

    List<IsNextInSequence> seqList = proxy.getIsNextInSequenceList();

    if (seqList != null) {

      String[] seqarray = new String[seqList.size()];
      for (int i = 0; i < seqarray.length; i++) {
        seqarray[i] = seqList.get(i).getResource();
      }
      mongoProxy.setEdmIsNextInSequence(seqarray);
    }
    String docType = SolrUtils.exists(String.class,
        (proxy.getType().getType().xmlValue())).toString();

    mongoProxy.setEdmType(DocType.safeValueOf(docType));

    mongoProxy.setProxyFor(SolrUtils.exists(String.class, proxy
        .getProxyFor().getResource()));
    mongoProxy.setProxyIn(SolrUtils.resourceListToArray(proxy
        .getProxyInList()));
    mongoProxy.setEdmHasMet(MongoUtils.createResourceMapFromList(proxy
        .getHasMetList()));
    mongoProxy.setYear(MongoUtils.createLiteralMapFromList(proxy
        .getYearList()));
    mongoProxy.setEdmHasType(MongoUtils
        .createResourceOrLiteralMapFromList(proxy.getHasTypeList()));
    mongoProxy.setEdmHasType(MongoUtils.createResourceOrLiteralMapFromList(proxy
        .getHasTypeList()));
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
                    .getAlternative()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsAlternative();
            Map<String, List<String>> retMap = MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getAlternative());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsAlternative(tempMap);
          }
        }
        if (europeanaType.ifConformsTo()) {
          if (mongoProxy.getDctermsConformsTo() == null) {
            mongoProxy
                .setDctermsConformsTo(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getConformsTo()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsConformsTo();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getConformsTo());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsConformsTo(tempMap);
          }
        }
        if (europeanaType.ifCreated()) {
          if (mongoProxy.getDctermsCreated() == null) {
            mongoProxy
                .setDctermsCreated(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getCreated()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsCreated();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getCreated());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsCreated(tempMap);
          }
        }
        if (europeanaType.ifExtent()) {
          if (mongoProxy.getDctermsExtent() == null) {
            mongoProxy
                .setDctermsExtent(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getExtent()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsExtent();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getExtent());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsExtent(tempMap);
          }
        }
        if (europeanaType.ifHasFormat()) {
          if (mongoProxy.getDctermsHasFormat() == null) {
            mongoProxy
                .setDctermsHasFormat(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getHasFormat()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsHasFormat();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getHasFormat());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsHasFormat(tempMap);
          }
        }
        if (europeanaType.ifHasPart()) {
          if (mongoProxy.getDctermsHasPart() == null) {
            mongoProxy
                .setDctermsHasPart(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getHasPart()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsHasPart();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getHasPart());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsHasPart(tempMap);
          }
        }
        if (europeanaType.ifHasVersion()) {
          if (mongoProxy.getDctermsHasVersion() == null) {
            mongoProxy
                .setDctermsHasVersion(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getHasVersion()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsHasVersion();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getHasVersion());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsHasVersion(tempMap);
          }
        }

        if (europeanaType.ifIsFormatOf()) {
          if (mongoProxy.getDctermsIsFormatOf() == null) {
            mongoProxy
                .setDctermsIsFormatOf(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIsFormatOf()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIsFormatOf();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIsFormatOf());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIsFormatOf(tempMap);
          }
        }

        if (europeanaType.ifIsPartOf()) {
          if (mongoProxy.getDctermsIsPartOf() == null) {
            mongoProxy
                .setDctermsIsPartOf(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIsPartOf()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIsPartOf();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIsPartOf());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIsPartOf(tempMap);
          }
        }
        if (europeanaType.ifIsReferencedBy()) {
          if (mongoProxy.getDctermsIsReferencedBy() == null) {
            mongoProxy
                .setDctermsIsReferencedBy(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIsReferencedBy()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIsReferencedBy();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIsReferencedBy());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIsReferencedBy(tempMap);
          }
        }
        if (europeanaType.ifIsReplacedBy()) {
          if (mongoProxy.getDctermsIsReplacedBy() == null) {
            mongoProxy
                .setDctermsIsReplacedBy(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIsReplacedBy()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIsReplacedBy();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIsReplacedBy());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIsReplacedBy(tempMap);
          }
        }

        if (europeanaType.ifIsRequiredBy()) {
          if (mongoProxy.getDctermsIsRequiredBy() == null) {
            mongoProxy
                .setDctermsIsRequiredBy(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIsRequiredBy()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIsRequiredBy();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIsRequiredBy());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIsRequiredBy(tempMap);
          }
        }
        if (europeanaType.ifIssued()) {
          if (mongoProxy.getDctermsIssued() == null) {
            mongoProxy
                .setDctermsIssued(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIssued()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIssued();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIssued());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIssued(tempMap);
          }
        }

        if (europeanaType.ifIsVersionOf()) {
          if (mongoProxy.getDctermsIsVersionOf() == null) {
            mongoProxy
                .setDctermsIsVersionOf(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getIsVersionOf()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsIsVersionOf();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getIsVersionOf());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsIsVersionOf(tempMap);
          }
        }

        if (europeanaType.ifMedium()) {
          if (mongoProxy.getDctermsMedium() == null) {
            mongoProxy
                .setDctermsMedium(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getMedium()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsMedium();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getMedium());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsMedium(tempMap);
          }
        }

        if (europeanaType.ifProvenance()) {
          if (mongoProxy.getDctermsProvenance() == null) {
            mongoProxy
                .setDctermsProvenance(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getProvenance()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsProvenance();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getProvenance());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsProvenance(tempMap);
          }
        }
        if (europeanaType.ifReferences()) {
          if (mongoProxy.getDctermsReferences() == null) {
            mongoProxy
                .setDctermsReferences(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getReferences()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsReferences();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getReferences());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsReferences(tempMap);
          }
        }

        if (europeanaType.ifReplaces()) {
          if (mongoProxy.getDctermsReplaces() == null) {
            mongoProxy
                .setDctermsReplaces(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getReplaces()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsReplaces();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getReplaces());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsReplaces(tempMap);
          }
        }

        if (europeanaType.ifRequires()) {
          if (mongoProxy.getDctermsRequires() == null) {
            mongoProxy
                .setDctermsRequires(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getRequires()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsRequires();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getRequires());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsRequires(tempMap);
          }
        }
        if (europeanaType.ifSpatial()) {
          if (mongoProxy.getDctermsSpatial() == null) {
            mongoProxy
                .setDctermsSpatial(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getSpatial()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsSpatial();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getSpatial());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsSpatial(tempMap);
          }
        }
        if (europeanaType.ifTableOfContents()) {
          if (mongoProxy.getDctermsTOC() == null) {
            mongoProxy
                .setDctermsTOC(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getTableOfContents()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsTOC();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getTableOfContents());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsTOC(tempMap);
          }
        }
        if (europeanaType.ifTemporal()) {
          if (mongoProxy.getDctermsTemporal() == null) {
            mongoProxy
                .setDctermsTemporal(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getTemporal()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDctermsTemporal();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getTemporal());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDctermsTemporal(tempMap);
          }
        }
        if (europeanaType.ifContributor()) {
          if (mongoProxy.getDcContributor() == null) {
            mongoProxy
                .setDcContributor(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getContributor()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcContributor();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getContributor());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcContributor(tempMap);
          }
        }
        if (europeanaType.ifCoverage()) {
          if (mongoProxy.getDcCoverage() == null) {
            mongoProxy
                .setDcCoverage(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getCoverage()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcCoverage();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getCoverage());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcCoverage(tempMap);
          }
        }

        if (europeanaType.ifCreator()) {
          if (mongoProxy.getDcCreator() == null) {
            mongoProxy
                .setDcCreator(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getCreator()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcCreator();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getCreator());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcCreator(tempMap);
          }
        }
        if (europeanaType.ifDate()) {
          if (mongoProxy.getDcDate() == null) {
            mongoProxy
                .setDcDate(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getDate()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcDate();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getDate());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcDate(tempMap);
          }
        }

        if (europeanaType.ifDescription()) {
          if (mongoProxy.getDcDescription() == null) {
            mongoProxy
                .setDcDescription(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getDescription()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcDescription();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getDescription());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcDescription(tempMap);
          }
        }
        if (europeanaType.ifFormat()) {
          if (mongoProxy.getDcFormat() == null) {
            mongoProxy
                .setDcFormat(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getFormat()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcFormat();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getFormat());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcFormat(tempMap);
          }
        }

        if (europeanaType.ifIdentifier()) {
          if (mongoProxy.getDcIdentifier() == null) {
            mongoProxy.setDcIdentifier(MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getIdentifier()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcIdentifier();
            Map<String, List<String>> retMap = MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getIdentifier());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcIdentifier(tempMap);
          }
        }
        if (europeanaType.ifLanguage()) {
          if (mongoProxy.getDcLanguage() == null) {
            mongoProxy.setDcLanguage(MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getLanguage()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcLanguage();
            Map<String, List<String>> retMap = MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getLanguage());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcLanguage(tempMap);
          }
        }
        if (europeanaType.ifPublisher()) {
          if (mongoProxy.getDcPublisher() == null) {
            mongoProxy
                .setDcPublisher(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getPublisher()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcPublisher();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getPublisher());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcPublisher(tempMap);
          }
        }

        if (europeanaType.ifRelation()) {
          if (mongoProxy.getDcRelation() == null) {
            mongoProxy
                .setDcRelation(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getRelation()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcRelation();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getRelation());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcRelation(tempMap);
          }
        }
        if (europeanaType.ifRights()) {
          if (mongoProxy.getDcRights() == null) {
            mongoProxy
                .setDcRights(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getRights()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcRights();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getRights());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcRights(tempMap);
          }
        }

        if (europeanaType.ifSource()) {
          if (mongoProxy.getDcSource() == null) {
            mongoProxy
                .setDcSource(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getSource()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcSource();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getSource());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcSource(tempMap);
          }
        }

        if (europeanaType.ifSubject()) {
          if (mongoProxy.getDcSubject() == null) {
            mongoProxy
                .setDcSubject(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getSubject()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcSubject();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getSubject());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcSubject(tempMap);
          }
        }

        if (europeanaType.ifTitle()) {
          if (mongoProxy.getDcTitle() == null) {
            mongoProxy.setDcTitle(MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getTitle()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcTitle();
            Map<String, List<String>> retMap = MongoUtils
                .createLiteralMapFromString(europeanaType
                    .getTitle());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcTitle(tempMap);
          }
        }
        if (europeanaType.ifType()) {
          if (mongoProxy.getDcType() == null) {
            mongoProxy
                .setDcType(MongoUtils
                    .createResourceOrLiteralMapFromString(europeanaType
                        .getType()));
          } else {
            Map<String, List<String>> tempMap = mongoProxy
                .getDcType();
            Map<String, List<String>> retMap = MongoUtils
                .createResourceOrLiteralMapFromString(europeanaType
                    .getType());
            addValuesFromMapToMap(tempMap, retMap);
            mongoProxy.setDcType(tempMap);
          }
        }
      }
    }
    return mongoProxy;
  }

  private static void addValuesFromMapToMap(Map<String, List<String>> tempMap,
      Map<String, List<String>> retMap) {
    if (retMap != null && tempMap != null) {
      for (Entry<String, List<String>> entry : retMap
          .entrySet()) {
        if (tempMap.containsKey(entry.getKey())) {
          List<String> values = tempMap.get(entry
              .getKey());
          values.addAll(retMap.get(entry.getKey()));
        } else {
          tempMap.put(entry.getKey(), entry.getValue());
        }
      }
    }
  }
}
