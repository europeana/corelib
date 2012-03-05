
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * The class of “abstract temporal extents, in the sense of Galilean physics, having a beginning, an end and a duration” (CIDOC CRM) Example:2001-12-31, 01.01.01 – 02.02.02, 1503 –
 1506 (the time span of the creation of Mona Lisa)
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:ns1="http://purl.org/dc/terms/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="TimeSpanType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:ContextualTemporalResourceType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns1:isPartOf" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TimeSpanType extends ContextualTemporalResourceType
{
    private List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();

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
