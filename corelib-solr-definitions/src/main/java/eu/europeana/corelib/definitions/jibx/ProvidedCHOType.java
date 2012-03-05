
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://www.w3.org/2002/07/owl#" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ProvidedCHOType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns1:EuropeanaType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns2:sameAs" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element type="ns:ResourceType" name="isNextInSequence" minOccurs="0" maxOccurs="1"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ProvidedCHOType extends EuropeanaType
{
    private List<SameAs> sameAList = new ArrayList<SameAs>();
    private ResourceType isNextInSequence;

    /** 
     * Get the list of 'sameAs' element items.
     * 
     * @return list
     */
    public List<SameAs> getSameAList() {
        return sameAList;
    }

    /** 
     * Set the list of 'sameAs' element items.
     * 
     * @param list
     */
    public void setSameAList(List<SameAs> list) {
        sameAList = list;
    }

    /** 
     * Get the 'isNextInSequence' element value.
     * 
     * @return value
     */
    public ResourceType getIsNextInSequence() {
        return isNextInSequence;
    }

    /** 
     * Set the 'isNextInSequence' element value.
     * 
     * @param isNextInSequence
     */
    public void setIsNextInSequence(ResourceType isNextInSequence) {
        this.isNextInSequence = isNextInSequence;
    }
}
