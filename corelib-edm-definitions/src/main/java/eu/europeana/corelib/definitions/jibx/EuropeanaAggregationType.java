
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
 * &lt;xs:complexType xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/elements/1.1/" xmlns:ns2="http://purl.org/dc/terms/" xmlns:ns3="http://www.w3.org/ns/dqv#" xmlns:ns4="http://www.openarchives.org/ore/terms/" xmlns:ns5="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="EuropeanaAggregationType">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:AboutType">
 *       &lt;xs:sequence>
 *         &lt;xs:element ref="ns1:creator" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:aggregatedCHO" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:collectionName" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:datasetName" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:country" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:hasView" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:isShownBy" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:preview" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:landingPage" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:language" minOccurs="1" maxOccurs="1"/>
 *         &lt;xs:element ref="ns5:rights" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns4:aggregates" minOccurs="0" maxOccurs="unbounded"/>
 *         &lt;xs:element ref="ns5:completeness" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:created" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns2:modified" minOccurs="0" maxOccurs="1"/>
 *         &lt;xs:element ref="ns3:hasQualityAnnotation" minOccurs="0" maxOccurs="unbounded"/>
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
    private DatasetName datasetName;
    private Country country;
    private List<HasView> hasViewList = new ArrayList<HasView>();
    private IsShownBy isShownBy;
    private Preview preview;
    private LandingPage landingPage;
    private Language1 language;
    private Rights1 rights;
    private List<Aggregates> aggregateList = new ArrayList<Aggregates>();
    private Completeness completeness;
    private Created created;
    private Modified modified;
    private List<HasQualityAnnotation> hasQualityAnnotationList = new ArrayList<HasQualityAnnotation>();

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

    /** 
     * Get the 'completeness' element value.
     * 
     * @return value
     */
    public Completeness getCompleteness() {
        return completeness;
    }

    /** 
     * Set the 'completeness' element value.
     * 
     * @param completeness
     */
    public void setCompleteness(Completeness completeness) {
        this.completeness = completeness;
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
     * Get the list of 'hasQualityAnnotation' element items.
     * 
     * @return list
     */
    public List<HasQualityAnnotation> getHasQualityAnnotationList() {
        return hasQualityAnnotationList;
    }

    /** 
     * Set the list of 'hasQualityAnnotation' element items.
     * 
     * @param list
     */
    public void setHasQualityAnnotationList(List<HasQualityAnnotation> list) {
        hasQualityAnnotationList = list;
    }
}
