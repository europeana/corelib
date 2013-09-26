
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tImport">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleAttributesDocumented">
 *       &lt;xs:attribute type="xs:string" use="required" name="namespace"/>
 *       &lt;xs:attribute type="xs:string" use="required" name="location"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TImport extends TExtensibleAttributesDocumented
{
    private String namespace;
    private String location;

    /** 
     * Get the 'namespace' attribute value.
     * 
     * @return value
     */
    public String getNamespace() {
        return namespace;
    }

    /** 
     * Set the 'namespace' attribute value.
     * 
     * @param namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /** 
     * Get the 'location' attribute value.
     * 
     * @return value
     */
    public String getLocation() {
        return location;
    }

    /** 
     * Set the 'location' attribute value.
     * 
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
