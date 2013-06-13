
package eu.europeana.corelib.definitions.jibx;

import java.util.ArrayList;
import java.util.List;

/** 
 *  The set of resources related to a single cultural heritage object that
 collectively represent that object in Europeana. Such set consists of: all
 descriptions about the object that Europeana collects from (possibly different)
 content providers, including thumbnails and other forms of abstractions, as well as
 of the description of the object Europeana builds. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:ns2="http://www.openarchives.org/ore/terms/" xmlns:ns3="http://purl.org/dc/elements/1.1/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="EuropeanaAggregationType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns3:creator" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:aggregatedCHO" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:collectionName" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:country" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:hasView" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns1:isShownBy" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:preview" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:landingPage" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:language" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns1:rights" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:aggregates" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class EuropeanaAggregationType extends AboutType
{
    private Creator creator;
    private AggregatedCHO aggregatedCHO;
    private CollectionName collectionName;
    private Country country;
    private List<HasView> hasViewList = new ArrayList<HasView>();
    private IsShownBy isShownBy;
    private Preview preview;
    private LandingPage landingPage;
    private Language1 language;
    private Rights1 rights;
    private List<Aggregates> aggregateList = new ArrayList<Aggregates>();

    /** 
     * Get the 'creator' element value.
     * 
     * @return value
     */
    public Creator getCreator() {
        return creator;
    }

    /** 
     * Set the 'creator' element value.
     * 
     * @param creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

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
     * Get the 'collectionName' element value.
     * 
     * @return value
     */
    public CollectionName getCollectionName() {
        return collectionName;
    }

    /** 
     * Set the 'collectionName' element value.
     * 
     * @param collectionName
     */
    public void setCollectionName(CollectionName collectionName) {
        this.collectionName = collectionName;
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
     * Get the 'preview' element value.
     * 
     * @return value
     */
    public Preview getPreview() {
        return preview;
    }

    /** 
     * Set the 'preview' element value.
     * 
     * @param preview
     */
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    /** 
     * Get the 'landingPage' element value.
     * 
     * @return value
     */
    public LandingPage getLandingPage() {
        return landingPage;
    }

    /** 
     * Set the 'landingPage' element value.
     * 
     * @param landingPage
     */
    public void setLandingPage(LandingPage landingPage) {
        this.landingPage = landingPage;
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
     * Get the 'rights' element value.
     * 
     * @return value
     */
    public Rights1 getRights() {
        return rights;
    }

    /** 
     * Set the 'rights' element value.
     * 
     * @param rights
     */
    public void setRights(Rights1 rights) {
        this.rights = rights;
    }

    /** 
     * Get the list of 'aggregates' element items.
     * 
     * @return list
     */
    public List<Aggregates> getAggregateList() {
        return aggregateList;
    }

    /** 
     * Set the list of 'aggregates' element items.
     * 
     * @param list
     */
    public void setAggregateList(List<Aggregates> list) {
        aggregateList = list;
    }
}
