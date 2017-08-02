package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.PhysicalThing;
import eu.europeana.corelib.utils.StringArrayUtils;

@Entity("PhysicalThing")
public class PhysicalThingImpl extends AbstractEdmEntityImpl implements PhysicalThing {

	private Map<String,List<String>> dcContributor;
	private Map<String,List<String>> dcCoverage;
	private Map<String,List<String>> dcCreator;
	private Map<String,List<String>> dcDate;
	private Map<String,List<String>> dcDescription;
	private Map<String,List<String>> dcFormat;
	private Map<String,List<String>> dcIdentifier;
	private Map<String,List<String>> dcLanguage;
	private Map<String,List<String>> dcPublisher;
	private Map<String,List<String>> dcRelation;
	private Map<String,List<String>> dcRights;
	private Map<String,List<String>> dcSource;
	private Map<String,List<String>> dcSubject;
	private Map<String,List<String>> dcTitle;
	private Map<String,List<String>> dcType;
	private Map<String,List<String>> dctermsAlternative;
	private Map<String,List<String>> dctermsConformsTo;
	private Map<String,List<String>> dctermsCreated;
	private Map<String,List<String>> dctermsExtent;
	private Map<String,List<String>> dctermsHasFormat;
	private Map<String,List<String>> dctermsHasPart;
	private Map<String,List<String>> dctermsHasVersion;
	private Map<String,List<String>> dctermsIsFormatOf;
	private Map<String,List<String>> dctermsIsPartOf;
	private Map<String,List<String>> dctermsIsReferencedBy;
	private Map<String,List<String>> dctermsIsReplacedBy;
	private Map<String,List<String>> dctermsIsRequiredBy;
	private Map<String,List<String>> dctermsIssued;
	private Map<String,List<String>> dctermsIsVersionOf;
	private Map<String,List<String>> dctermsMedium;
	private Map<String,List<String>> dctermsProvenance;
	private Map<String,List<String>> dctermsReferences;
	private Map<String,List<String>> dctermsReplaces;
	private Map<String,List<String>> dctermsRequires;
	private Map<String,List<String>> dctermsSpatial;
	private Map<String,List<String>> dctermsTOC;
	private Map<String,List<String>> dctermsTemporal;
	private String[] rdfType;
	private Map<String,List<String>> edmHasMet;
	private Map<String,List<String>> edmHasType;
	private String[] edmIncorporates;
	private String[] edmIsDerivativeOf;
	private Map<String,List<String>> edmIsRelatedTo;
	private String edmIsRepresentationOf;
	private String[] edmIsSimilarTo;
	private String[] edmIsSuccessorOf;
	private String[] edmRealizes;
	private String[] edmWasPresentAt;
	private Map<String, List<String>> edmCurrentLocation;
	private String[] edmIsNextInSequence;
	private Map<String,List<String>> edmRights;

	@Override
	public Map<String,List<String>> getEdmRights() {
		return edmRights;
	}

