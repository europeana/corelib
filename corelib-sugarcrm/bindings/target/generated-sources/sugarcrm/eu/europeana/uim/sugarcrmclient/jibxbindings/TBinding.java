
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;
import org.jibx.runtime.QName;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tBinding">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleDocumented">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:tBindingOperation" name="operation" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *       &lt;xs:attribute type="xs:QName" use="required" name="type"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TBinding extends TExtensibleDocumented
{
    private List<TBindingOperation> operationList = new ArrayList<TBindingOperation>();
    private String name;
    private QName type;

    /** 
     * Get the list of 'operation' element items.
     * 
     * @return list
     */
    public List<TBindingOperation> getOperationList() {
        return operationList;
    }

    /** 
     * Set the list of 'operation' element items.
     * 
     * @param list
     */
    public void setOperationList(List<TBindingOperation> list) {
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

    /** 
     * Get the 'type' attribute value.
     * 
     * @return value
     */
    public QName getType() {
        return type;
    }

    /** 
     * Set the 'type' attribute value.
     * 
     * @param type
     */
    public void setType(QName type) {
        this.type = type;
    }
}
