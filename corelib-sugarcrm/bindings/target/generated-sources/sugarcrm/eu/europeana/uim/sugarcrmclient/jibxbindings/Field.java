
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="field">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="xs:string" name="type"/>
 *     &lt;xs:element type="xs:string" name="label"/>
 *     &lt;xs:element type="xs:int" name="required"/>
 *     &lt;xs:element type="ns:name_value_list" name="options"/>
 *     &lt;xs:element type="xs:string" name="default_value"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Field
{
    private String name;
    private String type;
    private String label;
    private int required;
    private NameValueList options;
    private String defaultValue;

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
     * Get the 'type' element value.
     * 
     * @return value
     */
    public String getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** 
     * Get the 'label' element value.
     * 
     * @return value
     */
    public String getLabel() {
        return label;
    }

    /** 
     * Set the 'label' element value.
     * 
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /** 
     * Get the 'required' element value.
     * 
     * @return value
     */
    public int getRequired() {
        return required;
    }

    /** 
     * Set the 'required' element value.
     * 
     * @param required
     */
    public void setRequired(int required) {
        this.required = required;
    }

    /** 
     * Get the 'options' element value.
     * 
     * @return value
     */
    public NameValueList getOptions() {
        return options;
    }

    /** 
     * Set the 'options' element value.
     * 
     * @param options
     */
    public void setOptions(NameValueList options) {
        this.options = options;
    }

    /** 
     * Get the 'default_value' element value.
     * 
     * @return value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /** 
     * Set the 'default_value' element value.
     * 
     * @param defaultValue
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
