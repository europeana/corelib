
package eu.europeana.corelib.definitions.jibx;

/** 
 *  This class comprises people, either individually or in groups, who have the potential to perform intentional actions for which they can be held responsible. Example:Leonardo da
 Vinci, the British Museum, W3C
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AgentType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns1:ContextualTemporalResourceType">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:ResourceType" name="timeSpan" minOccurs="0" maxOccurs="1"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class AgentType extends ContextualTemporalResourceType
{
    private ResourceType timeSpan;

    /** 
     * Get the 'timeSpan' element value.
     * 
     * @return value
     */
    public ResourceType getTimeSpan() {
        return timeSpan;
    }

    /** 
     * Set the 'timeSpan' element value.
     * 
     * @param timeSpan
     */
    public void setTimeSpan(ResourceType timeSpan) {
        this.timeSpan = timeSpan;
    }
}
