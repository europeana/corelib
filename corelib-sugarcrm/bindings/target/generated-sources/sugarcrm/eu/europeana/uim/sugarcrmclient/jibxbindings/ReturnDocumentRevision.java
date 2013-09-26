
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="return_document_revision">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:document_revision" name="document_revision"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ReturnDocumentRevision
{
    private DocumentRevision documentRevision;
    private ErrorValue error;

    /** 
     * Get the 'document_revision' element value.
     * 
     * @return value
     */
    public DocumentRevision getDocumentRevision() {
        return documentRevision;
    }

    /** 
     * Set the 'document_revision' element value.
     * 
     * @param documentRevision
     */
    public void setDocumentRevision(DocumentRevision documentRevision) {
        this.documentRevision = documentRevision;
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
