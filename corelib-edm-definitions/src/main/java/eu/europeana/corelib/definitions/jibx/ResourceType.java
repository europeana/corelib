
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ResourceType">
 *   &lt;xs:attribute use="required" ref="ns:resource"/>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:attribute xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="resource"/>
 * </pre>
 */
public class ResourceType
{
    private String resource;

    /** 
     * Get the 'resource' attribute value.
     * 
     * @return value
     */
    public String getResource() {
        return resource;
    }

    /** 
     * Set the 'resource' attribute value.
     * 
     * @param resource
     */
    public void setResource(String resource) {
        this.resource = resource;
    }
}
