
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entries_count_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="result_count"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntriesCountResult
{
    private int resultCount;
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
