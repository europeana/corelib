
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entry_list_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="result_count"/>
 *     &lt;xs:element type="xs:int" name="next_offset" minOccurs="0"/>
 *     &lt;xs:element type="ns:field_list" name="field_list" minOccurs="0"/>
 *     &lt;xs:element type="ns:entry_list" name="entry_list" minOccurs="0"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntryListResult
{
    private int resultCount;
    private Integer nextOffset;
    private FieldList fieldList;
    private EntryList entryList;
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
     * Get the 'field_list' element value.
     * 
     * @return value
     */
    public FieldList getFieldList() {
        return fieldList;
    }

    /** 
     * Set the 'field_list' element value.
     * 
     * @param fieldList
     */
    public void setFieldList(FieldList fieldList) {
        this.fieldList = fieldList;
    }

    /** 
     * Get the 'entry_list' element value.
     * 
     * @return value
     */
    public EntryList getEntryList() {
        return entryList;
    }

    /** 
     * Set the 'entry_list' element value.
     * 
     * @param entryList
     */
    public void setEntryList(EntryList entryList) {
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
