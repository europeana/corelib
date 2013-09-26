
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_related_notesResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:get_entry_result" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetRelatedNotesResponse
{
    private GetEntryResult _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public GetEntryResult getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(GetEntryResult _return) {
        this._return = _return;
    }
}
