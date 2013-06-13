
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Base class for ProvidedCHO implementations
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="BaseProvidedCHOType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:EuropeanaType">
 *       &lt;xs:sequence minOccurs="1" maxOccurs="1">
 *         &lt;xs:element ref="ns:currentLocation" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns:hasMet" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:hasType" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:incorporates" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:isDerivativeOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:isNextInSequence" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:isRelatedTo" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:isRepresentationOf" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns:isSimilarTo" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:isSuccessorOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns:realizes" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class BaseProvidedCHOType extends EuropeanaType
{
    private CurrentLocation currentLocation;
    private List<HasMet> hasMetList = new ArrayList<HasMet>();
    private List<HasType> hasTypeList = new ArrayList<HasType>();
    private List<Incorporates> incorporateList = new ArrayList<Incorporates>();
    private List<IsDerivativeOf> isDerivativeOfList = new ArrayList<IsDerivativeOf>();
    private List<IsNextInSequence> isNextInSequenceList = new ArrayList<IsNextInSequence>();
    private List<IsRelatedTo> isRelatedToList = new ArrayList<IsRelatedTo>();
    private IsRepresentationOf isRepresentationOf;
    private List<IsSimilarTo> isSimilarToList = new ArrayList<IsSimilarTo>();
    private List<IsSuccessorOf> isSuccessorOfList = new ArrayList<IsSuccessorOf>();
    private List<Realizes> realizeList = new ArrayList<Realizes>();

    /** 
     * Get the 'currentLocation' element value.
     * 
     * @return value
     */
    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    /** 
     * Set the 'currentLocation' element value.
     * 
     * @param currentLocation
     */
    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    /** 
     * Get the list of 'hasMet' element items.
     * 
     * @return list
     */
    public List<HasMet> getHasMetList() {
        return hasMetList;
    }

    /** 
     * Set the list of 'hasMet' element items.
     * 
     * @param list
     */
    public void setHasMetList(List<HasMet> list) {
        hasMetList = list;
    }

    /** 
     * Get the list of 'hasType' element items.
     * 
     * @return list
     */
    public List<HasType> getHasTypeList() {
        return hasTypeList;
    }

    /** 
     * Set the list of 'hasType' element items.
     * 
     * @param list
     */
    public void setHasTypeList(List<HasType> list) {
        hasTypeList = list;
    }

    /** 
     * Get the list of 'incorporates' element items.
     * 
     * @return list
     */
    public List<Incorporates> getIncorporateList() {
        return incorporateList;
    }

    /** 
     * Set the list of 'incorporates' element items.
     * 
     * @param list
     */
    public void setIncorporateList(List<Incorporates> list) {
        incorporateList = list;
    }

    /** 
     * Get the list of 'isDerivativeOf' element items.
     * 
     * @return list
     */
    public List<IsDerivativeOf> getIsDerivativeOfList() {
        return isDerivativeOfList;
    }

    /** 
     * Set the list of 'isDerivativeOf' element items.
     * 
     * @param list
     */
    public void setIsDerivativeOfList(List<IsDerivativeOf> list) {
        isDerivativeOfList = list;
    }

    /** 
     * Get the list of 'isNextInSequence' element items.
     * 
     * @return list
     */
    public List<IsNextInSequence> getIsNextInSequenceList() {
        return isNextInSequenceList;
    }

    /** 
     * Set the list of 'isNextInSequence' element items.
     * 
     * @param list
     */
    public void setIsNextInSequenceList(List<IsNextInSequence> list) {
        isNextInSequenceList = list;
    }

    /** 
     * Get the list of 'isRelatedTo' element items.
     * 
     * @return list
     */
    public List<IsRelatedTo> getIsRelatedToList() {
        return isRelatedToList;
    }

    /** 
     * Set the list of 'isRelatedTo' element items.
     * 
     * @param list
     */
    public void setIsRelatedToList(List<IsRelatedTo> list) {
        isRelatedToList = list;
    }

    /** 
     * Get the 'isRepresentationOf' element value.
     * 
     * @return value
     */
    public IsRepresentationOf getIsRepresentationOf() {
        return isRepresentationOf;
    }

    /** 
     * Set the 'isRepresentationOf' element value.
     * 
     * @param isRepresentationOf
     */
    public void setIsRepresentationOf(IsRepresentationOf isRepresentationOf) {
        this.isRepresentationOf = isRepresentationOf;
    }

    /** 
     * Get the list of 'isSimilarTo' element items.
     * 
     * @return list
     */
    public List<IsSimilarTo> getIsSimilarToList() {
        return isSimilarToList;
    }

    /** 
     * Set the list of 'isSimilarTo' element items.
     * 
     * @param list
     */
    public void setIsSimilarToList(List<IsSimilarTo> list) {
        isSimilarToList = list;
    }

    /** 
     * Get the list of 'isSuccessorOf' element items.
     * 
     * @return list
     */
    public List<IsSuccessorOf> getIsSuccessorOfList() {
        return isSuccessorOfList;
    }

    /** 
     * Set the list of 'isSuccessorOf' element items.
     * 
     * @param list
     */
    public void setIsSuccessorOfList(List<IsSuccessorOf> list) {
        isSuccessorOfList = list;
    }

    /** 
     * Get the list of 'realizes' element items.
     * 
     * @return list
     */
    public List<Realizes> getRealizeList() {
        return realizeList;
    }

    /** 
     * Set the list of 'realizes' element items.
     * 
     * @param list
     */
    public void setRealizeList(List<Realizes> list) {
        realizeList = list;
    }
}
