package eu.europeana.corelib.edm.utils.construct;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
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


public class FullBeanHandler {

    private EdmMongoServer mongoServer;
    Logger log = Logger.getLogger(this.getClass().getName());

    public FullBeanHandler(EdmMongoServer mongoServer) {
        this.mongoServer = mongoServer;
    }

    public void saveEdmClasses(FullBeanImpl fullBean, boolean isFirstSave) throws MongoUpdateException {
        List<AgentImpl> agents = new ArrayList<AgentImpl>();
        List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
        List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
        List<PlaceImpl> places = new ArrayList<PlaceImpl>();
        List<LicenseImpl> licenses = new ArrayList<>();
        List<ServiceImpl> services = new ArrayList<>();
        if (fullBean.getAgents() != null) {
            for (AgentImpl agent : fullBean.getAgents()) {
                AgentImpl retAgent = mongoServer.searchByAbout(AgentImpl.class,
                        agent.getAbout());
                if (retAgent != null) {
                    agents.add(new AgentUpdater().update(retAgent, agent, mongoServer));
                } else {
                    try {
                        mongoServer.getDatastore().save(agent);
                        agents.add(agent);
                    } catch (Exception e) {
                        agents.add(new AgentUpdater().update(mongoServer.searchByAbout(AgentImpl.class,
                                agent.getAbout()), agent, mongoServer));
                    }
                }
            }
        }
        if (fullBean.getPlaces() != null) {
            for (PlaceImpl place : fullBean.getPlaces()) {
                PlaceImpl retPlace = mongoServer.searchByAbout(PlaceImpl.class,
                        place.getAbout());
                if (retPlace != null) {
                    places.add(new PlaceUpdater().update(retPlace, place, mongoServer));
                } else {
                    try {
                        mongoServer.getDatastore().save(place);
                        places.add(place);
                    } catch (Exception e) {
                        places.add(new PlaceUpdater().update(mongoServer.searchByAbout(PlaceImpl.class,
                                place.getAbout()), place, mongoServer));
                    }
                }
            }
        }
        if (fullBean.getConcepts() != null) {
            for (ConceptImpl concept : fullBean.getConcepts()) {
                ConceptImpl retConcept = mongoServer.searchByAbout(
                        ConceptImpl.class, concept.getAbout());
                if (retConcept != null) {
                    concepts.add(new ConceptUpdater().update(retConcept, concept,
                            mongoServer));
                } else {
                    try {
                        mongoServer.getDatastore().save(concept);
                        concepts.add(concept);
                    } catch (Exception e) {
                        concepts.add(new ConceptUpdater().update(mongoServer.searchByAbout(
                                ConceptImpl.class, concept.getAbout()), concept,
                                mongoServer));
                    }
                }
            }
        }
        if (fullBean.getTimespans() != null) {
            for (TimespanImpl timespan : fullBean.getTimespans()) {
                TimespanImpl retTimespan = mongoServer.searchByAbout(
                        TimespanImpl.class, timespan.getAbout());
                if (retTimespan != null) {
                    timespans.add(new TimespanUpdater().update(retTimespan, timespan,
                            mongoServer));
                } else {
                    try {
                        mongoServer.getDatastore().save(timespan);
                        timespans.add(timespan);
                    } catch (Exception e) {
                        timespans.add(new TimespanUpdater().update(mongoServer.searchByAbout(
                                TimespanImpl.class, timespan.getAbout()), timespan,
                                mongoServer));
                    }
                }
            }
        }
        
        if(fullBean.getLicenses()!=null){
        	for(LicenseImpl license: fullBean.getLicenses()){
        		LicenseImpl retLicense =mongoServer.searchByAbout(
                        LicenseImpl.class, license.getAbout());
        	
        	if (retLicense != null) {
                licenses.add(new LicenseUpdater().update(retLicense, license,
                        mongoServer));
            } else {
                try {
                    mongoServer.getDatastore().save(license);
                    licenses.add(license);
                } catch (Exception e) {
                    licenses.add(new LicenseUpdater().update(mongoServer.searchByAbout(
                            LicenseImpl.class, license.getAbout()), license,
                            mongoServer));
                }
            }
        	}
        }

        if(fullBean.getServices()!=null){
            for(ServiceImpl service:fullBean.getServices()){
                ServiceImpl retService = mongoServer.searchByAbout(ServiceImpl.class,service.getAbout());
                if(retService!=null){
                    services.add(new ServiceUpdater().update(retService,service,mongoServer));
                } else {
                    try {
                        mongoServer.getDatastore().save(service);
                        services.add(service);
                    } catch (Exception e){
                        services.add(new ServiceUpdater().update(retService,service,mongoServer));
                    }
                }
            }
        }
        
        if (isFirstSave) {
            mongoServer.getDatastore().save(fullBean.getProvidedCHOs());
            mongoServer.getDatastore().save(fullBean.getEuropeanaAggregation());
            mongoServer.getDatastore().save(fullBean.getProxies());
            mongoServer.getDatastore().save(fullBean.getAggregations());
            if (fullBean.getEuropeanaAggregation().getWebResources() != null) {
                mongoServer.getDatastore().save(
                        fullBean.getEuropeanaAggregation().getWebResources());
            }
            for (AggregationImpl aggr : fullBean.getAggregations()) {
                if (aggr.getWebResources() != null) {
                    mongoServer.getDatastore().save(aggr.getWebResources());
                }
            }
        } else {
            List<ProvidedCHOImpl> pChos = new ArrayList<ProvidedCHOImpl>();
            List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
            List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
            for (ProvidedCHOImpl pCho : fullBean.getProvidedCHOs()) {
                pChos.add(new ProvidedChoUpdater().update(
                        mongoServer.searchByAbout(ProvidedCHOImpl.class,
                                pCho.getAbout()), pCho, mongoServer));
            }
            for (AggregationImpl aggr : fullBean.getAggregations()) {
                aggregations.add(new AggregationUpdater().update(
                        mongoServer.searchByAbout(AggregationImpl.class,
                                aggr.getAbout()), aggr, mongoServer));
            }
            fullBean.setEuropeanaAggregation(new EuropeanaAggregationUpdater().update(mongoServer.searchByAbout(
                    EuropeanaAggregationImpl.class, fullBean
                    .getEuropeanaAggregation().getAbout()), (EuropeanaAggregationImpl) fullBean
                    .getEuropeanaAggregation(), mongoServer));

            for (ProxyImpl prx : fullBean.getProxies()) {

                proxies.add(new ProxyUpdater().update(
                        mongoServer.searchByAbout(ProxyImpl.class,
                                prx.getAbout()), prx, mongoServer));
            }
            fullBean.setProvidedCHOs(pChos);
            fullBean.setAggregations(aggregations);
            fullBean.setProxies(proxies);
        }
        fullBean.setAgents(agents);
        fullBean.setPlaces(places);
        fullBean.setConcepts(concepts);
        fullBean.setTimespans(timespans);
        fullBean.setLicenses(licenses);
        fullBean.setServices(services);
    }

