
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_relationships">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="ns:set_relationship_list" name="set_relationship_list"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetRelationships
{
    private String session;
    private SetRelationshipList setRelationshipList;

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
     * Get the 'set_relationship_list' element value.
     * 
     * @return value
     */
    public SetRelationshipList getSetRelationshipList() {
        return setRelationshipList;
    }

    /** 
     * Set the 'set_relationship_list' element value.
     * 
     * @param setRelationshipList
     */
    public void setSetRelationshipList(SetRelationshipList setRelationshipList) {
        this.setRelationshipList = setRelationshipList;
    }
}
