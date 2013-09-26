
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="create_lead">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="user_name"/>
 *     &lt;xs:element type="xs:string" name="password"/>
 *     &lt;xs:element type="xs:string" name="first_name"/>
 *     &lt;xs:element type="xs:string" name="last_name"/>
 *     &lt;xs:element type="xs:string" name="email_address"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class CreateLead
{
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;

    /** 
     * Get the 'user_name' element value.
     * 
     * @return value
     */
    public String getUserName() {
        return userName;
    }

    /** 
     * Set the 'user_name' element value.
     * 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 
     * Get the 'password' element value.
     * 
     * @return value
     */
    public String getPassword() {
        return password;
    }

    /** 
     * Set the 'password' element value.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** 
     * Get the 'first_name' element value.
     * 
     * @return value
     */
    public String getFirstName() {
        return firstName;
    }

    /** 
     * Set the 'first_name' element value.
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** 
     * Get the 'last_name' element value.
     * 
     * @return value
     */
    public String getLastName() {
        return lastName;
    }

    /** 
     * Set the 'last_name' element value.
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** 
     * Get the 'email_address' element value.
     * 
     * @return value
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /** 
     * Set the 'email_address' element value.
     * 
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
