package eu.europeana.corelib.definitions.edm.beans;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.License;
import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.edm.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.corelib.definitions.edm.entity.Service;
import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Interface for the FullBean. FullBean contains all the fields exposed by the MongoDB required by Portal in order to
 * fully present a single record page
 * 
 * NOTE: Draft to be crosschecked and validated over time
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@ kb.nl>
 */
public interface FullBean extends IdBean {

	/**
	 * 
	 * @return the usertags for this record
	 */
	String[] getUserTags();

	/**
	 * 
	 * @return the list of places indexed for this record
	 */
	List<? extends Place> getPlaces();

	/**
	 * Sets the list of places for this record
	 * @param places
	 */
	void setPlaces(List<? extends Place> places);

	/**
	 * 
	 * @return The list of agents for this record
	 */
	List<? extends Agent> getAgents();

	/**
	 * Sets the list of agents for this record
	 * @param agents
	 */
	void setAgents(List<? extends Agent> agents);

	/**
	 * 
	 * @return the list of timespans for this record
	 */
	List<? extends Timespan> getTimespans();

	/**
	 * 
	 * @return the list of concepts for this record
	 */
	List<? extends Concept> getConcepts();

	/**
	 * Sets the list of concepts for this record
	 * @param concepts
	 */
	void setConcepts(List<? extends Concept> concepts);

	/**
	 * Sets the list of aggregations for this record 
	 * @param aggregations
	 */
	void setAggregations(List<? extends Aggregation> aggregations);

	/**
	 * 
	 * @return the list of prxies for this record
	 */
	List<? extends Proxy> getProxies();

	/**
	 * Sets the list of proxies for this record
	 * @param proxies
	 */
	void setProxies(List<? extends Proxy> proxies);

	/**
	 * Sets the europeanaId for this record (edm:ProvidedCHO@rdf:about)
	 * @param europeanaId
	 */
	void setEuropeanaId(ObjectId europeanaId);

	/**
	 * Sets the title for this record
	 * @param title
	 */
	void setTitle(String[] title);

	/**
	 * Sets the year for this record
	 * @param year
	 */
	void setYear(String[] year);

	/**
	 * Sets the provider for this record
	 * @param provider
	 */
	void setProvider(String[] provider);

	/**
	 * Sets the language for this record
	 * @param language
	 */
	void setLanguage(String[] language);

	/**
	 * Sets the type for this record
	 * @param type
	 */
	void setType(DocType type);

	/**
	 * Sets the europeana completeness for this record
	 * @param europeanaCompleteness
	 */
	void setEuropeanaCompleteness(int europeanaCompleteness);

	/**
	 * Sets the list of timespans for this record
	 * @param timespans
	 */
	void setTimespans(List<? extends Timespan> timespans);

	/**
	 * 
	 * @return The list of Aggregations for this record
	 */
	List<? extends Aggregation> getAggregations();

	/**
	 * 
	 * @return The similar items for this record
	 */
	List<? extends BriefBean> getSimilarItems();

	/**
	 * Sets the similar items for this record
	 * @param similarItems
	 */
	void setSimilarItems(List<? extends BriefBean> similarItems);

	/**
	 * 
	 * @return the list of ProvidedCHOs for this record
	 */
	List<? extends ProvidedCHO> getProvidedCHOs();

	/**
	 * Sets the list of ProvidedCHOs for this record
	 * @param providedCHOs
	 */
	void setProvidedCHOs(List<? extends ProvidedCHO> providedCHOs);

	/**
	 * 
	 * @return The rdf:about of the providedCHO of this record
	 */
	String getAbout();

	/**
	 * Sets the rdf:about of the providedCHO of this record
	 * @param about
	 */
	void setAbout(String about);

	/**
	 * 
	 * @return the europeana aggregation of the record
	 */
	EuropeanaAggregation getEuropeanaAggregation();

	/**
	 * Sets the europeana aggregation for this record
	 * @param europeanaAggregation
	 */
	void setEuropeanaAggregation(EuropeanaAggregation europeanaAggregation);

	/**
	 * 
	 * @return the title for this record
	 */
	String[] getTitle();

	/**
	 * 
	 * @return the year for this record
	 */
	String[] getYear();

	/**
	 * 
	 * @return the provider for this record
	 */
	String[] getProvider();

	/**
	 * 
	 * @return the language for this record
	 */
	String[] getLanguage();

	/**
	 * 
	 * @return the type for this record
	 */
	DocType getType();

	/**
	 * 
	 * @return the europeana completeness for this record
	 */
	int getEuropeanaCompleteness();

	/**
	 *
	 * @return the europeana collection name for this record
	 */
	String[] getEuropeanaCollectionName();


	/**
	 * 
	 * @return the country for this record
	 */
	String[] getCountry();

	/**
	 * Sets the country for this record
	 * @param country
	 */
	void setCountry(String[] country);

	/**
	 * 
	 * @return the date the record was ingested
	 */
	Date getTimestamp();

	/**
	 * Sets the Europeana collection name for this record
	 * @param europeanaCollectionName
	 */
	void setEuropeanaCollectionName(String[] europeanaCollectionName);
	/**
	 * The date the record was created
	 * @return 
	 */
	Date getTimestampCreated();
	/**
	 * The date the record was updated
	 * @return 
	 */
	Date getTimestampUpdated();
	/**
	 * The date the record was created
	 */
	void setTimestampCreated(Date timestampCreated);
	/**
	 * The date the record was updated
	 */
	void setTimestampUpdated(Date timestampUpdated);
	
	/**
	 * The cc:License for the record
	 * @return
	 */
	List<? extends License> getLicenses();
	
	/**
	 * Set the cc:License for the record
	 */
	void setLicenses(List<? extends License> licenses);

	/**
	 * Get the associated list of svcs:Service for the record
	 */
	List<? extends Service> getServices();

	/**
	 * Set the list of svcs:Service for the record
	 * @param services
     */
	void setServices(List<? extends Service> services);
}
