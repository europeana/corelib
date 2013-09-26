
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_available_modulesResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:module_list" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetAvailableModulesResponse
{
    private ModuleList _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public ModuleList getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(ModuleList _return) {
        this._return = _return;
    }
}
