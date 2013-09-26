
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_relationship">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="ns:set_relationship_value" name="set_relationship_value"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetRelationship
{
    private String session;
    private SetRelationshipValue setRelationshipValue;

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
     * Get the 'set_relationship_value' element value.
     * 
     * @return value
     */
    public SetRelationshipValue getSetRelationshipValue() {
        return setRelationshipValue;
    }

    /** 
     * Set the 'set_relationship_value' element value.
     * 
     * @param setRelationshipValue
     */
    public void setSetRelationshipValue(
            SetRelationshipValue setRelationshipValue) {
        this.setRelationshipValue = setRelationshipValue;
    }
}
