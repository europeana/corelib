package eu.europeana.corelib.solr.entity;

import java.util.Map;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.PhysicalThing;
import eu.europeana.corelib.utils.StringArrayUtils;
@Entity("PhysicalThing")
public class PhysicalThingImpl extends AbstractEdmEntityImpl implements PhysicalThing {

	private Map<String,String> dcContributor;
	private Map<String,String> dcCoverage;
	private Map<String,String> dcCreator;
	private Map<String,String> dcDate;
	private Map<String,String> dcDescription;
	private Map<String,String> dcFormat;
	private Map<String,String> dcIdentifier;
	private Map<String,String> dcLanguage;
	private Map<String,String> dcPublisher;
	private Map<String,String> dcRelation;
	private Map<String,String> dcRights;
	private Map<String,String> dcSource;
	private Map<String,String> dcSubject;
	private Map<String,String> dcTitle;
	private Map<String,String> dcType;
	private Map<String,String> dctermsAlternative;
	private Map<String,String> dctermsConformsTo;
	private Map<String,String> dctermsCreated;
	private Map<String,String> dctermsExtent;
	private Map<String,String> dctermsHasFormat;
	private Map<String,String> dctermsHasPart;
	private Map<String,String> dctermsHasVersion;
	private Map<String,String> dctermsIsFormatOf;
	private Map<String,String> dctermsIsPartOf;
	private Map<String,String> dctermsIsReferencedBy;
	private Map<String,String> dctermsIsReplacedBy;
	private Map<String,String> dctermsIsRequiredBy;
	private Map<String,String> dctermsIssued;
	private Map<String,String> dctermsIsVersionOf;
	private Map<String,String> dctermsMedium;
	private Map<String,String> dctermsProvenance;
	private Map<String,String> dctermsReferences;
	private Map<String,String> dctermsReplaces;
	private Map<String,String> dctermsRequires;
	private Map<String,String> dctermsSpatial;
	private Map<String,String> dctermsTOC;
	private Map<String,String> dctermsTemporal;
	private String[] rdfType;
	private Map<String,String> edmHasMet;
	private Map<String,String> edmHasType;
	private String[] edmIncorporates;
	private String[] edmIsDerivativeOf;
	private Map<String,String> edmIsRelatedTo;
	private String edmIsRepresentationOf;
	private String[] edmIsSimilarTo;
	private String[] edmIsSuccessorOf;
	private String[] edmRealizes;
	private String[] edmWasPresentAt;
	private String edmCurrentLocation;
	private String edmIsNextInSequence;
	private Map<String,String> edmRights;

	@Override
	public Map<String,String> getEdmRights() {
		return edmRights;
	}

