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

package eu.europeana.corelib.definitions.solr.beans;


import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.entity.Timespan;


/**
 * Interface for the FullBean. FullBean contains all the fields exposed by the MongoDB required by Portal in order to
 * fully present a single record page
 * 
 * NOTE: Draft to be crosschecked and validated over time
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@ kb.nl>
 */
public interface FullBean extends ApiBean {

	/**
	 * Retrieve the edm:isShownBy field
	 * 
	 * @return A String array with the edm:isShownBy fields for an object
	 */
	String[] getEdmIsShownBy();

	/**
	 * Retrieve the edm:isShownAt field
	 * 
	 * @return A String array with the edm:isShownAt fields for an object
	 */
	String[] getEdmIsShownAt();

	/**
	 * Retrieve the edm:provider field
	 * 
	 * @return A String array with the edm:provider fields for an object
	 */
	String[] getEdmProvider();

	/**
	 * Retrieve the dc:rights field for an Aggregation
	 * 
	 * @return A String array with the dc:rights field for an Aggregation
	 */
	String[] getAggregationDcRights();

	/**
	 * Retrieve the ore:proxy rdf:about attribute
	 * 
	 * @return A string array with the rdf:about fields for all proxies pointing to a CHO
	 */
	String[] getOreProxy();

	/**
	 * Retrieve the owl:sameAs field from a Proxy
	 * 
	 * @return A String array with the owl:sameAs for each proxy pointing to a CHO
	 */
	String[] getOwlSameAs();

	/**
	 * Retrieve dc:coverage fields from a Proxy
	 * 
	 * @return A String array with the dc:coverage fields for all proxies pointing to a CHO
	 */
	String[] getDcCoverage();

	/**
	 * Retrieve dc:publisher fields from a Proxy
	 * 
	 * @return A String array with the dc:publisher fields for all proxies pointing to a CHO
	 */
	String[] getDcPublisher();

	/**
	 * Retrieve dc:identifier fields from a Proxy
	 * 
	 * @return A String array with the dc:identifier fields for all proxies pointing to a CHO
	 */
	String[] getDcIdentifier();

	/**
	 * Retrieve dc:relation fields from a Proxy
	 * 
	 * @return A String array with the dc:relation fields for all proxies pointing to a CHO
	 */
	String[] getDcRelation();

	/**
	 * Retrieve dc:rights fields from a Proxy
	 * 
	 * @return A String array with the dc:rights fields for all proxies pointing to a CHO
	 */
	String[] getProxyDcRights();

	/**
	 * Retrieve dc:source fields from a Proxy
	 * 
	 * @return A String array with the dc:source fields for all proxies pointing to a CHO
	 */
	String[] getDcSource();

	/**
	 * Retrieve dcterms:alternative fields from a Proxy
	 * 
	 * @return A String array with the dcterms:alternative fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsAlternative();

	/**
	 * Retrieve dcterms:conformsTo fields from a Proxy
	 * 
	 * @return A String array with the dcterms:conformsTo fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsConformsTo();

	/**
	 * Retrieve dcterms:created fields from a Proxy
	 * 
	 * @return A Date array with the dcterms:created fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsCreated();

	/**
	 * Retrieve dcterms:extent fields from a Proxy
	 * 
	 * @return A String array with the dcterms:extent fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsExtent();

	/**
	 * Retrieve dcterms:hasFormat fields from a Proxy
	 * 
	 * @return A String array with the dcterms:hasFormat fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsHasFormat();

	/**
	 * Retrieve dcterms:isPartOf fields from a Proxy
	 * 
	 * @return A String array with the dcterms:isPartOf fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsIsPartOf();

	/**
	 * Retrieve dcterms:isReferencedBy fields from a Proxy
	 * 
	 * @return A String array with the dcterms:isReferencedBy fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsIsReferencedBy();

	/**
	 * Retrieve dcterms:isReplacedBy fields from a Proxy
	 * 
	 * @return A String array with the dcterms:isReplacedBy fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsIsReplacedBy();

	/**
	 * Retrieve dcterms:isRequiredBy fields from a Proxy
	 * 
	 * @return A String array with the dcterms:isRequiredby fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsIsRequiredBy();

	/**
	 * Retrieve dcterms:isVersionOf fields from a Proxy
	 * 
	 * @return A String array with the dcterms:isVersionOf fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsIsVersionOf();

	/**
	 * Retrieve dcterms:issued fields from a Proxy
	 * 
	 * @return A String array with the dcterms:issued fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsIssued();

	/**
	 * Retrieve dcterms:medium fields from a Proxy
	 * 
	 * @return A String array with the dcterms:medium fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsMedium();

	/**
	 * Retrieve dcterms:provenance fields from a Proxy
	 * 
	 * @return A String array with the dcterms:provenance fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsProvenance();

	/**
	 * Retrieve dcterms:reference fields from a Proxy
	 * 
	 * @return A String array with the dcterms:reference fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsReferences();

	/**
	 * Retrieve dcterms:replaces fields from a Proxy
	 * 
	 * @return A String array with the dcterms:replaces fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsReplaces();

	/**
	 * Retrieve dcterms:requires fields from a Proxy
	 * 
	 * @return A String array with the dcterms:requires fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsRequires();

	/**
	 * Retrieve dcterms:tableOfContents fields from a Proxy
	 * 
	 * @return A String array with the dcterms:tableOfContents fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsTableOfContents();

	/**
	 * Retrieve dcterms:temporal fields from a Proxy
	 * 
	 * @return A String array with the dcterms:temporal fields for all proxies pointing to a CHO
	 */
	String[] getDcTermsTemporal();

