
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="link_name_value">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="name"/>
 *     &lt;xs:element type="ns:link_array_list" name="records"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class LinkNameValue
{
    private String name;
    private LinkArrayList records;

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
     * Get the 'records' element value.
     * 
     * @return value
     */
    public LinkArrayList getRecords() {
        return records;
    }

    /** 
     * Set the 'records' element value.
     * 
     * @param records
     */
    public void setRecords(LinkArrayList records) {
        this.records = records;
    }
}
