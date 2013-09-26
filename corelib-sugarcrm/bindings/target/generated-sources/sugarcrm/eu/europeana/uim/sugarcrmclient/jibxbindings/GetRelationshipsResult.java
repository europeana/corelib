
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_relationships_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:ids_mods" name="ids"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetRelationshipsResult
{
    private IdsMods ids;
    private ErrorValue error;

    /** 
     * Get the 'ids' element value.
     * 
     * @return value
     */
    public IdsMods getIds() {
        return ids;
    }

    /** 
     * Set the 'ids' element value.
     * 
     * @param ids
     */
    public void setIds(IdsMods ids) {
        this.ids = ids;
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
