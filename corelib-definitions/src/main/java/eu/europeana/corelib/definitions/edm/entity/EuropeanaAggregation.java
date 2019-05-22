package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * Europeana Aggregation interface
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface EuropeanaAggregation extends AbstractEdmEntity {

	/**
	 * 
	 * @return the edm:aggregatedCHO for an edm:EuropeanaAggregation
	 */
	String getAggregatedCHO();
	
	/**
	 * sets the edm:aggregatedCHO for an edm:EuropeanaAggregation
	 * @param aggregatedCHO
	 */
	void setAggregatedCHO(String aggregatedCHO);
	
	/**
	 * 
	 * @return the edm:aggregates for an edm:EuropeanaAggregation
	 */
	String[] getAggregates();
	/**
	 * sets the edm:aggregates for an edm:EuropeanaAggregation
	 * @param aggregates
	 */
	void setAggregates(String[] aggregates);
	
	/**
	 * 
	 * @return the dc:creator for an edm:EuropeanaAggregation
	 */
	Map<String,List<String>> getDcCreator();
	
	/**
	 * sets the dc:creator for an edm:EuropeanaAggregation
	 * @param dcCreator
	 */
	void setDcCreator(Map<String,List<String>> dcCreator);
	
	/**
	 * 
	 * @return the edm:landingPage for an edm:EuropeanaAggregation
	 */
	String getEdmLandingPage();

	/**
	 * sets the edm:landingPage for an edm:EuropeanaAggregation
	 * @param edmLandingPage
	 */
	void setEdmLandingPage(String edmLandingPage);

	/**
	 * 
	 * @return the edm:isShownBy for an edm:EuropeanaAggregation
	 */
	String getEdmIsShownBy();
	
	/**
	 * sets the edm:isShownBy for an edm:EuropeanaAggregation
	 * @param edmIsShownBy
	 */
	void setEdmIsShownBy(String edmIsShownBy);
	
	/**
	 * 
	 * @return the edm:hasView for an edm:EuropeanaAggregation
	 */
	String[] getEdmHasView();
	
	/**
	 * the edm:hasView for an edm:EuropeanaAggregation
	 * @param edmHasView
	 */
	void setEdmHasView(String[] edmHasView);
	
	/**
	 * 
	 * @return the edm:country for an edm:EuropeanaAggregation
	 */
	Map<String,List<String>> getEdmCountry();
	
	/**
	 * sets the edm:country for an edm:EuropeanaAggregation
	 * @param edmCountry
	 */
	void setEdmCountry(Map<String,List<String>> edmCountry);
	
	/**
	 * 
	 * @return the edm:language for an edm:EuropeanaAggregation
	 */
	Map<String,List<String>> getEdmLanguage();
	
	/**
	 * sets the edm:language for an edm:EuropeanaAggregation
	 * @param edmLanguage
	 */
	void setEdmLanguage(Map<String,List<String>> edmLanguage);
	
	/**
	 * 
	 * @return the edm:rights for an edm:EuropeanaAggregation
	 */
	Map<String,List<String>> getEdmRights();
	
	/**
	 * sets the edm:rights for an edm:EuropeanaAggregation
	 * @param edmRights
	 */
	void setEdmRights(Map<String,List<String>> edmRights);

	/**
	 * 
	 * @return edm:WebResource list for the edm:EuropeanaAggregation
	 */
	List<? extends WebResource> getWebResources();

	/**
	 * sets the edm:WebResource list for the edm:EuropeanaAggregation
	 * @param webResources
	 */
	void setWebResources(List<? extends WebResource> webResources);

	/**
	 * Return original image url that is selected by Metis Media Service as a preview (so a thumbnail should be available
	 * for this image)
	 * @return the edm:preview for the edm:EuropeanaAggregation
	 */
	String getEdmPreview();

	/**
	 * sets the edm:preview for the edm:EuropeanaAggregation
	 * @param edmPreview
	 */
	void setEdmPreview(String edmPreview);
}
