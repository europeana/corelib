
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="set_campaign_merge">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="ns:select_fields" name="targets"/>
 *     &lt;xs:element type="xs:string" name="campaign_id"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SetCampaignMerge
{
    private String session;
    private SelectFields targets;
    private String campaignId;

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
     * Get the 'targets' element value.
     * 
     * @return value
     */
    public SelectFields getTargets() {
        return targets;
    }

    /** 
     * Set the 'targets' element value.
     * 
     * @param targets
     */
    public void setTargets(SelectFields targets) {
        this.targets = targets;
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
}
