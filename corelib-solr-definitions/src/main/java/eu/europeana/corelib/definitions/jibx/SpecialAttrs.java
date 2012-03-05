
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:attributeGroup xmlns:xml="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="specialAttrs">
 *   &lt;xs:attribute ref="xml:base">
 *     &lt;!-- Reference to inner class Base -->
 *   &lt;/xs:attribute>
 *   &lt;xs:attribute ref="xml:lang">
 *     &lt;!-- Reference to inner class Lang -->
 *   &lt;/xs:attribute>
 *   &lt;xs:attribute ref="xml:space"/>
 *   &lt;xs:attribute ref="xml:id">
 *     &lt;!-- Reference to inner class Id -->
 *   &lt;/xs:attribute>
 * &lt;/xs:attributeGroup>
 * </pre>
 */
public class SpecialAttrs
{
    private Base base;
    private Lang lang;
    private Space space;
    private Id id;

    /** 
     * Get the 'base' attribute value.
     * 
     * @return value
     */
    public Base getBase() {
        return base;
    }

    /** 
     * Set the 'base' attribute value.
     * 
     * @param base
     */
    public void setBase(Base base) {
        this.base = base;
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
     * Get the 'space' attribute value.
     * 
     * @return value
     */
    public Space getSpace() {
        return space;
    }

    /** 
     * Set the 'space' attribute value.
     * 
     * @param space
     */
    public void setSpace(Space space) {
        this.space = space;
    }

    /** 
     * Get the 'id' attribute value.
     * 
     * @return value
     */
    public Id getId() {
        return id;
    }

    /** 
     * Set the 'id' attribute value.
     * 
     * @param id
     */
    public void setId(Id id) {
        this.id = id;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" ref="xml:base"/>
     * 
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="base"/>
     * </pre>
     */
    public static class Base
    {
        private String base;

        /** 
         * Get the 'base' attribute value. 
        <div xmlns="http://www.w3.org/1999/xhtml">
         
          <h3>base (as an attribute name)</h3>
          <p>
           denotes an attribute whose value
           provides a URI to be used as the base for interpreting any
           relative URIs in the scope of the element on which it
           appears; its value is inherited.  This name is reserved
           by virtue of its definition in the XML Base specification.</p>
         
         <p>
          See <a href="http://www.w3.org/TR/xmlbase/">http://www.w3.org/TR/xmlbase/</a>
          for information about this attribute.
         </p>
        </div>
        
         * 
         * @return value
         */
        public String getBase() {
            return base;
        }

        /** 
         * Set the 'base' attribute value. 
        <div xmlns="http://www.w3.org/1999/xhtml">
         
          <h3>base (as an attribute name)</h3>
          <p>
           denotes an attribute whose value
           provides a URI to be used as the base for interpreting any
           relative URIs in the scope of the element on which it
           appears; its value is inherited.  This name is reserved
           by virtue of its definition in the XML Base specification.</p>
         
         <p>
          See <a href="http://www.w3.org/TR/xmlbase/">http://www.w3.org/TR/xmlbase/</a>
          for information about this attribute.
         </p>
        </div>
        
         * 
         * @param base
         */
        public void setBase(String base) {
            this.base = base;
        }
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" ref="xml:lang"/>
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
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" ref="xml:id"/>
     * 
     * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="id"/>
     * </pre>
     */
    public static class Id
    {
        private String id;

        /** 
         * Get the 'id' attribute value. 
        <div xmlns="http://www.w3.org/1999/xhtml">
         
          <h3>id (as an attribute name)</h3> 
          <p>
           denotes an attribute whose value
           should be interpreted as if declared to be of type ID.
           This name is reserved by virtue of its definition in the
           xml:id specification.</p>
         
         <p>
          See <a href="http://www.w3.org/TR/xml-id/">http://www.w3.org/TR/xml-id/</a>
          for information about this attribute.
         </p>
        </div>
        
         * 
         * @return value
         */
        public String getId() {
            return id;
        }

        /** 
         * Set the 'id' attribute value. 
        <div xmlns="http://www.w3.org/1999/xhtml">
         
          <h3>id (as an attribute name)</h3> 
          <p>
           denotes an attribute whose value
           should be interpreted as if declared to be of type ID.
           This name is reserved by virtue of its definition in the
           xml:id specification.</p>
         
         <p>
          See <a href="http://www.w3.org/TR/xml-id/">http://www.w3.org/TR/xml-id/</a>
          for information about this attribute.
         </p>
        </div>
        
         * 
         * @param id
         */
        public void setId(String id) {
            this.id = id;
        }
    }
}
