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
package eu.europeana.corelib.definitions.solr.entity;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * EDM Proxy fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Proxy extends AbstractEdmEntity {

	/**
	 * Retrieve the dc:contributor fields for a Proxy
	 * 
	 * @return String array representing the dc:contributor fields of a Proxy
	 */
	String[] getDcContributor();

	/**
	 * Retrieve the dc:coverage fields for a Proxy
	 * 
	 * @return String array representing the dc:coverage fields of a Proxy
	 */
	String[] getDcCoverage();

	/**
	 * Retrieve the dc:creator fields for a Proxy
	 * 
	 * @return String array representing the dc:creator fields for a Proxy
	 */
	String[] getDcCreator();

	/**
	 * Retrieve the dc:date fields for a Proxy
	 * 
	 * @return Date array representing the dc:date fields for a Proxy
	 */
	String[] getDcDate();

	/**
	 * Retrieve the dc:description fields for a Proxy
	 * 
	 * @return String array representing the dc:description fields for a Proxy
	 */
	String[] getDcDescription();

	/**
	 * Retrieve the dc:format fields for a Proxy
	 * 
	 * @return String array representing the dc:format fields for a Proxy
	 */
	String[] getDcFormat();

	/**
	 * Retrieve the dc:identifier fields for a Proxy
	 * 
	 * @return String array representing the dc:identifier fields for a Proxy
	 */
	String[] getDcIdentifier();

	/**
	 * Retrieve the dc:language fields for a Proxy
	 * 
	 * @return String array representing the dc:language fields for a Proxy
	 */
	String[] getDcLanguage();

	/**
	 * Retrieve the dc:publisher fields for a Proxy
	 * 
	 * @return String array representing the dc:publisher fields for a Proxy
	 */
	String[] getDcPublisher();

	/**
	 * Retrieve the dc:relation fields for a Proxy
	 * 
	 * @return String array representing the dc:relation fields for a Proxy
	 */
	String[] getDcRelation();

	/**
	 * Retrieve the dc:rights fields for a Proxy
	 * 
	 * @return String array representing the dc:rights fields for a Proxy
	 */
	String[] getDcRights();

	/**
	 * Retrieve the dc:source fields for a Proxy
	 * 
	 * @return String array representing the dc:soure fields for a Proxy
	 */
	String[] getDcSource();

	/**
	 * Retrieve the dc:subject fields for a Proxy
	 * 
	 * @return String array representing the dc:subject fields for a Proxy
	 */
	String[] getDcSubject();

	/**
	 * Retrieve the dc:title fields for a Proxy
	 * 
	 * @return String array representing the dc:title fields for a Proxy
	 */
	String[] getDcTitle();

	/**
	 * Retrieve the dc:type fields for a Proxy
	 * 
	 * @return String array representing the dc:type fields for a Proxy
	 */
	String[] getDcType();

	/**
	 * Retrieve the dcterms:alternative fields for a Proxy
	 * 
	 * @return String array representing the dcterms:alternative fields for a
	 *         Proxy
	 */
	String[] getDctermsAlternative();

	/**
	 * Retrieve the dc:conformsTo fields for a Proxy
	 * 
	 * @return String array representing the dc:conformsTo fields for a Proxy
	 */
	String[] getDctermsConformsTo();

	/**
	 * Retrieve the dcterms:created fields for a Proxy
	 * 
	 * @return Date array representing the dcterms:created fields for a Proxy
	 */
	String[] getDctermsCreated();

	/**
	 * Retrieve the dcterms:extent fields for a Proxy
	 * 
	 * @return String array representing the dc:extent fields for a Proxy
	 */
	String[] getDctermsExtent();

	/**
	 * Retrieve the dcterms:hasFormat fields for a Proxy
	 * 
	 * @return String array representing the dcterms:hasFormat fields for a
	 *         Proxy
	 */
	String[] getDctermsHasFormat();

	/**
	 * Retrieve the dcterms:hasPart fields for a Proxy
	 * 
	 * @return String array representing the dcterms:hasPart fields for a Proxy
	 */
	String[] getDctermsHasPart();

	/**
	 * Retrieve the dcterms:hasVersion fields for a Proxy
	 * 
	 * @return String array representing the dcterms:hasVersion fields for a
	 *         Proxy
	 */
	String[] getDctermsHasVersion();

	/**
	 * Retrieve the dcterms:isVersionOf fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isVersionOf fields for a
	 *         Proxy
	 */
	String[] getDctermsIsFormatOf();

	/**
	 * Retrieve the dcterms:isFormatOf fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isFormatOf fields for a
	 *         Proxy
	 */
	String[] getDctermsIsPartOf();

	/**
	 * Retrieve the dcterms:isReferencedBy fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isReferencedBy fields for a
	 *         Proxy
	 */
	String[] getDctermsIsReferencedBy();

	/**
	 * Retrieve the dcterms:isReplacedBy fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isReplacedBy fields for a
	 *         Proxy
	 */
	String[] getDctermsIsReplacedBy();

	/**
	 * Retrieve the dcterms:isRequiredBy fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isRequiredBy fields for a
	 *         Proxy
	 */
	String[] getDctermsIsRequiredBy();

	/**
	 * Retrieve the dcterms:issued fields for a Proxy
	 * 
	 * @return String array representing the dcterms:issued fields for a Proxy
	 */
	String[] getDctermsIssued();

	/**
	 * Retrieve the dcterms:isVersionOf fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isVersionOf fields for a
	 *         Proxy
	 */
	String[] getDctermsIsVersionOf();

	/**
	 * Retrieve the dcterms:medium fields for a Proxy
	 * 
	 * @return String array representing the dcterms:medium fields for a Proxy
	 */
	String[] getDctermsMedium();

	/**
	 * Retrieve the dcterms:provenance fields for a Proxy
	 * 
	 * @return String array representing the dcterms:provenance fields for a
	 *         Proxy
	 */
	String[] getDctermsProvenance();

	/**
	 * Retrieve the dcterms:references fields for a Proxy
	 * 
	 * @return String array representing the dcterms:references fields for a
	 *         Proxy
	 */
	String[] getDctermsReferences();

	/**
	 * Retrieve the dcterms:replaces fields for a Proxy
	 * 
	 * @return String array representing the dcterms:replaces fields for a Proxy
	 */
	String[] getDctermsReplaces();

	/**
	 * Retrieve the dcterms:requires fields for a Proxy
	 * 
	 * @return String array representing the dcterms:requires fields for a Proxy
	 */
	String[] getDctermsRequires();

	/**
	 * Retrieve the dcterms:spatial fields for a Proxy
	 * 
	 * @return String array representing the dcterms:spatial fields for a Proxy
	 */
	String[] getDctermsSpatial();

	/**
	 * Retrieve the dcterms:tableOfContents fields for a Proxy
	 * 
	 * @return String array representing the dcterms:tableOfContents fields for
	 *         a Proxy
	 */
	String[] getDctermsTOC();

	/**
	 * Retrieve the dcterms:temporal fields for a Proxy
	 * 
	 * @return String array representing the dcterms:temporal fields for a Proxy
	 */
	String[] getDctermsTemporal();

	/**
	 * Retrieve the edm:type fields for a Proxy
	 * 
	 * @return DocType representing the edm:type fields for a Proxy
	 */
	DocType getEdmType();

	/**
	 * Retrieve the edm:currentLocation fields for a Proxy
	 * 
	 * @return String representing the edm:currentLocation fields for a Proxy
	 */
	String getEdmCurrentLocation();

	/**
	 * Set the dcContributor field for a Proxy
	 * 
	 * @param dcContributor
	 * 			String array containing the dcContributor of a Proxy
	 */
	void setDcContributor(String[] dcContributor);

	/**
	 * Set the dcCoverage field for a Proxy
	 * 
	 * @param dcCoverage
	 * 			String array containing the dcCoverage of a Proxy
	 */
	void setDcCoverage(String[] dcCoverage);

	/**
	 * Set the dcCreator field for a Proxy
	 * 
	 * @param dcCreator
	 * 			String array containing the dcCreator of a Proxy
	 */
	void setDcCreator(String[] dcCreator);

	/**
	 * Set the dcDate field for a Proxy
	 * 
	 * @param dcDate
	 * 			String array containing the dcDate of a Proxy
	 */
	void setDcDate(String[] dcDate);

	/**
	 * Set the dcDescription field for a Proxy
	 * 
	 * @param dcDescription
	 * 			String array containing the dcDescription of a Proxy
	 */
	void setDcDescription(String[] dcDescription);

	/**
	 * Set the dcFormat field for a Proxy
	 * 
	 * @param dcFormat
	 * 			String array containing the dcFormat of a Proxy
	 */
	void setDcFormat(String[] dcFormat);

	/**
	 * Set the dcIdentifier field for a Proxy
	 * 
	 * @param dcIdentifier
	 * 			String array containing the dcIdentifier of a Proxy
	 */
	void setDcIdentifier(String[] dcIdentifier);

	/**
	 * Set the dcLanguage field for a Proxy
	 * 
	 * @param dcLanguage
	 * 			String array containing the dcLanguage of a Proxy
	 */
	void setDcLanguage(String[] dcLanguage);

	/**
	 * Set the dcPublisher field for a Proxy
	 * 
	 * @param dcPublisher
	 * 			String array containing the dcPublisher of a Proxy
	 */
	void setDcPublisher(String[] dcPublisher);

	/**
	 * Set the dcRelation field for a Proxy
	 * 
	 * @param dcRelation
	 * 			String array containing the dcRelation of a Proxy
	 */
	void setDcRelation(String[] dcRelation);

	/**
	 * Set the dcRights field for a Proxy
	 * 
	 * @param dcRights
	 * 			String array containing the dcRights of a Proxy
	 */
	void setDcRights(String[] dcRights);

	/**
	 * Set the dcSource field for a Proxy
	 * 
	 * @param dcSource
	 * 			String array containing the dcSource of a Proxy
	 */
	void setDcSource(String[] dcSource);

	/**
	 * Set the dcSubject field for a Proxy
	 * 
	 * @param dcSubject
	 * 			String array containing the dcSubject of a Proxy
	 */
	void setDcSubject(String[] dcSubject);

	/**
	 * Set the dcTitle field for a Proxy
	 * 
	 * @param dcTitle
	 * 			String array containing the dcTitle of a Proxy
	 */
	void setDcTitle(String[] dcTitle);

	/**
	 * Set the dcType field for a Proxy
	 * 
	 * @param dcType
	 * 			String array containing the dcType of a Proxy
	 */
	void setDcType(String[] dcType);

	/**
	 * Set the dctermsAlternative field for a Proxy
	 * 
	 * @param dctermsAlternative
	 * 			String array containing the dctermsAlternative of a Proxy
	 */
	void setDctermsAlternative(String[] dctermsAlternative);

	/**
	 * Set the dctermsConformsTo field for a Proxy
	 * 
	 * @param dctermsConformsTo
	 * 			String array containing the dctermsConformsTo of a Proxy
	 */
	void setDctermsConformsTo(String[] dctermsConformsTo);

	/**
	 * Set the dctermsCreated field for a Proxy
	 * 
	 * @param dctermsCreated
	 * 			String array containing the dctermsCreated of a Proxy
	 */
	void setDctermsCreated(String[] dctermsCreated);

	/**
	 * Set the dctermsExtent field for a Proxy
	 * 
	 * @param dctermsExtent
	 * 			String array containing the dctermsExtent of a Proxy
	 */
	void setDctermsExtent(String[] dctermsExtent);

	/**
	 * Set the dctermsHasFormat field for a Proxy
	 * 
	 * @param dctermsHasFormat
	 * 			String array containing the dctermsHasFormat of a Proxy
	 */
	void setDctermsHasFormat(String[] dctermsHasFormat);

	/**
	 * Set the dctermsHasPart field for a Proxy
	 * 
	 * @param dctermsHasPart
	 * 			String array containing the dctermsHasPart of a Proxy
	 */
	void setDctermsHasPart(String[] dctermsHasPart);

	/**
	 * Set the dctermsHasVersion field for a Proxy
	 * 
	 * @param dctermsHasVersion
	 * 			String array containing the dctermsHasVersion of a Proxy
	 */
	void setDctermsHasVersion(String[] dctermsHasVersion);

	/**
	 * Set the dctermsIsFormatOf field for a Proxy
	 * 
	 * @param dctermsIsFormatOf
	 * 			String array containing the dctermsIsFormatOf of a Proxy
	 */
	void setDctermsIsFormatOf(String[] dctermsIsFormatOf);

	/**
	 * Set the dctermsIsPartOf field for a Proxy
	 * 
	 * @param dctermsIsPartOf
	 * 			String array containing the dctermsIsPartOf of a Proxy
	 */
	void setDctermsIsPartOf(String[] dctermsIsPartOf);

	/**
	 * Set the dctermsIsReferencedBy field for a Proxy
	 * 
	 * @param dctermsIsReferencedBy
	 * 			String array containing the dctermsIsReferencedBy of a Proxy
	 */
	void setDctermsIsReferencedBy(String[] dctermsIsReferencedBy);

	/**
	 * Set the dctermsIsReplacedBy field for a Proxy
	 * 
	 * @param dctermsIsReplacedBy
	 * 			String array containing the dctermsIsReplacedBy of a Proxy
	 */
	void setDctermsIsReplacedBy(String[] dctermsIsReplacedBy);

	/**
	 * Set the dctermsIsRequiredBy field for a Proxy
	 * 
	 * @param dctermsIsRequiredBy
	 * 			String array containing the dctermsIsRequiredBy of a Proxy
	 */
	void setDctermsIsRequiredBy(String[] dctermsIsRequiredBy);

	/**
	 * Set the dctermsIssued field for a Proxy
	 * 
	 * @param dctermsIssued
	 * 			String array containing the dctermsIssued of a Proxy
	 */
	void setDctermsIssued(String[] dctermsIssued);

	/**
	 * Set the dctermsIsVersionOf field for a Proxy
	 * 
	 * @param dctermsIsVersionOf
	 * 			String array containing the dctermsIsVersionOf of a Proxy
	 */
	void setDctermsIsVersionOf(String[] dctermsIsVersionOf);

	/**
	 * Set the dctermsMedium field for a Proxy
	 * 
	 * @param dctermsMedium
	 * 			String array containing the dctermsMedium of a Proxy
	 */
	void setDctermsMedium(String[] dctermsMedium);

	/**
	 * Set the dctermsProvenance field for a Proxy
	 * 
	 * @param dctermsProvenance
	 * 			String array containing the dctermsProvenance of a Proxy
	 */
	void setDctermsProvenance(String[] dctermsProvenance);

	/**
	 * Set the dctermsReferences field for a Proxy
	 * 
	 * @param dctermsReferences
	 * 			String array containing the dctermsReference of a Proxy
	 */
	void setDctermsReferences(String[] dctermsReferences);

	/**
	 * Set the dctermsReplaces field for a Proxy
	 * 
	 * @param dctermsReplaces
	 * 			String array containing the dctermsReplaces of a Proxy
	 */
	void setDctermsReplaces(String[] dctermsReplaces);

	/**
	 * Set the dctermsRequires field for a Proxy
	 * 
	 * @param dctermsRequires
	 * 			String array containing the dctermsRequires of a Proxy
	 */
	void setDctermsRequires(String[] dctermsRequires);

	/**
	 * Set the dctermsSpatial field for a Proxy
	 * 
	 * @param dctermsSpatial
	 * 			String array containing the dctermsSpatial of a Proxy
	 */
	void setDctermsSpatial(String[] dctermsSpatial);

	/**
	 * Set the dctermsTableOfContents field for a Proxy
	 * 
	 * @param dctermsTableOfContents
	 * 			String array containing the dctermsTableOfContents of a Proxy
	 */
	void setDctermsTOC(String[] dctermsTOC);

	/**
	 * Set the dctermsTemporal field for a Proxy
	 * 
	 * @param dctermsTemporal
	 * 			String array containing the dctermsTemporal of a Proxy
	 */
	void setDctermsTemporal(String[] dctermsTemporal);

	/**
	 * Set the edmType field for a Proxy
	 * 
	 * @param edmType
	 * 			String array containing the edmType of a Proxy
	 */
	void setEdmType(DocType edmType);

	/**
	 * Set the edmCurrentLocation field for a Proxy
	 * 
	 * @param edmCurrentLocation
	 * 			String array containing the edmCurrentLocation of a Proxy
	 */
	void setEdmCurrentLocation(String edmCurrentLocation);

	/**
	 * Retrieves the edmRights field for a Proxy
	 * 
	 * @return String array containing the edmRights of a Proxy
	 */
	String getEdmRights();

	/**
	 * Set the edmRights field for a Proxy
	 * 
	 * @param edmRights
	 * 			String array containing the edmRights of a Proxy
	 */
	void setEdmRights(String edmRights);

	/**
	 * Retrieve the ore:ProxyIn field for a Proxy (It always points to the rdf:About of an Aggregation)
	 * 
	 * @return	String containing the proxyIn of a Proxy
	 */
	String getProxyIn();

	/**
	 * Set the proxyIn field for a Proxy
	 * 
	 * @param proxyIn
	 * 			String containing the proxyIn of a Proxy
	 */
	void setProxyIn(String proxyIn);

	/**
	 * Retrieve the ore:ProxyFor field for a Proxy (It always points to the rdf:About of an ProvidedCHO)
	 * 
	 * @return	String containing the proxyFor of a Proxy
	 */
	String getProxyFor();

	/**
	 * Set the proxyFor field for a Proxy
	 * 
	 * @param proxyFor
	 * 			String containing the proxyIn of a Proxy
	 */
	void setProxyFor(String proxyFor);

}
