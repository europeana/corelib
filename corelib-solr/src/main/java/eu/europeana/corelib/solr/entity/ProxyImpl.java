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
package eu.europeana.corelib.solr.entity;

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Proxy;

import com.google.code.morphia.annotations.*;
import org.bson.types.*;
/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.Proxy
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

@Entity("Proxy")
public class ProxyImpl implements Proxy {

	@Id ObjectId proxyId;
	
	private String[] owlSameAs;
	private String[] dcContributor;
	private String[] dcCoverage;
	private String[] dcCreator;
	private Date[] dcDate;
	private String[] dcDescription;
	private String[] dcFormat;
	private String[] dcIdentifier;
	private String[] dcLanguage;
	private String[] dcPublisher;
	private String[] dcRelation;
	private String[] dcRights;
	private String[] dcSource;
	private String[] dcSubject;
	private String[] dcTitle;
	private String[] dcType;
	private String[] dctermsAlternative;
	private String[] dctermsConformsTo;
	private Date[] dctermsCreated;
	private String[] dctermsExtent;
	private String[] dctermsHasFormat;
	private String[] dctermsHasPart;
	private String[] dctermsHasVersion;
	private String[] dctermsIsFormatOf;
	private String[] dctermsIsPartOf;
	private String[] dctermsIsReferencedBy;
	private String[] dctermsIsReplacedBy;
	private String[] dctermsIsRequiredBy;
	private String[] dctermsIssued;
	private String[] dctermsIsVersionOf;
	private String[] dctermsMedium;
	private String[] dctermsProvenance;
	private String[] dctermsReferences;
	private String[] dctermsReplaces;
	private String[] dctermsRequires;
	private String[] dctermsSpatial;
	private String[] dctermsTOC;
	private String[] dctermsTemporal;
	private DocType edmType;
	private String edmCurrentLocation;
	private String edmIsNextInSequence;
	
	
	@Override
	public String[] getOwlSameAs() {
		
		return this.owlSameAs;
		
	}

	@Override
	public String[] getDcContributor() {
		return this.dcContributor;
	}

	@Override
	public String[] getDcCoverage() {
		return this.dcCoverage;
	}

	@Override
	public String[] getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public Date[] getDcDate() {
		return this.dcDate;
	}

	@Override
	public String[] getDcDescription() {
		return this.dcDescription;
	}

	@Override
	public String[] getDcFormat() {
		return this.dcFormat;
	}

	@Override
	public String[] getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public String[] getDcLanguage() {
		return this.dcLanguage;
	}

	@Override
	public String[] getDcPublisher() {
		return this.dcPublisher;
	}

	@Override
	public String[] getDcRelation() {
		return this.dcRelation;
	}

	@Override
	public String[] getDcRights() {
		return this.dcRights;
	}

	@Override
	public String[] getDcSource() {
		return this.dcSource;
	}

	@Override
	public String[] getDcSubject() {
		return this.dcSubject;
	}

	@Override
	public String[] getDcTitle() {
		return this.dcTitle;
	}

	@Override
	public String[] getDcType() {
		return this.dcType;
	}

	@Override
	public String[] getDctermsAlternative() {
		return this.dctermsAlternative;
	}

	@Override
	public String[] getDctermsConformsTo() {
		return this.dctermsConformsTo;
	}

	@Override
	public Date[] getDctermsCreated() {
		return this.dctermsCreated;
	}

	@Override
	public String[] getDctermsExtent() {
		return this.dctermsExtent;
	}

	@Override
	public String[] getDctermsHasFormat() {
		return this.dctermsHasFormat;
	}

	@Override
	public String[] getDctermsHasPart() {
		return this.dctermsHasPart;
	}

	@Override
	public String[] getDctermsHasVersion() {
		return this.dctermsHasVersion;
	}

	@Override
	public String[] getDctermsIsFormatOf() {
		return this.dctermsIsFormatOf;
	}

	@Override
	public String[] getDctermsIsPartOf() {
		return this.dctermsIsPartOf;
	}

	@Override
	public String[] getDctermsIsReferencedBy() {
		return this.dctermsIsReferencedBy;
	}

	@Override
	public String[] getDctermsIsReplacedBy() {
		return this.dctermsIsReplacedBy;
	}

	@Override
	public String[] getDctermsIsRequiredBy() {
		return this.dctermsIsRequiredBy;
	}

	@Override
	public String[] getDctermsIssued() {
		return this.dctermsIssued;
	}

	@Override
	public String[] getDctermsIsVersionOf() {
		return this.dctermsIsVersionOf;
	}

	@Override
	public String[] getDctermsMedium() {
		return this.dctermsMedium;
	}

	@Override
	public String[] getDctermsProvenance() {
		return this.dctermsProvenance;
	}

	@Override
	public String[] getDctermsReferences() {
		return this.dctermsReferences;
	}

	@Override
	public String[] getDctermsReplaces() {
		return this.dctermsReplaces;
	}

	@Override
	public String[] getDctermsRequires() {
		return this.dctermsRequires;
	}

	@Override
	public String[] getDctermsSpatial() {
		return this.dctermsSpatial;
	}

	@Override
	public String[] getDctermsTOC() {
		return this.dctermsTOC;
	}

	@Override
	public String[] getDctermsTemporal() {
		return this.dctermsTemporal;
	}

	@Override
	public DocType getEdmType() {
		return this.edmType;
	}

	@Override
	public String getEdmCurrentLocation() {
		return this.edmCurrentLocation;
	}

	@Override
	public ObjectId getProxyId() {
		return this.proxyId;
	}

	@Override
	public String getEdmIsNextInSequence() {
		return this.edmIsNextInSequence;
	}

