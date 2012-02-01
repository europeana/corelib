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

package eu.europeana.corelib.definitions.solr.beans;

import java.util.Date;

/**
 * Interface for the FullBean. FullBean contains all the fields exposed by the
 * SOLR engine required by Portal in order to fully present a single record page
 * 
 * NOTE: Draft to be crosschecked and validated over time
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@kb.nl>
 */
public interface FullBean extends ApiBean {

	String[] getEdmHasView();

	String[] getEdmIsShownBy();

	String[] getEdmIsShownAt();

	String[] getEdmProvider();

	String[] getAggregationDcRights();

	String[] getEdmWebResource();

	String[] getEdmWebResourceDcRights();

	String[] getEdmWebResourceEdmRights();

	String[] getOreProxy();

	String[] getOwlSameAs();

	String[] getDcCoverage();

	String[] getDcPublisher();

	String[] getDcIdentifier();

	String[] getDcRelation();

	String[] getProxyDcRights();

	String[] getProxyEdmRights();

	String[] getDcSource();

	String[] getDcTermsAlternative();

	String[] getDcTermsConformsTo();

	String[] getDcTermsCreated();

	String[] getDcTermsExtent();

	String[] getDcTermsHasFormat();

	String[] getDcTermsIsPartOf();

	String[] getDcTermsIsReferencedBy();

	String[] getDcTermsIsReplacedBy();

	String[] getDcTermsIsRequiredBy();

	String[] getDcTermsIsVersionOf();

	String[] getDcTermsIssued();

	String[] getDcTermsMedium();

	String[] getDcTermsProvenance();

	String[] getDcTermsReferences();

	String[] getDcTermsReplaces();

	String[] getDcTermsRequires();

	String[] getDcTermsTableOfContents();

	String[] getDcTermsTemporal();

	String[] getEdmUGC();

	String[] getEdmCurrentLocation();

	String[] getEdmIsNextInSequence();

	String[] getUserTags();
	
	String[] getEdmAgentBroaderLabels();

	String[] getEdmAgentSkosNote();

	Date[] getEdmAgentBegin();

	Date[] getEdmAgentEnd();

	String[] getEdmTimeSpanSkosNote();

	String[] getEdmPlaceSkosNote();

	String[] getEdmConceptNote();
}
