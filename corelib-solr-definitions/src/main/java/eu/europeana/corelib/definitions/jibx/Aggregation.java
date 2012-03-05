
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://purl.org/dc/elements/1.1/" xmlns:ns3="http://www.openarchives.org/ore/terms/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Aggregation">
 *   &lt;xs:complexType>
 *     &lt;xs:complexContent>
 *       &lt;xs:extension base="ns:AboutType">
 *         &lt;xs:sequence>
 *           &lt;xs:element ref="ns1:aggregatedCHO" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:hasView" minOccurs="0" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns1:dataProvider" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:provider" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns2:rights" minOccurs="0" maxOccurs="unbounded"/>
 *           &lt;xs:element ref="ns1:rights" minOccurs="1" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:isShownBy" minOccurs="0" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:isShownAt" minOccurs="0" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:object" minOccurs="0" maxOccurs="1"/>
 *           &lt;xs:element ref="ns1:ugc" minOccurs="0" maxOccurs="1"/>
 *         &lt;/xs:sequence>
 *       &lt;/xs:extension>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Aggregation extends AboutType
{
    private AggregatedCHO aggregatedCHO;
    private List<HasView> hasViewList = new ArrayList<HasView>();
    private DataProvider dataProvider;
    private Provider provider;
    private List<Rights1> rightList = new ArrayList<Rights1>();
    private Rights rights;
    private IsShownBy isShownBy;
    private IsShownAt isShownAt;
    private _Object object;
    private Ugc ugc;

    /** 
     * Get the 'aggregatedCHO' element value.
     * 
     * @return value
     */
    public AggregatedCHO getAggregatedCHO() {
        return aggregatedCHO;
    }

    /** 
     * Set the 'aggregatedCHO' element value.
     * 
     * @param aggregatedCHO
     */
    public void setAggregatedCHO(AggregatedCHO aggregatedCHO) {
        this.aggregatedCHO = aggregatedCHO;
    }

    /** 
     * Get the list of 'hasView' element items.
     * 
     * @return list
     */
    public List<HasView> getHasViewList() {
        return hasViewList;
    }

    /** 
     * Set the list of 'hasView' element items.
     * 
     * @param list
     */
    public void setHasViewList(List<HasView> list) {
        hasViewList = list;
    }

    /** 
     * Get the 'dataProvider' element value.
     * 
     * @return value
     */
    public DataProvider getDataProvider() {
        return dataProvider;
    }

    /** 
     * Set the 'dataProvider' element value.
     * 
     * @param dataProvider
     */
    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
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
     * Get the list of 'rights' element items.
     * 
     * @return list
     */
    public List<Rights1> getRightList() {
        return rightList;
    }

    /** 
     * Set the list of 'rights' element items.
     * 
     * @param list
     */
    public void setRightList(List<Rights1> list) {
        rightList = list;
    }

    /** 
     * Get the 'rights' element value.
     * 
     * @return value
     */
    public Rights getRights() {
        return rights;
    }

    /** 
     * Set the 'rights' element value.
     * 
     * @param rights
     */
    public void setRights(Rights rights) {
        this.rights = rights;
    }

    /** 
     * Get the 'isShownBy' element value.
     * 
     * @return value
     */
    public IsShownBy getIsShownBy() {
        return isShownBy;
    }

    /** 
     * Set the 'isShownBy' element value.
     * 
     * @param isShownBy
     */
    public void setIsShownBy(IsShownBy isShownBy) {
        this.isShownBy = isShownBy;
    }

    /** 
     * Get the 'isShownAt' element value.
     * 
     * @return value
     */
    public IsShownAt getIsShownAt() {
        return isShownAt;
    }

    /** 
     * Set the 'isShownAt' element value.
     * 
     * @param isShownAt
     */
    public void setIsShownAt(IsShownAt isShownAt) {
        this.isShownAt = isShownAt;
    }

    /** 
     * Get the 'object' element value.
     * 
     * @return value
     */
    public _Object getObject() {
        return object;
    }

    /** 
     * Set the 'object' element value.
     * 
     * @param object
     */
    public void setObject(_Object object) {
        this.object = object;
    }

    /** 
     * Get the 'ugc' element value.
     * 
     * @return value
     */
    public Ugc getUgc() {
        return ugc;
    }

    /** 
     * Set the 'ugc' element value.
     * 
     * @param ugc
     */
    public void setUgc(Ugc ugc) {
        this.ugc = ugc;
    }
}
