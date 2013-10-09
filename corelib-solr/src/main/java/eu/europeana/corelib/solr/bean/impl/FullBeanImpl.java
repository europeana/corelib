/*
 *  Copyright 2007-2012 The Europeana Foundation
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
package eu.europeana.corelib.solr.bean.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Transient;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.entity.Timespan;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.web.service.impl.EuropeanaUrlServiceImpl;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.FullBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@SuppressWarnings("unchecked")
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("record")
public class FullBeanImpl implements FullBean {

	@Id
	protected ObjectId europeanaId;

	@Indexed(unique = true)
	protected String about;

	protected String[] title;

	protected String[] year;

	protected String[] provider;

	protected String[] language;

	protected Date timestamp;

	protected DocType type;

	protected int europeanaCompleteness;

	protected boolean optOut;

	@Transient
	protected List<BriefBeanImpl> similarItems;

	@Reference
	protected List<PlaceImpl> places;

	@Reference
	protected List<AgentImpl> agents;

	@Reference
	protected List<TimespanImpl> timespans;

	@Reference
	protected List<ConceptImpl> concepts;

	@Reference
	protected List<AggregationImpl> aggregations;

	@Reference
	protected List<ProvidedCHOImpl> providedCHOs;

	@Reference
	protected EuropeanaAggregationImpl europeanaAggregation;

	@Reference
	protected List<ProxyImpl> proxies;

	protected String[] country;
	protected String[] userTags;

	protected String[] europeanaCollectionName;

	/**
	 * GETTERS & SETTTERS
	 */
	@Override
	public List<PlaceImpl> getPlaces() {
		return this.places;
	}

	@Override
	public void setPlaces(List<? extends Place> places) {
		this.places = (List<PlaceImpl>) places;
	}

	@Override
	public List<AgentImpl> getAgents() {
		return this.agents;
	}

	@Override
	public String getAbout() {
		return this.about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public void setAgents(List<? extends Agent> agents) {
		this.agents = (List<AgentImpl>) agents;
	}

	@Override
	public List<TimespanImpl> getTimespans() {
		return this.timespans;
	}

	@Override
	public void setTimespans(List<? extends Timespan> timespans) {
		this.timespans = (List<TimespanImpl>) timespans;
	}

	@Override
	public List<ConceptImpl> getConcepts() {
		return this.concepts;
	}

	@Override
	public void setConcepts(List<? extends Concept> concepts) {
		this.concepts = (List<ConceptImpl>) concepts;
	}

	@Override
	public List<AggregationImpl> getAggregations() {
		return this.aggregations;
	}

	@Override
	public void setAggregations(List<? extends Aggregation> aggregations) {
		this.aggregations = (List<AggregationImpl>) aggregations;
	}

	@Override
	public EuropeanaAggregation getEuropeanaAggregation() {
		String previewUrl = null;
		DocType type = null;
		if (this.getAggregations().get(0).getEdmObject() != null) {
			previewUrl = this.getAggregations().get(0).getEdmObject();

		}
		if (previewUrl != null) {
			this.europeanaAggregation
					.setEdmPreview(new EuropeanaUrlServiceImpl()
							.getThumbnailUrl(previewUrl, type).toString());
		} else {
			this.europeanaAggregation.setEdmPreview("");
		}
		return this.europeanaAggregation;
	}

	@Override
	public void setEuropeanaAggregation(
			EuropeanaAggregation europeanaAggregation) {
		this.europeanaAggregation = (EuropeanaAggregationImpl) europeanaAggregation;
	}

	@Override
	public List<ProxyImpl> getProxies() {
		return this.proxies;
	}

	@Override
	public void setProxies(List<? extends Proxy> proxies) {
		this.proxies = (List<ProxyImpl>) proxies;
	}

	@Override
	public List<ProvidedCHOImpl> getProvidedCHOs() {
		return this.providedCHOs;
	}

	@Override
	public void setProvidedCHOs(List<? extends ProvidedCHO> providedCHOs) {
		this.providedCHOs = (List<ProvidedCHOImpl>) providedCHOs;
	}

	@Override
	public void setEuropeanaId(ObjectId europeanaId) {
		this.europeanaId = europeanaId;
	}

	@Override
	public void setTitle(String[] title) {
		this.title = title.clone();
	}

	@Override
	public void setYear(String[] year) {
		this.year = year.clone();
	}

	@Override
	public void setProvider(String[] provider) {
		this.provider = provider.clone();
	}

	@Override
	public void setLanguage(String[] language) {
		this.language = language.clone();
	}

	@Override
	public void setType(DocType type) {
		this.type = type;
	}

	@Override
	public void setEuropeanaCompleteness(int europeanaCompleteness) {
		this.europeanaCompleteness = europeanaCompleteness;
	}

	@Override
	public String[] getTitle() {
		return (this.title != null ? this.title.clone() : null);
	}

	@Override
	public String[] getYear() {
		return (this.year != null ? this.year.clone() : null);
	}

	@Override
	public String[] getProvider() {
		return (this.provider != null ? this.provider.clone() : null);
	}

	@Override
	public String[] getLanguage() {
		return (this.language != null ? this.language.clone() : null);
	}

	@Override
	public DocType getType() {
		return this.type;
	}

	@Override
	public int getEuropeanaCompleteness() {
		return this.europeanaCompleteness;
	}

	@Override
	public String[] getUserTags() {
		return this.userTags != null ? this.userTags.clone() : null;
	}

	public void setUserTags(String[] userTags) {
		this.userTags = userTags.clone();
	}

	@Override
	public String getId() {
		if (this.europeanaId != null) {
			return this.europeanaId.toString();
		}
		return null;
	}

	@Override
	public int hashCode() {
		return StringUtils.isNotBlank(this.about) ? this.about.hashCode()
				: this.europeanaId.toStringMongod().hashCode();
	}

	@Override
	public String[] getCountry() {
		return this.country != null ? country.clone() : null;
	}

	@Override
	public void setCountry(String[] country) {
		this.country = country.clone();
	}

	@Override
	public String[] getEuropeanaCollectionName() {
		return this.europeanaCollectionName != null ? this.europeanaCollectionName
				.clone() : null;
	}

	@Override
	public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
		this.europeanaCollectionName = europeanaCollectionName != null ? europeanaCollectionName
				.clone() : null;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public List<? extends BriefBean> getSimilarItems() {
		return this.similarItems;
	}

	@Override
	public void setSimilarItems(List<? extends BriefBean> similarItems) {
		this.similarItems = (List<BriefBeanImpl>) similarItems;
	}

	@Override
	public Boolean isOptedOut() {
		return this.optOut;
	}

	@Override
	public void setOptOut(boolean optOut) {
		this.optOut = optOut;
	}
}
