
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_entries_detailsResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:set_entries_detail_result" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetEntriesDetailsResponse
{
    private SetEntriesDetailResult _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public SetEntriesDetailResult getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(SetEntriesDetailResult _return) {
        this._return = _return;
    }
}
