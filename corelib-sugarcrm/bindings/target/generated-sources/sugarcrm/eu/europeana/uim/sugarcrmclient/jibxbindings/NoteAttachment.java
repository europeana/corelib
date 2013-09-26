
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="note_attachment">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="id"/>
 *     &lt;xs:element type="xs:string" name="filename"/>
 *     &lt;xs:element type="xs:string" name="file"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NoteAttachment
{
    private String id;
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
