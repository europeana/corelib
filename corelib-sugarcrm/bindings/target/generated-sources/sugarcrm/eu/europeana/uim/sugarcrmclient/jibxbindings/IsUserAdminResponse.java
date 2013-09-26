
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="is_user_adminResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:int" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class IsUserAdminResponse
{
    private int _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public int getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(int _return) {
        this._return = _return;
    }
}
