
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" mixed="true" name="tDocumentation">
 *   &lt;xs:sequence>
 *     &lt;xs:any minOccurs="0" maxOccurs="unbounded" processContents="lax"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TDocumentation
{
    private List<Element> anyList = new ArrayList<Element>();

    /** 
     * Get the list of 'tDocumentation' complexType items.
     * 
     * @return list
     */
    public List<Element> getAnyList() {
        return anyList;
    }

    /** 
     * Set the list of 'tDocumentation' complexType items.
     * 
     * @param list
     */
    public void setAnyList(List<Element> list) {
        anyList = list;
    }
}
