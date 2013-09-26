
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="contact_detail">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="email_address"/>
 *     &lt;xs:element type="xs:string" name="name1"/>
 *     &lt;xs:element type="xs:string" name="name2"/>
 *     &lt;xs:element type="xs:string" name="association"/>
 *     &lt;xs:element type="xs:string" name="id"/>
 *     &lt;xs:element type="xs:string" name="msi_id"/>
 *     &lt;xs:element type="xs:string" name="type"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ContactDetail
{
    private String emailAddress;
    private String name1;
    private String name2;
    private String association;
    private String id;
    private String msiId;
    private String type;

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
     * Get the 'name1' element value.
     * 
     * @return value
     */
    public String getName1() {
        return name1;
    }

    /** 
     * Set the 'name1' element value.
     * 
     * @param name1
     */
    public void setName1(String name1) {
        this.name1 = name1;
    }

    /** 
     * Get the 'name2' element value.
     * 
     * @return value
     */
    public String getName2() {
        return name2;
    }

    /** 
     * Set the 'name2' element value.
     * 
     * @param name2
     */
    public void setName2(String name2) {
        this.name2 = name2;
    }

    /** 
     * Get the 'association' element value.
     * 
     * @return value
     */
    public String getAssociation() {
        return association;
    }

    /** 
     * Set the 'association' element value.
     * 
     * @param association
     */
    public void setAssociation(String association) {
        this.association = association;
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
     * Get the 'msi_id' element value.
     * 
     * @return value
     */
    public String getMsiId() {
        return msiId;
    }

    /** 
     * Set the 'msi_id' element value.
     * 
     * @param msiId
     */
    public void setMsiId(String msiId) {
        this.msiId = msiId;
    }

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public String getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
