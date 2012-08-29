/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.definitions.solr.beans;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.entity.Timespan;

/**
 * Interface for the FullBean. FullBean contains all the fields exposed by the MongoDB required by Portal in order to
 * fully present a single record page
 * 
 * NOTE: Draft to be crosschecked and validated over time
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@ kb.nl>
 */
public interface FullBean extends IdBean {


	String[] getUserTags();

	List<? extends Place> getPlaces();

	void setPlaces(List<? extends Place> places);

	List<? extends Agent> getAgents();

	void setAgents(List<? extends Agent> agents);

	List<? extends Timespan> getTimespans();

	List<? extends Concept> getConcepts();

	void setConcepts(List<? extends Concept> concepts);

	void setAggregations(List<? extends Aggregation> aggregations);

	List<? extends Proxy> getProxies();

	void setProxies(List<? extends Proxy> proxies);

	void setEuropeanaId(ObjectId europeanaId);

	void setTitle(String[] title);

	void setYear(String[] year);

	void setProvider(String[] provider);

	void setLanguage(String[] language);

	void setType(DocType type);

	void setEuropeanaCompleteness(int europeanaCompleteness);

	void setTimespans(List<? extends Timespan> timespans);

	List<? extends Aggregation> getAggregations();

	List<? extends BriefBean> getRelatedItems();

	void setRelatedItems(List<? extends BriefBean> relatedItems);

	List<? extends ProvidedCHO> getProvidedCHOs();

	void setProvidedCHOs(List<? extends ProvidedCHO> providedCHOs);

	String getAbout();

	void setAbout(String about);

	void setWhen(String[] when);

	String[] getWhen();

	void setWhere(String[] where);

	String[] getWhere();

	void setWhat(String[] what);

	String[] getWhat();

	void setWho(String[] who);

	String[] getWho();

	EuropeanaAggregation getEuropeanaAggregation();

	void setEuropeanaAggregation(EuropeanaAggregation europeanaAggregation);

	String[] getTitle();

	String[] getYear();

	String[] getProvider();

	String[] getLanguage();

	DocType getType();

	int getEuropeanaCompleteness();


	String[] getEuropeanaCollectionName();


	String[] getCountry();


	void setCountry(String[] country);


	Date getTimestamp();


	void setEuropeanaCollectionName(String[] europeanaCollectionName);
}
