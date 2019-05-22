package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Provider Proxy fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Proxy extends BasicProxy {

	/**
	 * Retrieve the edm:type fields for a Proxy
	 * 
	 * @return DocType representing the edm:type fields for a Proxy
	 */
	DocType getEdmType();

	/**
	 * Set the edmType field for a Proxy
	 * 
	 * @param edmType
	 * 			String array containing the edmType of a Proxy
	 */
	void setEdmType(DocType edmType);

	/**
	 * 
	 * @return if the current proxy is the europeana proxy
	 */
	boolean isEuropeanaProxy();

	/**
	 * sets if the current proxy is the europeana proxy
	 * @param europeanaProxy
	 */
	void setEuropeanaProxy(boolean europeanaProxy);

	/**
	 * 
	 * @return the year (required for europeana proxy)
	 */
	Map<String,List<String>> getYear();

	/**
	 * sets the year for the europeana proxy
	 * @param year
	 */
	void setYear(Map<String,List<String>> year);

	/**
	 * 
	 * @return the user tags for the europeana proxy
	 */
	Map<String,List<String>>getUserTags();

	/**
	 * sets user tags for the europeana proxy
	 * @param userTags
	 */
	void setUserTags(Map<String,List<String>> userTags);
}
