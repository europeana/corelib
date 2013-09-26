
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="user_detail">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="email_address"/>
 *     &lt;xs:element type="xs:string" name="user_name"/>
 *     &lt;xs:element type="xs:string" name="first_name"/>
 *     &lt;xs:element type="xs:string" name="last_name"/>
 *     &lt;xs:element type="xs:string" name="department"/>
 *     &lt;xs:element type="xs:string" name="id"/>
 *     &lt;xs:element type="xs:string" name="title"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class UserDetail
{
    private String emailAddress;
    private String userName;
    private String firstName;
    private String lastName;
    private String department;
    private String id;
    private String title;

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
     * Get the 'department' element value.
     * 
     * @return value
     */
    public String getDepartment() {
        return department;
    }

    /** 
     * Set the 'department' element value.
     * 
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
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

    /** 
     * Get the 'title' element value.
     * 
     * @return value
     */
    public String getTitle() {
        return title;
    }

    /** 
     * Set the 'title' element value.
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