	@Override
	public void setProxyId(ObjectId proxyId) {
		this.proxyId = proxyId;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
	}

	@Override
	public void setDcContributor(String[] dcContributor) {
		this.dcContributor = dcContributor;
	}

	@Override
	public void setDcCoverage(String[] dcCoverage) {
		this.dcCoverage = dcCoverage;
	}

	@Override
	public void setDcCreator(String[] dcCreator) {
		this.dcCreator = dcCreator;
	}

	@Override
	public void setDcDate(Date[] dcDate) {
		this.dcDate = dcDate;
	}

	@Override
	public void setDcDescription(String[] dcDescription) {
		this.dcDescription = dcDescription;
	}

	@Override
	public void setDcFormat(String[] dcFormat) {
		this.dcFormat = dcFormat;
	}

	@Override
	public void setDcIdentifier(String[] dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}

	@Override
	public void setDcLanguage(String[] dcLanguage) {
		this.dcLanguage = dcLanguage;
	}

	@Override
	public void setDcPublisher(String[] dcPublisher) {
		this.dcPublisher = dcPublisher;
	}

	@Override
	public void setDcRelation(String[] dcRelation) {
		this.dcRelation = dcRelation;
	}

	@Override
	public void setDcRights(String[] dcRights) {
		this.dcRights = dcRights;
	}

	@Override
	public void setDcSource(String[] dcSource) {
		this.dcSource = dcSource;
	}

	@Override
	public void setDcSubject(String[] dcSubject) {
		this.dcSubject = dcSubject;
	}

	@Override
	public void setDcTitle(String[] dcTitle) {
		this.dcTitle = dcTitle;
	}

	@Override
	public void setDcType(String[] dcType) {
		this.dcType = dcType;
	}

	@Override
	public void setDctermsAlternative(String[] dctermsAlternative) {
		this.dctermsAlternative = dctermsAlternative;
	}

	@Override
	public void setDctermsConformsTo(String[] dctermsConformsTo) {
		this.dctermsConformsTo = dctermsConformsTo;
	}

	@Override
	public void setDctermsCreated(Date[] dctermsCreated) {
		this.dctermsCreated = dctermsCreated;
	}

	@Override
	public void setDctermsExtent(String[] dctermsExtent) {
		this.dctermsExtent = dctermsExtent;
	}

	@Override
	public void setDctermsHasFormat(String[] dctermsHasFormat) {
		this.dctermsHasFormat = dctermsHasFormat;
	}

	@Override
	public void setDctermsHasPart(String[] dctermsHasPart) {
		this.dctermsHasPart = dctermsHasPart;
	}

	@Override
	public void setDctermsHasVersion(String[] dctermsHasVersion) {
		this.dctermsHasVersion = dctermsHasVersion;
	}

	@Override
	public void setDctermsIsFormatOf(String[] dctermsIsFormatOf) {
		this.dctermsIsFormatOf = dctermsIsFormatOf;
	}

	@Override
	public void setDctermsIsPartOf(String[] dctermsIsPartOf) {
		this.dctermsIsPartOf = dctermsIsPartOf;
	}

	@Override
	public void setDctermsIsReferencedBy(String[] dctermsIsReferencedBy) {
		this.dctermsIsReferencedBy = dctermsIsReferencedBy;
	}

	@Override
	public void setDctermsIsReplacedBy(String[] dctermsIsReplacedBy) {
		this.dctermsIsReplacedBy = dctermsIsReplacedBy;
	}

	@Override
	public void setDctermsIsRequiredBy(String[] dctermsIsRequiredBy) {
		this.dctermsIsRequiredBy = dctermsIsRequiredBy;
	}

	@Override
	public void setDctermsIssued(String[] dctermsIssued) {
		this.dctermsIssued = dctermsIssued;
	}

	@Override
	public void setDctermsIsVersionOf(String[] dctermsIsVersionOf) {
		this.dctermsIsVersionOf = dctermsIsVersionOf;
	}

	@Override
	public void setDctermsMedium(String[] dctermsMedium) {
		this.dctermsMedium = dctermsMedium;
	}

	@Override
	public void setDctermsProvenance(String[] dctermsProvenance) {
		this.dctermsProvenance = dctermsProvenance;
	}

	@Override
	public void setDctermsReferences(String[] dctermsReferences) {
		this.dctermsReferences = dctermsReferences;
	}

	@Override
	public void setDctermsReplaces(String[] dctermsReplaces) {
		this.dctermsReplaces = dctermsReplaces;
	}

	@Override
	public void setDctermsRequires(String[] dctermsRequires) {
		this.dctermsRequires = dctermsRequires;
	}

	@Override
	public void setDctermsSpatial(String[] dctermsSpatial) {
		this.dctermsSpatial = dctermsSpatial;
	}

	@Override
	public void setDctermsTOC(String[] dctermsTOC) {
		this.dctermsTOC = dctermsTOC;
	}

	@Override
	public void setDctermsTemporal(String[] dctermsTemporal) {
		this.dctermsTemporal = dctermsTemporal;
	}

	@Override
	public void setEdmType(DocType edmType) {
		this.edmType = edmType;
	}

	@Override
	public void setEdmCurrentLocation(String edmCurrentLocation) {
		this.edmCurrentLocation = edmCurrentLocation;
	}

	@Override
	public void setEdmIsNextInSequence(String edmIsNextInSequence) {
		this.edmIsNextInSequence = edmIsNextInSequence;
	}

	@Override
	public boolean equals(Object o){
		return this.getProxyId().equals(((ProxyImpl)o).getProxyId());
	}
}
