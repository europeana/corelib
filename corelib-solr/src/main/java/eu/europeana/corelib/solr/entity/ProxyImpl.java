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

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.Proxy
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

@Entity("Proxy")
public class ProxyImpl implements Proxy {

	@Id ObjectId id;
	@Indexed(unique=true)
	private String about;
	private String[] dcContributor;
	private String[] dcCoverage;
	private String[] dcCreator;
	private String[] dcDate;
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
	private String[] dctermsCreated;
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
	private String edmRights;
	private String proxyIn;
	private String proxyFor;
	
	@Override
	public String getEdmRights() {
		return edmRights;
	}

	@Override
	public void setEdmRights(String edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public String getProxyIn() {
		return proxyIn;
	}

	@Override
	public void setProxyIn(String proxyIn) {
		this.proxyIn = proxyIn;
	}

	@Override
	public String getProxyFor() {
		return proxyFor;
	}

	@Override
	public void setProxyFor(String proxyFor) {
		this.proxyFor = proxyFor;
	}

	@Override
	public String getAbout() {
		return about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
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
	public String[] getDcDate() {
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
	public String[] getDctermsCreated() {
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
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public void setDcContributor(String[] dcContributor) {
		this.dcContributor = dcContributor.clone();
	}

	@Override
	public void setDcCoverage(String[] dcCoverage) {
		this.dcCoverage = dcCoverage.clone();
	}

	@Override
	public void setDcCreator(String[] dcCreator) {
		this.dcCreator = dcCreator.clone();
	}

	@Override
	public void setDcDate(String[] dcDate) {
		this.dcDate = dcDate.clone();
	}

	@Override
	public void setDcDescription(String[] dcDescription) {
		this.dcDescription = dcDescription.clone();
	}

	@Override
	public void setDcFormat(String[] dcFormat) {
		this.dcFormat = dcFormat.clone();
	}

	@Override
	public void setDcIdentifier(String[] dcIdentifier) {
		this.dcIdentifier = dcIdentifier.clone();
	}

	@Override
	public void setDcLanguage(String[] dcLanguage) {
		this.dcLanguage = dcLanguage.clone();
	}

	@Override
	public void setDcPublisher(String[] dcPublisher) {
		this.dcPublisher = dcPublisher.clone();
	}

	@Override
	public void setDcRelation(String[] dcRelation) {
		this.dcRelation = dcRelation.clone();
	}

	@Override
	public void setDcRights(String[] dcRights) {
		this.dcRights = dcRights.clone();
	}

	@Override
	public void setDcSource(String[] dcSource) {
		this.dcSource = dcSource.clone();
	}

	@Override
	public void setDcSubject(String[] dcSubject) {
		this.dcSubject = dcSubject.clone();
	}

	@Override
	public void setDcTitle(String[] dcTitle) {
		this.dcTitle = dcTitle.clone();
	}

	@Override
	public void setDcType(String[] dcType) {
		this.dcType = dcType.clone();
	}

	@Override
	public void setDctermsAlternative(String[] dctermsAlternative) {
		this.dctermsAlternative = dctermsAlternative.clone();
	}

	@Override
	public void setDctermsConformsTo(String[] dctermsConformsTo) {
		this.dctermsConformsTo = dctermsConformsTo.clone();
	}

	@Override
	public void setDctermsCreated(String[] strings) {
		this.dctermsCreated = strings.clone();
	}

	@Override
	public void setDctermsExtent(String[] dctermsExtent) {
		this.dctermsExtent = dctermsExtent.clone();
	}

	@Override
	public void setDctermsHasFormat(String[] dctermsHasFormat) {
		this.dctermsHasFormat = dctermsHasFormat.clone();
	}

	@Override
	public void setDctermsHasPart(String[] dctermsHasPart) {
		this.dctermsHasPart = dctermsHasPart.clone();
	}

	@Override
	public void setDctermsHasVersion(String[] dctermsHasVersion) {
		this.dctermsHasVersion = dctermsHasVersion.clone();
	}

	@Override
	public void setDctermsIsFormatOf(String[] dctermsIsFormatOf) {
		this.dctermsIsFormatOf = dctermsIsFormatOf.clone();
	}

	@Override
	public void setDctermsIsPartOf(String[] dctermsIsPartOf) {
		this.dctermsIsPartOf = dctermsIsPartOf.clone();
	}

	@Override
	public void setDctermsIsReferencedBy(String[] dctermsIsReferencedBy) {
		this.dctermsIsReferencedBy = dctermsIsReferencedBy.clone();
	}

	@Override
	public void setDctermsIsReplacedBy(String[] dctermsIsReplacedBy) {
		this.dctermsIsReplacedBy = dctermsIsReplacedBy.clone();
	}

	@Override
	public void setDctermsIsRequiredBy(String[] dctermsIsRequiredBy) {
		this.dctermsIsRequiredBy = dctermsIsRequiredBy.clone();
	}

	@Override
	public void setDctermsIssued(String[] dctermsIssued) {
		this.dctermsIssued = dctermsIssued.clone();
	}

	@Override
	public void setDctermsIsVersionOf(String[] dctermsIsVersionOf) {
		this.dctermsIsVersionOf = dctermsIsVersionOf.clone();
	}

	@Override
	public void setDctermsMedium(String[] dctermsMedium) {
		this.dctermsMedium = dctermsMedium.clone();
	}

	@Override
	public void setDctermsProvenance(String[] dctermsProvenance) {
		this.dctermsProvenance = dctermsProvenance.clone();
	}

	@Override
	public void setDctermsReferences(String[] dctermsReferences) {
		this.dctermsReferences = dctermsReferences.clone();
	}

	@Override
	public void setDctermsReplaces(String[] dctermsReplaces) {
		this.dctermsReplaces = dctermsReplaces.clone();
	}

	@Override
	public void setDctermsRequires(String[] dctermsRequires) {
		this.dctermsRequires = dctermsRequires.clone();
	}

	@Override
	public void setDctermsSpatial(String[] dctermsSpatial) {
		this.dctermsSpatial = dctermsSpatial.clone();
	}

	@Override
	public void setDctermsTOC(String[] dctermsTOC) {
		this.dctermsTOC = dctermsTOC.clone();
	}

	@Override
	public void setDctermsTemporal(String[] dctermsTemporal) {
		this.dctermsTemporal = dctermsTemporal.clone();
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
	public boolean equals(Object o) {
		if(o==null){
			return false;
		}
		if(o.getClass() == this.getClass()){
			return this.getId().equals(((ProxyImpl) o).getId());
		}
		return false;
	}

	@Override
	public int hashCode(){ 
		return this.about.hashCode();
	}
}
