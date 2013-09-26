
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_set_entry_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="id"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewSetEntryResult
{
    private String id;

    /** 
     * Get the 'id' element value.
     * 
     * @return value
     */
    public String getId() {
        return id;
    }

    /** 
     * Set the 'id' element value.
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}
