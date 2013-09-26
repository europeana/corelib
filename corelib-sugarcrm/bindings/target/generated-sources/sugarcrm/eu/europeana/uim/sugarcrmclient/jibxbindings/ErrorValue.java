
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="error_value">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="number" minOccurs="0"/>
 *     &lt;xs:element type="xs:string" name="name" minOccurs="0"/>
 *     &lt;xs:element type="xs:string" name="description" minOccurs="0"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ErrorValue
{
    private String number;
    private String name;
    private String description;

    /** 
     * Get the 'number' element value.
     * 
     * @return value
     */
    public String getNumber() {
        return number;
    }

    /** 
     * Set the 'number' element value.
     * 
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /** 
     * Get the 'name' element value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'description' element value.
     * 
     * @return value
     */
    public String getDescription() {
        return description;
    }

    /** 
     * Set the 'description' element value.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
