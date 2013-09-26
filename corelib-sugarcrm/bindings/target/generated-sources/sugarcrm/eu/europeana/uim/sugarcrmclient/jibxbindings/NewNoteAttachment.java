
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_note_attachment">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="id"/>
 *     &lt;xs:element type="xs:string" name="filename"/>
 *     &lt;xs:element type="xs:string" name="file"/>
 *     &lt;xs:element type="xs:string" name="related_module_id"/>
 *     &lt;xs:element type="xs:string" name="related_module_name"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewNoteAttachment
{
    private String id;
    private String filename;
    private String file;
    private String relatedModuleId;
    private String relatedModuleName;

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

    /** 
     * Get the 'related_module_id' element value.
     * 
     * @return value
     */
    public String getRelatedModuleId() {
        return relatedModuleId;
    }

    /** 
     * Set the 'related_module_id' element value.
     * 
     * @param relatedModuleId
     */
    public void setRelatedModuleId(String relatedModuleId) {
        this.relatedModuleId = relatedModuleId;
    }

    /** 
     * Get the 'related_module_name' element value.
     * 
     * @return value
     */
    public String getRelatedModuleName() {
        return relatedModuleName;
    }

    /** 
     * Set the 'related_module_name' element value.
     * 
     * @param relatedModuleName
     */
    public void setRelatedModuleName(String relatedModuleName) {
        this.relatedModuleName = relatedModuleName;
    }
}
