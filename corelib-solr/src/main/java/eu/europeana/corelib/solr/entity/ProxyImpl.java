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

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.Proxy
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

@Embedded
public class ProxyImpl implements Proxy {

	private ObjectId id;
	@Indexed(unique=false)
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
		return this.proxyIn;
	}

	@Override
	public void setProxyIn(String proxyIn) {
		this.proxyIn = proxyIn;
	}

	@Override
	public String getProxyFor() {
		return this.proxyFor;
	}

	@Override
	public void setProxyFor(String proxyFor) {
		this.proxyFor = proxyFor;
	}

	@Override
	public String getAbout() {
		return this.about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public String[] getDcContributor() {
		return (this.dcContributor!=null?this.dcContributor.clone():null);
	}

	@Override
	public String[] getDcCoverage() {
		return (this.dcCoverage!=null?this.dcCoverage.clone():null);
	}

	@Override
	public String[] getDcCreator() {
		return (this.dcCreator!=null?this.dcCreator.clone():null);
	}

	@Override
	public String[] getDcDate() {
		return (this.dcDate!=null?this.dcDate.clone():null);
	}

	@Override
	public String[] getDcDescription() {
		return (this.dcDescription!=null?this.dcDescription.clone():null);
	}

	@Override
	public String[] getDcFormat() {
		return (this.dcFormat!=null?this.dcFormat.clone():null);
	}

	@Override
	public String[] getDcIdentifier() {
		return (this.dcIdentifier!=null?this.dcIdentifier.clone():null);
	}

	@Override
	public String[] getDcLanguage() {
		return (this.dcLanguage!=null?this.dcLanguage.clone():null);
	}

	@Override
	public String[] getDcPublisher() {
		return (this.dcPublisher!=null?this.dcPublisher.clone():null);
	}

	@Override
	public String[] getDcRelation() {
		return (this.dcRelation!=null?this.dcRelation.clone():null);
	}

	@Override
	public String[] getDcRights() {
		return (this.dcRights!=null?this.dcRights.clone():null);
	}

	@Override
	public String[] getDcSource() {
		return (this.dcSource!=null?this.dcSource.clone():null);
	}

	@Override
	public String[] getDcSubject() {
		return (this.dcSubject!=null?this.dcSubject.clone():null);
	}

	@Override
	public String[] getDcTitle() {
		return (this.dcTitle!=null?this.dcTitle.clone():null);
	}

	@Override
	public String[] getDcType() {
		return (this.dcType!=null?this.dcType.clone():null);
	}

	@Override
	public String[] getDctermsAlternative() {
		return (this.dctermsAlternative!=null?this.dctermsAlternative.clone():null);
	}

	@Override
	public String[] getDctermsConformsTo() {
		return (this.dctermsConformsTo!=null?this.dctermsConformsTo.clone():null);
	}

	@Override
	public String[] getDctermsCreated() {
		return (this.dctermsCreated!=null?this.dctermsCreated.clone():null);
	}

	@Override
	public String[] getDctermsExtent() {
		return (this.dctermsExtent!=null?this.dctermsExtent.clone():null);
	}

	@Override
	public String[] getDctermsHasFormat() {
		return (this.dctermsHasFormat!=null?this.dctermsHasFormat.clone():null);
	}

	@Override
	public String[] getDctermsHasPart() {
		return (this.dctermsHasPart!=null?this.dctermsHasPart.clone():null);
	}

	@Override
	public String[] getDctermsHasVersion() {
		return (this.dctermsHasVersion!=null?this.dctermsHasVersion.clone():null);
	}

	@Override
	public String[] getDctermsIsFormatOf() {
		return (this.dctermsIsFormatOf!=null?this.dctermsIsFormatOf.clone():null);
	}

	@Override
	public String[] getDctermsIsPartOf() {
		return (this.dctermsIsPartOf!=null?this.dctermsIsPartOf.clone():null);
	}

	@Override
	public String[] getDctermsIsReferencedBy() {
		return (this.dctermsIsReferencedBy!=null?this.dctermsIsReferencedBy.clone():null);
	}

	@Override
	public String[] getDctermsIsReplacedBy() {
		return (this.dctermsIsReplacedBy!=null?this.dctermsIsReplacedBy.clone():null);
	}

	@Override
	public String[] getDctermsIsRequiredBy() {
		return (this.dctermsIsRequiredBy!=null?this.dctermsIsRequiredBy.clone():null);
	}

	@Override
	public String[] getDctermsIssued() {
		return (this.dctermsIssued!=null?this.dctermsIssued.clone():null);
	}

	@Override
	public String[] getDctermsIsVersionOf() {
		return (this.dctermsIsVersionOf!=null?this.dctermsIsVersionOf.clone():null);
	}

	@Override
	public String[] getDctermsMedium() {
		return (this.dctermsMedium!=null?this.dctermsMedium.clone():null);
	}

	@Override
	public String[] getDctermsProvenance() {
		return (this.dctermsProvenance!=null?this.dctermsProvenance.clone():null);
	}

	@Override
	public String[] getDctermsReferences() {
		return (this.dctermsReferences!=null?this.dctermsReferences.clone():null);
	}

	@Override
	public String[] getDctermsReplaces() {
		return (this.dctermsReferences!=null?this.dctermsReplaces.clone():null);
	}

	@Override
	public String[] getDctermsRequires() {
		return (this.dctermsRequires!=null?this.dctermsRequires.clone():null);
	}

	@Override
	public String[] getDctermsSpatial() {
		return (this.dctermsSpatial!=null?this.dctermsSpatial.clone():null);
	}

	@Override
	public String[] getDctermsTOC() {
		return (this.dctermsTOC!=null?this.dctermsTOC.clone():null);
	}

	@Override
	public String[] getDctermsTemporal() {
		return (this.dctermsTemporal!=null?this.dctermsTemporal.clone():null);
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
			return this.getProxyIn().equals(((ProxyImpl) o).getProxyIn());
		}
		return false;
	}

	@Override
	public int hashCode(){ 
		return this.about.hashCode();
	}
}
