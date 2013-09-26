
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_attendee_listResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:get_sync_result_encoded" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetAttendeeListResponse
{
    private GetSyncResultEncoded _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public GetSyncResultEncoded getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(GetSyncResultEncoded _return) {
        this._return = _return;
    }
}
