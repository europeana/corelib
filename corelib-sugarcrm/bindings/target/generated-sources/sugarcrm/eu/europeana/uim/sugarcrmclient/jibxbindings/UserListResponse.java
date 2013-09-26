
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="user_listResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:user_detail_array" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class UserListResponse
{
    private UserDetailArray _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public UserDetailArray getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(UserDetailArray _return) {
        this._return = _return;
    }
}
