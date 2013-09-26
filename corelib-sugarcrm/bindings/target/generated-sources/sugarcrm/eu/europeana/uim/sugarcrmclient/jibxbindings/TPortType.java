
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tPortType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleAttributesDocumented">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:tOperation" name="operation" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TPortType extends TExtensibleAttributesDocumented
{
    private List<TOperation> operationList = new ArrayList<TOperation>();
    private String name;

    /** 
     * Get the list of 'operation' element items.
     * 
     * @return list
     */
    public List<TOperation> getOperationList() {
        return operationList;
    }

    /** 
     * Set the list of 'operation' element items.
     * 
     * @param list
     */
    public void setOperationList(List<TOperation> list) {
        operationList = list;
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