	@Override
	public void setEdmRights(Map<String,List<String>> edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public Map<String,List<String>> getDcContributor() {
		return this.dcContributor;
	}

	@Override
	public Map<String,List<String>> getDcCoverage() {
		return this.dcCoverage;
	}

	@Override
	public Map<String,List<String>> getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public Map<String,List<String>> getDcDate() {
		return this.dcDate;
	}

	@Override
	public Map<String,List<String>> getDcDescription() {
		return this.dcDescription;
	}

	@Override
	public Map<String,List<String>> getDcFormat() {
		return this.dcFormat;
	}

	@Override
	public Map<String,List<String>> getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public Map<String,List<String>> getDcLanguage() {
		return this.dcLanguage;
	}

	@Override
	public Map<String,List<String>> getDcPublisher() {
		return this.dcPublisher;
	}

	@Override
	public Map<String,List<String>> getDcRelation() {
		return this.dcRelation;
	}

	@Override
	public Map<String,List<String>> getDcRights() {
		return this.dcRights;
	}

	@Override
	public Map<String,List<String>> getDcSource() {
		return this.dcSource;
	}

	@Override
	public Map<String,List<String>> getDcSubject() {
		return this.dcSubject;
	}

	@Override
	public Map<String,List<String>> getDcTitle() {
		return this.dcTitle;
	}

	@Override
	public Map<String,List<String>> getDcType() {
		return this.dcType;
	}

	@Override
	public Map<String,List<String>> getDctermsAlternative() {
		return this.dctermsAlternative;
	}

	@Override
	public Map<String,List<String>> getDctermsConformsTo() {
		return this.dctermsConformsTo;
	}

	@Override
	public Map<String,List<String>> getDctermsCreated() {
		return this.dctermsCreated;
	}

	@Override
	public Map<String,List<String>> getDctermsExtent() {
		return this.dctermsExtent;
	}

	@Override
	public Map<String,List<String>> getDctermsHasFormat() {
		return this.dctermsHasFormat;
	}

	@Override
	public Map<String,List<String>> getDctermsHasPart() {
		return this.dctermsHasPart;
	}

	@Override
	public Map<String,List<String>> getDctermsHasVersion() {
		return  this.dctermsHasVersion;
	}

	@Override
	public Map<String,List<String>> getDctermsIsFormatOf() {
		return this.dctermsIsFormatOf;
	}

	@Override
	public Map<String,List<String>> getDctermsIsPartOf() {
		return this.dctermsIsPartOf;
	}

	@Override
	public Map<String,List<String>> getDctermsIsReferencedBy() {
		return this.dctermsIsReferencedBy;
	}

	@Override
	public Map<String,List<String>> getDctermsIsReplacedBy() {
		return this.dctermsIsReplacedBy;
	}

	@Override
	public Map<String,List<String>> getDctermsIsRequiredBy() {
		return this.dctermsIsRequiredBy;
	}

	@Override
	public Map<String,List<String>> getDctermsIssued() {
		return this.dctermsIssued;
	}

	@Override
	public Map<String,List<String>> getDctermsIsVersionOf() {
		return this.dctermsIsVersionOf;
	}

	@Override
	public Map<String,List<String>> getDctermsMedium() {
		return this.dctermsMedium;
	}

	@Override
	public Map<String,List<String>> getDctermsProvenance() {
		return this.dctermsProvenance;
	}

	@Override
	public Map<String,List<String>> getDctermsReferences() {
		return this.dctermsReferences;
	}

	@Override
	public Map<String,List<String>> getDctermsReplaces() {
		return this.dctermsReplaces;
	}

	@Override
	public Map<String,List<String>>getDctermsRequires() {
		return this.dctermsRequires;
	}

	@Override
	public Map<String,List<String>> getDctermsSpatial() {
		return this.dctermsSpatial;
	}

	@Override
	public Map<String,List<String>> getDctermsTOC() {
		return this.dctermsTOC;
	}

	@Override
	public Map<String,List<String>> getDctermsTemporal() {
		return this.dctermsTemporal;
	}

	@Override
	public Map<String, List<String>> getEdmCurrentLocation() {
		return this.edmCurrentLocation;
	}

	@Override
	public void setDcContributor(Map<String,List<String>> dcContributor) {
		this.dcContributor = dcContributor;
	}

	@Override
	public void setDcCoverage(Map<String,List<String>> dcCoverage) {
		this.dcCoverage = dcCoverage;
	}

	@Override
	public void setDcCreator(Map<String,List<String>> dcCreator) {
		this.dcCreator = dcCreator;
	}

	@Override
	public void setDcDate(Map<String,List<String>> dcDate) {
		this.dcDate = dcDate;
	}

	@Override
	public void setDcDescription(Map<String,List<String>> dcDescription) {
		this.dcDescription = dcDescription;
	}

	@Override
	public void setDcFormat(Map<String,List<String>> dcFormat) {
		this.dcFormat = dcFormat;
	}

	@Override
	public void setDcIdentifier(Map<String,List<String>> dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}

	@Override
	public void setDcLanguage(Map<String,List<String>> dcLanguage) {
		this.dcLanguage = dcLanguage;
	}

	@Override
	public void setDcPublisher(Map<String,List<String>> dcPublisher) {
		this.dcPublisher = dcPublisher;
	}

	@Override
	public void setDcRelation(Map<String,List<String>> dcRelation) {
		this.dcRelation = dcRelation;
	}

	@Override
	public void setDcRights(Map<String,List<String>> dcRights) {
		this.dcRights = dcRights;
	}

	@Override
	public void setDcSource(Map<String,List<String>> dcSource) {
		this.dcSource = dcSource;
	}

	@Override
	public void setDcSubject(Map<String,List<String>> dcSubject) {
		this.dcSubject = dcSubject;
	}

	@Override
	public void setDcTitle(Map<String,List<String>> dcTitle) {
		this.dcTitle = dcTitle;
	}

	@Override
	public void setDcType(Map<String,List<String>> dcType) {
		this.dcType = dcType;
	}

	@Override
	public void setDctermsAlternative(Map<String,List<String>> dctermsAlternative) {
		this.dctermsAlternative = dctermsAlternative;
	}

	@Override
	public void setDctermsConformsTo(Map<String,List<String>> dctermsConformsTo) {
		this.dctermsConformsTo = dctermsConformsTo;
	}

	@Override
	public void setDctermsCreated(Map<String,List<String>> strings) {
		this.dctermsCreated = strings;
	}

	@Override
	public void setDctermsExtent(Map<String,List<String>> dctermsExtent) {
		this.dctermsExtent = dctermsExtent;
	}

	@Override
	public void setDctermsHasFormat(Map<String,List<String>> dctermsHasFormat) {
		this.dctermsHasFormat = dctermsHasFormat;
	}

	@Override
	public void setDctermsHasPart(Map<String,List<String>> dctermsHasPart) {
		this.dctermsHasPart = dctermsHasPart;
	}

	@Override
	public void setDctermsHasVersion(Map<String,List<String>> dctermsHasVersion) {
		this.dctermsHasVersion = dctermsHasVersion;
	}

	@Override
	public void setDctermsIsFormatOf(Map<String,List<String>> dctermsIsFormatOf) {
		this.dctermsIsFormatOf = dctermsIsFormatOf;
	}

	@Override
	public void setDctermsIsPartOf(Map<String,List<String>> dctermsIsPartOf) {
		this.dctermsIsPartOf = dctermsIsPartOf;
	}

	@Override
	public void setDctermsIsReferencedBy(Map<String,List<String>> dctermsIsReferencedBy) {
		this.dctermsIsReferencedBy = dctermsIsReferencedBy;
	}

	@Override
	public void setDctermsIsReplacedBy(Map<String,List<String>> dctermsIsReplacedBy) {
		this.dctermsIsReplacedBy = dctermsIsReplacedBy;
	}

	@Override
	public void setDctermsIsRequiredBy(Map<String,List<String>> dctermsIsRequiredBy) {
		this.dctermsIsRequiredBy = dctermsIsRequiredBy;
	}

	@Override
	public void setDctermsIssued(Map<String,List<String>> dctermsIssued) {
		this.dctermsIssued = dctermsIssued;
	}

	@Override
	public void setDctermsIsVersionOf(Map<String,List<String>> dctermsIsVersionOf) {
		this.dctermsIsVersionOf = dctermsIsVersionOf;
	}

	@Override
	public void setDctermsMedium(Map<String,List<String>> dctermsMedium) {
		this.dctermsMedium = dctermsMedium;
	}

	@Override
	public void setDctermsProvenance(Map<String,List<String>> dctermsProvenance) {
		this.dctermsProvenance = dctermsProvenance;
	}

	@Override
	public void setDctermsReferences(Map<String,List<String>> dctermsReferences) {
		this.dctermsReferences = dctermsReferences;
	}

	@Override
	public void setDctermsReplaces(Map<String,List<String>> dctermsReplaces) {
		this.dctermsReplaces = dctermsReplaces;
	}

	@Override
	public void setDctermsRequires(Map<String,List<String>> dctermsRequires) {
		this.dctermsRequires = dctermsRequires;
	}

	@Override
	public void setDctermsSpatial(Map<String,List<String>> dctermsSpatial) {
		this.dctermsSpatial = dctermsSpatial;
	}

	@Override
	public void setDctermsTOC(Map<String,List<String>> dctermsTOC) {
		this.dctermsTOC = dctermsTOC;
	}

	@Override
	public void setDctermsTemporal(Map<String,List<String>> dctermsTemporal) {
		this.dctermsTemporal = dctermsTemporal;
	}

	@Override
	public void setEdmCurrentLocation(Map<String, List<String>> edmCurrentLocation) {
		this.edmCurrentLocation = edmCurrentLocation;
	}

	@Override
	public void setRdfType(String[] rdfType) {
		this.rdfType = rdfType!=null?rdfType.clone():null;
	}

	@Override
	public String[] getRdfType() {
		return (StringArrayUtils.isNotBlank(rdfType) ? this.rdfType.clone() : null);
	}

	@Override
	public Map<String,List<String>> getEdmHasMet() {
		return this.edmHasMet;
	}

	@Override
	public void setEdmHasMet(Map<String,List<String>> edmHasMet) {
		this.edmHasMet = edmHasMet;
	}

	@Override
	public Map<String,List<String>> getEdmHasType() {
		return this.edmHasType;
	}

	@Override
	public void setEdmHasType(Map<String,List<String>> edmHasType) {
		this.edmHasType = edmHasType;
	}

	@Override
	public void setEdmIncorporates(String[] edmIncorporates) {
		this.edmIncorporates = edmIncorporates!=null?edmIncorporates.clone():null;
	}

	@Override
	public String[] getEdmIncorporates() {
		return (StringArrayUtils.isNotBlank(edmIncorporates) ? this.edmIncorporates.clone() : null);
	}

	@Override
	public void setEdmIsDerivativeOf(String[] edmIsDerivativeOf) {
		this.edmIsDerivativeOf= edmIsDerivativeOf!=null?edmIsDerivativeOf.clone():null;
	}

	@Override
	public String[] getEdmIsDerivativeOf() {
		return (StringArrayUtils.isNotBlank(edmIsDerivativeOf) ? this.edmIsDerivativeOf.clone() : null);
	}

	@Override
	public void setEdmIsRelatedTo(Map<String,List<String>> edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
	}

	@Override
	public Map<String,List<String>> getEdmIsRelatedTo() {
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
		this.edmIsSimilarTo = edmIsSimilarTo!=null?edmIsSimilarTo.clone():null;
	}

	@Override
	public String[] getEdmIsSimilarTo() {
		return (StringArrayUtils.isNotBlank(edmIsSimilarTo) ? this.edmIsSimilarTo.clone() : null);
	}

	@Override
	public void setEdmIsSuccessorOf(String[] edmIsSuccessorOf) {
		this.edmIsSuccessorOf = edmIsSuccessorOf!=null?edmIsSuccessorOf.clone():null;
	}

	@Override
	public String[] getEdmIsSuccessorOf() {
		return (StringArrayUtils.isNotBlank(edmIsSuccessorOf) ? this.edmIsSuccessorOf.clone() : null);
	}

	@Override
	public void setEdmRealizes(String[] edmRealizes) {
		this.edmRealizes = edmRealizes!=null?edmRealizes.clone():null;
	}

	@Override
	public String[] getEdmRealizes() {
		return (StringArrayUtils.isNotBlank(edmRealizes) ? this.edmRealizes.clone() : null);
	}

	@Override
	public void setEdmWasPresentAt(String[] edmWasPresentAt) {
		this.edmWasPresentAt = edmWasPresentAt!=null?edmWasPresentAt.clone():null;
	}

	@Override
	public String[] getEdmWasPresentAt() {
		return (StringArrayUtils.isNotBlank(edmWasPresentAt) ? this.edmWasPresentAt.clone() : null);
	}

	@Override
	public String[] getEdmIsNextInSequence() {
		return this.edmIsNextInSequence;
	}

	@Override
	public void setEdmIsNextInSequence(String[] edmIsNextInSequence) {
		this.edmIsNextInSequence = edmIsNextInSequence;
	}
}
