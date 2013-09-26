
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * 
 This type is extended by  component types to allow them to be documented

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tDocumented">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:tDocumentation" name="documentation" minOccurs="0"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TDocumented
{
    private TDocumentation documentation;

    /** 
     * Get the 'documentation' element value.
     * 
     * @return value
     */
    public TDocumentation getDocumentation() {
        return documentation;
    }

    /** 
     * Set the 'documentation' element value.
     * 
     * @param documentation
     */
    public void setDocumentation(TDocumentation documentation) {
        this.documentation = documentation;
    }
}
