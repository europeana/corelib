
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="login">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:user_auth" name="user_auth"/>
 *     &lt;xs:element type="xs:string" name="application_name"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Login
{
    private UserAuth userAuth;
    private String applicationName;

    /** 
     * Get the 'user_auth' element value.
     * 
     * @return value
     */
    public UserAuth getUserAuth() {
        return userAuth;
    }

    /** 
     * Set the 'user_auth' element value.
     * 
     * @param userAuth
     */
    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    /** 
     * Get the 'application_name' element value.
     * 
     * @return value
     */
    public String getApplicationName() {
        return applicationName;
    }

    /** 
     * Set the 'application_name' element value.
     * 
     * @param applicationName
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
