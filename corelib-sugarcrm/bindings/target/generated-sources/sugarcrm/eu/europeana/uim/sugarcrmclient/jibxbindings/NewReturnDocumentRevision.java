
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_return_document_revision">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:document_revision" name="document_revision"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewReturnDocumentRevision
{
    private DocumentRevision documentRevision;

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
}
