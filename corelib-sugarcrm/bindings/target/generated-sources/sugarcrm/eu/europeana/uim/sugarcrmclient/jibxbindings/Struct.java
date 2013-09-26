
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:group xmlns:ns="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Struct">
 *   &lt;xs:sequence>
 *     &lt;xs:any minOccurs="0" maxOccurs="unbounded" processContents="lax" namespace="##any"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:group>
 * </pre>
 */
public class Struct
{
    private List<Element> anyList = new ArrayList<Element>();

    /** 
     * Get the list of 'Struct' group items.
     * 
     * @return list
     */
    public List<Element> getAnyList() {
        return anyList;
    }

    /** 
     * Set the list of 'Struct' group items.
     * 
     * @param list
     */
    public void setAnyList(List<Element> list) {
        anyList = list;
    }
}
