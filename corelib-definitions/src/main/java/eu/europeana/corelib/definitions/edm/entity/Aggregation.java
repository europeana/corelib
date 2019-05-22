package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * EDM Aggregation fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Aggregation extends AbstractEdmEntity {

	/**
	 * Retrieves the edm:dataProvider field from an Aggregation
	 * 
	 * @return String representing the edm:DataProvider field
	 */
	Map<String,List<String>> getEdmDataProvider();

	/**
	 * Retrieves the unique edm:isShownBy field from an Aggregation
	 * 
	 * @return String representing the edm:isShownBy field
	 */
	String getEdmIsShownBy();

	/**
	 * Retrieves the unique edm:isShownAt field from an Aggregation
	 * 
	 * @return String representing the edm:isShownAt field
	 */
	String getEdmIsShownAt();

	/**
	 * Retrieves the unique edm:object field from an Aggregation
	 * 
	 * @return String representing the edm:object field
	 */
	String getEdmObject();

	/**
	 * Retrieves the unique edm:provider field from an Aggregation
	 * 
	 * @return String representing the edm:provider field
	 */
	Map<String,List<String>> getEdmProvider();

	/**
	 * Retrieves the dc:rights fields from an Aggregation
	 * 
	 * @return String array representing the dc:rights fields
	 */
	Map<String,List<String>> getDcRights();

	/**
	 * Retrieves the unique edm:rights field from an Aggregation
	 * 
	 * @return String representing the edm:rights fields
	 */
	Map<String,List<String>> getEdmRights();

	/**
	 * Set the dc:rights field for an Aggregation
	 * 
	 * @param dcRights
	 *            String array with the dc:rights
	 */
	void setDcRights(Map<String,List<String>> dcRights);

	/**
	 * Set the edm:provider field for an Aggregation
	 * 
	 * @param edmProvider
	 *            String with the edm:provider
	 */
	void setEdmProvider(Map<String,List<String>> edmProvider);

	/**
	 * Set the edm:rights field for an Aggregation
	 * 
	 * @param edmRights
	 *            String with the edm:rights
	 */
	void setEdmRights(Map<String,List<String>> edmRights);

	/**
	 * Set the edm:object for an Aggregation
	 * 
	 * @param edmObject
	 *            String with the edm:object
	 */
	void setEdmObject(String edmObject);

	/**
	 * Set the edm:isShownAt field for an Aggregation
	 * 
	 * @param edmIsShownAt
	 *            String with the edm:isShownAt
	 */
	void setEdmIsShownAt(String edmIsShownAt);

	/**
	 * Set the edm:isShownBy field for an Aggregation
	 * 
	 * @param edmIsShownBy
	 *            String with the edm:isShownBy
	 */
	void setEdmIsShownBy(String edmIsShownBy);

	/**
	 * Set the edm:dataProvider field for an Aggregation
	 * 
	 * @param edmDataProvider
	 *            String with the edm:dataProvider
	 */
	void setEdmDataProvider(Map<String,List<String>> edmDataProvider);

	/**
	 * Returns the list of WebResources for an Aggregation
	 * 
	 * @return A list of WebResources and all implementing classes for this
	 *         Aggregation
	 */
	List<? extends WebResource> getWebResources();

	/**
	 * Set the Web resources for an Aggregation
	 * 
	 * @param webResources
	 *            The list of web resources for the aggregation
	 */
	void setWebResources(List<? extends WebResource> webResources);

	/**
	 * Retrieve the edm:UGC field for an Aggregation of it exists Valid value is
	 * TRUE
	 * 
	 * @return The edm:UGC field value
	 */
	String getEdmUgc();

	/**
	 * Set the edm:UGC field value for an Aggregation
	 * 
	 * @param edmUgc
	 */
	void setEdmUgc(String edmUgc);

	/**
	 * Retrieve the edm:previedNoDistribute flag for an Aggregation (currently
	 * not on the Europeana schema)
	 * 
	 * @return the edm:previewNoDistribute flag field
	 */
	Boolean getEdmPreviewNoDistribute();

	/**
	 * Set the edm:previedNoDistribute flag for an Aggregation (currently not on
	 * the Europeana schema)
	 * 
	 * @param edmPreviewNoDistribute
	 *            the edm:previewNoDistribute
	 */
	void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute);

	/**
	 * Retrieve the digital representations of an Aggregation (edm:HasView) For
	 * each edm:hasView, there is exactly one WebResource
	 * 
	 * @return A string array with the URLs that point to the providers' digital
	 *         representations
	 */
	String[] getHasView();

	/**
	 * Retrieve the digital representations of an Aggregation (edm:HasView) For
	 * each edm:hasView, there is exactly one WebResource
	 * 
	 * @param hasView
	 *            A string array with the URLs that point to the providers'
	 *            digital representations
	 */
	void setHasView(String[] hasView);

	/**
	 * Retrieve the edm:aggregatedCHO field for an Aggregation This points to
	 * the corresponding providedCHO rdf:about field value
	 * 
	 * @return A string pointing to the corresponding providedCHO
	 */
	String getAggregatedCHO();

	/**
	 * Set the edm:aggregatedCHO field for an Aggregation This points to the
	 * corresponding providedCHO rdf:about field value
	 * 
	 * @param aggregatedCHO
	 *            A string pointing to the corresponding providedCHO
	 */
	void setAggregatedCHO(String aggregatedCHO);

	/**
	 * 
	 * @return the edm:aggregaties for an Aggregation
	 */
	String[] getAggregates();

	/**
	 * sets the edm:aggregates for an Aggregation
	 * @param aggregates
	 */
	void setAggregates(String[] aggregates);

	/**
	 * TODO: check if required
	 * @return the edm:unstored for an Aggregation
	 */
	String[] getEdmUnstored();

	/**
	 * Sets the edm:unstored for an Aggregation
	 * @param edmUnstored
	 */
	void setEdmUnstored(String[] edmUnstored);

	/**
	 * edm:intermediateProvider
 	 * @return
     */
	Map<String,List<String>> getEdmIntermediateProvider();

	/**
	 * edm:intermediateProvider
	 */

	void setEdmIntermediateProvider(Map<String,List<String>> edmIntermediateProvider);
}
