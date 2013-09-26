
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="new_return_note_attachment">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:new_note_attachment" name="note_attachment"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NewReturnNoteAttachment
{
    private NewNoteAttachment noteAttachment;

    /** 
     * Get the 'note_attachment' element value.
     * 
     * @return value
     */
    public NewNoteAttachment getNoteAttachment() {
        return noteAttachment;
    }

    /** 
     * Set the 'note_attachment' element value.
     * 
     * @param noteAttachment
     */
    public void setNoteAttachment(NewNoteAttachment noteAttachment) {
        this.noteAttachment = noteAttachment;
    }
}
