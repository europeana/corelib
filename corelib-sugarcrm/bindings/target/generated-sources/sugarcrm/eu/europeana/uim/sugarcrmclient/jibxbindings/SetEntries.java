
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_entries">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="ns:name_value_lists" name="name_value_lists"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetEntries
{
    private String session;
    private String moduleName;
    private NameValueLists nameValueLists;

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
     * Get the 'module_name' element value.
     * 
     * @return value
     */
    public String getModuleName() {
        return moduleName;
    }

    /** 
     * Set the 'module_name' element value.
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

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
}