    public FullBeanImpl updateFullBean(FullBeanImpl fullBean) throws MongoUpdateException {
        saveEdmClasses(fullBean, false);
        Query<FullBeanImpl> updateQuery = mongoServer.getDatastore()
                .createQuery(FullBeanImpl.class).field("about")
                .equal(fullBean.getAbout().replace("/item", ""));
        UpdateOperations<FullBeanImpl> ops = mongoServer.getDatastore()
                .createUpdateOperations(FullBeanImpl.class);
        ops.set("title", fullBean.getTitle() != null ? fullBean.getTitle()
                : new String[]{});
        ops.set("year", fullBean.getYear() != null ? fullBean.getYear()
                : new String[]{});
        ops.set("provider",
                fullBean.getProvider() != null ? fullBean.getProvider()
                : new String[]{});
        ops.set("language",
                fullBean.getLanguage() != null ? fullBean.getLanguage()
                : new String[]{});
        ops.set("type", fullBean.getType() != null ? fullBean.getType()
                : DocType.IMAGE);
        ops.set("europeanaCompleteness", fullBean.getEuropeanaCompleteness());
        ops.set("places", fullBean.getPlaces() != null ? fullBean.getPlaces()
                : new ArrayList<PlaceImpl>());
        ops.set("agents", fullBean.getAgents() != null ? fullBean.getAgents()
                : new ArrayList<AgentImpl>());
        ops.set("timespans",
                fullBean.getTimespans() != null ? fullBean.getTimespans()
                : new ArrayList<TimespanImpl>());
        ops.set("concepts",
                fullBean.getConcepts() != null ? fullBean.getConcepts()
                : new ArrayList<ConceptImpl>());
        ops.set("aggregations", fullBean.getAggregations());
        ops.set("providedCHOs", fullBean.getProvidedCHOs());
        ops.set("europeanaAggregation", fullBean.getEuropeanaAggregation());
        ops.set("proxies", fullBean.getProxies());
        ops.set("country",
                fullBean.getCountry() != null ? fullBean.getCountry()
                : new String[]{});
        ops.set("services",fullBean.getServices());
        ops.set("europeanaCollectionName",
                fullBean.getEuropeanaCollectionName());
        mongoServer.getDatastore().update(updateQuery, ops);
        try {
            return (FullBeanImpl) mongoServer.getFullBean(fullBean.getAbout());
        } catch (MongoDBException | MongoRuntimeException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return fullBean;
    }
}
