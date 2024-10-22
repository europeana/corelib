package eu.europeana.corelib.solr.bean.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.morphia.annotations.*;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.*;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.entity.*;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Basic CHO record as retrieved from Mongo
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.definitions.edm.beans.FullBean
 */
@SuppressWarnings("unchecked")
@JsonInclude(Include.NON_EMPTY)
@Entity(value = "record", useDiscriminator = false)
@Indexes({
        @Index(fields = {@Field("about")}, options = @IndexOptions(unique = true)),
        @Index(fields = {@Field("timestampCreated")}),
        @Index(fields = {@Field("timestampUpdated")}),
        @Index(fields = {@Field("places")}),
        @Index(fields = {@Field("agents")}),
        @Index(fields = {@Field("organizations")}),
        @Index(fields = {@Field("timespans")}),
        @Index(fields = {@Field("concepts")}),
        @Index(fields = {@Field("licenses")}),
        @Index(fields = {@Field("services")})})
public class FullBeanImpl implements FullBean {

    @Id
    protected ObjectId europeanaId;

    protected String about;

    protected String[] title;

    protected String[] year;

    protected String[] provider;

    protected String[] language;

    protected Date timestampCreated;

    protected Date timestampUpdated;

    protected String type;

    protected int europeanaCompleteness;

    @Reference
    protected List<PlaceImpl> places = new ArrayList<>();

    @Reference
    protected List<AgentImpl> agents = new ArrayList<>();

    @Reference
    protected List<OrganizationImpl> organizations = new ArrayList<>();

    @Reference
    protected List<TimespanImpl> timespans = new ArrayList<>();

    @Reference
    protected List<ConceptImpl> concepts = new ArrayList<>();

    @Reference
    protected List<AggregationImpl> aggregations = new ArrayList<>();

    @Reference
    protected List<ProvidedCHOImpl> providedCHOs = new ArrayList<>();

    @Reference
    protected EuropeanaAggregationImpl europeanaAggregation;

    @Reference
    protected List<ProxyImpl> proxies = new ArrayList<>();

    @Reference
    protected List<LicenseImpl> licenses = new ArrayList<>();

    @Reference
    protected List<ServiceImpl> services = new ArrayList<>();

    protected List<QualityAnnotationImpl> qualityAnnotations = new ArrayList<>();

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
    public List<OrganizationImpl> getOrganizations() {
        return this.organizations;
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
    public void setOrganizations(List<? extends Organization> organizations) {
        this.organizations = (List<OrganizationImpl>) organizations;
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

    /**
     * @deprecated unused, there are no records that have this field
     */
    @Override
    @Deprecated(since = "June 2021", forRemoval = true)
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
    public void setType(String type) {
        this.type = Optional.ofNullable(DocType.safeValueOf(type)).map(DocType::getEnumNameValue)
                .orElse(null);
    }

    @Override
    public String getType() {
        if (this.type != null) {
            return this.type;
        }
        for (Proxy p : this.getProxies()) {
            if (p.getEdmType() != null) {
                return p.getEdmType();
            }
        }
        return null;
    }

    @Override
    public void setEuropeanaCompleteness(int europeanaCompleteness) {
        this.europeanaCompleteness = europeanaCompleteness;
    }

    /**
     * @deprecated unused, there are no records that have this field
     */
    @Override
    @Deprecated(since = "June 2021", forRemoval = true)
    public String[] getTitle() {
        return (this.title == null ? null : this.title.clone());
    }

    @Override
    public String[] getYear() {
        return (this.year == null ? null : this.year.clone());
    }

    @Override
    public String[] getProvider() {
        return (this.provider == null ? null : this.provider.clone());
    }

    @Override
    public String[] getLanguage() {
        return (this.language == null ? null : this.language.clone());
    }

    @Override
    public int getEuropeanaCompleteness() {
        return this.europeanaCompleteness;
    }

    @Override
    public String[] getUserTags() {
        return this.userTags == null ? null : this.userTags.clone();
    }

    public void setUserTags(String[] userTags) {
        this.userTags = userTags.clone();
    }

    @Override
    public String getId() {
        return (this.europeanaId == null ? null : europeanaId.toString());
    }

    /**
     * @return either the hashcode of the about field, or otherwise a hexed hashcode of the
     * europeanaId
     */
    @Override
    public int hashCode() {
        return (StringUtils.isBlank(this.about) ? this.europeanaId.toHexString().hashCode() : this.about.hashCode());
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
        return Objects.equals(about, fullBean.about);
    }

    @Override
    public String[] getCountry() {
        return this.country == null ? null : country.clone();
    }

    @Override
    public void setCountry(String[] country) {
        this.country = country.clone();
    }

    @Override
    public String[] getEuropeanaCollectionName() {
        return this.europeanaCollectionName == null ? null : this.europeanaCollectionName.clone();
    }

    @Override
    public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
        this.europeanaCollectionName =
                (europeanaCollectionName == null ? null : europeanaCollectionName.clone());
    }

    @Override
    public Date getTimestampCreated() {
        return this.timestampCreated == null ? new Date(0) : this.timestampCreated;
    }

    @Override
    public Date getTimestampUpdated() {
        return timestampUpdated == null ? new Date(0) : this.timestampUpdated;
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

    @Override
    public List<? extends QualityAnnotation> getQualityAnnotations() {
        return qualityAnnotations;
    }

    @Override
    public void setQualityAnnotations(List<? extends QualityAnnotation> qualityAnnotations) {
        this.qualityAnnotations = (List<QualityAnnotationImpl>) qualityAnnotations;
    }

}
