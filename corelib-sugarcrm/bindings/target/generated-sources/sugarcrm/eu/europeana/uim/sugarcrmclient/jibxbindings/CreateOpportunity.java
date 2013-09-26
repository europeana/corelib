
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="create_opportunity">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="user_name"/>
 *     &lt;xs:element type="xs:string" name="password"/>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="xs:string" name="amount"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class CreateOpportunity
{
    private String userName;
    private String password;
    private String name;
    private String amount;

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
     * Get the 'name' element value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'amount' element value.
     * 
     * @return value
     */
    public String getAmount() {
        return amount;
    }

    /** 
     * Set the 'amount' element value.
     * 
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
