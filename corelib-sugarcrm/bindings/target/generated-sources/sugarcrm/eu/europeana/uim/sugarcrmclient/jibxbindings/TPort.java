
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import org.jibx.runtime.QName;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tPort">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleDocumented">
 *       &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *       &lt;xs:attribute type="xs:QName" use="required" name="binding"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TPort extends TExtensibleDocumented
{
    private String name;
    private QName binding;

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
     * Get the 'binding' attribute value.
     * 
     * @return value
     */
    public QName getBinding() {
        return binding;
    }

    /** 
     * Set the 'binding' attribute value.
     * 
     * @param binding
     */
    public void setBinding(QName binding) {
        this.binding = binding;
    }
}
