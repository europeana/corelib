
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_set_entries_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:select_fields" name="ids"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewSetEntriesResult
{
    private SelectFields ids;

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
}
