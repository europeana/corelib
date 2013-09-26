
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_relationships">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="xs:string" name="module_id"/>
 *     &lt;xs:element type="xs:string" name="related_module"/>
 *     &lt;xs:element type="xs:string" name="related_module_query"/>
 *     &lt;xs:element type="xs:int" name="deleted"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetRelationships
{
    private String session;
    private String moduleName;
    private String moduleId;
    private String relatedModule;
    private String relatedModuleQuery;
    private int deleted;

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
     * Get the 'related_module' element value.
     * 
     * @return value
     */
    public String getRelatedModule() {
        return relatedModule;
    }

    /** 
     * Set the 'related_module' element value.
     * 
     * @param relatedModule
     */
    public void setRelatedModule(String relatedModule) {
        this.relatedModule = relatedModule;
    }

    /** 
     * Get the 'related_module_query' element value.
     * 
     * @return value
     */
    public String getRelatedModuleQuery() {
        return relatedModuleQuery;
    }

    /** 
     * Set the 'related_module_query' element value.
     * 
     * @param relatedModuleQuery
     */
    public void setRelatedModuleQuery(String relatedModuleQuery) {
        this.relatedModuleQuery = relatedModuleQuery;
    }

    /** 
     * Get the 'deleted' element value.
     * 
     * @return value
     */
    public int getDeleted() {
        return deleted;
    }

    /** 
     * Set the 'deleted' element value.
     * 
     * @param deleted
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
