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

import java.util.Map;

/**
 * EDM WebResource Fields implementation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface WebResource extends AbstractEdmEntity {

	/**
	 * Retrieve the dc:rights fields of a WebResource
	 * 
	 * @return String array representing the dc:rights fields of a WebResource
	 */
	Map<String,String> getWebResourceDcRights();

	/**
	 * Retrieve the edm:rights field of a WebResource
	 * 
	 * @return String representing the edm:rights fields of a WebResource
	 */
	Map<String,String> getWebResourceEdmRights();

	/**
	 * Set the dc:rights fields of a WebResource
	 * 
	 * @param webResourceDcRights
	 *            String array representing the dc:rights fields of a
	 *            WebResource
	 */
	void setWebResourceDcRights(Map<String,String> webResourceDcRights);

	/**
	 * Set the edm:rights field of a WebResource
	 * 
	 * @param webResourceEdmRights
	 *            String representing the edm:rights fields of a WebResource
	 */
	void setWebResourceEdmRights(Map<String,String> webResourceEdmRights);

	void setIsNextInSequence(String isNextInSequence);

	String getIsNextInSequence();

	void setDctermsHasPart(Map<String,String> dctermsHasPart);

	Map<String,String> getDctermsHasPart();

	void setDctermsIsFormatOf(Map<String,String> dctermsIsFormatOf);

	Map<String,String> getDctermsIsFormatOf();

	void setDctermsCreated(Map<String,String> dctermsCreated);

	Map<String,String> getDctermsCreated();

	Map<String,String> getDctermsConformsTo();

	void setDctermsConformsTo(Map<String,String> dctermsConformsTo);

	void setDctermsIssued(Map<String,String> dctermsIssued);

	Map<String,String> getDctermsIssued();

	Map<String,String> getDcDescription();

	void setDcDescription(Map<String,String> dcDescription);

	Map<String,String> getDcFormat();

	void setDcFormat(Map<String,String> dcFormat);

	Map<String,String> getDcSource();

	void setDcSource(Map<String,String> dcSource);

	Map<String,String> getDctermsExtent();

	void setDctermsExtent(Map<String,String> dctermsExtent);

}
