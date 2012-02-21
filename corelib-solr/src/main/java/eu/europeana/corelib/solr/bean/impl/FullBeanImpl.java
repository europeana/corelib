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
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

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
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.FullBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Entity("record")
public class FullBeanImpl implements FullBean {
	@Id
	ObjectId europeanaId;
	@Indexed String about;

	private String[] title;

	private String[] creator;

	private String[] year;

	private String[] provider;

	private String[] language;

	private DocType type;

	private int europeanaCompleteness;

	@Transient
	private ArrayList<BriefBeanImpl> relatedItems;

	@Reference
	private ArrayList<PlaceImpl> places;

	@Reference
	private ArrayList<AgentImpl> agents;

	@Reference
	private ArrayList<TimespanImpl> timespans;

	@Reference
	private ArrayList<ConceptImpl> concepts;

	@Reference
	private ArrayList<AggregationImpl> aggregations;
	
	@Reference
	private ArrayList<ProvidedCHOImpl> providedCHOs;

	// TODO:check if Europeana Aggregation needs to be stored separately
	@Reference
	private EuropeanaAggregation europeanaAggregation;

	@Reference
	private ArrayList<ProxyImpl> proxies;

	@Override
	public ArrayList<PlaceImpl> getPlaces() {
		return places;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPlaces(List<? extends Place> places) {
		this.places = (ArrayList<PlaceImpl>) places;
	}

	@Override
	public ArrayList<AgentImpl> getAgents() {
		return agents;
	}
	@Override
	public String getAbout() {
		return about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setAgents(List<? extends Agent> agents) {
		this.agents = (ArrayList<AgentImpl>) agents;
	}

	@Override
	public ArrayList<TimespanImpl> getTimespans() {
		return timespans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTimespans(List<? extends Timespan> timespans) {
		this.timespans = (ArrayList<TimespanImpl>) timespans;
	}

	@Override
	public ArrayList<ConceptImpl> getConcepts() {
		return concepts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setConcepts(List<? extends Concept> concepts) {
		this.concepts = (ArrayList<ConceptImpl>) concepts;
	}

	@Override
	public ArrayList<AggregationImpl> getAggregations() {
		return aggregations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAggregations(List<? extends Aggregation> aggregations) {
		this.aggregations = (ArrayList<AggregationImpl>) aggregations;
	}

	public EuropeanaAggregation getEuropeanaAggregation() {
		return europeanaAggregation;
	}

	// TODO required??
	public void setEuropeanaAggregation(EuropeanaAggregation europeanaAggregation) {
		this.europeanaAggregation = europeanaAggregation;
	}

	@Override
	public ArrayList<ProxyImpl> getProxies() {
		return proxies;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setProxies(List<? extends Proxy> proxies) {
		this.proxies = (ArrayList<ProxyImpl>) proxies;
	}

	@Override
	public ArrayList<ProvidedCHOImpl> getProvidedCHOs(){
		return providedCHOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setProvidedCHOs(List<? extends ProvidedCHO> providedCHOs) {
		this.providedCHOs = (ArrayList<ProvidedCHOImpl>) providedCHOs;
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
	public void setCreator(String[] creator) {
		this.creator = creator.clone();
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
		return this.title;
	}

	@Override
	public String[] getEdmObject() {
		ArrayList<String> edmObjects = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {
			edmObjects.add(aggregation.getEdmObject());
		}
		return edmObjects.toArray(new String[edmObjects.size()]);
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
		// What if more than one aggregations point to a providedCHO (more edm:dataProvider)
		ArrayList<String> aggregationDataProviders = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {

			aggregationDataProviders.add(aggregation.getEdmDataProvider());

		}
		return aggregationDataProviders.toArray(new String[aggregationDataProviders.size()]);
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
		ArrayList<String> dctermsSpatialList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsSpatial : proxy.getDctermsSpatial()) {
				dctermsSpatialList.add(dctermsSpatial);
			}
		}
		return dctermsSpatialList.toArray(new String[dctermsSpatialList.size()]);
	}

	@Override
	public int getEuropeanaCompleteness() {
		return this.europeanaCompleteness;
	}

	@Override
	public ArrayList<Map<String, String>> getEdmPlaceLabel() {
		ArrayList<Map<String, String>> prefLabels = new ArrayList<Map<String, String>>();
		for (Place place : this.places) {
			prefLabels.add(place.getAltLabel());
		}
		return prefLabels;
	}

	@Override
	public String[] getEdmPlaceIsPartOf() {
		ArrayList<String> broaderPlaces = new ArrayList<String>();
		for (Place place : this.places) {
			for (String broaderPlace : place.getIsPartOf()) {
				broaderPlaces.add(broaderPlace);
			}
		}
		return broaderPlaces.toArray(new String[broaderPlaces.size()]);
	}

	@Override
	public Float[] getEdmPlaceLatitude() {
		ArrayList<Float> latitudes = new ArrayList<Float>();
		for (Place place : this.places) {
			latitudes.add(place.getLatitude());
		}
		return latitudes.toArray(new Float[latitudes.size()]);
	}

	@Override
	public Float[] getEdmPlaceLongitude() {
		ArrayList<Float> longitudes = new ArrayList<Float>();
		for (Place place : this.places) {
			longitudes.add(place.getLongitude());
		}
		return longitudes.toArray(new Float[longitudes.size()]);
	}

	@Override
	public String[] getEdmTimespan() {
		ArrayList<String> timespanIds = new ArrayList<String>();
		for (Timespan timespan : this.timespans) {

			timespanIds.add(timespan.getId().toString());

		}
		return timespanIds.toArray(new String[timespanIds.size()]);
	}

	@Override
	public ArrayList<Map<String, String>> getEdmTimespanLabel() {
		ArrayList<Map<String, String>> prefLabels = new ArrayList<Map<String, String>>();
		for (Timespan timespan : this.timespans) {
			prefLabels.add(timespan.getPrefLabel());
		}

		return prefLabels;
	}

	@Override
	public String[] getEdmTimespanIsPartOf() {
		ArrayList<String> broaderPeriods = new ArrayList<String>();
		for (Timespan timespan : this.timespans) {
			for (String broaderPeriod : timespan.getIsPartOf()) {
				broaderPeriods.add(broaderPeriod);
			}
		}
		return broaderPeriods.toArray(new String[broaderPeriods.size()]);
	}

	@Override
	public Date[] getEdmTimespanBegin() {
		ArrayList<Date> startDates = new ArrayList<Date>();
		for (Timespan timespan : this.timespans) {

			startDates.add(timespan.getBegin());

		}
		return startDates.toArray(new Date[startDates.size()]);
	}

	@Override
	public Date[] getEdmTimespanEnd() {
		ArrayList<Date> endDates = new ArrayList<Date>();
		for (Timespan timespan : this.timespans) {

			endDates.add(timespan.getEnd());
		}
		return endDates.toArray(new Date[endDates.size()]);
	}

	@Override
	public String[] getEdmConcept() {
		ArrayList<String> conceptIds = new ArrayList<String>();
		for (Concept concept : this.concepts) {

			conceptIds.add(concept.getId().toString());

		}
		return conceptIds.toArray(new String[conceptIds.size()]);
	}

	@Override
	public ArrayList<Map<String, String>> getEdmConceptLabel() {
		ArrayList<Map<String, String>> prefLabels = new ArrayList<Map<String, String>>();
		for (Concept concept : this.concepts) {
			prefLabels.add(concept.getPrefLabel());

		}
		return prefLabels;
	}

	@Override
	public String[] getEdmConceptBroaderTerm() {
		ArrayList<String> broaderTerms = new ArrayList<String>();
		for (Concept concept : this.concepts)
			for (String broaderTerm : concept.getBroader()) {
				broaderTerms.add(broaderTerm);
			}
		return broaderTerms.toArray(new String[broaderTerms.size()]);
	}

	@Override
	public String[] getEdmAgent() {
		ArrayList<String> agentIds = new ArrayList<String>();
		for (Agent agent : this.agents) {

			agentIds.add(agent.getId().toString());

		}
		return agentIds.toArray(new String[agentIds.size()]);
	}

	@Override
	public ArrayList<Map<String, String>> getEdmAgentLabel() {
		ArrayList<Map<String, String>> prefLabels = new ArrayList<Map<String, String>>();
		for (Agent agent : this.agents) {

			prefLabels.add(agent.getPrefLabel());

		}
		return prefLabels;
	}

	@Override
	public String[] getEdmIsShownBy() {
		ArrayList<String> aggregationIsShownByList = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {

			aggregationIsShownByList.add(aggregation.getEdmIsShownBy());

		}
		return aggregationIsShownByList.toArray(new String[aggregationIsShownByList.size()]);
	}

	@Override
	public String[] getEdmIsShownAt() {
		ArrayList<String> aggregationIsShownAtList = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {

			aggregationIsShownAtList.add(aggregation.getEdmIsShownAt());

		}
		return aggregationIsShownAtList.toArray(new String[aggregationIsShownAtList.size()]);
	}

	@Override
	public String[] getEdmProvider() {
		ArrayList<String> aggregationProviders = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {

			aggregationProviders.add(aggregation.getEdmProvider());

		}
		return aggregationProviders.toArray(new String[aggregationProviders.size()]);
	}

	@Override
	public String[] getAggregationDcRights() {
		ArrayList<String> aggregationDcRightsList = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {
			for (String aggregationDcRights : aggregation.getDcRights()) {
				aggregationDcRightsList.add(aggregationDcRights);
			}
		}
		return aggregationDcRightsList.toArray(new String[aggregationDcRightsList.size()]);
	}

	@Override
	public String[] getAggregationEdmRights() {
		ArrayList<String> aggregationEdmRightsList = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {

			aggregationEdmRightsList.add(aggregation.getEdmRights());

		}
		return aggregationEdmRightsList.toArray(new String[aggregationEdmRightsList.size()]);
	}

	@Override
	public String[] getEdmWebResource() {
		ArrayList<String> webResourceUrls = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {
			for (WebResource webResource : aggregation.getWebResources()) {

				webResourceUrls.add(webResource.getWebResource());
			}
		}
		return webResourceUrls.toArray(new String[webResourceUrls.size()]);
	}

	@Override
	public String[] getEdmWebResourceDcRights() {
		ArrayList<String> webResourceDcRightsList = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {
			for (WebResource webResource : aggregation.getWebResources())
				for (String webResourceDcRights : webResource.getWebResourceDcRights()) {
					webResourceDcRightsList.add(webResourceDcRights);
				}
		}
		return webResourceDcRightsList.toArray(new String[webResourceDcRightsList.size()]);
	}

	@Override
	public String[] getEdmWebResourceEdmRights() {
		ArrayList<String> webResourceEdmRightsList = new ArrayList<String>();
		for (Aggregation aggregation : this.aggregations) {
			for (WebResource webResource : aggregation.getWebResources()) {
				webResourceEdmRightsList.add(webResource.getWebResourceEdmRights());
			}
		}
		return webResourceEdmRightsList.toArray(new String[webResourceEdmRightsList.size()]);
	}

	@Override
	public String[] getOreProxy() {
		ArrayList<String> owlProxies = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {

			owlProxies.add(proxy.getId().toString());

		}
		return owlProxies.toArray(new String[owlProxies.size()]);
	}

	@Override
	public String[] getOwlSameAs() {
		ArrayList<String> owlSameAsList = new ArrayList<String>();
		for (ProvidedCHO providedCHO: this.providedCHOs) {
			for (String owlSameAs : providedCHO.getOwlSameAs()) {
				owlSameAsList.add(owlSameAs);
			}
		}
		return owlSameAsList.toArray(new String[owlSameAsList.size()]);
	}

	@Override
	public String[] getDcCoverage() {
		ArrayList<String> dcCoverageList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dcCoverage : proxy.getDcCoverage()) {
				dcCoverageList.add(dcCoverage);
			}
		}
		return dcCoverageList.toArray(new String[dcCoverageList.size()]);
	}

	@Override
	public String[] getDcPublisher() {
		ArrayList<String> dcPublisherList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dcPublisher : proxy.getDcPublisher()) {
				dcPublisherList.add(dcPublisher);
			}
		}
		return dcPublisherList.toArray(new String[dcPublisherList.size()]);
	}

	@Override
	public String[] getDcIdentifier() {
		ArrayList<String> dcIdentifierList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dcIdentifier : proxy.getDcIdentifier()) {
				dcIdentifierList.add(dcIdentifier);
			}
		}
		return dcIdentifierList.toArray(new String[dcIdentifierList.size()]);
	}

