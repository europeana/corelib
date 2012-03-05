
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/2004/02/skos/core#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Concept">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns1:ContextualHierarchicalResourceType">
 *         &lt;xs:sequence>
 *           &lt;xs:element ref="ns:broader" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Concept extends ContextualHierarchicalResourceType
{
    private List<Broader> broaderList = new ArrayList<Broader>();

    /** 
     * Get the list of 'broader' element items.
     * 
     * @return list
     */
    public List<Broader> getBroaderList() {
        return broaderList;
    }

    /** 
     * Set the list of 'broader' element items.
     * 
     * @param list
     */
    public void setBroaderList(List<Broader> list) {
        broaderList = list;
    }
}
