
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tBindingOperation">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleDocumented">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:tBindingOperationMessage" name="input" minOccurs="0"/>
 *         &lt;xs:element type="ns:tBindingOperationMessage" name="output" minOccurs="0"/>
 *         &lt;xs:element type="ns:tBindingOperationFault" name="fault" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TBindingOperation extends TExtensibleDocumented
{
    private TBindingOperationMessage input;
    private TBindingOperationMessage output;
    private List<TBindingOperationFault> faultList = new ArrayList<TBindingOperationFault>();
    private String name;

    /** 
     * Get the 'input' element value.
     * 
     * @return value
     */
    public TBindingOperationMessage getInput() {
        return input;
    }

    /** 
     * Set the 'input' element value.
     * 
     * @param input
     */
    public void setInput(TBindingOperationMessage input) {
        this.input = input;
    }

    /** 
     * Get the 'output' element value.
     * 
     * @return value
     */
    public TBindingOperationMessage getOutput() {
        return output;
    }

    /** 
     * Set the 'output' element value.
     * 
     * @param output
     */
    public void setOutput(TBindingOperationMessage output) {
        this.output = output;
    }

    /** 
     * Get the list of 'fault' element items.
     * 
     * @return list
     */
    public List<TBindingOperationFault> getFaultList() {
        return faultList;
    }

    /** 
     * Set the list of 'fault' element items.
     * 
     * @param list
     */
    public void setFaultList(List<TBindingOperationFault> list) {
        faultList = list;
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
