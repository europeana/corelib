
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * 
 An URL for an image, or anything else that can be downloaded and
 represents a CHO.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="WebResourceType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns2:rights" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:rights" minOccurs="1" maxOccurs="1"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class WebResourceType extends AboutType
{
    private List<Rights1> rightList = new ArrayList<Rights1>();
    private Rights rights;

    /** 
     * Get the list of 'rights' element items.
     * 
     * @return list
     */
    public List<Rights1> getRightList() {
        return rightList;
    }

    /** 
     * Set the list of 'rights' element items.
     * 
     * @param list
     */
    public void setRightList(List<Rights1> list) {
        rightList = list;
    }

    /** 
     * Get the 'rights' element value.
     * 
     * @return value
     */
    public Rights getRights() {
        return rights;
    }

    /** 
     * Set the 'rights' element value.
     * 
     * @param rights
     */
    public void setRights(Rights rights) {
        this.rights = rights;
    }
}
