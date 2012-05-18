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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexed;
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
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.FullBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@SuppressWarnings("unchecked")
@Entity("record")
public class FullBeanImpl implements FullBean {

	@Id
	private ObjectId europeanaId;

	@Indexed(unique = true)
	private String about;

	private String[] title;

	private String[] year;

	private String[] provider;

	private String[] language;

	private DocType type;

	private int europeanaCompleteness;
	
	@Transient
	private List<BriefBeanImpl> relatedItems;

	@Embedded
	@Indexed (unique=false)
	private List<PlaceImpl> places;

	@Embedded
	@Indexed (unique=false)
	private List<AgentImpl> agents;

	@Embedded
	@Indexed (unique=false)
	private List<TimespanImpl> timespans;

	@Embedded
	@Indexed (unique=false)
	private List<ConceptImpl> concepts;

	@Embedded
	private List<AggregationImpl> aggregations;

	@Embedded
	private List<ProvidedCHOImpl> providedCHOs;

	// TODO:check if Europeana Aggregation needs to be stored separately
	@Embedded
	private EuropeanaAggregation europeanaAggregation;

	@Embedded
	private List<ProxyImpl> proxies;

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
		if (agents != null) {
			this.agents = (List<AgentImpl>) agents;
		} else {
			this.agents = null;
		}
	}

	@Override
	public List<TimespanImpl> getTimespans() {
		return this.timespans;
	}

	@Override
	public void setTimespans(List<? extends Timespan> timespans) {
		if (timespans != null) {
			this.timespans = (List<TimespanImpl>) timespans;
		} else {
			this.timespans = null;
		}
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

	public EuropeanaAggregation getEuropeanaAggregation() {
		return this.europeanaAggregation;
	}

	// TODO required??
	public void setEuropeanaAggregation(EuropeanaAggregation europeanaAggregation) {
		this.europeanaAggregation = europeanaAggregation;
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
	public String[] getEdmObject() {
		if (this.aggregations != null) {
			ArrayList<String> edmObjects = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				edmObjects.add(aggregation.getEdmObject());
			}
			return StringArrayUtils.toArray(edmObjects);
		}
		return null;
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
	public String[] getDataProvider() {
		// What if more than one aggregations point to a providedCHO (more
		// edm:dataProvider)
		if (this.aggregations != null) {
			ArrayList<String> aggregationDataProvidersList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				aggregationDataProvidersList.add(aggregation.getEdmDataProvider());
			}
			return StringArrayUtils.toArray(aggregationDataProvidersList);
		}
		return null;
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
	public String[] getDctermsSpatial() {
		if (this.proxies != null) {
			ArrayList<String> dctermsSpatialList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsSpatialList, proxy.getDctermsSpatial());
			}
			return StringArrayUtils.toArray(dctermsSpatialList);
		}
		return null;
	}

	@Override
	public int getEuropeanaCompleteness() {
		return this.europeanaCompleteness;
	}

	@Override
	public List<Map<String, String>> getEdmPlaceLabel() {
		if (this.places != null) {
			List<Map<String, String>> prefLabels = new ArrayList<Map<String, String>>();
			for (Place place : this.places) {
				prefLabels.add(place.getAltLabel());
			}
			return prefLabels;
		}
		return null;
	}

	@Override
	public String[] getEdmPlaceIsPartOf() {
		if (this.places != null) {
			List<String> broaderPlacesList = new ArrayList<String>();
			for (Place place : this.places) {
				StringArrayUtils.addToList(broaderPlacesList, place.getIsPartOf());
			}
			return StringArrayUtils.toArray(broaderPlacesList);
		}
		return null;
	}

	@Override
	public Float getEdmPlaceLatitude() {
		if (this.places != null) {
			List<Float> latitudesList = new ArrayList<Float>();
			for (Place place : this.places) {
				latitudesList.add(place.getLatitude());
			}
			return latitudesList.toArray(new Float[latitudesList.size()])[0];
		}
		return null;
	}

	@Override
	public Float getEdmPlaceLongitude() {
		if (this.places != null) {
			List<Float> longitudesList = new ArrayList<Float>();
			for (Place place : this.places) {
				longitudesList.add(place.getLongitude());
			}
			return longitudesList.toArray(new Float[longitudesList.size()])[0];
		}
		return null;
	}

	@Override
	public String[] getEdmTimespan() {
		if (this.timespans != null) {
			List<String> timespanIds = new ArrayList<String>();
			for (Timespan timespan : this.timespans) {
				if (timespan.getId() != null) {
					timespanIds.add(timespan.getId().toString());
				}
			}
			return StringArrayUtils.toArray(timespanIds);
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmTimespanLabel() {
		if (this.timespans != null) {
			ArrayList<Map<String, String>> prefLabels = new ArrayList<Map<String, String>>();
			for (Timespan timespan : this.timespans) {
				prefLabels.add(timespan.getPrefLabel());
			}
			return prefLabels;
		}
		return null;
	}

	@Override
	public String[] getEdmTimespanIsPartOf() {
		if (this.timespans != null) {
			List<String> broaderPeriodList = new ArrayList<String>();
			for (Timespan timespan : this.timespans) {
				StringArrayUtils.addToList(broaderPeriodList, timespan.getIsPartOf());
			}
			return StringArrayUtils.toArray(broaderPeriodList);
		}
		return null;
	}

	@Override
	public String[] getEdmTimespanBegin() {
		if (this.timespans != null) {
			List<String> startDateList = new ArrayList<String>();
			for (Timespan timespan : this.timespans) {
				startDateList.add(timespan.getBegin());
			}
			return StringArrayUtils.toArray(startDateList);
		}
		return null;
	}

	@Override
	public String[] getEdmTimespanEnd() {
		if (this.timespans != null) {
			ArrayList<String> endDateList = new ArrayList<String>();
			for (Timespan timespan : this.timespans) {
				endDateList.add(timespan.getEnd());
			}
			return StringArrayUtils.toArray(endDateList);
		}
		return null;
	}

	@Override
	public String[] getEdmConcept() {
		if (this.concepts != null) {
			List<String> conceptIdList = new ArrayList<String>();
			for (Concept concept : this.concepts) {
				conceptIdList.add(concept.getId().toString());
			}
			return StringArrayUtils.toArray(conceptIdList);
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmConceptLabel() {
		if (this.concepts != null) {
			List<Map<String, String>> prefLabelList = new ArrayList<Map<String, String>>();
			for (Concept concept : this.concepts) {
				prefLabelList.add(concept.getPrefLabel());
			}
			return prefLabelList;
		}
		return null;
	}

	@Override
	public String[] getEdmConceptBroaderTerm() {
		if (this.concepts != null) {
			List<String> broaderTermList = new ArrayList<String>();
			for (Concept concept : this.concepts) {
				StringArrayUtils.addToList(broaderTermList, concept.getBroader());
			}
			return StringArrayUtils.toArray(broaderTermList);
		}
		return null;
	}

	@Override
	public String[] getEdmAgent() {
		if (this.agents != null) {
			List<String> agentIdList = new ArrayList<String>();
			for (Agent agent : this.agents) {
				if (agent.getId() != null) {
					agentIdList.add(agent.getId().toString());
				}
			}
			return StringArrayUtils.toArray(agentIdList);
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmAgentLabel() {
		if (this.agents != null) {
			List<Map<String, String>> prefLabelList = new ArrayList<Map<String, String>>();
			for (Agent agent : this.agents) {
				prefLabelList.add(agent.getPrefLabel());
			}
			return prefLabelList;
		}
		return null;
	}

	@Override
	public String[] getEdmIsShownBy() {
		if (this.aggregations != null) {
			List<String> aggregationIsShownByList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				aggregationIsShownByList.add(aggregation.getEdmIsShownBy());
			}
			return StringArrayUtils.toArray(aggregationIsShownByList);
		}
		return null;
	}

	@Override
	public String[] getEdmIsShownAt() {
		if (this.aggregations != null) {
			List<String> aggregationIsShownAtList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				aggregationIsShownAtList.add(aggregation.getEdmIsShownAt());
			}
			return StringArrayUtils.toArray(aggregationIsShownAtList);
		}
		return null;
	}

	@Override
	public String[] getEdmProvider() {
		if (this.aggregations != null) {
			List<String> aggregationProviderList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				aggregationProviderList.add(aggregation.getEdmProvider());
			}
			return StringArrayUtils.toArray(aggregationProviderList);
		}
		return null;
	}

	@Override
	public String[] getAggregationDcRights() {
		if (this.aggregations != null) {
			List<String> aggregationDcRightsList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				StringArrayUtils.addToList(aggregationDcRightsList, aggregation.getDcRights());
			}
			return StringArrayUtils.toArray(aggregationDcRightsList);
		}
		return null;
	}

	@Override
	public String[] getAggregationEdmRights() {
		if (this.aggregations != null) {
			List<String> aggregationEdmRightsList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				aggregationEdmRightsList.add(aggregation.getEdmRights());
			}
			return StringArrayUtils.toArray(aggregationEdmRightsList);
		}
		return null;
	}

	@Override
	public String[] getEdmWebResource() {
		if (this.aggregations != null) {
			List<String> webResourceUrlList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				if (aggregation.getWebResources() != null) {
					for (WebResource webResource : aggregation.getWebResources()) {
						webResourceUrlList.add(webResource.getAbout());
					}
				}
			}
			return StringArrayUtils.toArray(webResourceUrlList);
		}
		return null;
	}

	@Override
	public String[] getEdmWebResourceDcRights() {
		if (this.aggregations != null) {
			List<String> webResourceDcRightsList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				if (aggregation.getWebResources() != null) {
					for (WebResource webResource : aggregation.getWebResources()) {
						StringArrayUtils.addToList(webResourceDcRightsList, webResource.getWebResourceDcRights());
					}
				}
			}
			return StringArrayUtils.toArray(webResourceDcRightsList);
		}
		return null;
	}

	@Override
	public String[] getEdmWebResourceEdmRights() {
		if (this.aggregations != null) {
			List<String> webResourceEdmRightsList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				if (aggregation.getWebResources() != null) {
					for (WebResource webResource : aggregation.getWebResources()) {
						webResourceEdmRightsList.add(webResource.getWebResourceEdmRights());
					}
				}
			}
			return StringArrayUtils.toArray(webResourceEdmRightsList);
		}
		return null;
	}

	@Override
	public String[] getOreProxy() {
		if (this.proxies != null) {
			List<String> owlProxyList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				if (proxy.getId() != null) {
					owlProxyList.add(proxy.getId().toString());
				}
			}
			return StringArrayUtils.toArray(owlProxyList);
		}
		return null;
	}

	@Override
	public String[] getOwlSameAs() {
		if (this.providedCHOs != null) {
			List<String> owlSameAsList = new ArrayList<String>();
			for (ProvidedCHO providedCHO : this.providedCHOs) {
				StringArrayUtils.addToList(owlSameAsList, providedCHO.getOwlSameAs());
			}
			return StringArrayUtils.toArray(owlSameAsList);
		}
		return null;
	}

	@Override
	public String[] getDcCoverage() {
		if (this.proxies != null) {
			List<String> dcCoverageList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dcCoverageList, proxy.getDcCoverage());
			}
			return StringArrayUtils.toArray(dcCoverageList);
		}
		return null;
	}

	@Override
	public String[] getDcPublisher() {
		if (this.proxies != null) {
			List<String> dcPublisherList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dcPublisherList, proxy.getDcPublisher());
			}
			return StringArrayUtils.toArray(dcPublisherList);
		}
		return null;
	}

	@Override
	public String[] getDcIdentifier() {
		if (this.proxies != null) {
			List<String> dcIdentifierList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dcIdentifierList, proxy.getDcIdentifier());
			}
			return StringArrayUtils.toArray(dcIdentifierList);
		}
		return null;
	}

	@Override
	public String[] getDcRelation() {
		if (this.proxies != null) {
			List<String> dcRelationList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dcRelationList, proxy.getDcRelation());
			}
			return StringArrayUtils.toArray(dcRelationList);
		}
		return null;
	}

	@Override
	public String[] getProxyDcRights() {
		if (this.proxies != null) {
			List<String> dcRightsList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dcRightsList, proxy.getDcRights());
			}
			return StringArrayUtils.toArray(dcRightsList);
		}
		return null;
	}

	@Override
	public String[] getDcSource() {
		if (this.proxies != null) {
			List<String> dcSourceList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dcSourceList, proxy.getDcSource());
			}
			return StringArrayUtils.toArray(dcSourceList);
		}
		return null;
	}

	@Override
	public String[] getDctermsAlternative() {
		if (this.proxies != null) {
			List<String> dctermsAlternativeList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsAlternativeList, proxy.getDctermsAlternative());
			}
			return StringArrayUtils.toArray(dctermsAlternativeList);
		}
		return null;
	}

	@Override
	public String[] getDctermsConformsTo() {
		if (this.proxies != null) {
			List<String> dctermsConformsToList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsConformsToList, proxy.getDctermsConformsTo());
			}
			return StringArrayUtils.toArray(dctermsConformsToList);
		}
		return null;
	}

	@Override
	public String[] getDctermsCreated() {
		if (this.proxies != null) {
			List<String> dctermsCreatedList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsCreatedList, proxy.getDctermsCreated());
			}
			return StringArrayUtils.toArray(dctermsCreatedList);
		}
		return null;
	}

	@Override
	public String[] getDctermsExtent() {
		if (this.proxies != null) {
			List<String> dctermsExtentList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsExtentList, proxy.getDctermsExtent());
			}
			return StringArrayUtils.toArray(dctermsExtentList);
		}
		return null;
	}

	@Override
	public String[] getDctermsHasFormat() {
		if (this.proxies != null) {
			List<String> dctermsHasFormatList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsHasFormatList, proxy.getDctermsHasFormat());
			}
			return StringArrayUtils.toArray(dctermsHasFormatList);
		}
		return null;
	}

	@Override
	public String[] getDctermsIsPartOf() {
		if (this.proxies != null) {
			List<String> dctermsIsPartOfList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsIsPartOfList, proxy.getDctermsIsPartOf());
			}
			return StringArrayUtils.toArray(dctermsIsPartOfList);
		}
		return null;
	}

	@Override
	public String[] getDctermsIsReferencedBy() {
		if (this.proxies != null) {
			List<String> dctermsIsReferencedByList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsIsReferencedByList, proxy.getDctermsIsReferencedBy());
			}
			return StringArrayUtils.toArray(dctermsIsReferencedByList);
		}
		return null;
	}

	@Override
	public String[] getDctermsIsReplacedBy() {
		if (this.proxies != null) {
			List<String> dctermsIsReplacedByList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsIsReplacedByList, proxy.getDctermsIsReplacedBy());
			}
			return StringArrayUtils.toArray(dctermsIsReplacedByList);
		}
		return null;
	}

	@Override
	public String[] getDctermsIsRequiredBy() {
		if (this.proxies != null) {
			List<String> dctermsIsRequiredByList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsIsRequiredByList, proxy.getDctermsIsRequiredBy());
			}
			return StringArrayUtils.toArray(dctermsIsRequiredByList);
		}
		return null;
	}

	@Override
	public String[] getDctermsIsVersionOf() {
		if (this.proxies != null) {
			List<String> dctermsIsVersionOfList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsIsVersionOfList, proxy.getDctermsIsVersionOf());
			}
			return StringArrayUtils.toArray(dctermsIsVersionOfList);
		}
		return null;
	}

	@Override
	public String[] getDctermsIssued() {
		if (this.proxies != null) {
			List<String> dctermsIssuedList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsIssuedList, proxy.getDctermsIssued());
			}
			return StringArrayUtils.toArray(dctermsIssuedList);
		}
		return null;
	}

	@Override
	public String[] getDctermsMedium() {
		if (this.proxies != null) {
			List<String> dctermsMediumList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsMediumList, proxy.getDctermsMedium());
			}
			return StringArrayUtils.toArray(dctermsMediumList);
		}
		return null;
	}

	@Override
	public String[] getDctermsProvenance() {
		if (this.proxies != null) {
			List<String> dctermsProvenanceList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsProvenanceList, proxy.getDctermsProvenance());
			}
			return StringArrayUtils.toArray(dctermsProvenanceList);
		}
		return null;
	}

	@Override
	public String[] getDctermsReferences() {
		if (this.proxies != null) {
			List<String> dctermsReferencesList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsReferencesList, proxy.getDctermsReferences());
			}
			return StringArrayUtils.toArray(dctermsReferencesList);
		}
		return null;
	}

	@Override
	public String[] getDctermsReplaces() {
		if (this.proxies != null) {
			List<String> dctermsReplacesList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsReplacesList, proxy.getDctermsReplaces());
			}
			return StringArrayUtils.toArray(dctermsReplacesList);
		}
		return null;
	}

	@Override
	public String[] getDctermsRequires() {
		if (this.proxies != null) {
			List<String> dctermsRequiresList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsRequiresList, proxy.getDctermsRequires());
			}
			return StringArrayUtils.toArray(dctermsRequiresList);
		}
		return null;
	}

	@Override
	public String[] getDctermsTableOfContents() {
		if (this.proxies != null) {
			List<String> dctermsTableOfContentsList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsTableOfContentsList, proxy.getDctermsTOC());
			}
			return StringArrayUtils.toArray(dctermsTableOfContentsList);
		}
		return null;
	}

	@Override
	public String[] getDctermsTemporal() {
		if (this.proxies != null) {
			List<String> dctermsTemporalList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				StringArrayUtils.addToList(dctermsTemporalList, proxy.getDctermsTemporal());
			}
			return StringArrayUtils.toArray(dctermsTemporalList);
		}
		return null;
	}

	@Override
	public String[] getEdmUGC() {
		if (this.aggregations != null) {
			List<String> edmUGCList = new ArrayList<String>();
			for (Aggregation aggregation : this.aggregations) {
				edmUGCList.add(aggregation.getEdmUgc());
			}
			return StringArrayUtils.toArray(edmUGCList);
		}
		return null;
	}

	@Override
	public String[] getEdmCurrentLocation() {
		if (this.proxies != null) {
			List<String> edmCurrentLocationList = new ArrayList<String>();
			for (Proxy proxy : this.proxies) {
				edmCurrentLocationList.add(proxy.getEdmCurrentLocation());
			}
			return StringArrayUtils.toArray(edmCurrentLocationList);
		}
		return null;
	}

	@Override
	public String[] getEdmIsNextInSequence() {
		if (this.providedCHOs != null) {
			List<String> edmIsNextInSequenceList = new ArrayList<String>();
			for (ProvidedCHO providedCHO : this.providedCHOs) {
				edmIsNextInSequenceList.add(providedCHO.getEdmIsNextInSequence());
			}
			return StringArrayUtils.toArray(edmIsNextInSequenceList);
		}
		return null;
	}

	@Override
	public String[] getUserTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmAgentSkosNote() {
		if (this.agents != null) {
			List<String> agentNotes = new ArrayList<String>();
			for (Agent agent : this.agents) {
				StringArrayUtils.addToList(agentNotes, agent.getNote());
			}
			return StringArrayUtils.toArray(agentNotes);
		}
		return null;
	}

	@Override
	public String[] getEdmAgentBegin() {
		if (this.agents != null) {
			List<String> agentBeginDates = new ArrayList<String>();
			for (Agent agent : this.agents) {
				agentBeginDates.add(agent.getBegin());
			}
			return StringArrayUtils.toArray(agentBeginDates);
		}
		return null;
	}

	@Override
	public String[] getEdmAgentEnd() {
		if (this.agents != null) {
			List<String> agentEndDates = new ArrayList<String>();
			for (Agent agent : this.agents) {
				agentEndDates.add(agent.getEnd());
			}
			return StringArrayUtils.toArray(agentEndDates);
		}
		return null;
	}

	@Override
	public String[] getEdmTimeSpanSkosNote() {
		if (this.timespans != null) {
			List<String> notes = new ArrayList<String>();
			for (Timespan timespan : this.timespans) {
				StringArrayUtils.addToList(notes, timespan.getNote());
			}
			return StringArrayUtils.toArray(notes);
		}
		return null;
	}

	@Override
	public String[] getEdmPlaceSkosNote() {
		if (this.places != null) {
			List<String> notes = new ArrayList<String>();
			for (Place place : this.places) {
				StringArrayUtils.addToList(notes, place.getNote());
			}
			return StringArrayUtils.toArray(notes);
		}
		return null;
	}

	@Override
	public String[] getEdmConceptNote() {
		if (this.concepts != null) {
			List<String> notes = new ArrayList<String>();
			for (Concept concept : this.concepts) {
				StringArrayUtils.addToList(notes, concept.getNote());
			}
			return StringArrayUtils.toArray(notes);
		}
		return null;
	}

	@Override
	public String getId() {
		if (this.europeanaId != null) {
			return this.europeanaId.toString();
		}
		return null;
	}

	@Override
	public String[] getEdmPlace() {
		if (this.places != null) {
			List<String> placeIds = new ArrayList<String>();
			for (Place place : this.places) {
				if (place.getId() != null) {
					placeIds.add(place.getId().toString());
				}
			}
			return StringArrayUtils.toArray(placeIds);
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmAgentAltLabels() {
		if (this.agents != null) {
			List<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
			for (Agent agent : this.agents) {
				altLabels.add(agent.getAltLabel());
			}
			return altLabels;
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmPlaceAltLabels() {
		if (this.places != null) {
			List<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
			for (Place place : this.places) {
				altLabels.add(place.getAltLabel());
			}
			return altLabels;
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmTimespanAltLabels() {
		if (this.timespans != null) {
			List<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
			for (Timespan timespan : this.timespans) {
				altLabels.add(timespan.getAltLabel());
			}
			return altLabels;
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getSkosConceptAltLabels() {
		if (this.concepts != null) {
			List<Map<String, String>> altLabels = new ArrayList<Map<String, String>>();
			for (Concept concept : this.concepts) {
				altLabels.add(concept.getAltLabel());
			}
			return altLabels;
		}
		return null;
	}

	@Override
	public Boolean[] getEdmPreviewNoDistribute() {
		if (this.aggregations != null) {
			List<Boolean> edmPreviewNoDistributeList = new ArrayList<Boolean>();
			for (Aggregation aggregation : aggregations) {
				edmPreviewNoDistributeList.add(aggregation.getEdmPreviewNoDistribute());
			}
			return edmPreviewNoDistributeList.toArray(new Boolean[edmPreviewNoDistributeList.size()]);
		}
		return null;
	}

	@Override
	public String getFullDocUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDctermsHasPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmPlaceAltLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmConceptBroaderLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getEdmTimespanBroaderTerm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> getEdmTimespanBroaderLabel() {
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
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			return this.getId().equals(((FullBeanImpl) o).getId());
		}
		return false;
	}

	@Override
	public List<? extends BriefBean> getRelatedItems() {
		return this.relatedItems;
	}

	@Override
	public void setRelatedItems(List<? extends BriefBean> relatedItems) {
		this.relatedItems = (List<BriefBeanImpl>) relatedItems;

	}

	@Override
	public String[] getDcCreator() {
		if (this.proxies != null) {
			List<String> dcCreatorList = new ArrayList<String>();
			for (Proxy proxy : proxies) {
				StringArrayUtils.addToList(dcCreatorList, proxy.getDcCreator());
			}
			return StringArrayUtils.toArray(dcCreatorList);
		}
		return null;
	}

	@Override
	public String[] getDcContributor() {
		if (this.proxies != null) {
			List<String> dcContributorList = new ArrayList<String>();
			for (Proxy proxy : proxies) {
				StringArrayUtils.addToList(dcContributorList, proxy.getDcContributor());
			}
			return StringArrayUtils.toArray(dcContributorList);
		}
		return null;
	}

	@Override
	public int hashCode() {
		return this.providedCHOs.hashCode();
	}

	@Override
	public String[] getUgc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUgc(String[] ugc) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getEdmRights() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEdmRights(String[] edmRights) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCountry(String[] country) {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getEuropeanaCollectionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDctermsIsPartOf(String[] dctermsIsPartOf) {
		// TODO Auto-generated method stub

	}
}
