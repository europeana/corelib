
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:ns2="http://www.w3.org/ns/oa#" xmlns:ns3="http://www.w3.org/ns/dqv#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="QualityAnnotation">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence minOccurs="1" maxOccurs="1">
 *           &lt;xs:element ref="ns1:created" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns2:hasTarget" minOccurs="1" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns2:hasBody" minOccurs="1" maxOccurs="1"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class QualityAnnotation extends AboutType
{
    private Created created;
    private List<HasTarget> hasTargetList = new ArrayList<HasTarget>();
    private HasBody hasBody;

    /** 
     * Get the 'created' element value.
     * 
     * @return value
     */
    public Created getCreated() {
        return created;
    }

    /** 
     * Set the 'created' element value.
     * 
     * @param created
     */
    public void setCreated(Created created) {
        this.created = created;
    }

    /** 
     * Get the list of 'hasTarget' element items.
     * 
     * @return list
     */
    public List<HasTarget> getHasTargetList() {
        return hasTargetList;
    }

    /** 
     * Set the list of 'hasTarget' element items.
     * 
     * @param list
     */
    public void setHasTargetList(List<HasTarget> list) {
        hasTargetList = list;
    }

    /** 
     * Get the 'hasBody' element value.
     * 
     * @return value
     */
    public HasBody getHasBody() {
        return hasBody;
    }

    /** 
     * Set the 'hasBody' element value.
     * 
     * @param hasBody
     */
    public void setHasBody(HasBody hasBody) {
        this.hasBody = hasBody;
    }
}