	@Override
	public String[] getDcRelation() {
		ArrayList<String> dcRelationList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dcRelation : proxy.getDcRelation()) {
				dcRelationList.add(dcRelation);
			}
		}
		return dcRelationList.toArray(new String[dcRelationList.size()]);
	}

	@Override
	public String[] getProxyDcRights() {
		ArrayList<String> dcRightsList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dcRights : proxy.getDcRights()) {
				dcRightsList.add(dcRights);
			}
		}
		return dcRightsList.toArray(new String[dcRightsList.size()]);
	}

	@Override
	public String[] getDcSource() {
		ArrayList<String> dcSourceList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dcSource : proxy.getDcSource()) {
				dcSourceList.add(dcSource);
			}
		}
		return dcSourceList.toArray(new String[dcSourceList.size()]);
	}

	@Override
	public String[] getDcTermsAlternative() {
		ArrayList<String> dctermsAlternativeList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsAlternative : proxy.getDctermsAlternative()) {
				dctermsAlternativeList.add(dctermsAlternative);
			}
		}
		return dctermsAlternativeList.toArray(new String[dctermsAlternativeList.size()]);
	}

	@Override
	public String[] getDcTermsConformsTo() {
		ArrayList<String> dctermsConformsToList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsConformsTo : proxy.getDctermsConformsTo()) {
				dctermsConformsToList.add(dctermsConformsTo);
			}
		}
		return dctermsConformsToList.toArray(new String[dctermsConformsToList.size()]);
	}

	@Override
	public Date[] getDcTermsCreated() {
		ArrayList<Date> dctermsCreatedList = new ArrayList<Date>();
		for (Proxy proxy : this.proxies) {
			for (Date dctermsCreated : proxy.getDctermsCreated()) {
				dctermsCreatedList.add(dctermsCreated);
			}
		}
		return dctermsCreatedList.toArray(new Date[dctermsCreatedList.size()]);
	}

	@Override
	public String[] getDcTermsExtent() {
		ArrayList<String> dctermsExtentList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsExtent : proxy.getDctermsExtent()) {
				dctermsExtentList.add(dctermsExtent);
			}
		}
		return dctermsExtentList.toArray(new String[dctermsExtentList.size()]);
	}

	@Override
	public String[] getDcTermsHasFormat() {
		ArrayList<String> dctermsHasFormatList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsHasFormat : proxy.getDctermsHasFormat()) {
				dctermsHasFormatList.add(dctermsHasFormat);
			}
		}
		return dctermsHasFormatList.toArray(new String[dctermsHasFormatList.size()]);
	}

	@Override
	public String[] getDcTermsIsPartOf() {
		ArrayList<String> dctermsIsPartOfList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsIsPartOf : proxy.getDctermsIsPartOf()) {
				dctermsIsPartOfList.add(dctermsIsPartOf);
			}
		}
		return dctermsIsPartOfList.toArray(new String[dctermsIsPartOfList.size()]);
	}

	@Override
	public String[] getDcTermsIsReferencedBy() {
		ArrayList<String> dctermsIsReferencedByList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsIsReferencedBy : proxy.getDctermsIsReferencedBy()) {
				dctermsIsReferencedByList.add(dctermsIsReferencedBy);
			}
		}
		return dctermsIsReferencedByList.toArray(new String[dctermsIsReferencedByList.size()]);
	}

	@Override
	public String[] getDcTermsIsReplacedBy() {
		ArrayList<String> dctermsIsReplacedByList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsIsReplacedBy : proxy.getDctermsIsReplacedBy()) {
				dctermsIsReplacedByList.add(dctermsIsReplacedBy);
			}
		}
		return dctermsIsReplacedByList.toArray(new String[dctermsIsReplacedByList.size()]);
	}

	@Override
	public String[] getDcTermsIsRequiredBy() {
		ArrayList<String> dctermsIsRequiredByList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsIsRequiredBy : proxy.getDctermsIsRequiredBy()) {
				dctermsIsRequiredByList.add(dctermsIsRequiredBy);
			}
		}
		return dctermsIsRequiredByList.toArray(new String[dctermsIsRequiredByList.size()]);
	}

	@Override
	public String[] getDcTermsIsVersionOf() {
		ArrayList<String> dctermsIsVersionOfList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsIsVersionOf : proxy.getDctermsIsVersionOf()) {
				dctermsIsVersionOfList.add(dctermsIsVersionOf);
			}
		}
		return dctermsIsVersionOfList.toArray(new String[dctermsIsVersionOfList.size()]);
	}

	@Override
	public String[] getDcTermsIssued() {
		ArrayList<String> dctermsIssuedList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsIssued : proxy.getDctermsIssued()) {
				dctermsIssuedList.add(dctermsIssued);
			}
		}
		return dctermsIssuedList.toArray(new String[dctermsIssuedList.size()]);
	}

	@Override
	public String[] getDcTermsMedium() {
		ArrayList<String> dctermsMediumList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsMedium : proxy.getDctermsMedium()) {
				dctermsMediumList.add(dctermsMedium);
			}
		}
		return dctermsMediumList.toArray(new String[dctermsMediumList.size()]);
	}

	@Override
	public String[] getDcTermsProvenance() {
		ArrayList<String> dctermsProvenanceList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsProvenance : proxy.getDctermsProvenance()) {
				dctermsProvenanceList.add(dctermsProvenance);
			}
		}
		return dctermsProvenanceList.toArray(new String[dctermsProvenanceList.size()]);
	}

	@Override
	public String[] getDcTermsReferences() {
		ArrayList<String> dctermsReferencesList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsReferences : proxy.getDctermsReferences()) {
				dctermsReferencesList.add(dctermsReferences);
			}
		}
		return dctermsReferencesList.toArray(new String[dctermsReferencesList.size()]);
	}

	@Override
	public String[] getDcTermsReplaces() {
		ArrayList<String> dctermsReplacesList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsReplaces : proxy.getDctermsReplaces()) {
				dctermsReplacesList.add(dctermsReplaces);
			}
		}
		return dctermsReplacesList.toArray(new String[dctermsReplacesList.size()]);
	}

	@Override
	public String[] getDcTermsRequires() {
		ArrayList<String> dctermsRequiresList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsRequires : proxy.getDctermsRequires()) {
				dctermsRequiresList.add(dctermsRequires);
			}
		}
		return dctermsRequiresList.toArray(new String[dctermsRequiresList.size()]);
	}

	@Override
	public String[] getDcTermsTableOfContents() {
		ArrayList<String> dctermsTableOfContentsList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsTableOfContents : proxy.getDctermsTOC()) {
				dctermsTableOfContentsList.add(dctermsTableOfContents);
			}
		}
		return dctermsTableOfContentsList.toArray(new String[dctermsTableOfContentsList.size()]);
	}

	@Override
	public String[] getDcTermsTemporal() {
		ArrayList<String> dctermsTemporalList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {
			for (String dctermsTemporal : proxy.getDctermsTOC()) {
				dctermsTemporalList.add(dctermsTemporal);
			}
		}
		return dctermsTemporalList.toArray(new String[dctermsTemporalList.size()]);
	}

	@Override
	public String[] getEdmUGC() {
		ArrayList<String> edmUGCList = new ArrayList<String>();
		for(Aggregation aggregation:this.aggregations){
			edmUGCList.add(aggregation.getEdmUgc());
		}
		return edmUGCList.toArray(new String[edmUGCList.size()]);
	}

	@Override
	public String[] getEdmCurrentLocation() {
		ArrayList<String> edmCurrentLocationList = new ArrayList<String>();
		for (Proxy proxy : this.proxies) {

			edmCurrentLocationList.add(proxy.getEdmCurrentLocation());

		}
		return edmCurrentLocationList.toArray(new String[edmCurrentLocationList.size()]);
	}

	@Override
	public String[] getEdmIsNextInSequence() {
		ArrayList<String> edmIsNextInSequenceList = new ArrayList<String>();
		for (ProvidedCHO providedCHO:this.providedCHOs) {

			edmIsNextInSequenceList.add(providedCHO.getEdmIsNextInSequence());

		}
		return edmIsNextInSequenceList.toArray(new String[edmIsNextInSequenceList.size()]);
	}

	@Override
	public String[] getUserTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmAgentSkosNote() {
		ArrayList<String> agentNotes = new ArrayList<String>();
		for (Agent agent : this.agents)
			for (String agentNote : agent.getNote()) {
				agentNotes.add(agentNote);
			}
		return agentNotes.toArray(new String[agentNotes.size()]);
	}

	@Override
	public Date[] getEdmAgentBegin() {
		ArrayList<Date> agentBeginDates = new ArrayList<Date>();
		for (Agent agent : this.agents) {
			agentBeginDates.add(agent.getBegin());
		}
		return agentBeginDates.toArray(new Date[agentBeginDates.size()]);
	}

	@Override
	public Date[] getEdmAgentEnd() {
		ArrayList<Date> agentEndDates = new ArrayList<Date>();
		for (Agent agent : this.agents) {
			agentEndDates.add(agent.getEnd());
		}
		return agentEndDates.toArray(new Date[agentEndDates.size()]);
	}

	@Override
	public String[] getEdmTimeSpanSkosNote() {
		ArrayList<String> notes = new ArrayList<String>();
		for (Timespan timespan : this.timespans) {
			for (String note : timespan.getNote()) {
				notes.add(note);
			}
		}
		return notes.toArray(new String[notes.size()]);
	}

	@Override
	public String[] getEdmPlaceSkosNote() {
		ArrayList<String> notes = new ArrayList<String>();
		for (Place place : this.places) {
			for (String note : place.getNote()) {
				notes.add(note);
			}
		}
		return notes.toArray(new String[notes.size()]);
	}

	@Override
	public String[] getEdmConceptNote() {
		ArrayList<String> notes = new ArrayList<String>();
		for (Concept concept : this.concepts) {
			for (String note : concept.getNote()) {
				notes.add(note);
			}
		}
		return notes.toArray(new String[notes.size()]);
	}

	@Override
	public String getId() {
		return this.europeanaId.toString();
	}

	@Override
	public String[] getEdmPlace() {
		ArrayList<String> placeIds = new ArrayList<String>();
		for (Place place : this.places) {

			placeIds.add(place.getId().toString());

		}
		return placeIds.toArray(new String[placeIds.size()]);
	}

	@Override
	public ArrayList<Map<String, String>> getEdmAgentAltLabels() {
		ArrayList<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
		for (Agent agent : this.agents) {

			altLabels.add(agent.getAltLabel());

		}
		return altLabels;
	}

	@Override
	public ArrayList<Map<String, String>> getEdmPlaceAltLabels() {
		ArrayList<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
		for (Place place : this.places) {

			altLabels.add(place.getAltLabel());

		}
		return altLabels;
	}

	@Override
	public ArrayList<Map<String, String>> getEdmTimespanAltLabels() {
		ArrayList<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
		for (Timespan timespan : this.timespans) {

			altLabels.add(timespan.getAltLabel());
		}
		return altLabels;
	}

	@Override
	public ArrayList<Map<String, String>> getSkosConceptAltLabels() {
		ArrayList<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
		for (Concept concept : this.concepts) {

			altLabels.add(concept.getAltLabel());

		}
		return altLabels;
	}

	@Override
	public Boolean[] getEdmPreviewNoDistribute() {
		ArrayList<Boolean> edmPreviewNoDistributeList = new ArrayList<Boolean>();
		for(Aggregation aggregation:aggregations){
			edmPreviewNoDistributeList.add(aggregation.getEdmPreviewNoDistribute());
		}
		return edmPreviewNoDistributeList.toArray(new Boolean[edmPreviewNoDistributeList.size()]);
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
	public ArrayList<Map<String, String>> getEdmPlaceAltLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map<String, String>> getEdmConceptBroaderLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmTimespanBroaderTerm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Map<String, String>> getEdmTimespanBroaderLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmPlaceBroaderTerm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return this.getId().equals(((FullBeanImpl) o).getId());
	}

	@Override
	public List<? extends BriefBean> getRelatedItems() {
		return this.relatedItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setRelatedItems(List<? extends BriefBean> relatedItems) {
		this.relatedItems = (ArrayList<BriefBeanImpl>) relatedItems;

	}

}
