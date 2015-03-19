
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Base class for WebResource implementations
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://www.w3.org/2002/07/owl#" xmlns:ns3="http://purl.org/dc/terms/" xmlns:ns4="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="WebResourceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns4:creator" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:description" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:format" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:rights" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:source" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:conformsTo" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:created" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:extent" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:hasPart" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:isFormatOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:issued" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:isNextInSequence" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:rights" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class WebResourceType extends AboutType
{
    private List<Creator> creatorList = new ArrayList<Creator>();
    private List<Description> descriptionList = new ArrayList<Description>();
    private List<Format> formatList = new ArrayList<Format>();
    private List<Rights> rightList = new ArrayList<Rights>();
    private List<Source> sourceList = new ArrayList<Source>();
    private List<ConformsTo> conformsToList = new ArrayList<ConformsTo>();
    private List<Created> createdList = new ArrayList<Created>();
    private List<Extent> extentList = new ArrayList<Extent>();
    private List<HasPart> hasPartList = new ArrayList<HasPart>();
    private List<IsFormatOf> isFormatOfList = new ArrayList<IsFormatOf>();
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
    private List<Issued> issuedList = new ArrayList<Issued>();
    private IsNextInSequence isNextInSequence;
    private Rights1 rights;
    private List<SameAs> sameAList = new ArrayList<SameAs>();

    /** 
     * Get the list of 'creator' element items.
     * 
     * @return list
     */
    public List<Creator> getCreatorList() {
        return creatorList;
    }

    /** 
     * Set the list of 'creator' element items.
     * 
     * @param list
     */
    public void setCreatorList(List<Creator> list) {
        creatorList = list;
    }

    /** 
     * Get the list of 'description' element items.
     * 
     * @return list
     */
    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    /** 
     * Set the list of 'description' element items.
     * 
     * @param list
     */
    public void setDescriptionList(List<Description> list) {
        descriptionList = list;
    }

    /** 
     * Get the list of 'format' element items.
     * 
     * @return list
     */
    public List<Format> getFormatList() {
        return formatList;
    }

    /** 
     * Set the list of 'format' element items.
     * 
     * @param list
     */
    public void setFormatList(List<Format> list) {
        formatList = list;
    }

    /** 
     * Get the list of 'rights' element items.
     * 
     * @return list
     */
    public List<Rights> getRightList() {
        return rightList;
    }

    /** 
     * Set the list of 'rights' element items.
     * 
     * @param list
     */
    public void setRightList(List<Rights> list) {
        rightList = list;
    }

    /** 
     * Get the list of 'source' element items.
     * 
     * @return list
     */
    public List<Source> getSourceList() {
        return sourceList;
    }

    /** 
     * Set the list of 'source' element items.
     * 
     * @param list
     */
    public void setSourceList(List<Source> list) {
        sourceList = list;
    }

    /** 
     * Get the list of 'conformsTo' element items.
     * 
     * @return list
     */
    public List<ConformsTo> getConformsToList() {
        return conformsToList;
    }

    /** 
     * Set the list of 'conformsTo' element items.
     * 
     * @param list
     */
    public void setConformsToList(List<ConformsTo> list) {
        conformsToList = list;
    }

    /** 
     * Get the list of 'created' element items.
     * 
     * @return list
     */
    public List<Created> getCreatedList() {
        return createdList;
    }

    /** 
     * Set the list of 'created' element items.
     * 
     * @param list
     */
    public void setCreatedList(List<Created> list) {
        createdList = list;
    }

    /** 
     * Get the list of 'extent' element items.
     * 
     * @return list
     */
    public List<Extent> getExtentList() {
        return extentList;
    }

    /** 
     * Set the list of 'extent' element items.
     * 
     * @param list
     */
    public void setExtentList(List<Extent> list) {
        extentList = list;
    }

    /** 
     * Get the list of 'hasPart' element items.
     * 
     * @return list
     */
    public List<HasPart> getHasPartList() {
        return hasPartList;
    }

    /** 
     * Set the list of 'hasPart' element items.
     * 
     * @param list
     */
    public void setHasPartList(List<HasPart> list) {
        hasPartList = list;
    }

    /** 
     * Get the list of 'isFormatOf' element items.
     * 
     * @return list
     */
    public List<IsFormatOf> getIsFormatOfList() {
        return isFormatOfList;
    }

    /** 
     * Set the list of 'isFormatOf' element items.
     * 
     * @param list
     */
    public void setIsFormatOfList(List<IsFormatOf> list) {
        isFormatOfList = list;
    }

    /** 
     * Get the list of 'isPartOf' element items.
     * 
     * @return list
     */
    public List<IsPartOf> getIsPartOfList() {
        return isPartOfList;
    }

    /** 
     * Set the list of 'isPartOf' element items.
     * 
     * @param list
     */
    public void setIsPartOfList(List<IsPartOf> list) {
        isPartOfList = list;
    }

    /** 
     * Get the list of 'issued' element items.
     * 
     * @return list
     */
    public List<Issued> getIssuedList() {
        return issuedList;
    }

    /** 
     * Set the list of 'issued' element items.
     * 
     * @param list
     */
    public void setIssuedList(List<Issued> list) {
        issuedList = list;
    }

    /** 
     * Get the 'isNextInSequence' element value.
     * 
     * @return value
     */
    public IsNextInSequence getIsNextInSequence() {
        return isNextInSequence;
    }

    /** 
     * Set the 'isNextInSequence' element value.
     * 
     * @param isNextInSequence
     */
    public void setIsNextInSequence(IsNextInSequence isNextInSequence) {
        this.isNextInSequence = isNextInSequence;
    }

    /** 
     * Get the 'rights' element value.
     * 
     * @return value
     */
    public Rights1 getRights() {
        return rights;
    }

    /** 
     * Set the 'rights' element value.
     * 
     * @param rights
     */
    public void setRights(Rights1 rights) {
        this.rights = rights;
    }

    /** 
     * Get the list of 'sameAs' element items.
     * 
     * @return list
     */
    public List<SameAs> getSameAList() {
        return sameAList;
    }

    /** 
     * Set the list of 'sameAs' element items.
     * 
     * @param list
     */
    public void setSameAList(List<SameAs> list) {
        sameAList = list;
    }
}
