package eu.europeana.corelib.solr.utils.construct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.util.ClientUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.server.EdmMongoServer;

public class FullBeanHandler {
	
	private EdmMongoServer mongoServer;
	Logger log = Logger.getLogger(this.getClass().getName());
	
	public FullBeanHandler(EdmMongoServer mongoServer) {
		this.mongoServer = mongoServer;
	}
	
	public boolean removeRecord(HttpSolrServer solrServer, RDF rdf) {
		try {
			solrServer.deleteByQuery("europeana_id:"
					+ ClientUtils.escapeQueryChars(rdf.getProvidedCHOList()
							.get(0).getAbout()));

			DBCollection records = mongoServer.getDatastore().getDB()
					.getCollection("record");
			DBCollection proxies = mongoServer.getDatastore().getDB()
					.getCollection("Proxy");
			DBCollection providedCHOs = mongoServer.getDatastore().getDB()
					.getCollection("ProvidedCHO");
			DBCollection aggregations = mongoServer.getDatastore().getDB()
					.getCollection("Aggregation");
			DBCollection europeanaAggregations = mongoServer.getDatastore()
					.getDB().getCollection("EuropeanaAggregation");
			DBCollection physicalThing = mongoServer.getDatastore().getDB()
					.getCollection("PhysicalThing");
			DBObject query = new BasicDBObject("about", rdf
					.getProvidedCHOList().get(0).getAbout());
			DBObject proxyQuery = new BasicDBObject("about", "/proxy/provider"
					+ rdf.getProvidedCHOList().get(0).getAbout());
			DBObject europeanaProxyQuery = new BasicDBObject("about",
					"/proxy/europeana"
							+ rdf.getProvidedCHOList().get(0).getAbout());

			DBObject providedCHOQuery = new BasicDBObject("about", "/item"
					+ rdf.getProvidedCHOList().get(0).getAbout());
			DBObject aggregationQuery = new BasicDBObject("about",
					"/aggregation/provider"
							+ rdf.getProvidedCHOList().get(0).getAbout());
			DBObject europeanaAggregationQuery = new BasicDBObject("about",
					"/aggregation/europeana"
							+ rdf.getProvidedCHOList().get(0).getAbout());
			europeanaAggregations.remove(europeanaAggregationQuery,
					WriteConcern.FSYNC_SAFE);
			records.remove(query, WriteConcern.FSYNC_SAFE);
			proxies.remove(europeanaProxyQuery, WriteConcern.FSYNC_SAFE);
			proxies.remove(proxyQuery, WriteConcern.FSYNC_SAFE);
			physicalThing.remove(europeanaProxyQuery, WriteConcern.FSYNC_SAFE);
			physicalThing.remove(proxyQuery, WriteConcern.FSYNC_SAFE);
			providedCHOs.remove(providedCHOQuery, WriteConcern.FSYNC_SAFE);
			aggregations.remove(aggregationQuery, WriteConcern.FSYNC_SAFE);
			return true;
		} catch (SolrServerException e) {
			log.log(Level.SEVERE,e.getMessage());
		
		} catch (IOException e) {
			log.log(Level.SEVERE,e.getMessage());
			
		}
		return false;
	}

	public void clearData( String collection) {
		DBCollection records = mongoServer.getDatastore().getDB()
				.getCollection("record");
		DBCollection proxies = mongoServer.getDatastore().getDB()
				.getCollection("Proxy");
		DBCollection physicalThing = mongoServer.getDatastore().getDB()
				.getCollection("PhysicalThing");
		DBCollection providedCHOs = mongoServer.getDatastore().getDB()
				.getCollection("ProvidedCHO");
		DBCollection aggregations = mongoServer.getDatastore().getDB()
				.getCollection("Aggregation");
		DBCollection europeanaAggregations = mongoServer.getDatastore()
				.getDB().getCollection("EuropeanaAggregation");

		DBObject query = new BasicDBObject("about", Pattern.compile("^/"
				+ collection + "/"));
		DBObject proxyQuery = new BasicDBObject("about",
				Pattern.compile("^/proxy/provider/" + collection + "/"));
		DBObject europeanaProxyQuery = new BasicDBObject("about",
				Pattern.compile("^/proxy/europeana/" + collection + "/"));

		DBObject providedCHOQuery = new BasicDBObject("about",
				Pattern.compile("^/item/" + collection + "/"));
		DBObject aggregationQuery = new BasicDBObject("about",
				Pattern.compile("^/aggregation/provider/" + collection + "/"));
		DBObject europeanaAggregationQuery = new BasicDBObject("about",
				Pattern.compile("^/aggregation/europeana/" + collection + "/"));
		europeanaAggregations.remove(europeanaAggregationQuery,
				WriteConcern.FSYNC_SAFE);
		records.remove(query, WriteConcern.FSYNC_SAFE);
		proxies.remove(europeanaProxyQuery, WriteConcern.FSYNC_SAFE);
		proxies.remove(proxyQuery, WriteConcern.FSYNC_SAFE);
		physicalThing.remove(proxyQuery, WriteConcern.FSYNC_SAFE);
		physicalThing.remove(europeanaProxyQuery, WriteConcern.FSYNC_SAFE);
		providedCHOs.remove(providedCHOQuery, WriteConcern.FSYNC_SAFE);
		aggregations.remove(aggregationQuery, WriteConcern.FSYNC_SAFE);
	}
	
	public void saveEdmClasses(FullBeanImpl fullBean, boolean isFirstSave) {
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		if (fullBean.getAgents() != null) {
			for (AgentImpl agent : fullBean.getAgents()) {
				AgentImpl retAgent = mongoServer.searchByAbout(AgentImpl.class,
						agent.getAbout());
				if (retAgent != null) {
					agents.add(new AgentUpdater().update(retAgent, agent, mongoServer));
				} else {
					agents.add(agent);
					mongoServer.getDatastore().save(agent);
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
					places.add(place);
					mongoServer.getDatastore().save(place);
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
					concepts.add(concept);
					mongoServer.getDatastore().save(concept);
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
					timespans.add(timespan);
					mongoServer.getDatastore().save(timespan);
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
							.getEuropeanaAggregation().getAbout()), (EuropeanaAggregationImpl)fullBean
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
		
	}
	
	public FullBeanImpl updateFullBean(FullBeanImpl fullBean) {
		saveEdmClasses(fullBean,false);
		Query<FullBeanImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(FullBeanImpl.class).field("about")
				.equal(fullBean.getAbout().replace("/item", ""));
		UpdateOperations<FullBeanImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(FullBeanImpl.class);
		ops.set("title", fullBean.getTitle() != null ? fullBean.getTitle()
				: new String[] {});
		ops.set("year", fullBean.getYear() != null ? fullBean.getYear()
				: new String[] {});
		ops.set("provider",
				fullBean.getProvider() != null ? fullBean.getProvider()
						: new String[] {});
		ops.set("language",
				fullBean.getLanguage() != null ? fullBean.getLanguage()
						: new String[] {});
		ops.set("type", fullBean.getType() != null ? fullBean.getType()
				: DocType.IMAGE);
		ops.set("europeanaCompleteness", fullBean.getEuropeanaCompleteness());
		ops.set("optOut", fullBean.isOptedOut());
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
						: new String[] {});
		ops.set("europeanaCollectionName",
				fullBean.getEuropeanaCollectionName());
		mongoServer.getDatastore().update(updateQuery, ops);
		try {
			return (FullBeanImpl) mongoServer.getFullBean(fullBean.getAbout());
		} catch (MongoDBException e) {
			log.log(Level.SEVERE,e.getMessage());
		}
		return fullBean;
	}
}