	String[] getEdmUGC();

	/**
	 * Retrieve edm:currentLocation fields from a Proxy
	 * 
	 * @return A String array with the edm:currentLocation fields for all proxies pointing to a CHO
	 */
	String[] getEdmCurrentLocation();

	/**
	 * Retrieve edm:isNextInSequence fields from a Proxy
	 * 
	 * @return A String array with the edm:isNextInSequence fields for all proxies pointing to a CHO
	 */
	String[] getEdmIsNextInSequence();

	String[] getUserTags();

	/**
	 * Retrieve skos:altLabel fields from an Agent
	 * 
	 * @return A String array with the edm:broader fields for all Agents pointing to a CHO
	 */
	List<Map<String, String>> getEdmAgentAltLabels();

	/**
	 * Retrieve skos:note fields from an Agent
	 * 
	 * @return A String array with the skos:note fields for all Agents pointing to a CHO
	 */
	String[] getEdmAgentSkosNote();

	/**
	 * Retrieve edm:begin fields from an Agent
	 * 
	 * @return A Date array with the edm:begin fields for all Agents pointing to a CHO
	 */
	String[] getEdmAgentBegin();

	/**
	 * Retrieve edm:end fields from an Agent
	 * 
	 * @return A Date array with the edm:end fields for all Agents pointing to a CHO
	 */
	String[] getEdmAgentEnd();

	/**
	 * Retrieve skos:note fields from a Timespan
	 * 
	 * @return A String array with the skos:note fields for all Timespans pointing to a CHO
	 */
	String[] getEdmTimeSpanSkosNote();

	/**
	 * Retrieve skos:note fields from a Place
	 * 
	 * @return A String array with the skos:note fields for all Places pointing to a CHO
	 */
	String[] getEdmPlaceSkosNote();

	/**
	 * Retrieve skos:note fields from a Concept
	 * 
	 * @return A String array with the skos:note fields for all Concepts pointing to a CHO
	 */
	String[] getEdmConceptNote();

	/**
	 * Retrieve skos:altLabel fields from a Place
	 * 
	 * @return A String array with the skos:altLabel fields for all Places pointing to a CHO
	 */
	List<Map<String, String>> getEdmPlaceAltLabels();

	/**
	 * Retrieve skos:altLabel fields from a Timespan
	 * 
	 * @return A String array with the skos:altLabel fields for all Timespans pointing to a CHO
	 */
	List<Map<String, String>> getEdmTimespanAltLabels();

	/**
	 * Retrieve skos:altLabel fields from a Concept
	 * 
	 * @return A String array with the skos:altLabel fields for all Concepts pointing to a CHO
	 */
	List<Map<String, String>> getSkosConceptAltLabels();

	/**
	 * Retrieve whether a thumbnail should be previewed or not according to the content providers
	 * 
	 * @return A Boolean array to decide whether Europeana should show previews of distant files or not
	 */
	Boolean[] getEdmPreviewNoDistribute();

	/**
	 * Retrieve the edm:isPartOf from a Place
	 * 
	 * @return A String array with the edm:isPartOf fields for all Places pointing to a CHO
	 */
	String[] getEdmPlaceIsPartOf();

	/**
	 * Retrieve the edm:isPartOf from a Timespan
	 * 
	 * @return A String array with the edm:isPartOf fields for all Timespans pointing to a CHO
	 */
	String[] getEdmTimespanIsPartOf();

	String[] getEdmWebResource();

	String[] getEdmWebResourceDcRights();

	String[] getEdmWebResourceEdmRights();

	List<? extends Place> getPlaces();

	void setPlaces(List<? extends Place> places);

	List<? extends Agent> getAgents();

	void setAgents(List<? extends Agent> agents);

	List<? extends Timespan> getTimespans();

	List<? extends Concept> getConcepts();

	void setConcepts(List<? extends Concept> concepts);

	void setAggregations(List<? extends Aggregation> aggregations);

	List<? extends Proxy> getProxies();

	void setProxies(List<? extends Proxy> proxies);

	void setEuropeanaId(ObjectId europeanaId);

	void setTitle(String[] title);

	void setCreator(String[] creator);

	void setYear(String[] year);

	void setProvider(String[] provider);

	void setLanguage(String[] language);

	void setType(DocType type);

	void setEuropeanaCompleteness(int europeanaCompleteness);

	void setTimespans(List<? extends Timespan> timespans);

	List<? extends Aggregation> getAggregations();

	List<? extends BriefBean> getRelatedItems();

	void setRelatedItems(List<? extends BriefBean> relatedItems);

	List<? extends ProvidedCHO> getProvidedCHOs();

	void setProvidedCHOs(List<? extends ProvidedCHO> providedCHOs);

	String getAbout();

	void setAbout(String about);

}
