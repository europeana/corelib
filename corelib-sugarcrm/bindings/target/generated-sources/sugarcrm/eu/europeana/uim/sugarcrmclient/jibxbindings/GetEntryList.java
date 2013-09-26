
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entry_list">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="xs:string" name="query"/>
 *     &lt;xs:element type="xs:string" name="order_by"/>
 *     &lt;xs:element type="xs:int" name="offset"/>
 *     &lt;xs:element type="ns:select_fields" name="select_fields"/>
 *     &lt;xs:element type="xs:int" name="max_results"/>
 *     &lt;xs:element type="xs:int" name="deleted"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntryList
{
    private String session;
    private String moduleName;
    private String query;
    private String orderBy;
    private int offset;
    private SelectFields selectFields;
    private int maxResults;
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
     * Get the 'query' element value.
     * 
     * @return value
     */
    public String getQuery() {
        return query;
    }

    /** 
     * Set the 'query' element value.
     * 
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /** 
     * Get the 'order_by' element value.
     * 
     * @return value
     */
    public String getOrderBy() {
        return orderBy;
    }

    /** 
     * Set the 'order_by' element value.
     * 
     * @param orderBy
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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
}
