
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_entriesResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:set_entries_result" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetEntriesResponse
{
    private SetEntriesResult _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public SetEntriesResult getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(SetEntriesResult _return) {
        this._return = _return;
    }
}
