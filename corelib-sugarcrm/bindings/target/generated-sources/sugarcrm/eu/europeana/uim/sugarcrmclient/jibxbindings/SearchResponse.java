
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="searchResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:contact_detail_array" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SearchResponse
{
    private ContactDetailArray _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public ContactDetailArray getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(ContactDetailArray _return) {
        this._return = _return;
    }
}
