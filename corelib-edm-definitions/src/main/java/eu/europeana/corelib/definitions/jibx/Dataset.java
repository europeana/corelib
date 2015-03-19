
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.w3.org/ns/dcat#" xmlns:ns2="http://www.europeana.eu/schemas/edm/" xmlns:ns3="http://www.w3.org/ns/adms#" xmlns:ns4="http://purl.org/dc/terms/" xmlns:ns5="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Dataset">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence>
 *           &lt;xs:element ref="ns2:datasetName" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns2:provider" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns5:identifier" minOccurs="1" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns4:created" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns4:extent" minOccurs="0" maxOccurs="1"/>
 *           &lt;xs:element ref="ns3:status" minOccurs="1" maxOccurs="1"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Dataset extends AboutType
{
    private DatasetName datasetName;
    private Provider provider;
    private List<Identifier> identifierList = new ArrayList<Identifier>();
    private Created created;
    private Extent extent;
    private Status status;

    /** 
     * Get the 'datasetName' element value.
     * 
     * @return value
     */
    public DatasetName getDatasetName() {
        return datasetName;
    }

    /** 
     * Set the 'datasetName' element value.
     * 
     * @param datasetName
     */
    public void setDatasetName(DatasetName datasetName) {
        this.datasetName = datasetName;
    }

    /** 
     * Get the 'provider' element value.
     * 
     * @return value
     */
    public Provider getProvider() {
        return provider;
    }

    /** 
     * Set the 'provider' element value.
     * 
     * @param provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /** 
     * Get the list of 'identifier' element items.
     * 
     * @return list
     */
    public List<Identifier> getIdentifierList() {
        return identifierList;
    }

    /** 
     * Set the list of 'identifier' element items.
     * 
     * @param list
     */
    public void setIdentifierList(List<Identifier> list) {
        identifierList = list;
    }

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
     * Get the 'extent' element value.
     * 
     * @return value
     */
    public Extent getExtent() {
        return extent;
    }

    /** 
     * Set the 'extent' element value.
     * 
     * @param extent
     */
    public void setExtent(Extent extent) {
        this.extent = extent;
    }

    /** 
     * Get the 'status' element value.
     * 
     * @return value
     */
    public Status getStatus() {
        return status;
    }

    /** 
     * Set the 'status' element value.
     * 
     * @param status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
