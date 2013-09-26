
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_related_notes">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="xs:string" name="module_id"/>
 *     &lt;xs:element type="ns:select_fields" name="select_fields"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetRelatedNotes
{
    private String session;
    private String moduleName;
    private String moduleId;
    private SelectFields selectFields;

    /** 
     * Get the 'session' element value.
     * 
     * @return value
     */
    public String getSession() {
        return session;
    }

    /** 
     * Set the 'session' element value.
     * 
     * @param session
     */
    public void setSession(String session) {
        this.session = session;
    }

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
     * Get the 'module_id' element value.
     * 
     * @return value
     */
    public String getModuleId() {
        return moduleId;
    }

    /** 
     * Set the 'module_id' element value.
     * 
     * @param moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    /** 
     * Get the 'select_fields' element value.
     * 
     * @return value
     */
    public SelectFields getSelectFields() {
        return selectFields;
    }

    /** 
     * Set the 'select_fields' element value.
     * 
     * @param selectFields
     */
    public void setSelectFields(SelectFields selectFields) {
        this.selectFields = selectFields;
    }
}
