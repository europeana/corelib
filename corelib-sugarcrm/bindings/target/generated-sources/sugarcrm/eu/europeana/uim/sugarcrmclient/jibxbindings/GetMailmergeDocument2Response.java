
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_mailmerge_document2Response">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:get_mailmerge_document_result" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetMailmergeDocument2Response
{
    private GetMailmergeDocumentResult _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public GetMailmergeDocumentResult getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(GetMailmergeDocumentResult _return) {
        this._return = _return;
    }
}
