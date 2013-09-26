
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="return_note_attachment">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:note_attachment" name="note_attachment"/>
 *     &lt;xs:element type="ns:error_value" name="error"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ReturnNoteAttachment
{
    private NoteAttachment noteAttachment;
    private ErrorValue error;

    /** 
     * Get the 'note_attachment' element value.
     * 
     * @return value
     */
    public NoteAttachment getNoteAttachment() {
        return noteAttachment;
    }

    /** 
     * Set the 'note_attachment' element value.
     * 
     * @param noteAttachment
     */
    public void setNoteAttachment(NoteAttachment noteAttachment) {
        this.noteAttachment = noteAttachment;
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
