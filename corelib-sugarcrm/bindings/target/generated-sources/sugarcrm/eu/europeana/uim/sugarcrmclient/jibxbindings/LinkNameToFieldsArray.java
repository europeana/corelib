
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="link_name_to_fields_array">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="ns:select_fields" name="value"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class LinkNameToFieldsArray
{
    private String name;
    private SelectFields value;

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
     * Get the 'value' element value.
     * 
     * @return value
     */
    public SelectFields getValue() {
        return value;
    }

    /** 
     * Set the 'value' element value.
     * 
     * @param value
     */
    public void setValue(SelectFields value) {
        this.value = value;
    }
}
