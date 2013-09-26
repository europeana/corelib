
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_entry_listResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:get_entry_list_result" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetEntryListResponse
{
    private GetEntryListResult _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public GetEntryListResult getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(GetEntryListResult _return) {
        this._return = _return;
    }
}
