
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="id_mod">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="id"/>
 *     &lt;xs:element type="xs:string" name="date_modified"/>
 *     &lt;xs:element type="xs:int" name="deleted"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class IdMod
{
    private String id;
    private String dateModified;
    private int deleted;

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
     * Get the 'date_modified' element value.
     * 
     * @return value
     */
    public String getDateModified() {
        return dateModified;
    }

    /** 
     * Set the 'date_modified' element value.
     * 
     * @param dateModified
     */
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    /** 
     * Get the 'deleted' element value.
     * 
     * @return value
     */
    public int getDeleted() {
        return deleted;
    }

    /** 
     * Set the 'deleted' element value.
     * 
     * @param deleted
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
