
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_mailmerge_document_result">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="html"/>
 *     &lt;xs:element type="ns:name_value_list" name="name_value_list"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetMailmergeDocumentResult
{
    private String html;
    private NameValueList nameValueList;
    private ErrorValue error;

    /** 
     * Get the 'html' element value.
     * 
     * @return value
     */
    public String getHtml() {
        return html;
    }

    /** 
     * Set the 'html' element value.
     * 
     * @param html
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /** 
     * Get the 'name_value_list' element value.
     * 
     * @return value
     */
    public NameValueList getNameValueList() {
        return nameValueList;
    }

    /** 
     * Set the 'name_value_list' element value.
     * 
     * @param nameValueList
     */
    public void setNameValueList(NameValueList nameValueList) {
        this.nameValueList = nameValueList;
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
