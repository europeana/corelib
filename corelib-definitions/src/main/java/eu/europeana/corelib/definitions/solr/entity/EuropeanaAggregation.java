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
package eu.europeana.corelib.definitions.solr.entity;

import java.util.List;
import java.util.Map;

/**
 * Europeana specific aggregation
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface EuropeanaAggregation extends AbstractEdmEntity {

	String getAggregatedCHO();
	
	void setAggregatedCHO(String aggregatedCHO);
	
	String[] getAggregates();
	
	void setAggregates(String[] aggregates);
	
	Map<String,List<String>> getDcCreator();
	
	void setDcCreator(Map<String,List<String>> dcCreator);
	
	String getEdmLandingPage();
	
	void setEdmLandingPage(String edmLandingPage);
	
	String getEdmIsShownBy();
	
	void setEdmIsShownBy(String edmIsShownBy);
	
	String[] getEdmHasView();
	
	void setEdmHasView(String[] edmHasView);
	
	Map<String,List<String>> getEdmCountry();
	
	void setEdmCountry(Map<String,List<String>> edmCountry);
	
	Map<String,List<String>> getEdmLanguage();
	
	void setEdmLanguage(Map<String,List<String>> edmLanguage);
	
	Map<String,List<String>> getEdmRights();
	
	void setEdmRights(Map<String,List<String>> edmRights);

	List<? extends WebResource> getWebResources();

	void setWebResources(List<? extends WebResource> webResources);

	String getEdmPreview();

	void setEdmPreview(String edmPreview);
}
