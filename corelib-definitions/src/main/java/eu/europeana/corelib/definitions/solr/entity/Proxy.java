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

import java.util.Date;

import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * EDM Proxy fields representation
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Proxy {
	/**
	 * Retrieve the unique Proxy Id
	 * @return ObjectId representing the unique Id of the Proxy
	 */
	ObjectId getProxyId();
	
	/**
	 * Retrieve the owl:sameAs fields for a Proxy
	 * @return String array representing the owl:sameAs fields of a Proxy
	 */
	String[] getOwlSameAs();
	
	/**
	 * Retrieve the dc:contributor fields for a Proxy
	 * @return String array representing the dc:contributor fields of a Proxy
	 */
	String[] getDcContributor();
	
	/**
	 * Retrieve the dc:coverage fields for a Proxy
	 * @return String array representing the dc:coverage fields of a Proxy
	 */
	String[] getDcCoverage();
	
	/**
	 * Retrieve the dc:creator fields for a Proxy
	 * @return String array representing the dc:creator fields for a Proxy
	 */
	String[] getDcCreator();
	
	/**
	 * Retrieve the dc:date fields for a Proxy
	 * @return Date array representing the dc:date fields for a Proxy
	 */
	Date[] getDcDate();
	
	/**
	 * Retrieve the dc:description fields for a Proxy
	 * @return String array representing the dc:description fields for a Proxy
	 */
	String[] getDcDescription();
	
	/**
	 * Retrieve the dc:format fields for a Proxy
	 * @return String array representing the dc:format fields for a Proxy
	 */
	String[] getDcFormat();
	
	/**
	 * Retrieve the dc:identifier fields for a Proxy
	 * @return String array representing the dc:identifier fields for a Proxy
	 */
	String[] getDcIdentifier();
	
	/**
	 * Retrieve the dc:language fields for a Proxy
	 * @return String array representing the dc:language fields for a Proxy
	 */
	String[] getDcLanguage();
	
	/**
	 * Retrieve the dc:publisher fields for a Proxy
	 * @return String array representing the dc:publisher fields for a Proxy
	 */
	String[] getDcPublisher();
	
	/**
	 * Retrieve the dc:relation fields for a Proxy
	 * @return String array representing the dc:relation fields for a Proxy
	 */
	String[] getDcRelation();
	
	/**
	 * Retrieve the dc:rights fields for a Proxy
	 * @return String array representing the dc:rights fields for a Proxy
	 */
	String[] getDcRights();
	
	/**
	 * Retrieve the dc:source fields for a Proxy
	 * @return String array representing the dc:soure fields for a Proxy
	 */
	String[] getDcSource();
	
	/**
	 * Retrieve the dc:subject fields for a Proxy
	 * @return String array representing the dc:subject fields for a Proxy
	 */
	String[] getDcSubject();
	
	/**
	 * Retrieve the dc:title fields for a Proxy
	 * @return String array representing the dc:title fields for a Proxy
	 */
	String[] getDcTitle();
	
	/**
	 * Retrieve the dc:type fields for a Proxy
	 * @return String array representing the dc:type fields for a Proxy
	 */
	String[] getDcType();
	
	/**
	 * Retrieve the dcterms:alternative fields for a Proxy
	 * @return String array representing the dcterms:alternative fields for a Proxy
	 */
	String[] getDctermsAlternative();
	
	/**
	 * Retrieve the dc:conformsTo fields for a Proxy
	 * @return String array representing the dc:conformsTo fields for a Proxy
	 */
	String[] getDctermsConformsTo();
	
	/**
	 * Retrieve the dcterms:created fields for a Proxy
	 * @return Date array representing the dcterms:created fields for a Proxy
	 */
	Date[] getDctermsCreated();
	
	/**
	 * Retrieve the dcterms:extent fields for a Proxy
	 * @return String array representing the dc:extent fields for a Proxy
	 */
	String[] getDctermsExtent();
	
	/**
	 * Retrieve the dcterms:hasFormat fields for a Proxy
	 * @return String array representing the dcterms:hasFormat fields for a Proxy
	 */
	String[] getDctermsHasFormat();
	
	/**
	 * Retrieve the dcterms:hasPart fields for a Proxy
	 * @return String array representing the dcterms:hasPart fields for a Proxy
	 */
	String[] getDctermsHasPart();
	
	/**
	 * Retrieve the dcterms:hasVersion fields for a Proxy
	 * @return String array representing the dcterms:hasVersion fields for a Proxy
	 */
	String[] getDctermsHasVersion();
	
	/**
	 * Retrieve the dcterms:isVersionOf fields for a Proxy
	 * @return String array representing the dcterms:isVersionOf fields for a Proxy
	 */
	String[] getDctermsIsFormatOf();
	
	/**
	 * Retrieve the dcterms:isFormatOf fields for a Proxy
	 * @return String array representing the dcterms:isFormatOf fields for a Proxy
	 */
	String[] getDctermsIsPartOf();
	
	/**
	 * Retrieve the dcterms:isReferencedBy fields for a Proxy
	 * @return String array representing the dcterms:isReferencedBy fields for a Proxy
	 */
	String[] getDctermsIsReferencedBy();
	
	/**
	 * Retrieve the dcterms:isReplacedBy fields for a Proxy
	 * @return String array representing the dcterms:isReplacedBy fields for a Proxy
	 */
	String[] getDctermsIsReplacedBy();
	
	/**
	 * Retrieve the dcterms:isRequiredBy fields for a Proxy
	 * @return String array representing the dcterms:isRequiredBy fields for a Proxy
	 */
	String[] getDctermsIsRequiredBy();
	
	/**
	 * Retrieve the dcterms:issued fields for a Proxy
	 * @return String array representing the dcterms:issued fields for a Proxy
	 */
	String[] getDctermsIssued();
	
	/**
	 * Retrieve the dcterms:isVersionOf fields for a Proxy
	 * @return String array representing the dcterms:isVersionOf fields for a Proxy
	 */
	String[] getDctermsIsVersionOf();
	
	/**
	 * Retrieve the dcterms:medium fields for a Proxy
	 * @return String array representing the dcterms:medium fields for a Proxy
	 */
	String[] getDctermsMedium();
	
	/**
	 * Retrieve the dcterms:provenance fields for a Proxy
	 * @return String array representing the dcterms:provenance fields for a Proxy
	 */
	String[] getDctermsProvenance();
	
	/**
	 * Retrieve the dcterms:references fields for a Proxy
	 * @return String array representing the dcterms:references fields for a Proxy
	 */
	String[] getDctermsReferences();
	
	/**
	 * Retrieve the dcterms:replaces fields for a Proxy
	 * @return String array representing the dcterms:replaces fields for a Proxy
	 */
	String[] getDctermsReplaces();
	
	/**
	 * Retrieve the dcterms:requires fields for a Proxy
	 * @return String array representing the dcterms:requires fields for a Proxy
	 */
	String[] getDctermsRequires();
	
	/**
	 * Retrieve the dcterms:spatial fields for a Proxy
	 * @return String array representing the dcterms:spatial fields for a Proxy
	 */
	String[] getDctermsSpatial();
	
	/**
	 * Retrieve the dcterms:tableOfContents fields for a Proxy
	 * @return String array representing the dcterms:tableOfContents fields for a Proxy
	 */
	String[] getDctermsTOC();
	
	/**
	 * Retrieve the dcterms:temporal fields for a Proxy
	 * @return String array representing the dcterms:temporal fields for a Proxy
	 */
	String[] getDctermsTemporal();
	
	/**
	 * Retrieve the edm:type fields for a Proxy
	 * @return DocType representing the edm:type fields for a Proxy
	 */
	DocType getEdmType();
	
	/**
	 * Retrieve the edm:currentLocation fields for a Proxy
	 * @return String representing the edm:currentLocation fields for a Proxy
	 */
	String getEdmCurrentLocation();
	
	/**
	 * Retrieve the edm:isNextInSequence fields for a Proxy
	 * @return String representing the edm:isNextInSequence fields for a Proxy
	 */
	String getEdmIsNextInSequence();
	
}
