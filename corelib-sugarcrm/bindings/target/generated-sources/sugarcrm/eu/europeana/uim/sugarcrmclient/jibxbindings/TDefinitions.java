
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tDefinitions">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleDocumented">
 *       &lt;xs:sequence>
 *         &lt;xs:group ref="ns:anyTopLevelOptionalElement" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute type="xs:string" use="optional" name="targetNamespace"/>
 *       &lt;xs:attribute type="xs:string" use="optional" name="name"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TDefinitions extends TExtensibleDocumented
{
    private List<AnyTopLevelOptionalElement> anyTopLevelOptionalElementList = new ArrayList<AnyTopLevelOptionalElement>();
    private String targetNamespace;
    private String name;

    /** 
     * Get the list of 'anyTopLevelOptionalElement' group items.
     * 
     * @return list
     */
    public List<AnyTopLevelOptionalElement> getAnyTopLevelOptionalElementList() {
        return anyTopLevelOptionalElementList;
    }

    /** 
     * Set the list of 'anyTopLevelOptionalElement' group items.
     * 
     * @param list
     */
    public void setAnyTopLevelOptionalElementList(
            List<AnyTopLevelOptionalElement> list) {
        anyTopLevelOptionalElementList = list;
    }

    /** 
     * Get the 'targetNamespace' attribute value.
     * 
     * @return value
     */
    public String getTargetNamespace() {
        return targetNamespace;
    }

    /** 
     * Set the 'targetNamespace' attribute value.
     * 
     * @param targetNamespace
     */
    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    /** 
     * Get the 'name' attribute value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' attribute value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
