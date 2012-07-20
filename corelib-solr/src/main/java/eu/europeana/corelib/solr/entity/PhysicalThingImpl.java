package eu.europeana.corelib.solr.entity;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.PhysicalThing;
import eu.europeana.corelib.utils.StringArrayUtils;
@Entity("PhysicalThing")
public class PhysicalThingImpl extends AbstractEdmEntityImpl implements PhysicalThing {


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
	private String[] rdfType;
	private String[] edmHasMet;
	private String[] edmHasType;
	private String[] edmIncorporates;
	private String[] edmIsDerivativeOf;
	private String[] edmIsRelatedTo;
	private String edmIsRepresentationOf;
	private String[] edmIsSimilarTo;
	private String[] edmIsSuccessorOf;
	private String[] edmRealizes;
	private String[] edmWasPresentAt;
	private String edmCurrentLocation;
	private String edmIsNextInSequence;
	private String edmRights;

	@Override
	public String getEdmRights() {
		return edmRights;
	}

	@Override
	public void setEdmRights(String edmRights) {
		this.edmRights = edmRights;
	}



	
	@Override
	public String[] getDcContributor() {
		return (StringArrayUtils.isNotBlank(dcContributor) ? this.dcContributor.clone() : null);
	}

	@Override
	public String[] getDcCoverage() {
		return (StringArrayUtils.isNotBlank(dcCoverage) ? this.dcCoverage.clone() : null);
	}

	@Override
	public String[] getDcCreator() {
		return (StringArrayUtils.isNotBlank(dcCreator) ? this.dcCreator.clone() : null);
	}

	@Override
	public String[] getDcDate() {
		return (StringArrayUtils.isNotBlank(dcDate) ? this.dcDate.clone() : null);
	}

	@Override
	public String[] getDcDescription() {
		return (StringArrayUtils.isNotBlank(dcDescription) ? this.dcDescription.clone() : null);
	}

	@Override
	public String[] getDcFormat() {
		return (StringArrayUtils.isNotBlank(dcFormat) ? this.dcFormat.clone() : null);
	}

	@Override
	public String[] getDcIdentifier() {
		return (StringArrayUtils.isNotBlank(dcIdentifier) ? this.dcIdentifier.clone() : null);
	}

	@Override
	public String[] getDcLanguage() {
		return (StringArrayUtils.isNotBlank(dcLanguage) ? this.dcLanguage.clone() : null);
	}

	@Override
	public String[] getDcPublisher() {
		return (StringArrayUtils.isNotBlank(dcPublisher) ? this.dcPublisher.clone() : null);
	}

	@Override
	public String[] getDcRelation() {
		return (StringArrayUtils.isNotBlank(dcRelation) ? this.dcRelation.clone() : null);
	}

	@Override
	public String[] getDcRights() {
		return (StringArrayUtils.isNotBlank(dcRights) ? this.dcRights.clone() : null);
	}

	@Override
	public String[] getDcSource() {
		return (StringArrayUtils.isNotBlank(dcSource) ? this.dcSource.clone() : null);
	}

	@Override
	public String[] getDcSubject() {
		return (StringArrayUtils.isNotBlank(dcSubject) ? this.dcSubject.clone() : null);
	}

	@Override
	public String[] getDcTitle() {
		return (StringArrayUtils.isNotBlank(dcTitle) ? this.dcTitle.clone() : null);
	}

	@Override
	public String[] getDcType() {
		return (StringArrayUtils.isNotBlank(dcType) ? this.dcType.clone() : null);
	}

	@Override
	public String[] getDctermsAlternative() {
		return (StringArrayUtils.isNotBlank(dctermsAlternative) ? this.dctermsAlternative
				.clone() : null);
	}

	@Override
	public String[] getDctermsConformsTo() {
		return (StringArrayUtils.isNotBlank(dctermsConformsTo) ? this.dctermsConformsTo.clone()
				: null);
	}

