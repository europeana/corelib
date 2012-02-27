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


/**
 * EDM Aggregation fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Aggregation extends AbstractEdmEntity {


	/**
	 * Retrieves the edm:dataProvider field from an Aggregation
	 * 
	 * @return String representing the edm:DataProvider field
	 */
	String getEdmDataProvider();

	/**
	 * Retrieves the unique edm:isShownBy field from an Aggregation
	 * 
	 * @return String representing the edm:isShownBy field
	 */
	String getEdmIsShownBy();

	/**
	 * Retrieves the unique edm:isShownAt field from an Aggregation
	 * 
	 * @return String representing the edm:isShownAt field
	 */
	String getEdmIsShownAt();

	/**
	 * Retrieves the unique edm:object field from an Aggregation
	 * 
	 * @return String representing the edm:object field
	 */
	String getEdmObject();

	/**
	 * Retrieves the unique edm:provider field from an Aggregation
	 * 
	 * @return String representing the edm:provider field
	 */
	String getEdmProvider();

	/**
	 * Retrieves the dc:rights fields from an Aggregation
	 * 
	 * @return String array representing the dc:rights fields
	 */
	String[] getDcRights();

	/**
	 * Retrieves the unique edm:rights field from an Aggregation
	 * 
	 * @return String representing the edm:rights fields
	 */
	String getEdmRights();

	void setDcRights(String[] dcRights);

	void setEdmProvider(String edmProvider);

	void setEdmRights(String edmRights);

	void setEdmObject(String edmObject);

	void setEdmIsShownAt(String edmIsShownAt);

	void setEdmIsShownBy(String edmIsShownBy);

	void setEdmDataProvider(String edmDataProvider);


	List<WebResource> getWebResources();

	void setWebResources(List<WebResource> webResources);

	String getAbout();

	void setAbout(String about);

	String getEdmUgc();

	void setEdmUgc(String edmUgc);

	Boolean getEdmPreviewNoDistribute();

	void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute);

	String[] getHasView();

	void setHasView(String[] hasView);
	
	String getAggregatedCHO();
	void setAggregatedCHO(String aggregatedCHO);

}