	@Override
	public void setEdmRights(Map<String,String> edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public Map<String,String> getDcContributor() {
		return this.dcContributor;
	}

	@Override
	public Map<String,String> getDcCoverage() {
		return this.dcCoverage;
	}

	@Override
	public Map<String,String> getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public Map<String,String> getDcDate() {
		return this.dcDate;
	}

	@Override
	public Map<String,String> getDcDescription() {
		return this.dcDescription;
	}

	@Override
	public Map<String,String> getDcFormat() {
		return this.dcFormat;
	}

	@Override
	public Map<String,String> getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public Map<String,String> getDcLanguage() {
		return this.dcLanguage;
	}

	@Override
	public Map<String,String> getDcPublisher() {
		return this.dcPublisher;
	}

	@Override
	public Map<String,String> getDcRelation() {
		return this.dcRelation;
	}

	@Override
	public Map<String,String> getDcRights() {
		return this.dcRights;
	}

	@Override
	public Map<String,String> getDcSource() {
		return this.dcSource;
	}

	@Override
	public Map<String,String> getDcSubject() {
		return this.dcSubject;
	}

	@Override
	public Map<String,String> getDcTitle() {
		return this.dcTitle;
	}

	@Override
	public Map<String,String> getDcType() {
		return this.dcType;
	}

	@Override
	public Map<String,String> getDctermsAlternative() {
		return this.dctermsAlternative;
	}

	@Override
	public Map<String,String> getDctermsConformsTo() {
		return this.dctermsConformsTo;
	}

	@Override
	public Map<String,String> getDctermsCreated() {
		return this.dctermsCreated;
	}

	@Override
	public Map<String,String> getDctermsExtent() {
		return this.dctermsExtent;
	}

	@Override
	public Map<String,String> getDctermsHasFormat() {
		return this.dctermsHasFormat;
	}

	@Override
	public Map<String,String> getDctermsHasPart() {
		return this.dctermsHasPart;
	}

	@Override
	public Map<String,String> getDctermsHasVersion() {
		return  this.dctermsHasVersion;
	}

	@Override
	public Map<String,String> getDctermsIsFormatOf() {
		return this.dctermsIsFormatOf;
	}

	@Override
	public Map<String,String> getDctermsIsPartOf() {
		return this.dctermsIsPartOf;
	}

	@Override
	public Map<String,String> getDctermsIsReferencedBy() {
		return this.dctermsIsReferencedBy;
	}

	@Override
	public Map<String,String> getDctermsIsReplacedBy() {
		return this.dctermsIsReplacedBy;
	}

	@Override
	public Map<String,String> getDctermsIsRequiredBy() {
		return this.dctermsIsRequiredBy;
	}

	@Override
	public Map<String,String> getDctermsIssued() {
		return this.dctermsIssued;
	}

	@Override
	public Map<String,String> getDctermsIsVersionOf() {
		return this.dctermsIsVersionOf;
	}

	@Override
	public Map<String,String> getDctermsMedium() {
		return this.dctermsMedium;
	}

	@Override
	public Map<String,String> getDctermsProvenance() {
		return this.dctermsProvenance;
	}

	@Override
	public Map<String,String> getDctermsReferences() {
		return this.dctermsReferences;
	}

	@Override
	public Map<String,String> getDctermsReplaces() {
		return this.dctermsReplaces;
	}

	@Override
	public Map<String,String>getDctermsRequires() {
		return this.dctermsRequires;
	}

	@Override
	public Map<String,String> getDctermsSpatial() {
		return this.dctermsSpatial;
	}

	@Override
	public Map<String,String> getDctermsTOC() {
		return this.dctermsTOC;
	}

	@Override
	public Map<String,String> getDctermsTemporal() {
		return this.dctermsTemporal;
	}

	@Override
	public String getEdmCurrentLocation() {
		return this.edmCurrentLocation;
	}

	@Override
	public void setDcContributor(Map<String,String> dcContributor) {
		this.dcContributor = dcContributor;
	}

	@Override
	public void setDcCoverage(Map<String,String> dcCoverage) {
		this.dcCoverage = dcCoverage;
	}

	@Override
	public void setDcCreator(Map<String,String> dcCreator) {
		this.dcCreator = dcCreator;
	}

	@Override
	public void setDcDate(Map<String,String> dcDate) {
		this.dcDate = dcDate;
	}

	@Override
	public void setDcDescription(Map<String,String> dcDescription) {
		this.dcDescription = dcDescription;
	}

	@Override
	public void setDcFormat(Map<String,String> dcFormat) {
		this.dcFormat = dcFormat;
	}

	@Override
	public void setDcIdentifier(Map<String,String> dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}

	@Override
	public void setDcLanguage(Map<String,String> dcLanguage) {
		this.dcLanguage = dcLanguage;
	}

	@Override
	public void setDcPublisher(Map<String,String> dcPublisher) {
		this.dcPublisher = dcPublisher;
	}

	@Override
	public void setDcRelation(Map<String,String> dcRelation) {
		this.dcRelation = dcRelation;
	}

	@Override
	public void setDcRights(Map<String,String> dcRights) {
		this.dcRights = dcRights;
	}

	@Override
	public void setDcSource(Map<String,String> dcSource) {
		this.dcSource = dcSource;
	}

	@Override
	public void setDcSubject(Map<String,String> dcSubject) {
		this.dcSubject = dcSubject;
	}

	@Override
	public void setDcTitle(Map<String,String> dcTitle) {
		this.dcTitle = dcTitle;
	}

	@Override
	public void setDcType(Map<String,String> dcType) {
		this.dcType = dcType;
	}

	@Override
	public void setDctermsAlternative(Map<String,String> dctermsAlternative) {
		this.dctermsAlternative = dctermsAlternative;
	}

	@Override
	public void setDctermsConformsTo(Map<String,String> dctermsConformsTo) {
		this.dctermsConformsTo = dctermsConformsTo;
	}

	@Override
	public void setDctermsCreated(Map<String,String> strings) {
		this.dctermsCreated = strings;
	}

	@Override
	public void setDctermsExtent(Map<String,String> dctermsExtent) {
		this.dctermsExtent = dctermsExtent;
	}

	@Override
	public void setDctermsHasFormat(Map<String,String> dctermsHasFormat) {
		this.dctermsHasFormat = dctermsHasFormat;
	}

	@Override
	public void setDctermsHasPart(Map<String,String> dctermsHasPart) {
		this.dctermsHasPart = dctermsHasPart;
	}

	@Override
	public void setDctermsHasVersion(Map<String,String> dctermsHasVersion) {
		this.dctermsHasVersion = dctermsHasVersion;
	}

	@Override
	public void setDctermsIsFormatOf(Map<String,String> dctermsIsFormatOf) {
		this.dctermsIsFormatOf = dctermsIsFormatOf;
	}

	@Override
	public void setDctermsIsPartOf(Map<String,String> dctermsIsPartOf) {
		this.dctermsIsPartOf = dctermsIsPartOf;
	}

	@Override
	public void setDctermsIsReferencedBy(Map<String,String> dctermsIsReferencedBy) {
		this.dctermsIsReferencedBy = dctermsIsReferencedBy;
	}

	@Override
	public void setDctermsIsReplacedBy(Map<String,String> dctermsIsReplacedBy) {
		this.dctermsIsReplacedBy = dctermsIsReplacedBy;
	}

	@Override
	public void setDctermsIsRequiredBy(Map<String,String> dctermsIsRequiredBy) {
		this.dctermsIsRequiredBy = dctermsIsRequiredBy;
	}

	@Override
	public void setDctermsIssued(Map<String,String> dctermsIssued) {
		this.dctermsIssued = dctermsIssued;
	}

	@Override
	public void setDctermsIsVersionOf(Map<String,String> dctermsIsVersionOf) {
		this.dctermsIsVersionOf = dctermsIsVersionOf;
	}

	@Override
	public void setDctermsMedium(Map<String,String> dctermsMedium) {
		this.dctermsMedium = dctermsMedium;
	}

	@Override
	public void setDctermsProvenance(Map<String,String> dctermsProvenance) {
		this.dctermsProvenance = dctermsProvenance;
	}

	@Override
	public void setDctermsReferences(Map<String,String> dctermsReferences) {
		this.dctermsReferences = dctermsReferences;
	}

	@Override
	public void setDctermsReplaces(Map<String,String> dctermsReplaces) {
		this.dctermsReplaces = dctermsReplaces;
	}

	@Override
	public void setDctermsRequires(Map<String,String> dctermsRequires) {
		this.dctermsRequires = dctermsRequires;
	}

	@Override
	public void setDctermsSpatial(Map<String,String> dctermsSpatial) {
		this.dctermsSpatial = dctermsSpatial;
	}

	@Override
	public void setDctermsTOC(Map<String,String> dctermsTOC) {
		this.dctermsTOC = dctermsTOC;
	}

	@Override
	public void setDctermsTemporal(Map<String,String> dctermsTemporal) {
		this.dctermsTemporal = dctermsTemporal;
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
		return (StringArrayUtils.isNotBlank(rdfType) ? this.rdfType.clone() : null);
	}

	@Override
	public Map<String,String> getEdmHasMet() {
		return this.edmHasMet;
	}

	@Override
	public void setEdmHasMet(Map<String,String> edmHasMet) {
		this.edmHasMet = edmHasMet;
	}

	@Override
	public Map<String,String> getEdmHasType() {
		return this.edmHasType;
	}

	@Override
	public void setEdmHasType(Map<String,String> edmHasType) {
		this.edmHasType = edmHasType;
	}

	@Override
	public void setEdmIncorporates(String[] edmIncorporates) {
		this.edmIncorporates = edmIncorporates;
	}

	@Override
	public String[] getEdmIncorporates() {
		return (StringArrayUtils.isNotBlank(edmIncorporates) ? this.edmIncorporates.clone() : null);
	}

	@Override
	public void setEdmIsDerivativeOf(String[] edmIsDerivativeOf) {
		this.edmIsDerivativeOf= edmIsDerivativeOf;
	}

	@Override
	public String[] getEdmIsDerivativeOf() {
		return (StringArrayUtils.isNotBlank(edmIsDerivativeOf) ? this.edmIsDerivativeOf.clone() : null);
	}

	@Override
	public void setEdmIsRelatedTo(Map<String,String> edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
	}

	@Override
	public Map<String,String> getEdmIsRelatedTo() {
		return this.edmIsRelatedTo;
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
		return (StringArrayUtils.isNotBlank(edmIsSimilarTo) ? this.edmIsSimilarTo.clone() : null);
	}

	@Override
	public void setEdmIsSuccessorOf(String[] edmIsSuccessorOf) {
		this.edmIsSuccessorOf = edmIsSuccessorOf;
	}

	@Override
	public String[] getEdmIsSuccessorOf() {
		return (StringArrayUtils.isNotBlank(edmIsSuccessorOf) ? this.edmIsSuccessorOf.clone() : null);
	}

	@Override
	public void setEdmRealizes(String[] edmRealizes) {
		this.edmRealizes = edmRealizes;
	}

	@Override
	public String[] getEdmRealizes() {
		return (StringArrayUtils.isNotBlank(edmRealizes) ? this.edmRealizes.clone() : null);
	}

	@Override
	public void setEdmWasPresentAt(String[] edmWasPresentAt) {
		this.edmWasPresentAt = edmWasPresentAt;
	}

	@Override
	public String[] getEdmWasPresentAt() {
		return (StringArrayUtils.isNotBlank(edmWasPresentAt) ? this.edmWasPresentAt.clone() : null);
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
