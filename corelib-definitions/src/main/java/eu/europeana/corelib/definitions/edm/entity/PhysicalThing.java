package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * Interface for the PhysicalThing. While it is not supported yet it is used as
 * the basis for the Proxy and EuropeanaProxy classes
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface PhysicalThing extends AbstractEdmEntity {
	/**
	 * Retrieve the dc:contributor fields for a Proxy
	 * 
	 * @return String array representing the dc:contributor fields of a Proxy
	 */
	Map<String, List<String>> getDcContributor();

	/**
	 * Retrieve the dc:coverage fields for a Proxy
	 * 
	 * @return String array representing the dc:coverage fields of a Proxy
	 */
	Map<String, List<String>> getDcCoverage();

	/**
	 * Retrieve the dc:creator fields for a Proxy
	 * 
	 * @return String array representing the dc:creator fields for a Proxy
	 */
	Map<String, List<String>> getDcCreator();

	/**
	 * Retrieve the dc:date fields for a Proxy
	 * 
	 * @return Date array representing the dc:date fields for a Proxy
	 */
	Map<String, List<String>> getDcDate();

	/**
	 * Retrieve the dc:description fields for a Proxy
	 * 
	 * @return String array representing the dc:description fields for a Proxy
	 */
	Map<String, List<String>> getDcDescription();

	/**
	 * Retrieve the dc:format fields for a Proxy
	 * 
	 * @return String array representing the dc:format fields for a Proxy
	 */
	Map<String, List<String>> getDcFormat();

	/**
	 * Retrieve the dc:identifier fields for a Proxy
	 * 
	 * @return String array representing the dc:identifier fields for a Proxy
	 */
	Map<String, List<String>> getDcIdentifier();

	/**
	 * Retrieve the dc:language fields for a Proxy
	 * 
	 * @return String array representing the dc:language fields for a Proxy
	 */
	Map<String, List<String>> getDcLanguage();

	/**
	 * Retrieve the dc:publisher fields for a Proxy
	 * 
	 * @return String array representing the dc:publisher fields for a Proxy
	 */
	Map<String, List<String>> getDcPublisher();

	/**
	 * Retrieve the dc:relation fields for a Proxy
	 * 
	 * @return String array representing the dc:relation fields for a Proxy
	 */
	Map<String, List<String>> getDcRelation();

	/**
	 * Retrieve the dc:rights fields for a Proxy
	 * 
	 * @return String array representing the dc:rights fields for a Proxy
	 */
	Map<String, List<String>> getDcRights();

	/**
	 * Retrieve the dc:source fields for a Proxy
	 * 
	 * @return String array representing the dc:soure fields for a Proxy
	 */
	Map<String, List<String>> getDcSource();

	/**
	 * Retrieve the dc:subject fields for a Proxy
	 * 
	 * @return String array representing the dc:subject fields for a Proxy
	 */
	Map<String, List<String>> getDcSubject();

	/**
	 * Retrieve the dc:title fields for a Proxy
	 * 
	 * @return String array representing the dc:title fields for a Proxy
	 */
	Map<String, List<String>> getDcTitle();

	/**
	 * Retrieve the dc:type fields for a Proxy
	 * 
	 * @return String array representing the dc:type fields for a Proxy
	 */
	Map<String, List<String>> getDcType();

	/**
	 * Retrieve the dcterms:alternative fields for a Proxy
	 * 
	 * @return String array representing the dcterms:alternative fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsAlternative();

	/**
	 * Retrieve the dc:conformsTo fields for a Proxy
	 * 
	 * @return String array representing the dc:conformsTo fields for a Proxy
	 */
	Map<String, List<String>> getDctermsConformsTo();

	/**
	 * Retrieve the dcterms:created fields for a Proxy
	 * 
	 * @return Date array representing the dcterms:created fields for a Proxy
	 */
	Map<String, List<String>> getDctermsCreated();

	/**
	 * Retrieve the dcterms:extent fields for a Proxy
	 * 
	 * @return String array representing the dc:extent fields for a Proxy
	 */
	Map<String, List<String>> getDctermsExtent();

	/**
	 * Retrieve the dcterms:hasFormat fields for a Proxy
	 * 
	 * @return String array representing the dcterms:hasFormat fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsHasFormat();

	/**
	 * Retrieve the dcterms:hasPart fields for a Proxy
	 * 
	 * @return String array representing the dcterms:hasPart fields for a Proxy
	 */
	Map<String, List<String>> getDctermsHasPart();

	/**
	 * Retrieve the dcterms:hasVersion fields for a Proxy
	 * 
	 * @return String array representing the dcterms:hasVersion fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsHasVersion();

	/**
	 * Retrieve the dcterms:isVersionOf fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isVersionOf fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsIsFormatOf();

	/**
	 * Retrieve the dcterms:isFormatOf fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isFormatOf fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsIsPartOf();

	/**
	 * Retrieve the dcterms:isReferencedBy fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isReferencedBy fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsIsReferencedBy();

	/**
	 * Retrieve the dcterms:isReplacedBy fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isReplacedBy fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsIsReplacedBy();

	/**
	 * Retrieve the dcterms:isRequiredBy fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isRequiredBy fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsIsRequiredBy();

	/**
	 * Retrieve the dcterms:issued fields for a Proxy
	 * 
	 * @return String array representing the dcterms:issued fields for a Proxy
	 */
	Map<String, List<String>> getDctermsIssued();

	/**
	 * Retrieve the dcterms:isVersionOf fields for a Proxy
	 * 
	 * @return String array representing the dcterms:isVersionOf fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsIsVersionOf();

	/**
	 * Retrieve the dcterms:medium fields for a Proxy
	 * 
	 * @return String array representing the dcterms:medium fields for a Proxy
	 */
	Map<String, List<String>> getDctermsMedium();

	/**
	 * Retrieve the dcterms:provenance fields for a Proxy
	 * 
	 * @return String array representing the dcterms:provenance fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsProvenance();

	/**
	 * Retrieve the dcterms:references fields for a Proxy
	 * 
	 * @return String array representing the dcterms:references fields for a
	 *         Proxy
	 */
	Map<String, List<String>> getDctermsReferences();

	/**
	 * Retrieve the dcterms:replaces fields for a Proxy
	 * 
	 * @return String array representing the dcterms:replaces fields for a Proxy
	 */
	Map<String, List<String>> getDctermsReplaces();

	/**
	 * Retrieve the dcterms:requires fields for a Proxy
	 * 
	 * @return String array representing the dcterms:requires fields for a Proxy
	 */
	Map<String, List<String>> getDctermsRequires();

	/**
	 * Retrieve the dcterms:spatial fields for a Proxy
	 * 
	 * @return String array representing the dcterms:spatial fields for a Proxy
	 */
	Map<String, List<String>> getDctermsSpatial();

	/**
	 * Retrieve the dcterms:tableOfContents fields for a Proxy
	 * 
	 * @return String array representing the dcterms:tableOfContents fields for
	 *         a Proxy
	 */
	Map<String, List<String>> getDctermsTOC();

	/**
	 * Retrieve the dcterms:temporal fields for a Proxy
	 * 
	 * @return String array representing the dcterms:temporal fields for a Proxy
	 */
	Map<String, List<String>> getDctermsTemporal();

	/**
	 * Retrieve the edm:currentLocation fields for a Proxy
	 * 
	 * @return String representing the edm:currentLocation fields for a Proxy
	 */
	Map<String, List<String>> getEdmCurrentLocation();

	/**
	 * Set the dcContributor field for a Proxy
	 * 
	 * @param dcContributor
	 *            String array containing the dcContributor of a Proxy
	 */
	void setDcContributor(Map<String, List<String>> dcContributor);

	/**
	 * Set the dcCoverage field for a Proxy
	 * 
	 * @param dcCoverage
	 *            String array containing the dcCoverage of a Proxy
	 */
	void setDcCoverage(Map<String, List<String>> dcCoverage);

	/**
	 * Set the dcCreator field for a Proxy
	 * 
	 * @param dcCreator
	 *            String array containing the dcCreator of a Proxy
	 */
	void setDcCreator(Map<String, List<String>> dcCreator);

	/**
	 * Set the dcDate field for a Proxy
	 * 
	 * @param dcDate
	 *            String array containing the dcDate of a Proxy
	 */
	void setDcDate(Map<String, List<String>> dcDate);

	/**
	 * Set the dcDescription field for a Proxy
	 * 
	 * @param dcDescription
	 *            String array containing the dcDescription of a Proxy
	 */
	void setDcDescription(Map<String, List<String>> dcDescription);

	/**
	 * Set the dcFormat field for a Proxy
	 * 
	 * @param dcFormat
	 *            String array containing the dcFormat of a Proxy
	 */
	void setDcFormat(Map<String, List<String>> dcFormat);

	/**
	 * Set the dcIdentifier field for a Proxy
	 * 
	 * @param dcIdentifier
	 *            String array containing the dcIdentifier of a Proxy
	 */
	void setDcIdentifier(Map<String, List<String>> dcIdentifier);

	/**
	 * Set the dcLanguage field for a Proxy
	 * 
	 * @param dcLanguage
	 *            String array containing the dcLanguage of a Proxy
	 */
	void setDcLanguage(Map<String, List<String>> dcLanguage);

	/**
	 * Set the dcPublisher field for a Proxy
	 * 
	 * @param dcPublisher
	 *            String array containing the dcPublisher of a Proxy
	 */
	void setDcPublisher(Map<String, List<String>> dcPublisher);

	/**
	 * Set the dcRelation field for a Proxy
	 * 
	 * @param dcRelation
	 *            String array containing the dcRelation of a Proxy
	 */
	void setDcRelation(Map<String, List<String>> dcRelation);

	/**
	 * Set the dcRights field for a Proxy
	 * 
	 * @param dcRights
	 *            String array containing the dcRights of a Proxy
	 */
	void setDcRights(Map<String, List<String>> dcRights);

	/**
	 * Set the dcSource field for a Proxy
	 * 
	 * @param dcSource
	 *            String array containing the dcSource of a Proxy
	 */
	void setDcSource(Map<String, List<String>> dcSource);

	/**
	 * Set the dcSubject field for a Proxy
	 * 
	 * @param dcSubject
	 *            String array containing the dcSubject of a Proxy
	 */
	void setDcSubject(Map<String, List<String>> dcSubject);

	/**
	 * Set the dcTitle field for a Proxy
	 * 
	 * @param dcTitle
	 *            String array containing the dcTitle of a Proxy
	 */
	void setDcTitle(Map<String, List<String>> dcTitle);

	/**
	 * Set the dcType field for a Proxy
	 * 
	 * @param dcType
	 *            String array containing the dcType of a Proxy
	 */
	void setDcType(Map<String, List<String>> dcType);

	/**
	 * Set the dctermsAlternative field for a Proxy
	 * 
	 * @param dctermsAlternative
	 *            String array containing the dctermsAlternative of a Proxy
	 */
	void setDctermsAlternative(Map<String, List<String>> dctermsAlternative);

	/**
	 * Set the dctermsConformsTo field for a Proxy
	 * 
	 * @param dctermsConformsTo
	 *            String array containing the dctermsConformsTo of a Proxy
	 */
	void setDctermsConformsTo(Map<String, List<String>> dctermsConformsTo);

	/**
	 * Set the dctermsCreated field for a Proxy
	 * 
	 * @param dctermsCreated
	 *            String array containing the dctermsCreated of a Proxy
	 */
	void setDctermsCreated(Map<String, List<String>> dctermsCreated);

	/**
	 * Set the dctermsExtent field for a Proxy
	 * 
	 * @param dctermsExtent
	 *            String array containing the dctermsExtent of a Proxy
	 */
	void setDctermsExtent(Map<String, List<String>> dctermsExtent);

	/**
	 * Set the dctermsHasFormat field for a Proxy
	 * 
	 * @param dctermsHasFormat
	 *            String array containing the dctermsHasFormat of a Proxy
	 */
	void setDctermsHasFormat(Map<String, List<String>> dctermsHasFormat);

	/**
	 * Set the dctermsHasPart field for a Proxy
	 * 
	 * @param dctermsHasPart
	 *            String array containing the dctermsHasPart of a Proxy
	 */
	void setDctermsHasPart(Map<String, List<String>> dctermsHasPart);

	/**
	 * Set the dctermsHasVersion field for a Proxy
	 * 
	 * @param dctermsHasVersion
	 *            String array containing the dctermsHasVersion of a Proxy
	 */
	void setDctermsHasVersion(Map<String, List<String>> dctermsHasVersion);

	/**
	 * Set the dctermsIsFormatOf field for a Proxy
	 * 
	 * @param dctermsIsFormatOf
	 *            String array containing the dctermsIsFormatOf of a Proxy
	 */
	void setDctermsIsFormatOf(Map<String, List<String>> dctermsIsFormatOf);

	/**
	 * Set the dctermsIsPartOf field for a Proxy
	 * 
	 * @param dctermsIsPartOf
	 *            String array containing the dctermsIsPartOf of a Proxy
	 */
	void setDctermsIsPartOf(Map<String, List<String>> dctermsIsPartOf);

	/**
	 * Set the dctermsIsReferencedBy field for a Proxy
	 * 
	 * @param dctermsIsReferencedBy
	 *            String array containing the dctermsIsReferencedBy of a Proxy
	 */
	void setDctermsIsReferencedBy(
			Map<String, List<String>> dctermsIsReferencedBy);

	/**
	 * Set the dctermsIsReplacedBy field for a Proxy
	 * 
	 * @param dctermsIsReplacedBy
	 *            String array containing the dctermsIsReplacedBy of a Proxy
	 */
	void setDctermsIsReplacedBy(Map<String, List<String>> dctermsIsReplacedBy);

	/**
	 * Set the dctermsIsRequiredBy field for a Proxy
	 * 
	 * @param dctermsIsRequiredBy
	 *            String array containing the dctermsIsRequiredBy of a Proxy
	 */
	void setDctermsIsRequiredBy(Map<String, List<String>> dctermsIsRequiredBy);

	/**
	 * Set the dctermsIssued field for a Proxy
	 * 
	 * @param dctermsIssued
	 *            String array containing the dctermsIssued of a Proxy
	 */
	void setDctermsIssued(Map<String, List<String>> dctermsIssued);

	/**
	 * Set the dctermsIsVersionOf field for a Proxy
	 * 
	 * @param dctermsIsVersionOf
	 *            String array containing the dctermsIsVersionOf of a Proxy
	 */
	void setDctermsIsVersionOf(Map<String, List<String>> dctermsIsVersionOf);

	/**
	 * Set the dctermsMedium field for a Proxy
	 * 
	 * @param dctermsMedium
	 *            String array containing the dctermsMedium of a Proxy
	 */
	void setDctermsMedium(Map<String, List<String>> dctermsMedium);

	/**
	 * Set the dctermsProvenance field for a Proxy
	 * 
	 * @param dctermsProvenance
	 *            String array containing the dctermsProvenance of a Proxy
	 */
	void setDctermsProvenance(Map<String, List<String>> dctermsProvenance);

	/**
	 * Set the dctermsReferences field for a Proxy
	 * 
	 * @param dctermsReferences
	 *            String array containing the dctermsReference of a Proxy
	 */
	void setDctermsReferences(Map<String, List<String>> dctermsReferences);

	/**
	 * Set the dctermsReplaces field for a Proxy
	 * 
	 * @param dctermsReplaces
	 *            String array containing the dctermsReplaces of a Proxy
	 */
	void setDctermsReplaces(Map<String, List<String>> dctermsReplaces);

	/**
	 * Set the dctermsRequires field for a Proxy
	 * 
	 * @param dctermsRequires
	 *            String array containing the dctermsRequires of a Proxy
	 */
	void setDctermsRequires(Map<String, List<String>> dctermsRequires);

	/**
	 * Set the dctermsSpatial field for a Proxy
	 * 
	 * @param dctermsSpatial
	 *            String array containing the dctermsSpatial of a Proxy
	 */
	void setDctermsSpatial(Map<String, List<String>> dctermsSpatial);

	/**
	 * Set the dctermsTableOfContents field for a Proxy
	 * 
	 * @param dctermsTOC
	 *            String array containing the dctermsTableOfContents of a Proxy
	 */
	void setDctermsTOC(Map<String, List<String>> dctermsTOC);

	/**
	 * Set the dctermsTemporal field for a Proxy
	 * 
	 * @param dctermsTemporal
	 *            String array containing the dctermsTemporal of a Proxy
	 */
	void setDctermsTemporal(Map<String, List<String>> dctermsTemporal);

	/**
	 * Set the edmCurrentLocation field for a Proxy
	 * 
	 * @param edmCurrentLocation
	 *            String array containing the edmCurrentLocation of a Proxy
	 */
	void setEdmCurrentLocation(Map<String, List<String>> edmCurrentLocation);

	/**
	 * Retrieves the edmRights field for a Proxy
	 * 
	 * @return String array containing the edmRights of a Proxy
	 */
	Map<String, List<String>> getEdmRights();

	/**
	 * Set the edmRights field for a Proxy
	 * 
	 * @param edmRights
	 *            String array containing the edmRights of a Proxy
	 */
	void setEdmRights(Map<String, List<String>> edmRights);

	/**
	 * 
	 * @return the edm:isNextInSequence for the edm:Proxy
	 */
	String[] getEdmIsNextInSequence();

	/**
	 * Set the edm:isNextInSequence fields for a ProvidedCHO
	 * 
	 * @param edmIsNextInSequence
	 *            String representing the edm:isNextInSequence fields for a
	 *            ProvidedCHO
	 */
	void setEdmIsNextInSequence(String[] edmIsNextInSequence);

	/**
	 * TODO: Yorgos Mamakis: is it used?
	 * ???????????
	 * @param rdfType
	 */
	void setRdfType(String[] rdfType);

	/**
	 * TODO: Yorgos Mamakis: is it used?
	 * @return ?????
	 */
	String[] getRdfType();

	/**
	 * 
	 * @return the edm:hasMet for the edm:Proxy
	 */
	Map<String, List<String>> getEdmHasMet();

	/**
	 * sets the edm:hasMet for the edm:Proxy
	 * @param edmHasMet
	 */
	void setEdmHasMet(Map<String, List<String>> edmHasMet);

	/**
	 * 
	 * @return the edm:hasType for the edm:Proxy
	 */
	Map<String, List<String>> getEdmHasType();

	/**
	 * sets the edm:hasType for the edm:Proxy
	 * @param edmhasType
	 */
	void setEdmHasType(Map<String, List<String>> edmhasType);

	/**
	 * sets the edm:incorporates for the edm:Proxy
	 * @param edmIncorporates
	 */
	void setEdmIncorporates(String[] edmIncorporates);

	/**
	 * 
	 * @return the edm:incorporates for the edm:Proxy
	 */
	String[] getEdmIncorporates();

	/**
	 * sets the edm:isDerivativeOf for the edm:Proxy
	 * @param edmIsDerivativeOf
	 */
	void setEdmIsDerivativeOf(String[] edmIsDerivativeOf);

	/**
	 * 
	 * @return the edm:isDerivativeOf for the edm:Proxy
	 */
	String[] getEdmIsDerivativeOf();

	/**
	 * sets the edm:isRelatedTo for the edm:Proxy
	 * @param edmIsRelatedTo
	 */
	void setEdmIsRelatedTo(Map<String, List<String>> edmIsRelatedTo);

	/**
	 * 
	 * @return the edm:isRelatedTo for the edm:Proxy
	 */
	Map<String, List<String>> getEdmIsRelatedTo();

	/**
	 * Sets the edm:isRepresentationOf for the edm:Proxy
	 * @param edmIsRepresentationOf
	 */
	void setEdmIsRepresentationOf(String edmIsRepresentationOf);

	/**
	 * 
	 * @return the edm:isRepresentationOf for the edm:Proxy
	 */
	String getEdmIsRepresentationOf();

	/**
	 * sets the edm:isSimilarTo for the edm:Proxy
	 * @param edmIsSimilarTo
	 */
	void setEdmIsSimilarTo(String[] edmIsSimilarTo);

	/**
	 * 
	 * @return the edm:isSimilarTo for the edm:Proxy
	 */
	String[] getEdmIsSimilarTo();

	/**
	 * sets the edm:isSuccessorOf for the edm:Proxy
	 * @param edmIsSuccessorOf
	 */
	void setEdmIsSuccessorOf(String[] edmIsSuccessorOf);

	/**
	 * 
	 * @return the edm:isSuccessorOf for the edm:Proxy
	 */
	String[] getEdmIsSuccessorOf();

	/**
	 * sets the edm:realizes for the edm:Proxy
	 * @param edmRealizes
	 */
	void setEdmRealizes(String[] edmRealizes);

	/**
	 * 
	 * @return the edm:realizes for the edm:Proxy
	 */
	String[] getEdmRealizes();

	/**
	 * sets the edm:wasPresentAt for the edm:Proxy
	 * @param edmWasPresentAt
	 */
	void setEdmWasPresentAt(String[] edmWasPresentAt);

	/**
	 * 
	 * @return the edm:wasPresentAt for the edm:Proxy
	 */
	String[] getEdmWasPresentAt();
}
