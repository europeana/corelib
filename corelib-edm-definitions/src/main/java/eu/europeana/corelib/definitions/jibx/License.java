
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.w3.org/ns/odrl/2/" xmlns:ns2="http://creativecommons.org/ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="License">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence>
 *           &lt;xs:element ref="ns1:inheritFrom" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns2:deprecatedOn" minOccurs="0" maxOccurs="1"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class License extends AboutType
{
    private InheritFrom inheritFrom;
    private DateType deprecatedOn;

    /** 
     * Get the 'inheritFrom' element value.
     * 
     * @return value
     */
    public InheritFrom getInheritFrom() {
        return inheritFrom;
    }

    /** 
     * Set the 'inheritFrom' element value.
     * 
     * @param inheritFrom
     */
    public void setInheritFrom(InheritFrom inheritFrom) {
        this.inheritFrom = inheritFrom;
    }

    /** 
     * Get the 'deprecatedOn' element value.
     * 
     * @return value
     */
    public DateType getDeprecatedOn() {
        return deprecatedOn;
    }

    /** 
     * Set the 'deprecatedOn' element value.
     * 
     * @param deprecatedOn
     */
    public void setDeprecatedOn(DateType deprecatedOn) {
        this.deprecatedOn = deprecatedOn;
    }
}
