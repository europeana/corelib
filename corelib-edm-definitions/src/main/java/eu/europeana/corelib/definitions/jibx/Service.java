
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:ns2="http://rdfs.org/sioc/services#" xmlns:ns3="http://usefulinc.com/ns/doap#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Service">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence minOccurs="1" maxOccurs="1">
 *           &lt;xs:element ref="ns1:conformsTo" minOccurs="1" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns3:implements" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Service extends AboutType
{
    private List<ConformsTo> conformsToList = new ArrayList<ConformsTo>();
    private List<Implements> implementList = new ArrayList<Implements>();

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
     * Get the list of 'implements' element items.
     * 
     * @return list
     */
    public List<Implements> getImplementList() {
        return implementList;
    }

    /** 
     * Set the list of 'implements' element items.
     * 
     * @param list
     */
    public void setImplementList(List<Implements> list) {
        implementList = list;
    }
}
