
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_sync_result_encoded">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="result"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetSyncResultEncoded
{
    private String result;
    private ErrorValue error;

    /** 
     * Get the 'result' element value.
     * 
     * @return value
     */
    public String getResult() {
        return result;
    }

    /** 
     * Set the 'result' element value.
     * 
     * @param result
     */
    public void setResult(String result) {
        this.result = result;
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
