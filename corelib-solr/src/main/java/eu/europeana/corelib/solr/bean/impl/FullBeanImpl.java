/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */


package eu.europeana.corelib.solr.bean.impl;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.*;

import eu.europeana.corelib.definitions.model.Agent;
import eu.europeana.corelib.definitions.model.Aggregation;
import eu.europeana.corelib.definitions.model.Concept;
import eu.europeana.corelib.definitions.model.EuropeanaAggregation;
import eu.europeana.corelib.definitions.model.Place;
import eu.europeana.corelib.definitions.model.Proxy;
import eu.europeana.corelib.definitions.model.Timespan;
import eu.europeana.corelib.definitions.model.WebResource;
import eu.europeana.corelib.definitions.model.impl.AgentImpl;
import eu.europeana.corelib.definitions.model.impl.AggregationImpl;
import eu.europeana.corelib.definitions.model.impl.ConceptImpl;
import eu.europeana.corelib.definitions.model.impl.PlaceImpl;
import eu.europeana.corelib.definitions.model.impl.ProxyImpl;
import eu.europeana.corelib.definitions.model.impl.TimespanImpl;
import eu.europeana.corelib.definitions.model.impl.WebResourceImpl;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.FullBean;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.FullBean
 * TODO: Decide how data are going to be stored in MongoDB
 * @author Yorgos.Mamakis@kb.nl
 *
 */

@Entity("record")
public class FullBeanImpl implements FullBean {
	@Id ObjectId europeana_id;
	private String title;
	private String[] edmObject;
	private String creator;
	private String year;
	private String provider;
	private String language;
	private DocType type;
	private int europeanaCompleteness;
	
	@Reference private ArrayList<PlaceImpl> places;
	@Reference private ArrayList<AgentImpl> agents;
	@Reference private ArrayList<TimespanImpl> timespans;
	@Reference private ArrayList<ConceptImpl> concepts;
	@Reference private ArrayList<AggregationImpl> aggregations;
	//TODO:implement proxy and check if Europeana Aggregation needs to be stored separately
	@Reference private EuropeanaAggregation europeanaAggregation;
	@Reference private ArrayList<ProxyImpl> proxy;
	@Reference private ArrayList<WebResourceImpl> webResources;
	
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	
	@Override
	public String[] getEdmObject() {
		//from EuropeanaAggregation unique id
		return this.edmObject;
	}

	@Override
	public String getCreator() {
		return this.creator;
	}

	@Override
	public String getYear() {
		return this.year;
	}

	@Override
	public String getProvider() {
		return this.provider;
	}

	@Override
	public String getDataProvider() {
		//What if more than one aggregations point to a providedCHO (more edm:dataProvider)
		
		ArrayList<String> aggregationDataProviders = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				aggregationDataProviders.add(aggregation.getEdmDataProvider());
			
		}
		
