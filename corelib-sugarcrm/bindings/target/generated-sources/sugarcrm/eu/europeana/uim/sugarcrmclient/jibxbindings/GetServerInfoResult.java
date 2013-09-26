
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_server_info_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="flavor"/>
 *     &lt;xs:element type="xs:string" name="version"/>
 *     &lt;xs:element type="xs:string" name="gmt_time"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetServerInfoResult
{
    private String flavor;
    private String version;
    private String gmtTime;

    /** 
     * Get the 'flavor' element value.
     * 
     * @return value
     */
    public String getFlavor() {
        return flavor;
    }

    /** 
     * Set the 'flavor' element value.
     * 
     * @param flavor
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /** 
     * Get the 'version' element value.
     * 
     * @return value
     */
    public String getVersion() {
        return version;
    }

    /** 
     * Set the 'version' element value.
     * 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /** 
     * Get the 'gmt_time' element value.
     * 
     * @return value
     */
    public String getGmtTime() {
        return gmtTime;
    }

    /** 
     * Set the 'gmt_time' element value.
     * 
     * @param gmtTime
     */
    public void setGmtTime(String gmtTime) {
        this.gmtTime = gmtTime;
    }
}
