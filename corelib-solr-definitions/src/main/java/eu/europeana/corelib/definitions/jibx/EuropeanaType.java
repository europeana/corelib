
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 EuropeanaType contains the Europeana Properties in addition to DC.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="EuropeanaType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns2:DCType">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns1:EdmType" name="type" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element type="ns:ResourceType" name="currentLocation" minOccurs="0" maxOccurs="1"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class EuropeanaType extends DCType
{
    private EdmType type;
    private ResourceType currentLocation;

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public EdmType getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(EdmType type) {
        this.type = type;
    }

    /** 
     * Get the 'currentLocation' element value.
     * 
     * @return value
     */
    public ResourceType getCurrentLocation() {
        return currentLocation;
    }

    /** 
     * Set the 'currentLocation' element value.
     * 
     * @param currentLocation
     */
    public void setCurrentLocation(ResourceType currentLocation) {
        this.currentLocation = currentLocation;
    }
}
