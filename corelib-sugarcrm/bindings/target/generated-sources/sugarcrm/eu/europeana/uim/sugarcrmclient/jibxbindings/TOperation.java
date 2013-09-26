
package eu.europeana.uim.sugarcrmclient.jibxbindings;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://schemas.xmlsoap.org/wsdl/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="tOperation">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:tExtensibleDocumented">
 *       &lt;xs:sequence>
 *         &lt;xs:choice>
 *           &lt;xs:group ref="ns:request-response-or-one-way-operation"/>
 *           &lt;xs:group ref="ns:solicit-response-or-notification-operation"/>
 *         &lt;/xs:choice>
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *       &lt;xs:attribute type="xs:string" use="optional" name="parameterOrder"/>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class TOperation extends TExtensibleDocumented
{
    private int choiceSelect = -1;
    private static final int REQUEST_RESPONSE_OR_ONE_WAY_OPERATION_CHOICE = 0;
    private static final int SOLICIT_RESPONSE_OR_NOTIFICATION_OPERATION_CHOICE = 1;
    private RequestResponseOrOneWayOperation requestResponseOrOneWayOperation;
    private SolicitResponseOrNotificationOperation solicitResponseOrNotificationOperation;
    private String name;
    private String parameterOrder;

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
     * Check if RequestResponseOrOneWayOperation is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifRequestResponseOrOneWayOperation() {
        return choiceSelect == REQUEST_RESPONSE_OR_ONE_WAY_OPERATION_CHOICE;
    }

    /** 
     * Get the 'request-response-or-one-way-operation' group value.
     * 
     * @return value
     */
    public RequestResponseOrOneWayOperation getRequestResponseOrOneWayOperation() {
        return requestResponseOrOneWayOperation;
    }

    /** 
     * Set the 'request-response-or-one-way-operation' group value.
     * 
     * @param requestResponseOrOneWayOperation
     */
    public void setRequestResponseOrOneWayOperation(
            RequestResponseOrOneWayOperation requestResponseOrOneWayOperation) {
        setChoiceSelect(REQUEST_RESPONSE_OR_ONE_WAY_OPERATION_CHOICE);
        this.requestResponseOrOneWayOperation = requestResponseOrOneWayOperation;
    }

    /** 
     * Check if SolicitResponseOrNotificationOperation is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifSolicitResponseOrNotificationOperation() {
        return choiceSelect == SOLICIT_RESPONSE_OR_NOTIFICATION_OPERATION_CHOICE;
    }

    /** 
     * Get the 'solicit-response-or-notification-operation' group value.
     * 
     * @return value
     */
    public SolicitResponseOrNotificationOperation getSolicitResponseOrNotificationOperation() {
        return solicitResponseOrNotificationOperation;
    }

    /** 
     * Set the 'solicit-response-or-notification-operation' group value.
     * 
     * @param solicitResponseOrNotificationOperation
     */
    public void setSolicitResponseOrNotificationOperation(
            SolicitResponseOrNotificationOperation solicitResponseOrNotificationOperation) {
        setChoiceSelect(SOLICIT_RESPONSE_OR_NOTIFICATION_OPERATION_CHOICE);
        this.solicitResponseOrNotificationOperation = solicitResponseOrNotificationOperation;
    }

    /** 
     * Get the 'name' attribute value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' attribute value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'parameterOrder' attribute value.
     * 
     * @return value
     */
    public String getParameterOrder() {
        return parameterOrder;
    }

    /** 
     * Set the 'parameterOrder' attribute value.
     * 
     * @param parameterOrder
     */
    public void setParameterOrder(String parameterOrder) {
        this.parameterOrder = parameterOrder;
    }
}
