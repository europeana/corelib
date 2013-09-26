
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_set_relationship_list_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="created"/>
 *     &lt;xs:element type="xs:int" name="failed"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewSetRelationshipListResult
{
    private int created;
    private int failed;

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
}
