
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import org.jibx.runtime.QName;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tPart">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleAttributesDocumented">
 *       &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *       &lt;xs:attribute type="xs:QName" use="optional" name="element"/>
 *       &lt;xs:attribute type="xs:QName" use="optional" name="type"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TPart extends TExtensibleAttributesDocumented
{
    private String name;
    private QName element;
    private QName type;

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
     * Get the 'element' attribute value.
     * 
     * @return value
     */
    public QName getElement() {
        return element;
    }

    /** 
     * Set the 'element' attribute value.
     * 
     * @param element
     */
    public void setElement(QName element) {
        this.element = element;
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
