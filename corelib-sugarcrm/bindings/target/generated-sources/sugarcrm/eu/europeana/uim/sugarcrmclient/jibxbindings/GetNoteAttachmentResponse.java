
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.sugarcrm.com/sugarcrm" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="get_note_attachmentResponse">
 *   &lt;xs:all>
 *     &lt;xs:element type="ns:return_note_attachment" name="return"/>
 *   &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 */
public class GetNoteAttachmentResponse
{
    private ReturnNoteAttachment _return;

    /** 
     * Get the 'return' element value.
     * 
     * @return value
     */
    public ReturnNoteAttachment getReturn() {
        return _return;
    }

    /** 
     * Set the 'return' element value.
     * 
     * @param _return
     */
    public void setReturn(ReturnNoteAttachment _return) {
        this._return = _return;
    }
}
