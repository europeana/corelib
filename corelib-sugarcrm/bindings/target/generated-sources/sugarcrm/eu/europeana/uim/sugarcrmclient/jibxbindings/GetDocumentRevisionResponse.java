
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_document_revisionResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:return_document_revision" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetDocumentRevisionResponse
{
    private ReturnDocumentRevision _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public ReturnDocumentRevision getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(ReturnDocumentRevision _return) {
        this._return = _return;
    }
}
