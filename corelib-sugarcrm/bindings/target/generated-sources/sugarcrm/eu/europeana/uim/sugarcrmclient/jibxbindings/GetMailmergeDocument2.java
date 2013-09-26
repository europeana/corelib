
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_mailmerge_document2">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="file_name"/>
 *     &lt;xs:element type="ns:select_fields" name="fields"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetMailmergeDocument2
{
    private String session;
    private String fileName;
    private SelectFields fields;

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
     * Get the 'file_name' element value.
     * 
     * @return value
     */
    public String getFileName() {
        return fileName;
    }

    /** 
     * Set the 'file_name' element value.
     * 
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /** 
     * Get the 'fields' element value.
     * 
     * @return value
     */
    public SelectFields getFields() {
        return fields;
    }

    /** 
     * Set the 'fields' element value.
     * 
     * @param fields
     */
    public void setFields(SelectFields fields) {
        this.fields = fields;
    }
}
