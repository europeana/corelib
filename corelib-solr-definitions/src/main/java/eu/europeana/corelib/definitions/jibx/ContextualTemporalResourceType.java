
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" abstract="false" name="ContextualTemporalResourceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:ContextualHierarchicalResourceType">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="begin" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element name="end" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ContextualTemporalResourceType
        extends
            ContextualHierarchicalResourceType
{
    private List<java.lang.String> begins = new ArrayList<java.lang.String>();
    private List<java.lang.String> ends = new ArrayList<java.lang.String>();

    /** 
     * Get the list of 'begin' element items.
     * 
     * @return list
     */
    public List<java.lang.String> getBegins() {
        return begins;
    }

    /** 
     * Set the list of 'begin' element items.
     * 
     * @param list
     */
    public void setBegins(List<java.lang.String> list) {
        begins = list;
    }

    /** 
     * Get the list of 'end' element items.
     * 
     * @return list
     */
    public List<java.lang.String> getEnds() {
        return ends;
    }

    /** 
     * Set the list of 'end' element items.
     * 
     * @param list
     */
    public void setEnds(List<java.lang.String> list) {
        ends = list;
    }
}
