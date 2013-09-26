
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_entryResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:set_entry_result" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetEntryResponse
{
    private SetEntryResult _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public SetEntryResult getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(SetEntryResult _return) {
        this._return = _return;
    }
}
