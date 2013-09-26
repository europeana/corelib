
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="relate_note_to_module">
 *   &lt;xs:all>
 *     &lt;xs:element type="xs:string" name="session"/>
 *     &lt;xs:element type="xs:string" name="note_id"/>
 *     &lt;xs:element type="xs:string" name="module_name"/>
 *     &lt;xs:element type="xs:string" name="module_id"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class RelateNoteToModule
{
    private String session;
    private String noteId;
    private String moduleName;
    private String moduleId;

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
     * Get the 'note_id' element value.
     * 
     * @return value
     */
    public String getNoteId() {
        return noteId;
    }

    /** 
     * Set the 'note_id' element value.
     * 
     * @param noteId
     */
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    /** 
     * Get the 'module_name' element value.
     * 
     * @return value
     */
    public String getModuleName() {
        return moduleName;
    }

    /** 
     * Set the 'module_name' element value.
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /** 
     * Get the 'module_id' element value.
     * 
     * @return value
     */
    public String getModuleId() {
        return moduleId;
    }

    /** 
     * Set the 'module_id' element value.
     * 
     * @param moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
