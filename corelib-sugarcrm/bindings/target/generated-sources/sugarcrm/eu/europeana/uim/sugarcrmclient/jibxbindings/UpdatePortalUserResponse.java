
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="update_portal_userResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:error_value" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class UpdatePortalUserResponse
{
    private ErrorValue _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public ErrorValue getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(ErrorValue _return) {
        this._return = _return;
    }
}
