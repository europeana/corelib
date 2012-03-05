
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.w3.org/2004/02/skos/core#" xmlns:ns2="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" abstract="true" name="ContextualResourceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns1:prefLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:altLabel" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:note" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public abstract class ContextualResourceType extends AboutType
{
    private List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
    private List<AltLabel> altLabelList = new ArrayList<AltLabel>();
    private List<Note> noteList = new ArrayList<Note>();

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
}
