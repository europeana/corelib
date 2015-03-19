
package eu.europeana.corelib.definitions.jibx;

/** 
 * A flag indicating that the specific Proxy can be used as a europeanaProxy

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:boolean" name="europeanaProxy"/>
 * </pre>
 */
public class EuropeanaProxy
{
    private boolean europeanaProxy;

    /** 
     * Get the 'europeanaProxy' element value.
     * 
     * @return value
     */
    public boolean isEuropeanaProxy() {
        return europeanaProxy;
    }

    /** 
     * Set the 'europeanaProxy' element value.
     * 
     * @param europeanaProxy
     */
    public void setEuropeanaProxy(boolean europeanaProxy) {
        this.europeanaProxy = europeanaProxy;
    }
}
