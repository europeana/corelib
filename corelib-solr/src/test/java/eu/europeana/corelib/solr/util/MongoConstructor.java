package eu.europeana.corelib.solr.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.RDF.Choice;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.server.importer.util.AgentFieldInput;
import eu.europeana.corelib.solr.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ConceptFieldInput;
import eu.europeana.corelib.solr.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProvidedCHOFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProxyFieldInput;
import eu.europeana.corelib.solr.server.importer.util.TimespanFieldInput;

public class MongoConstructor {
	private RDF record;
	private FullBeanImpl fullBean;
	private MongoDBServer mongoServer;

	public MongoConstructor(RDF record, MongoDBServer mongoServer) {
		this.record = record;
		this.mongoServer = mongoServer;
	}

	public RDF getRecord() {
		return this.record;
	}

	public void constructFullBean() throws InstantiationException, IllegalAccessException {
		fullBean = new FullBeanImpl();
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		List<WebResource> webResources = new ArrayList<WebResource>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<ProvidedCHOImpl>();
		List<Choice> elements = record.getChoiceList();
		for (Choice element : elements) {

			if (element.ifProvidedCHO()) {
				try {
					providedCHOs.add(ProvidedCHOFieldInput
							.createProvidedCHOMongoFields(element.getProvidedCHO(),
									mongoServer, false));
					if(proxies.size()>0){
						proxies.set(0, ProxyFieldInput.createProxyMongoFields(new ProxyImpl(),element.getProvidedCHO(), mongoServer));
					}
					else{
						proxies.add(ProxyFieldInput.createProxyMongoFields(new ProxyImpl(),element.getProvidedCHO(), mongoServer));
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (element.ifAggregation()) {

				aggregations.add(AggregationFieldInput
						.createAggregationMongoFields(element.getAggregation(),
								mongoServer));
				if(proxies.size()>0){
					proxies.set(0,ProxyFieldInput.addProxyForMongo(proxies.get(0),element.getAggregation(),mongoServer));
				}
				else{
					proxies.add(ProxyFieldInput.addProxyForMongo(new ProxyImpl(),element.getAggregation(),mongoServer));
				}
				
			}
			if (element.ifConcept()) {
				concepts.add(ConceptFieldInput.createConceptMongoFields(
						element.getConcept(), mongoServer));
			}
			if (element.ifPlace()) {
				places.add(PlaceFieldInput.createPlaceMongoFields(
						element.getPlace(), mongoServer));
			}
			
			if (element.ifWebResource()) {
				if (aggregationExists(element.getWebResource(), aggregations)) {
					aggregations = AggregationFieldInput
							.appendWebResource(aggregations,
									element.getWebResource(), mongoServer);
				} else {
					aggregations.add(AggregationFieldInput
							.createMongoAggregationFromWebResource(
									element.getWebResource(), mongoServer));
				}
			}
			if (element.ifTimeSpan()) {
				timespans
						.add(TimespanFieldInput.createTimespanMongoField(element.getTimeSpan(),mongoServer));
			}
			if (element.ifAgent()) {
				agents.add(AgentFieldInput.createAgentMongoEntity(element.getAgent(),mongoServer));
			}
		}

		AggregationImpl aggregation = aggregations.get(0);
		aggregation.setWebResources(webResources);
		fullBean.setProvidedCHOs(providedCHOs);
		fullBean.setAgents(agents);

		fullBean.setAggregations(aggregations);
		try{
		fullBean.setConcepts(concepts);
		fullBean.setPlaces(places);
		fullBean.setTimespans(timespans);
		fullBean.setProxies(proxies);}
		catch(Exception e){
			e.printStackTrace();
		}
		mongoServer.getDatastore().save(fullBean);
		mongoServer.close();
		this.record = null;
		mongoServer= null;
		fullBean = null;
	}

	private boolean aggregationExists(WebResourceType webResource,
			List<AggregationImpl> aggregations) {
		for (Aggregation aggregation : aggregations) {
			for (String hasView : aggregation.getHasView()) {
				if (StringUtils.equals(hasView, webResource.getAbout())) {
					return true;
				}
			}
		}
		return false;
	}
}