	@Override
	public String[] getDctermsCreated() {
		return (StringArrayUtils.isNotBlank(dctermsCreated) ? this.dctermsCreated.clone()
				: null);
	}

	@Override
	public String[] getDctermsExtent() {
		return (StringArrayUtils.isNotBlank(dctermsExtent) ? this.dctermsExtent.clone() : null);
	}

	@Override
	public String[] getDctermsHasFormat() {
		return (StringArrayUtils.isNotBlank(dctermsHasFormat) ? this.dctermsHasFormat.clone()
				: null);
	}

	@Override
	public String[] getDctermsHasPart() {
		return (StringArrayUtils.isNotBlank(dctermsHasPart) ? this.dctermsHasPart.clone()
				: null);
	}

	@Override
	public String[] getDctermsHasVersion() {
		return (StringArrayUtils.isNotBlank(dctermsHasVersion) ? this.dctermsHasVersion.clone()
				: null);
	}

	@Override
	public String[] getDctermsIsFormatOf() {
		return (StringArrayUtils.isNotBlank(dctermsIsFormatOf) ? this.dctermsIsFormatOf.clone()
				: null);
	}

	@Override
	public String[] getDctermsIsPartOf() {
		return (StringArrayUtils.isNotBlank(dctermsIsPartOf) ? this.dctermsIsPartOf.clone()
				: null);
	}

	@Override
	public String[] getDctermsIsReferencedBy() {
		return (StringArrayUtils.isNotBlank(dctermsIsReferencedBy) ? this.dctermsIsReferencedBy
				.clone() : null);
	}

	@Override
	public String[] getDctermsIsReplacedBy() {
		return (StringArrayUtils.isNotBlank(dctermsIsReplacedBy) ? this.dctermsIsReplacedBy
				.clone() : null);
	}

	@Override
	public String[] getDctermsIsRequiredBy() {
		return (StringArrayUtils.isNotBlank(dctermsIsRequiredBy) ? this.dctermsIsRequiredBy
				.clone() : null);
	}

	@Override
	public String[] getDctermsIssued() {
		return (StringArrayUtils.isNotBlank(dctermsIssued) ? this.dctermsIssued.clone() : null);
	}

	@Override
	public String[] getDctermsIsVersionOf() {
		return (StringArrayUtils.isNotBlank(dctermsIsVersionOf) ? this.dctermsIsVersionOf
				.clone() : null);
	}

	@Override
	public String[] getDctermsMedium() {
		return (StringArrayUtils.isNotBlank(dctermsMedium) ? this.dctermsMedium.clone() : null);
	}

	@Override
	public String[] getDctermsProvenance() {
		return (StringArrayUtils.isNotBlank(dctermsProvenance) ? this.dctermsProvenance.clone()
				: null);
	}

	@Override
	public String[] getDctermsReferences() {
		return (StringArrayUtils.isNotBlank(dctermsReferences) ? this.dctermsReferences.clone()
				: null);
	}

	@Override
	public String[] getDctermsReplaces() {
		return (StringArrayUtils.isNotBlank(dctermsReplaces) ? this.dctermsReplaces.clone()
				: null);
	}

	@Override
	public String[] getDctermsRequires() {
		return (StringArrayUtils.isNotBlank(dctermsRequires) ? this.dctermsRequires.clone()
				: null);
	}

	@Override
	public String[] getDctermsSpatial() {
		return (StringArrayUtils.isNotBlank(dctermsSpatial) ? this.dctermsSpatial.clone()
				: null);
	}

	@Override
	public String[] getDctermsTOC() {
		return (StringArrayUtils.isNotBlank(dctermsTOC) ? this.dctermsTOC.clone() : null);
	}

	@Override
	public String[] getDctermsTemporal() {
		return (StringArrayUtils.isNotBlank(dctermsTemporal) ? this.dctermsTemporal.clone()
				: null);
	}