		//temporary in order to maintain compatibility
		return aggregationDataProviders.get(0);
	}

	@Override
	public String getLanguage() {
		return this.language;
	}

	@Override
	public DocType getType() {
		return this.type;
	}

	@Override
	public String[] getDcTermsSpatial() {
		return null;
	}

	@Override
	public int getEuropeanaCompleteness() {
		return this.europeanaCompleteness;
	}




	@Override
	public String[] getEdmPlaceLabel() {
		ArrayList<String> prefLabels = new ArrayList<String>();
		for (Place place : this.places){
			for(String prefLabel:place.getPrefLabel()){
				prefLabels.add(prefLabel);
			}
		}
		return (String[]) prefLabels.toArray();
	}

	@Override
	public String[] getEdmPlaceIsPartOf() {
		ArrayList<String> broaderPlaces = new ArrayList<String>();
		for (Place place : this.places){
			for(String broaderPlace:place.getIsPartOf()){
				broaderPlaces.add(broaderPlace);
			}
		}
		return (String[]) broaderPlaces.toArray();
	}

	@Override
	public Float[] getEdmPlaceLatitude() {
		ArrayList<Float> latitudes = new ArrayList<Float>();
		for (Place place : this.places){
				latitudes.add(place.getLatitude());
		}
		return (Float[]) latitudes.toArray();
	}

	@Override
	public Float[] getEdmPlaceLongitude() {
		ArrayList<Float> longitudes = new ArrayList<Float>();
		for (Place place : this.places){
				longitudes.add(place.getLongitude());
		}
		return (Float[]) longitudes.toArray();
	}

	@Override
	public String[] getEdmTimespanTerm() {
		// TODO Decide what to do here
		return null;
	}

	@Override
	public String[] getEdmTimespanLabel() {
		ArrayList<String> prefLabels = new ArrayList<String>();
		for (Timespan timespan : this.timespans){
				for (String prefLabel:timespan.getPrefLabel()){
					prefLabels.add(prefLabel);
				}
		}
		return (String[]) prefLabels.toArray();
	}

	@Override
	public String[] getEdmTimespanIsPartOf() {
		ArrayList<String> broaderPeriods = new ArrayList<String>();
		for (Timespan timespan : this.timespans){
				for (String broaderPeriod:timespan.getIsPartOf()){
					broaderPeriods.add(broaderPeriod);
				}
		}
		return (String[]) broaderPeriods.toArray();
	}

	@Override
	public Date[] getEdmTimespanBegin() {
		ArrayList<Date>startDates = new ArrayList<Date>();
		for (Timespan timespan : this.timespans){
			
				startDates.add(timespan.getBegin());
			
		}
		return (Date[]) startDates.toArray();
	}

	@Override
	public Date[] getEdmTimespanEnd() {
		ArrayList<Date>endDates = new ArrayList<Date>();
		for (Timespan timespan : this.timespans){
			
				endDates.add(timespan.getBegin());
		}
		return (Date[]) endDates.toArray();
	}

	@Override
	public String[] getEdmConceptTerm() {
		// TODO Decide what to put here
		return null;
	}

	@Override
	public String[] getEdmConceptLabel() {
		ArrayList<String> conceptLabels = new ArrayList<String> ();
		for (Concept concept : this.concepts)
			for (String conceptLabel : concept.getPrefLabel()){
				conceptLabels.add(conceptLabel);
			}
		return (String[])conceptLabels.toArray();
	}

	@Override
	public String[] getEdmConceptBroaderTerm() {
		ArrayList<String> broaderTerms = new ArrayList<String> ();
		for (Concept concept : this.concepts)
			for (String broaderTerm : concept.getBroader()){
				broaderTerms.add(broaderTerm);
			}
		return (String[])broaderTerms.toArray();
	}

	@Override
	public String[] getEdmAgentTerm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmAgentLabel() {
		ArrayList<String> agentLabels = new ArrayList<String> ();
		for (Agent agent : this.agents)
			for (String agentLabel : agent.getPrefLabel()){
				agentLabels.add(agentLabel);
			}
		return (String[])agentLabels.toArray();
	}

	@Override
	public String[] getEdmHasView() {
		ArrayList<String> aggregationViews = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			for (String aggregationView : aggregation.getEdmHasView()){
				aggregationViews.add(aggregationView);
			}
		}
		return (String[])aggregationViews.toArray();
	}

	@Override
	public String[] getEdmIsShownBy() {
		ArrayList<String> aggregationIsShownByList = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				aggregationIsShownByList.add(aggregation.getEdmIsShownBy());
			
		}
		return (String[])aggregationIsShownByList.toArray();
	}

	@Override
	public String[] getEdmIsShownAt() {
		ArrayList<String> aggregationIsShownAtList = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				aggregationIsShownAtList.add(aggregation.getEdmIsShownAt());
			
		}
		return (String[])aggregationIsShownAtList.toArray();
	}

	@Override
	public String[] getEdmProvider() {
		ArrayList<String> aggregationProviders = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				aggregationProviders.add(aggregation.getEdmProvider());
			
		}
		return (String[])aggregationProviders.toArray();
	}

	@Override
	public String[] getAggregationDcRights() {
		ArrayList<String> aggregationDcRightsList = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			for (String aggregationDcRights : aggregation.getDcRights()){
				aggregationDcRightsList.add(aggregationDcRights);
			}
		}
		return (String[])aggregationDcRightsList.toArray();
	}
	
	@Override
	public String[] getAggregationEdmRights() {
		ArrayList<String> aggregationEdmRightsList = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				aggregationEdmRightsList.add(aggregation.getEdmRights());
			
		}
		return (String[])aggregationEdmRightsList.toArray();
	}
	@Override
	public String[] getEdmWebResource() {
		ArrayList<String> webResourceUrls = new ArrayList<String> ();
		for (WebResource webResource : this.webResources)
			for (String webResourceUrl: webResource.getEdmWebResource()){
				webResourceUrls.add(webResourceUrl);
			}
		return (String[])webResourceUrls.toArray();
	}

	@Override
	public String[] getEdmWebResourceDcRights() {
		ArrayList<String> webResourceDcRightsList = new ArrayList<String> ();
		for (WebResource webResource : this.webResources)
			for (String webResourceDcRights: webResource.getEdmWebResourceDcRights()){
				webResourceDcRightsList.add(webResourceDcRights);
			}
		return (String[])webResourceDcRightsList.toArray();
	}

	@Override
	public String[] getEdmWebResourceEdmRights() {
		ArrayList<String> webResourceEdmRightsList = new ArrayList<String> ();
		for (WebResource webResource : this.webResources)
		{
			webResourceEdmRightsList.add(webResource.getEdmWebResourceEdmRights());
		}	
		return (String[])webResourceEdmRightsList.toArray();
	}

	@Override
	public String[] getOreProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOwlSameAs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcCoverage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcPublisher() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcRelation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getProxyDcRights() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getProxyEdmRights() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsAlternative() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsConformsTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsCreated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsExtent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsHasFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsIsPartOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsIsReferencedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsIsReplacedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsIsRequiredBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsIsVersionOf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsIssued() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsMedium() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsProvenance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsReplaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsRequires() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsTableOfContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDcTermsTemporal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmUGC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmCurrentLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmIsNextInSequence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getUserTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmAgentSkosNote() {
		ArrayList<String> agentNotes = new ArrayList<String> ();
		for (Agent agent : this.agents)
			for (String agentNote : agent.getNote()){
				agentNotes.add(agentNote);
			}
		return (String[])agentNotes.toArray();
	}

	@Override
	public Date[] getEdmAgentBegin() {
		ArrayList<Date> agentBeginDates = new ArrayList<Date> ();
		for (Agent agent : this.agents){			
				agentBeginDates.add(agent.getBegin());
			}
		return (Date[])agentBeginDates.toArray();
	}

	@Override
	public Date[] getEdmAgentEnd() {
		ArrayList<Date> agentEndDates = new ArrayList<Date> ();
		for (Agent agent : this.agents){			
				agentEndDates.add(agent.getEnd());
			}
		return (Date[])agentEndDates.toArray();
	}

	@Override
	public String[] getEdmTimeSpanSkosNote() {
		ArrayList<String> notes = new ArrayList<String>();
		for (Timespan timespan : this.timespans){
				for (String note:timespan.getNote()){
					notes.add(note);
				}
		}
		return (String[]) notes.toArray();
	}

	@Override
	public String[] getEdmPlaceSkosNote() {
		ArrayList<String> notes = new ArrayList<String>();
		for (Place place: this.places){
				for (String note:place.getNote()){
					notes.add(note);
				}
		}
		return (String[]) notes.toArray();
	}

	@Override
	public String[] getEdmConceptNote() {
		ArrayList<String> notes = new ArrayList<String>();
		for (Concept concept: this.concepts){
				for (String note:concept.getNote()){
					notes.add(note);
				}
		}
		return (String[]) notes.toArray();
	}

	@Override
	public String getId() {
		return this.europeana_id.toString();
	}


	@Override
	public String[] getEdmPlaceTerm() {
		// TODO What to put here
		return null;
	}


	@Override
	public String[] getEdmAgentBroaderLabels() {
		ArrayList<String> agentLabels = new ArrayList<String> ();
		for (Agent agent : this.agents)
			for (String agentLabel : agent.getAltLabel()){
				agentLabels.add(agentLabel);
			}
		return (String[])agentLabels.toArray();
	}

}
