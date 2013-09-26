
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="name_value_operator">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="xs:string" name="value"/>
 *     &lt;xs:element type="xs:string" name="operator"/>
 *     &lt;xs:element type="ns:select_fields" name="value_array"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NameValueOperator
{
    private String name;
    private String value;
    private String operator;
    private SelectFields valueArray;

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
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'value' element value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /** 
     * Get the 'operator' element value.
     * 
     * @return value
     */
    public String getOperator() {
        return operator;
    }

    /** 
     * Set the 'operator' element value.
     * 
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /** 
     * Get the 'value_array' element value.
     * 
     * @return value
     */
    public SelectFields getValueArray() {
        return valueArray;
    }

    /** 
     * Set the 'value_array' element value.
     * 
     * @param valueArray
     */
    public void setValueArray(SelectFields valueArray) {
        this.valueArray = valueArray;
    }
}
