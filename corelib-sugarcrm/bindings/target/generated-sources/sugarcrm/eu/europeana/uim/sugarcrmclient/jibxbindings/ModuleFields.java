
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="module_fields">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="ns:field_list" name="module_fields"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ModuleFields
{
    private String moduleName;
    private FieldList moduleFields;
    private ErrorValue error;

    /** 
     * Get the 'module_name' element value.
     * 
     * @return value
     */
    public String getModuleName() {
        return moduleName;
    }

    /** 
     * Set the 'module_name' element value.
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /** 
     * Get the 'module_fields' element value.
     * 
     * @return value
     */
    public FieldList getModuleFields() {
        return moduleFields;
    }

    /** 
     * Set the 'module_fields' element value.
     * 
     * @param moduleFields
     */
    public void setModuleFields(FieldList moduleFields) {
        this.moduleFields = moduleFields;
    }

    /** 
     * Get the 'error' element value.
     * 
     * @return value
     */
    public ErrorValue getError() {
        return error;
    }

    /** 
     * Set the 'error' element value.
     * 
     * @param error
     */
    public void setError(ErrorValue error) {
        this.error = error;
    }
}
