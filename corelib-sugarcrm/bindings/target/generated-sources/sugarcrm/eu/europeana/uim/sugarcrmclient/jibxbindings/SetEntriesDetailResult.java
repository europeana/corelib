
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_entries_detail_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:name_value_lists" name="name_value_lists"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetEntriesDetailResult
{
    private NameValueLists nameValueLists;
    private ErrorValue error;

    /** 
     * Get the 'name_value_lists' element value.
     * 
     * @return value
     */
    public NameValueLists getNameValueLists() {
        return nameValueLists;
    }

    /** 
     * Set the 'name_value_lists' element value.
     * 
     * @param nameValueLists
     */
    public void setNameValueLists(NameValueLists nameValueLists) {
        this.nameValueLists = nameValueLists;
    }

    /** 
     * Get the 'error' element value.
     * 
     * @return value
     */
    public ErrorValue getError() {
        return error;
    }

    /** 
     * Set the 'error' element value.
     * 
     * @param error
     */
    public void setError(ErrorValue error) {
        this.error = error;
    }
}
