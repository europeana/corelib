package eu.europeana.corelib.solr.bean.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;
import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.License;
import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.definitions.edm.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.Service;
import eu.europeana.corelib.definitions.edm.entity.Timespan;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.LicenseImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.ServiceImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;

/**
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.definitions.edm.beans.FullBean
 */
@SuppressWarnings("unchecked")
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("record")
@Converters(DocType.DocTypeConverter.class)
public class FullBeanImpl implements FullBean {

    @Id
    protected ObjectId europeanaId;

    @Indexed(unique = true)
    protected String about;

    protected String[] title;

    protected String[] year;

    protected String[] provider;

    protected String[] language;

    protected Date timestampCreated;

    protected Date timestampUpdated;

    protected DocType type;

    protected int europeanaCompleteness;

    @Transient
    protected List<BriefBeanImpl> similarItems;

    @Reference
    @Indexed
    protected List<PlaceImpl> places;

    @Reference
    @Indexed
    protected List<AgentImpl> agents;
 
    @Reference
    @Indexed
    protected List<TimespanImpl> timespans;

    @Reference
    @Indexed
    protected List<ConceptImpl> concepts;

    @Reference
    protected List<AggregationImpl> aggregations;

    @Reference
    protected List<ProvidedCHOImpl> providedCHOs;

    @Reference
    protected EuropeanaAggregationImpl europeanaAggregation;

    @Reference
    protected List<ProxyImpl> proxies;

    @Reference
    @Indexed
    protected List<LicenseImpl> licenses;

    @Reference
    @Indexed
    protected List<ServiceImpl> services;

    protected String[] country;
    protected String[] userTags;
    protected String[] europeanaCollectionName;

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
        return this.europeanaAggregation;
    }

    @Override
    public void setEuropeanaAggregation(EuropeanaAggregation europeanaAggregation) {
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
        if (this.type != null) {
            return this.type;
        }
        for (Proxy p : this.getProxies()) {
            if (p.getEdmType() != null) {
                return p.getEdmType();
            }
        }
        LogManager.getLogger(FullBeanImpl.class).error("Type is null, no proxy.edmType found as fallback!");
        return null;
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

    /**
    * @return either the hashcode of the about field, or otherwise a hexed hashcode of the europeanaId
    */
    @Override
    public int hashCode() {
    return StringUtils.isNotBlank(this.about) ? this.about.hashCode() : this.europeanaId.toHexString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FullBeanImpl fullBean = (FullBeanImpl) o;
        if (!europeanaId.equals(fullBean.europeanaId)) {
            return false;
        }
        return about != null ? about.equals(fullBean.about) : fullBean.about == null;
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
        return this.europeanaCollectionName != null ? this.europeanaCollectionName.clone() : null;
    }

    @Override
    public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
        this.europeanaCollectionName = europeanaCollectionName != null ? europeanaCollectionName.clone() : null;
    }

    @Override
    public Date getTimestampCreated() {
        return this.timestampCreated != null ? this.timestampCreated : new Date(0);
    }

    @Override
    public Date getTimestampUpdated() {
        return timestampUpdated != null ? this.timestampUpdated : new Date(0);
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
    public Date getTimestamp() {
        return null;
    }

    @Override
    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    @Override
    public void setTimestampUpdated(Date timestampUpdated) {
        this.timestampUpdated = timestampUpdated;
    }

    @Override
    public List<LicenseImpl> getLicenses() {
        return licenses;
    }

    @Override
    public void setLicenses(List<? extends License> licenses) {
        this.licenses = (List<LicenseImpl>) licenses;
    }

    public void setAsParent() {
        if (!this.aggregations.isEmpty()) {
            for (AggregationImpl agg : this.aggregations) {
                agg.setParentBean(this);
            }
        }
    }

    @Override
    public void setServices(List<? extends Service> services) {
        this.services = (List<ServiceImpl>) services;
    }

    @Override
    public List<ServiceImpl> getServices() {
        return services;
    }

}
