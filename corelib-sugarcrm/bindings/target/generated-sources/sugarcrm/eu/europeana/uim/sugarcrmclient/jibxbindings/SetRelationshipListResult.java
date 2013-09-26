
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_relationship_list_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="created"/>
 *     &lt;xs:element type="xs:int" name="failed"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetRelationshipListResult
{
    private int created;
    private int failed;
    private ErrorValue error;

    /** 
     * Get the 'created' element value.
     * 
     * @return value
     */
    public int getCreated() {
        return created;
    }

    /** 
     * Set the 'created' element value.
     * 
     * @param created
     */
    public void setCreated(int created) {
        this.created = created;
    }

    /** 
     * Get the 'failed' element value.
     * 
     * @return value
     */
    public int getFailed() {
        return failed;
    }

    /** 
     * Set the 'failed' element value.
     * 
     * @param failed
     */
    public void setFailed(int failed) {
        this.failed = failed;
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
