
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="sync_get_modified_relationships">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="xs:string" name="related_module"/>
 *     &lt;xs:element type="xs:string" name="from_date"/>
 *     &lt;xs:element type="xs:string" name="to_date"/>
 *     &lt;xs:element type="xs:int" name="offset"/>
 *     &lt;xs:element type="xs:int" name="max_results"/>
 *     &lt;xs:element type="xs:int" name="deleted"/>
 *     &lt;xs:element type="xs:string" name="module_id"/>
 *     &lt;xs:element type="ns:select_fields" name="select_fields"/>
 *     &lt;xs:element type="ns:select_fields" name="ids"/>
 *     &lt;xs:element type="xs:string" name="relationship_name"/>
 *     &lt;xs:element type="xs:string" name="deletion_date"/>
 *     &lt;xs:element type="xs:int" name="php_serialize"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SyncGetModifiedRelationships
{
    private String session;
    private String moduleName;
    private String relatedModule;
    private String fromDate;
    private String toDate;
    private int offset;
    private int maxResults;
    private int deleted;
    private String moduleId;
    private SelectFields selectFields;
    private SelectFields ids;
    private String relationshipName;
    private String deletionDate;
    private int phpSerialize;

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
     * Get the 'from_date' element value.
     * 
     * @return value
     */
    public String getFromDate() {
        return fromDate;
    }

    /** 
     * Set the 'from_date' element value.
     * 
     * @param fromDate
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /** 
     * Get the 'to_date' element value.
     * 
     * @return value
     */
    public String getToDate() {
        return toDate;
    }

    /** 
     * Set the 'to_date' element value.
     * 
     * @param toDate
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /** 
     * Get the 'offset' element value.
     * 
     * @return value
     */
    public int getOffset() {
        return offset;
    }

    /** 
     * Set the 'offset' element value.
     * 
     * @param offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /** 
     * Get the 'max_results' element value.
     * 
     * @return value
     */
    public int getMaxResults() {
        return maxResults;
    }

    /** 
     * Set the 'max_results' element value.
     * 
     * @param maxResults
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
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

    /** 
     * Get the 'ids' element value.
     * 
     * @return value
     */
    public SelectFields getIds() {
        return ids;
    }

    /** 
     * Set the 'ids' element value.
     * 
     * @param ids
     */
    public void setIds(SelectFields ids) {
        this.ids = ids;
    }

    /** 
     * Get the 'relationship_name' element value.
     * 
     * @return value
     */
    public String getRelationshipName() {
        return relationshipName;
    }

    /** 
     * Set the 'relationship_name' element value.
     * 
     * @param relationshipName
     */
    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    /** 
     * Get the 'deletion_date' element value.
     * 
     * @return value
     */
    public String getDeletionDate() {
        return deletionDate;
    }

    /** 
     * Set the 'deletion_date' element value.
     * 
     * @param deletionDate
     */
    public void setDeletionDate(String deletionDate) {
        this.deletionDate = deletionDate;
    }

    /** 
     * Get the 'php_serialize' element value.
     * 
     * @return value
     */
    public int getPhpSerialize() {
        return phpSerialize;
    }

    /** 
     * Set the 'php_serialize' element value.
     * 
     * @param phpSerialize
     */
    public void setPhpSerialize(int phpSerialize) {
        this.phpSerialize = phpSerialize;
    }
}
