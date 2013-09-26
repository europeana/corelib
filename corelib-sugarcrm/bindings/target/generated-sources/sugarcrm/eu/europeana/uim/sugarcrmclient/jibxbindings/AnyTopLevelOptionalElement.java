
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * 
 Any top level optional element allowed to appear more then once - any child of definitions element except wsdl:types. Any extensibility element is allowed in any place.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:group xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="anyTopLevelOptionalElement">
 *   &lt;xs:choice>
 *     &lt;xs:element type="ns:tImport" name="import"/>
 *     &lt;xs:element type="ns:tTypes" name="types"/>
 *     &lt;xs:element type="ns:tMessage" name="message">
 *       &lt;xs:unique name="part">
 *         &lt;xs:selector xpath="wsdl:part"/>
 *         &lt;xs:field xpath="@name"/>
 *       &lt;/xs:unique>
 *     &lt;/xs:element>
 *     &lt;xs:element type="ns:tPortType" name="portType"/>
 *     &lt;xs:element type="ns:tBinding" name="binding"/>
 *     &lt;xs:element type="ns:tService" name="service">
 *       &lt;xs:unique name="port">
 *         &lt;xs:selector xpath="wsdl:port"/>
 *         &lt;xs:field xpath="@name"/>
 *       &lt;/xs:unique>
 *     &lt;/xs:element>
 *   &lt;/xs:choice>
 * &lt;/xs:group>
 * </pre>
 */
public class AnyTopLevelOptionalElement
{
    private int choiceSelect = -1;
    private static final int IMPORT_CHOICE = 0;
    private static final int TYPES_CHOICE = 1;
    private static final int MESSAGE_CHOICE = 2;
    private static final int PORT_TYPE_CHOICE = 3;
    private static final int BINDING_CHOICE = 4;
    private static final int SERVICE_CHOICE = 5;
    private TImport _import;
    private TTypes types;
    private TMessage message;
    private TPortType portType;
    private TBinding binding;
    private TService service;

    private void setChoiceSelect(int choice) {
        if (choiceSelect == -1) {
            choiceSelect = choice;
        } else if (choiceSelect != choice) {
            throw new IllegalStateException(
                    "Need to call clearChoiceSelect() before changing existing choice");
        }
    }

    /** 
     * Clear the choice selection.
     */
    public void clearChoiceSelect() {
        choiceSelect = -1;
    }

    /** 
     * Check if Import is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifImport() {
        return choiceSelect == IMPORT_CHOICE;
    }

    /** 
     * Get the 'import' element value.
     * 
     * @return value
     */
    public TImport getImport() {
        return _import;
    }

    /** 
     * Set the 'import' element value.
     * 
     * @param _import
     */
    public void setImport(TImport _import) {
        setChoiceSelect(IMPORT_CHOICE);
        this._import = _import;
    }

    /** 
     * Check if Types is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifTypes() {
        return choiceSelect == TYPES_CHOICE;
    }

    /** 
     * Get the 'types' element value.
     * 
     * @return value
     */
    public TTypes getTypes() {
        return types;
    }

    /** 
     * Set the 'types' element value.
     * 
     * @param types
     */
    public void setTypes(TTypes types) {
        setChoiceSelect(TYPES_CHOICE);
        this.types = types;
    }

    /** 
     * Check if Message is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifMessage() {
        return choiceSelect == MESSAGE_CHOICE;
    }

    /** 
     * Get the 'message' element value.
     * 
     * @return value
     */
    public TMessage getMessage() {
        return message;
    }

    /** 
     * Set the 'message' element value.
     * 
     * @param message
     */
    public void setMessage(TMessage message) {
        setChoiceSelect(MESSAGE_CHOICE);
        this.message = message;
    }

    /** 
     * Check if PortType is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifPortType() {
        return choiceSelect == PORT_TYPE_CHOICE;
    }

    /** 
     * Get the 'portType' element value.
     * 
     * @return value
     */
    public TPortType getPortType() {
        return portType;
    }

    /** 
     * Set the 'portType' element value.
     * 
     * @param portType
     */
    public void setPortType(TPortType portType) {
        setChoiceSelect(PORT_TYPE_CHOICE);
        this.portType = portType;
    }

    /** 
     * Check if Binding is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifBinding() {
        return choiceSelect == BINDING_CHOICE;
    }

    /** 
     * Get the 'binding' element value.
     * 
     * @return value
     */
    public TBinding getBinding() {
        return binding;
    }

    /** 
     * Set the 'binding' element value.
     * 
     * @param binding
     */
    public void setBinding(TBinding binding) {
        setChoiceSelect(BINDING_CHOICE);
        this.binding = binding;
    }

    /** 
     * Check if Service is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifService() {
        return choiceSelect == SERVICE_CHOICE;
    }

    /** 
     * Get the 'service' element value.
     * 
     * @return value
     */
    public TService getService() {
        return service;
    }

    /** 
     * Set the 'service' element value.
     * 
     * @param service
     */
    public void setService(TService service) {
        setChoiceSelect(SERVICE_CHOICE);
        this.service = service;
    }
}
