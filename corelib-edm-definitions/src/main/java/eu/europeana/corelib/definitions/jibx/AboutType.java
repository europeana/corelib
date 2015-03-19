
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AboutType">
 *   &lt;xs:attribute use="required" ref="ns:about"/>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:attribute xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="about"/>
 * </pre>
 */
public class AboutType
{
    private String about;

    /** 
     * Get the 'about' attribute value.
     * 
     * @return value
     */
    public String getAbout() {
        return about;
    }

    /** 
     * Set the 'about' attribute value.
     * 
     * @param about
     */
    public void setAbout(String about) {
        this.about = about;
    }
}
