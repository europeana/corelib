
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" abstract="true" name="tExtensibleDocumented">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tDocumented">
 *       &lt;xs:sequence>
 *         &lt;xs:any minOccurs="0" maxOccurs="unbounded" processContents="lax" namespace="##other"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public abstract class TExtensibleDocumented extends TDocumented
{
    private List<Element> anyList = new ArrayList<Element>();

    /** 
     * Get the list of any items.
     * 
     * @return list
     */
    public List<Element> getAnyList() {
        return anyList;
    }

    /** 
     * Set the list of any items.
     * 
     * @param list
     */
    public void setAnyList(List<Element> list) {
        anyList = list;
    }
}
