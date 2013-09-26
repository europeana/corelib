
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" abstract="true" name="tExtensibilityElement">
 *   &lt;xs:attribute use="optional" ref="wsdl:required">
 *     &lt;!-- Reference to inner class Required -->
 *   &lt;/xs:attribute>
 * &lt;/xs:complexType>
 * </pre>
 */
public abstract class TExtensibilityElement
{
    private Required required;

    /** 
     * Get the 'required' attribute value.
     * 
     * @return value
     */
    public Required getRequired() {
        return required;
    }

    /** 
     * Set the 'required' attribute value.
     * 
     * @param required
     */
    public void setRequired(Required required) {
        this.required = required;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:attribute xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" use="optional" ref="ns:required"/>
     * 
     * &lt;xs:attribute xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:boolean" name="required"/>
     * </pre>
     */
    public static class Required
    {
        private boolean required;

        /** 
         * Get the 'required' attribute value.
         * 
         * @return value
         */
        public boolean isRequired() {
            return required;
        }

        /** 
         * Set the 'required' attribute value.
         * 
         * @param required
         */
        public void setRequired(boolean required) {
            this.required = required;
        }
    }
}
