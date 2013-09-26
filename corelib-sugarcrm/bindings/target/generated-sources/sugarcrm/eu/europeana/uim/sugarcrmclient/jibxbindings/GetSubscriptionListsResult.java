
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_subscription_lists_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:newsletter_list" name="unsubscribed"/>
 *     &lt;xs:element type="ns:newsletter_list" name="subscribed"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetSubscriptionListsResult
{
    private NewsletterList unsubscribed;
    private NewsletterList subscribed;
    private ErrorValue error;

    /** 
     * Get the 'unsubscribed' element value.
     * 
     * @return value
     */
    public NewsletterList getUnsubscribed() {
        return unsubscribed;
    }

    /** 
     * Set the 'unsubscribed' element value.
     * 
     * @param unsubscribed
     */
    public void setUnsubscribed(NewsletterList unsubscribed) {
        this.unsubscribed = unsubscribed;
    }

    /** 
     * Get the 'subscribed' element value.
     * 
     * @return value
     */
    public NewsletterList getSubscribed() {
        return subscribed;
    }

    /** 
     * Set the 'subscribed' element value.
     * 
     * @param subscribed
     */
    public void setSubscribed(NewsletterList subscribed) {
        this.subscribed = subscribed;
    }

    /** 
     * Get the 'error' element value.
     * 
     * @return value
     */
    public ErrorValue getError() {
        return error;
    }

    /** 
     * Set the 'error' element value.
     * 
     * @param error
     */
    public void setError(ErrorValue error) {
        this.error = error;
    }
}
