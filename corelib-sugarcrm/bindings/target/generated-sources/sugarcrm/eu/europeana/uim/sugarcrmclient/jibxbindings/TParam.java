
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import org.jibx.runtime.QName;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tParam">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleAttributesDocumented">
 *       &lt;xs:attribute type="xs:string" use="optional" name="name"/>
 *       &lt;xs:attribute type="xs:QName" use="required" name="message"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TParam extends TExtensibleAttributesDocumented
{
    private String name;
    private QName message;

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
     * Get the 'message' attribute value.
     * 
     * @return value
     */
    public QName getMessage() {
        return message;
    }

    /** 
     * Set the 'message' attribute value.
     * 
     * @param message
     */
    public void setMessage(QName message) {
        this.message = message;
    }
}