	@Override
	public String getEdmCurrentLocation() {
		return this.edmCurrentLocation;
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
	public void setEdmCurrentLocation(String edmCurrentLocation) {
		this.edmCurrentLocation = edmCurrentLocation;
	}

	

	@Override
	public void setRdfType(String[] rdfType) {
		this.rdfType = rdfType;
	}

	@Override
	public String[] getRdfType() {
		return (StringArrayUtils.isNotBlank(rdfType) ? this.rdfType.clone()
				: null);
	}

	@Override
	public String[] getEdmHasMet() {
		return (StringArrayUtils.isNotBlank(edmHasMet) ? this.edmHasMet.clone()
				: null);
	}

	@Override
	public void setEdmHasMet(String[] edmHasMet) {
		this.edmHasMet = edmHasMet;
		
	}

	@Override
	public String[] getEdmHasType() {
		return (StringArrayUtils.isNotBlank(edmHasType) ? this.edmHasType.clone()
				: null);
	}

	@Override
	public void setEdmHasType(String[] edmHasType) {
		this.edmHasType = edmHasType;
		
	}

	@Override
	public void setEdmIncorporates(String[] edmIncorporates) {
		this.edmIncorporates = edmIncorporates;
	}

	@Override
	public String[] getEdmIncorporates() {
		return (StringArrayUtils.isNotBlank(edmIncorporates) ? this.edmIncorporates.clone()
				: null);
	}

	@Override
	public void setEdmIsDerivativeOf(String[] edmIsDerivativeOf) {
		this.edmIsDerivativeOf= edmIsDerivativeOf;
		
	}

	@Override
	public String[] getEdmIsDerivativeOf() {
		return (StringArrayUtils.isNotBlank(edmIsDerivativeOf) ? this.edmIsDerivativeOf.clone()
				: null);
	}

	@Override
	public void setEdmIsRelatedTo(String[] edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
		
	}

	@Override
	public String[] getEdmIsRelatedTo() {
		return (StringArrayUtils.isNotBlank(edmIsRelatedTo) ? this.edmIsRelatedTo.clone()
				: null);
	}

	@Override
	public void setEdmIsRepresentationOf(String edmIsRepresentationOf) {
		this.edmIsRepresentationOf = edmIsRepresentationOf;
		
	}

	@Override
	public String getEdmIsRepresentationOf() {
		return this.edmIsRepresentationOf;
	}

	@Override
	public void setEdmIsSimilarTo(String[] edmIsSimilarTo) {
		this.edmIsSimilarTo = edmIsSimilarTo;
	}

	@Override
	public String[] getEdmIsSimilarTo() {
		return (StringArrayUtils.isNotBlank(edmIsSimilarTo) ? this.edmIsSimilarTo.clone()
				: null);
	}

	@Override
	public void setEdmIsSuccessorOf(String[] edmIsSuccessorOf) {
		this.edmIsSuccessorOf = edmIsSuccessorOf;
	}

	@Override
	public String[] getEdmIsSuccessorOf() {
		return (StringArrayUtils.isNotBlank(edmIsSuccessorOf) ? this.edmIsSuccessorOf.clone()
				: null);
	}

	@Override
	public void setEdmRealizes(String[] edmRealizes) {
		this.edmRealizes = edmRealizes;
		
	}

	@Override
	public String[] getEdmRealizes() {
		return (StringArrayUtils.isNotBlank(edmRealizes) ? this.edmRealizes.clone()
				: null);
	}

	@Override
	public void setEdmWasPresentAt(String[] edmWasPresentAt) {
		this.edmWasPresentAt = edmWasPresentAt;
	}

	@Override
	public String[] getEdmWasPresentAt() {
		return (StringArrayUtils.isNotBlank(edmWasPresentAt) ? this.edmWasPresentAt.clone()
				: null);
	}

	@Override
	public String getEdmIsNextInSequence() {
		return this.edmIsNextInSequence;
	}
	
	@Override
	public void setEdmIsNextInSequence(String edmIsNextInSequence) {
		this.edmIsNextInSequence = edmIsNextInSequence;
	}

}
