
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:ns2="http://purl.org/dc/terms/" xmlns:ns3="http://www.w3.org/ns/adms#" xmlns:ns4="http://www.w3.org/ns/dcat#" xmlns:ns5="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Dataset">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence>
 *           &lt;xs:element ref="ns5:datasetName" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns5:provider" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns5:intermediateProvider" minOccurs="0" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns5:dataProvider" minOccurs="0" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns5:country" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns5:language" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:identifier" minOccurs="1" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns1:description" minOccurs="0" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns2:created" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns2:extent" minOccurs="0" maxOccurs="1"/>
 *           &lt;xs:element ref="ns2:modified" minOccurs="0" maxOccurs="1"/>
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
    private List<IntermediateProvider> intermediateProviderList = new ArrayList<IntermediateProvider>();
    private List<DataProvider> dataProviderList = new ArrayList<DataProvider>();
    private Country country;
    private Language1 language;
    private List<Identifier> identifierList = new ArrayList<Identifier>();
    private List<Description> descriptionList = new ArrayList<Description>();
    private Created created;
    private Extent extent;
    private Modified modified;
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
     * Get the list of 'intermediateProvider' element items.
     * 
     * @return list
     */
    public List<IntermediateProvider> getIntermediateProviderList() {
        return intermediateProviderList;
    }

    /** 
     * Set the list of 'intermediateProvider' element items.
     * 
     * @param list
     */
    public void setIntermediateProviderList(List<IntermediateProvider> list) {
        intermediateProviderList = list;
    }

    /** 
     * Get the list of 'dataProvider' element items.
     * 
     * @return list
     */
    public List<DataProvider> getDataProviderList() {
        return dataProviderList;
    }

    /** 
     * Set the list of 'dataProvider' element items.
     * 
     * @param list
     */
    public void setDataProviderList(List<DataProvider> list) {
        dataProviderList = list;
    }

    /** 
     * Get the 'country' element value.
     * 
     * @return value
     */
    public Country getCountry() {
        return country;
    }

    /** 
     * Set the 'country' element value.
     * 
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /** 
     * Get the 'language' element value.
     * 
     * @return value
     */
    public Language1 getLanguage() {
        return language;
    }

    /** 
     * Set the 'language' element value.
     * 
     * @param language
     */
    public void setLanguage(Language1 language) {
        this.language = language;
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
     * Get the list of 'description' element items.
     * 
     * @return list
     */
    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    /** 
     * Set the list of 'description' element items.
     * 
     * @param list
     */
    public void setDescriptionList(List<Description> list) {
        descriptionList = list;
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
     * Get the 'modified' element value.
     * 
     * @return value
     */
    public Modified getModified() {
        return modified;
    }

    /** 
     * Set the 'modified' element value.
     * 
     * @param modified
     */
    public void setModified(Modified modified) {
        this.modified = modified;
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
