
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_note_attachment">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="id"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetNoteAttachment
{
    private String session;
    private String id;

    /** 
     * Get the 'session' element value.
     * 
     * @return value
     */
    public String getSession() {
        return session;
    }

    /** 
     * Set the 'session' element value.
     * 
     * @param session
     */
    public void setSession(String session) {
        this.session = session;
    }

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
