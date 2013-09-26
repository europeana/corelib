
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="document_revision">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="id"/>
 *     &lt;xs:element type="xs:string" name="document_name"/>
 *     &lt;xs:element type="xs:string" name="revision"/>
 *     &lt;xs:element type="xs:string" name="filename"/>
 *     &lt;xs:element type="xs:string" name="file"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class DocumentRevision
{
    private String id;
    private String documentName;
    private String revision;
    private String filename;
    private String file;

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
     * Get the 'document_name' element value.
     * 
     * @return value
     */
    public String getDocumentName() {
        return documentName;
    }

    /** 
     * Set the 'document_name' element value.
     * 
     * @param documentName
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /** 
     * Get the 'revision' element value.
     * 
     * @return value
     */
    public String getRevision() {
        return revision;
    }

    /** 
     * Set the 'revision' element value.
     * 
     * @param revision
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /** 
     * Get the 'filename' element value.
     * 
     * @return value
     */
    public String getFilename() {
        return filename;
    }

    /** 
     * Set the 'filename' element value.
     * 
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /** 
     * Get the 'file' element value.
     * 
     * @return value
     */
    public String getFile() {
        return file;
    }

    /** 
     * Set the 'file' element value.
     * 
     * @param file
     */
    public void setFile(String file) {
        this.file = file;
    }
}
