
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="newsletter">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="xs:string" name="prospect_list_id"/>
 *     &lt;xs:element type="xs:string" name="campaign_id"/>
 *     &lt;xs:element type="xs:string" name="description"/>
 *     &lt;xs:element type="xs:string" name="frequency"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Newsletter
{
    private String name;
    private String prospectListId;
    private String campaignId;
    private String description;
    private String frequency;

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
     * Get the 'prospect_list_id' element value.
     * 
     * @return value
     */
    public String getProspectListId() {
        return prospectListId;
    }

    /** 
     * Set the 'prospect_list_id' element value.
     * 
     * @param prospectListId
     */
    public void setProspectListId(String prospectListId) {
        this.prospectListId = prospectListId;
    }

    /** 
     * Get the 'campaign_id' element value.
     * 
     * @return value
     */
    public String getCampaignId() {
        return campaignId;
    }

    /** 
     * Set the 'campaign_id' element value.
     * 
     * @param campaignId
     */
    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    /** 
     * Get the 'description' element value.
     * 
     * @return value
     */
    public String getDescription() {
        return description;
    }

    /** 
     * Set the 'description' element value.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     * Get the 'frequency' element value.
     * 
     * @return value
     */
    public String getFrequency() {
        return frequency;
    }

    /** 
     * Set the 'frequency' element value.
     * 
     * @param frequency
     */
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
