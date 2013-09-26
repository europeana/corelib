
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entry_list_result_encoded">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="result_count"/>
 *     &lt;xs:element type="xs:int" name="next_offset" minOccurs="0"/>
 *     &lt;xs:element type="xs:int" name="total_count"/>
 *     &lt;xs:element type="ns:select_fields" name="field_list"/>
 *     &lt;xs:element type="xs:string" name="entry_list"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntryListResultEncoded
{
    private int resultCount;
    private Integer nextOffset;
    private int totalCount;
    private SelectFields fieldList;
    private String entryList;
    private ErrorValue error;

    /** 
     * Get the 'result_count' element value.
     * 
     * @return value
     */
    public int getResultCount() {
        return resultCount;
    }

    /** 
     * Set the 'result_count' element value.
     * 
     * @param resultCount
     */
    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    /** 
     * Get the 'next_offset' element value.
     * 
     * @return value
     */
    public Integer getNextOffset() {
        return nextOffset;
    }

    /** 
     * Set the 'next_offset' element value.
     * 
     * @param nextOffset
     */
    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    /** 
     * Get the 'total_count' element value.
     * 
     * @return value
     */
    public int getTotalCount() {
        return totalCount;
    }

    /** 
     * Set the 'total_count' element value.
     * 
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /** 
     * Get the 'field_list' element value.
     * 
     * @return value
     */
    public SelectFields getFieldList() {
        return fieldList;
    }

    /** 
     * Set the 'field_list' element value.
     * 
     * @param fieldList
     */
    public void setFieldList(SelectFields fieldList) {
        this.fieldList = fieldList;
    }

    /** 
     * Get the 'entry_list' element value.
     * 
     * @return value
     */
    public String getEntryList() {
        return entryList;
    }

    /** 
     * Set the 'entry_list' element value.
     * 
     * @param entryList
     */
    public void setEntryList(String entryList) {
        this.entryList = entryList;
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
