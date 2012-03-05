
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xml="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="LiteralType">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:string">
 *       &lt;xs:attribute use="optional" ref="xml:lang">
 *         &lt;!-- Reference to inner class Lang -->
 *       &lt;/xs:attribute>
 *     &lt;/xs:extension>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class LiteralType
{
    private String string;
    private Lang lang;

    /** 
     * Get the extension value.
     * 
     * @return value
     */
    public String getString() {
        return string;
    }

    /** 
     * Set the extension value.
     * 
     * @param string
     */
    public void setString(String string) {
        this.string = string;
    }

    /** 
     * Get the 'lang' attribute value.
     * 
     * @return value
     */
    public Lang getLang() {
        return lang;
    }

    /** 
     * Set the 'lang' attribute value.
     * 
     * @param lang
     */
    public void setLang(Lang lang) {
        this.lang = lang;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" use="optional" ref="xml:lang"/>
     * 
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="lang"/>
     * </pre>
     */
    public static class Lang
    {
        private String lang;

        /** 
         * Get the 'lang' attribute value. 
        <div xmlns="http://www.w3.org/1999/xhtml">
         
          <h3>lang (as an attribute name)</h3>
          <p>
           denotes an attribute whose value
           is a language code for the natural language of the content of
           any element; its value is inherited.  This name is reserved
           by virtue of its definition in the XML specification.</p>
         
        </div>
        <div xmlns="http://www.w3.org/1999/xhtml">
         <h4>Notes</h4>
         <p>
          Attempting to install the relevant ISO 2- and 3-letter
          codes as the enumerated possible values is probably never
          going to be a realistic possibility.  
         </p>
         <p>
          See BCP 47 at <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">
           http://www.rfc-editor.org/rfc/bcp/bcp47.txt</a>
          and the IANA language subtag registry at
          <a href="http://www.iana.org/assignments/language-subtag-registry">
           http://www.iana.org/assignments/language-subtag-registry</a>
          for further information.
         </p>
         <p>
          The union allows for the 'un-declaration' of xml:lang with
          the empty string.
         </p>
        </div>
        
         * 
         * @return value
         */
        public String getLang() {
            return lang;
        }

        /** 
         * Set the 'lang' attribute value. 
        <div xmlns="http://www.w3.org/1999/xhtml">
         
          <h3>lang (as an attribute name)</h3>
          <p>
           denotes an attribute whose value
           is a language code for the natural language of the content of
           any element; its value is inherited.  This name is reserved
           by virtue of its definition in the XML specification.</p>
         
        </div>
        <div xmlns="http://www.w3.org/1999/xhtml">
         <h4>Notes</h4>
         <p>
          Attempting to install the relevant ISO 2- and 3-letter
          codes as the enumerated possible values is probably never
          going to be a realistic possibility.  
         </p>
         <p>
          See BCP 47 at <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">
           http://www.rfc-editor.org/rfc/bcp/bcp47.txt</a>
          and the IANA language subtag registry at
          <a href="http://www.iana.org/assignments/language-subtag-registry">
           http://www.iana.org/assignments/language-subtag-registry</a>
          for further information.
         </p>
         <p>
          The union allows for the 'un-declaration' of xml:lang with
          the empty string.
         </p>
        </div>
        
         * 
         * @param lang
         */
        public void setLang(String lang) {
            this.lang = lang;
        }
    }
}
