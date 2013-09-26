
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="update_portal_user">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="portal_name"/>
 *     &lt;xs:element type="ns:name_value_list" name="name_value_list"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class UpdatePortalUser
{
    private String session;
    private String portalName;
    private NameValueList nameValueList;

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
     * Get the 'portal_name' element value.
     * 
     * @return value
     */
    public String getPortalName() {
        return portalName;
    }

    /** 
     * Set the 'portal_name' element value.
     * 
     * @param portalName
     */
    public void setPortalName(String portalName) {
        this.portalName = portalName;
    }

    /** 
     * Get the 'name_value_list' element value.
     * 
     * @return value
     */
    public NameValueList getNameValueList() {
        return nameValueList;
    }

    /** 
     * Set the 'name_value_list' element value.
     * 
     * @param nameValueList
     */
    public void setNameValueList(NameValueList nameValueList) {
        this.nameValueList = nameValueList;
    }
}
