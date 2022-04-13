
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * The class of "abstract temporal extents, in the sense of Galilean
 physics, having a beginning, an end and a duration" (CIDOC CRM) Example:2001-12-31,
 01.01.01 - 02.02.02, 1503 - 1506 (the time span of the creation of Mona Lisa)
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:ns2="http://www.w3.org/2002/07/owl#" xmlns:ns3="http://www.w3.org/2004/02/skos/core#" xmlns:ns4="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="TimeSpanType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns3:prefLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:altLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns3:note" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:hasPart" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns4:begin" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:end" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:isNextInSequence" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TimeSpanType extends AboutType
{
    private List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
    private List<AltLabel> altLabelList = new ArrayList<AltLabel>();
    private List<Note> noteList = new ArrayList<Note>();
    private List<HasPart> hasPartList = new ArrayList<HasPart>();
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
    private Begin begin;
    private End end;
    private IsNextInSequence isNextInSequence;
    private List<SameAs> sameAList = new ArrayList<SameAs>();

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
     * Get the 'begin' element value.
     * 
     * @return value
     */
    public Begin getBegin() {
        return begin;
    }

    /** 
     * Set the 'begin' element value.
     * 
     * @param begin
     */
    public void setBegin(Begin begin) {
        this.begin = begin;
    }

    /** 
     * Get the 'end' element value.
     * 
     * @return value
     */
    public End getEnd() {
        return end;
    }

    /** 
     * Set the 'end' element value.
     * 
     * @param end
     */
    public void setEnd(End end) {
        this.end = end;
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
