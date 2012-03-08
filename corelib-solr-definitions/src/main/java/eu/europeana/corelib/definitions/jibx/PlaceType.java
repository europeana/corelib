
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * An "extent in space, in particular on the surface of
 the earth, in the pure sense of physics: independent from temporal
 phenomena and matter" (CIDOC CRM) Example:the region of
 space occupied by Rome today, the region of space occupied by the United
 Kingdom today, the region of space occupied by the Republic of
 Crimea in 1945

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:ns1="http://purl.org/dc/terms/" xmlns:ns2="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="PlaceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:ContextualHierarchicalResourceType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns2:pos_lat" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:pos_long" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class PlaceType extends ContextualHierarchicalResourceType
{
    private PosLat posLat;
    private PosLong posLong;
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();

    /** 
     * Get the 'pos_lat' element value.
     * 
     * @return value
     */
    public PosLat getPosLat() {
        return posLat;
    }

    /** 
     * Set the 'pos_lat' element value.
     * 
     * @param posLat
     */
    public void setPosLat(PosLat posLat) {
        this.posLat = posLat;
    }

    /** 
     * Get the 'pos_long' element value.
     * 
     * @return value
     */
    public PosLong getPosLong() {
        return posLong;
    }

    /** 
     * Set the 'pos_long' element value.
     * 
     * @param posLong
     */
    public void setPosLong(PosLong posLong) {
        this.posLong = posLong;
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
}
