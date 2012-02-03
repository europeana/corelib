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

package eu.europeana.corelib.solr.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.*;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.entity.Timespan;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.FullBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

@Entity("record")
public class FullBeanImpl implements FullBean {
	@Id ObjectId europeana_id;
	private String[] title;
	private String[] creator;
	private String[] year;
	private String[] provider;
	private String[] language;
	private DocType type;
	private int europeanaCompleteness;
	
	@Reference private ArrayList<PlaceImpl> places;
	@Reference private ArrayList<AgentImpl> agents;
	@Reference private ArrayList<TimespanImpl> timespans;
	@Reference private ArrayList<ConceptImpl> concepts;
	@Reference private ArrayList<AggregationImpl> aggregations;
	//TODO:implement proxy and check if Europeana Aggregation needs to be stored separately
	@Reference private EuropeanaAggregation europeanaAggregation;
	@Reference private ArrayList<ProxyImpl> proxies;
	@Reference private ArrayList<WebResourceImpl> webResources;
	
	
	@Override
	public String[] getTitle() {
		return this.title;
	}
	
	
	@Override
	public String[] getEdmObject() {
		ArrayList<String> edmObjects = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				edmObjects.add(aggregation.getEdmObject());
			
		}
		
		
		return (String[])edmObjects.toArray();
	}

	@Override
	public String[] getCreator() {
		return this.creator;
	}

	@Override
	public String[] getYear() {
		return this.year;
	}

	@Override
	public String[] getProvider() {
		return this.provider;
	}

	@Override
	public String[] getDataProvider() {
		//What if more than one aggregations point to a providedCHO (more edm:dataProvider)
		
		ArrayList<String> aggregationDataProviders = new ArrayList<String> ();
		for (Aggregation aggregation : this.aggregations){
			
				aggregationDataProviders.add(aggregation.getEdmDataProvider());
			
		}
		
		//temporary in order to maintain compatibility
		return (String[])aggregationDataProviders.toArray();
	}

	@Override
	public String[] getLanguage() {
		return this.language;
	}

	@Override
	public DocType getType() {
		return this.type;
	}

	@Override
	public String[] getDcTermsSpatial() {
		ArrayList<String> dctermsSpatialList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsSpatial : proxy.getDctermsSpatial()){
				dctermsSpatialList.add(dctermsSpatial);
			}
		}
		return (String[]) dctermsSpatialList.toArray();
	}

	@Override
	public int getEuropeanaCompleteness() {
		return this.europeanaCompleteness;
	}




	@Override
	public String[] getEdmPlaceLabel() {
		ArrayList<ArrayList<String>> prefLabels = new ArrayList<ArrayList<String>>();
		for (Place place : this.places){
			for(String[] prefLabel:place.getPrefLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(prefLabel[0]);
				tmp.add(prefLabel[1]);
				prefLabels.add(tmp);
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
	public String[] getEdmTimespan() {
		ArrayList<String> timespanIds = new ArrayList<String>();
		for (Timespan timespan : this.timespans){
			
				timespanIds.add(timespan.getTimespanId().toString());
			
		}
		return (String[]) timespanIds.toArray();
	}

	@Override
	public String[] getEdmTimespanLabel() {
		ArrayList<ArrayList<String>> prefLabels = new ArrayList<ArrayList<String>>();
		for (Timespan timespan : this.timespans){
			for(String[] prefLabel:timespan.getPrefLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(prefLabel[0]);
				tmp.add(prefLabel[1]);
				prefLabels.add(tmp);
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
	public String[] getEdmConcept() {
		ArrayList<String> conceptIds = new ArrayList<String>();
		for (Concept concept : this.concepts){
			
				conceptIds.add(concept.getConceptId().toString());
			
		}
		return (String[]) conceptIds.toArray();
	}

	@Override
	public String[] getEdmConceptLabel() {
		ArrayList<ArrayList<String>> prefLabels = new ArrayList<ArrayList<String>>();
		for (Concept concept: this.concepts){
			for(String[] prefLabel:concept.getPrefLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(prefLabel[0]);
				tmp.add(prefLabel[1]);
				prefLabels.add(tmp);
			}
		}
		return (String[]) prefLabels.toArray();
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
	public String[] getEdmAgent() {
		ArrayList<String> agentIds = new ArrayList<String>();
		for (Agent agent : this.agents){
			
				agentIds.add(agent.getAgentId().toString());
			
		}
		return (String[]) agentIds.toArray();
	}

	@Override
	public String[] getEdmAgentLabel() {
		ArrayList<ArrayList<String>> prefLabels = new ArrayList<ArrayList<String>>();
		for (Agent agent : this.agents){
			for(String[] prefLabel:agent.getPrefLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(prefLabel[0]);
				tmp.add(prefLabel[1]);
				prefLabels.add(tmp);
			}
		}
		return (String[]) prefLabels.toArray();
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
		
				webResourceUrls.add(webResource.getEdmWebResource());
			
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
		ArrayList<String> owlProxies = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			
				owlProxies.add(proxy.getProxyId().toString());
			
		}
		return (String[]) owlProxies.toArray();
	}

	@Override
	public String[] getOwlSameAs() {
		ArrayList<String> owlSameAsList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String owlSameAs : proxy.getOwlSameAs()){
				owlSameAsList.add(owlSameAs);
			}
		}
		return (String[]) owlSameAsList.toArray();
	}

	@Override
	public String[] getDcCoverage() {
		ArrayList<String> dcCoverageList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dcCoverage : proxy.getDcCoverage()){
				dcCoverageList.add(dcCoverage);
			}
		}
		return (String[]) dcCoverageList.toArray();
	}

	@Override
	public String[] getDcPublisher() {
		ArrayList<String> dcPublisherList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dcPublisher : proxy.getDcPublisher()){
				dcPublisherList.add(dcPublisher);
			}
		}
		return (String[]) dcPublisherList.toArray();
	}

	@Override
	public String[] getDcIdentifier() {
		ArrayList<String> dcIdentifierList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dcIdentifier : proxy.getDcIdentifier()){
				dcIdentifierList.add(dcIdentifier);
			}
		}
		return (String[]) dcIdentifierList.toArray();
	}

	@Override
	public String[] getDcRelation() {
		ArrayList<String> dcRelationList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dcRelation : proxy.getDcRelation()){
				dcRelationList.add(dcRelation);
			}
		}
		return (String[]) dcRelationList.toArray();
	}

	@Override
	public String[] getProxyDcRights() {
		ArrayList<String> dcRightsList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dcRights : proxy.getDcRights()){
				dcRightsList.add(dcRights);
			}
		}
		return (String[]) dcRightsList.toArray();
	}

	
	@Override
	public String[] getDcSource() {
		ArrayList<String> dcSourceList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dcSource : proxy.getDcSource()){
				dcSourceList.add(dcSource);
			}
		}
		return (String[]) dcSourceList.toArray();
	}

	@Override
	public String[] getDcTermsAlternative() {
		ArrayList<String> dctermsAlternativeList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsAlternative : proxy.getDctermsAlternative()){
				dctermsAlternativeList.add(dctermsAlternative);
			}
		}
		return (String[]) dctermsAlternativeList.toArray();
	}

	@Override
	public String[] getDcTermsConformsTo() {
		ArrayList<String> dctermsConformsToList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsConformsTo : proxy.getDctermsConformsTo()){
				dctermsConformsToList.add(dctermsConformsTo);
			}
		}
		return (String[]) dctermsConformsToList.toArray();
	}

	@Override
	public Date[] getDcTermsCreated() {
		ArrayList<Date> dctermsCreatedList = new ArrayList<Date> ();
		for (Proxy proxy : this.proxies){
			for(Date dctermsCreated : proxy.getDctermsCreated()){
				dctermsCreatedList.add(dctermsCreated);
			}
		}
		return (Date[]) dctermsCreatedList.toArray();
	}

	@Override
	public String[] getDcTermsExtent() {
		ArrayList<String> dctermsExtentList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsExtent : proxy.getDctermsExtent()){
				dctermsExtentList.add(dctermsExtent);
			}
		}
		return (String[]) dctermsExtentList.toArray();
	}

	@Override
	public String[] getDcTermsHasFormat() {
		ArrayList<String> dctermsHasFormatList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsHasFormat : proxy.getDctermsHasFormat()){
				dctermsHasFormatList.add(dctermsHasFormat);
			}
		}
		return (String[]) dctermsHasFormatList.toArray();
	}

	@Override
	public String[] getDcTermsIsPartOf() {
		ArrayList<String> dctermsIsPartOfList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsIsPartOf : proxy.getDctermsIsPartOf()){
				dctermsIsPartOfList.add(dctermsIsPartOf);
			}
		}
		return (String[]) dctermsIsPartOfList.toArray();
	}

	@Override
	public String[] getDcTermsIsReferencedBy() {
		ArrayList<String> dctermsIsReferencedByList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsIsReferencedBy : proxy.getDctermsIsReferencedBy()){
				dctermsIsReferencedByList.add(dctermsIsReferencedBy);
			}
		}
		return (String[]) dctermsIsReferencedByList.toArray();
	}

	@Override
	public String[] getDcTermsIsReplacedBy() {
		ArrayList<String> dctermsIsReplacedByList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsIsReplacedBy : proxy.getDctermsIsReplacedBy()){
				dctermsIsReplacedByList.add(dctermsIsReplacedBy);
			}
		}
		return (String[]) dctermsIsReplacedByList.toArray();
	}

	@Override
	public String[] getDcTermsIsRequiredBy() {
		ArrayList<String> dctermsIsRequiredByList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsIsRequiredBy : proxy.getDctermsIsRequiredBy()){
				dctermsIsRequiredByList.add(dctermsIsRequiredBy);
			}
		}
		return (String[]) dctermsIsRequiredByList.toArray();
	}

	@Override
	public String[] getDcTermsIsVersionOf() {
		ArrayList<String> dctermsIsVersionOfList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsIsVersionOf : proxy.getDctermsIsVersionOf()){
				dctermsIsVersionOfList.add(dctermsIsVersionOf);
			}
		}
		return (String[]) dctermsIsVersionOfList.toArray();
	}

	@Override
	public String[] getDcTermsIssued() {
		ArrayList<String> dctermsIssuedList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsIssued : proxy.getDctermsIssued()){
				dctermsIssuedList.add(dctermsIssued);
			}
		}
		return (String[]) dctermsIssuedList.toArray();
	}

	@Override
	public String[] getDcTermsMedium() {
		ArrayList<String> dctermsMediumList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsMedium : proxy.getDctermsMedium()){
				dctermsMediumList.add(dctermsMedium);
			}
		}
		return (String[]) dctermsMediumList.toArray();
	}

	@Override
	public String[] getDcTermsProvenance() {
		ArrayList<String> dctermsProvenanceList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsProvenance : proxy.getDctermsProvenance()){
				dctermsProvenanceList.add(dctermsProvenance);
			}
		}
		return (String[]) dctermsProvenanceList.toArray();
	}

	@Override
	public String[] getDcTermsReferences() {
		ArrayList<String> dctermsReferencesList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsReferences : proxy.getDctermsReferences()){
				dctermsReferencesList.add(dctermsReferences);
			}
		}
		return (String[]) dctermsReferencesList.toArray();
	}

	@Override
	public String[] getDcTermsReplaces() {
		ArrayList<String> dctermsReplacesList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsReplaces : proxy.getDctermsReplaces()){
				dctermsReplacesList.add(dctermsReplaces);
			}
		}
		return (String[]) dctermsReplacesList.toArray();
	}

	@Override
	public String[] getDcTermsRequires() {
		ArrayList<String> dctermsRequiresList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsRequires : proxy.getDctermsRequires()){
				dctermsRequiresList.add(dctermsRequires);
			}
		}
		return (String[]) dctermsRequiresList.toArray();
	}

	@Override
	public String[] getDcTermsTableOfContents() {
		ArrayList<String> dctermsTableOfContentsList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsTableOfContents : proxy.getDctermsTOC()){
				dctermsTableOfContentsList.add(dctermsTableOfContents);
			}
		}
		return (String[]) dctermsTableOfContentsList.toArray();
	}

	@Override
	public String[] getDcTermsTemporal() {
		ArrayList<String> dctermsTemporalList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			for(String dctermsTemporal : proxy.getDctermsTOC()){
				dctermsTemporalList.add(dctermsTemporal);
			}
		}
		return (String[]) dctermsTemporalList.toArray();
	}

	@Override
	public String[] getEdmUGC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmCurrentLocation() {
		ArrayList<String> edmCurrentLocationList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
		
				edmCurrentLocationList.add(proxy.getEdmCurrentLocation());
		
		}
		return (String[])edmCurrentLocationList.toArray();
	}

	@Override
	public String[] getEdmIsNextInSequence() {
		ArrayList<String> edmIsNextInSequenceList = new ArrayList<String> ();
		for (Proxy proxy : this.proxies){
			
				edmIsNextInSequenceList.add(proxy.getEdmIsNextInSequence());
			
		}
		return (String[]) edmIsNextInSequenceList.toArray();
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
	public String[] getEdmPlace() {
		ArrayList<String> placeIds = new ArrayList<String>();
		for (Place place : this.places){
			
				placeIds.add(place.getPlaceId().toString());
			
		}
		return (String[]) placeIds.toArray();
	}


	@Override
	public String[] getEdmAgentAltLabels() {
		ArrayList<ArrayList<String>> altLabels = new ArrayList<ArrayList<String>>();
		for (Agent agent : this.agents){
			for(String[] altLabel:agent.getAltLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(altLabel[0]);
				tmp.add(altLabel[1]);
				altLabels.add(tmp);
			}
		}
		return (String[]) altLabels.toArray();
	}

	@Override
	public String[] getEdmPlaceAltLabels() {
		ArrayList<ArrayList<String>> altLabels = new ArrayList<ArrayList<String>>();
		for (Place place : this.places){
			for(String[] altLabel:place.getAltLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(altLabel[0]);
				tmp.add(altLabel[1]);
				altLabels.add(tmp);
			}
		}
		return (String[]) altLabels.toArray();
	}


	@Override
	public String[] getEdmTimespanAltLabels() {
		ArrayList<ArrayList<String>> altLabels = new ArrayList<ArrayList<String>>();
		for (Timespan timespan : this.timespans){
			for(String[] altLabel:timespan.getAltLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(altLabel[0]);
				tmp.add(altLabel[1]);
				altLabels.add(tmp);
			}
		}
		return (String[]) altLabels.toArray();
	}


	@Override
	public String[] getSkosConceptAltLabels() {
		ArrayList<ArrayList<String>> altLabels = new ArrayList<ArrayList<String>>();
		for (Concept concept : this.concepts){
			for(String[] altLabel:concept.getAltLabel()){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(altLabel[0]);
				tmp.add(altLabel[1]);
				altLabels.add(tmp);
			}
		}
		return (String[]) altLabels.toArray();
	}


	@Override
	public Boolean[] getEdmPreviewNoDistribute() {
		// TODO Decide where this field is stored in EDM
		return null;
	}


	@Override
	public String getFullDocUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getDcTermsHasPart() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdmPlaceAltLabel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdmConceptBroaderLabel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdmTimespanBroaderTerm() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdmTimespanBroaderLabel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getEdmPlaceBroaderTerm() {
		// TODO Auto-generated method stub
		return null;
	}
}
