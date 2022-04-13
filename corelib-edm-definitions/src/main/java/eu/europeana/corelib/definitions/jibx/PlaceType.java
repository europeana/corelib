
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * An "extent in space, in particular on the surface of the earth, in the
 pure sense of physics: independent from temporal phenomena and matter" (CIDOC CRM)
 Example:the region of space occupied by Rome today, the region of space occupied by
 the United Kingdom today, the region of space occupied by the Republic of Crimea in
 1945
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:ns2="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:ns3="http://www.w3.org/2002/07/owl#" xmlns:ns4="http://www.w3.org/2004/02/skos/core#" xmlns:ns5="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="PlaceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns2:lat" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:long" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:alt" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:prefLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:altLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:note" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:hasPart" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:isNextInSequence" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class PlaceType extends AboutType
{
    private Lat lat;
    private _Long _long;
    private Alt alt;
    private List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
    private List<AltLabel> altLabelList = new ArrayList<AltLabel>();
    private List<Note> noteList = new ArrayList<Note>();
    private List<HasPart> hasPartList = new ArrayList<HasPart>();
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
    private IsNextInSequence isNextInSequence;
    private List<SameAs> sameAList = new ArrayList<SameAs>();

    /** 
     * Get the 'lat' element value.
     * 
     * @return value
     */
    public Lat getLat() {
        return lat;
    }

    /** 
     * Set the 'lat' element value.
     * 
     * @param lat
     */
    public void setLat(Lat lat) {
        this.lat = lat;
    }

    /** 
     * Get the 'long' element value.
     * 
     * @return value
     */
    public _Long getLong() {
        return _long;
    }

    /** 
     * Set the 'long' element value.
     * 
     * @param _long
     */
    public void setLong(_Long _long) {
        this._long = _long;
    }

    /** 
     * Get the 'alt' element value.
     * 
     * @return value
     */
    public Alt getAlt() {
        return alt;
    }

    /** 
     * Set the 'alt' element value.
     * 
     * @param alt
     */
    public void setAlt(Alt alt) {
        this.alt = alt;
    }

    /** 
     * Get the list of 'prefLabel' element items.
     * 
     * @return list
     */
    public List<PrefLabel> getPrefLabelList() {
        return prefLabelList;
    }

    /** 
     * Set the list of 'prefLabel' element items.
     * 
     * @param list
     */
    public void setPrefLabelList(List<PrefLabel> list) {
        prefLabelList = list;
    }

    /** 
     * Get the list of 'altLabel' element items.
     * 
     * @return list
     */
    public List<AltLabel> getAltLabelList() {
        return altLabelList;
    }

    /** 
     * Set the list of 'altLabel' element items.
     * 
     * @param list
     */
    public void setAltLabelList(List<AltLabel> list) {
        altLabelList = list;
    }

    /** 
     * Get the list of 'note' element items.
     * 
     * @return list
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /** 
     * Set the list of 'note' element items.
     * 
     * @param list
     */
    public void setNoteList(List<Note> list) {
        noteList = list;
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
