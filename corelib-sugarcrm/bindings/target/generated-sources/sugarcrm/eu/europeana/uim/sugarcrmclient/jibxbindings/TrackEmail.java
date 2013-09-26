
package eu.europeana.uim.sugarcrmclient.jibxbindings;

import java.sql.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="track_email">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="user_name"/>
 *     &lt;xs:element type="xs:string" name="password"/>
 *     &lt;xs:element type="xs:string" name="parent_id"/>
 *     &lt;xs:element type="xs:string" name="contact_ids"/>
 *     &lt;xs:element type="xs:date" name="date_sent"/>
 *     &lt;xs:element type="xs:string" name="email_subject"/>
 *     &lt;xs:element type="xs:string" name="email_body"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TrackEmail
{
    private String userName;
    private String password;
    private String parentId;
    private String contactIds;
    private Date dateSent;
    private String emailSubject;
    private String emailBody;

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
     * Get the 'parent_id' element value.
     * 
     * @return value
     */
    public String getParentId() {
        return parentId;
    }

    /** 
     * Set the 'parent_id' element value.
     * 
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /** 
     * Get the 'contact_ids' element value.
     * 
     * @return value
     */
    public String getContactIds() {
        return contactIds;
    }

    /** 
     * Set the 'contact_ids' element value.
     * 
     * @param contactIds
     */
    public void setContactIds(String contactIds) {
        this.contactIds = contactIds;
    }

    /** 
     * Get the 'date_sent' element value.
     * 
     * @return value
     */
    public Date getDateSent() {
        return dateSent;
    }

    /** 
     * Set the 'date_sent' element value.
     * 
     * @param dateSent
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /** 
     * Get the 'email_subject' element value.
     * 
     * @return value
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /** 
     * Set the 'email_subject' element value.
     * 
     * @param emailSubject
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /** 
     * Get the 'email_body' element value.
     * 
     * @return value
     */
    public String getEmailBody() {
        return emailBody;
    }

    /** 
     * Set the 'email_body' element value.
     * 
     * @param emailBody
     */
    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}
