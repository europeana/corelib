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
	Map<String,List<String>> getWebResourceDcRights();

	/**
	 * Retrieve the edm:rights field of a WebResource
	 * 
	 * @return String representing the edm:rights fields of a WebResource
	 */
	Map<String,List<String>> getWebResourceEdmRights();

	/**
	 * Set the dc:rights fields of a WebResource
	 * 
	 * @param webResourceDcRights
	 *            String array representing the dc:rights fields of a
	 *            WebResource
	 */
	void setWebResourceDcRights(Map<String,List<String>> webResourceDcRights);

	/**
	 * Set the edm:rights field of a WebResource
	 * 
	 * @param webResourceEdmRights
	 *            String representing the edm:rights fields of a WebResource
	 */
	void setWebResourceEdmRights(Map<String,List<String>> webResourceEdmRights);

	void setIsNextInSequence(String isNextInSequence);

	String getIsNextInSequence();

	void setDctermsHasPart(Map<String,List<String>> dctermsHasPart);

	Map<String,List<String>> getDctermsHasPart();

	void setDctermsIsFormatOf(Map<String,List<String>> dctermsIsFormatOf);

	Map<String,List<String>> getDctermsIsFormatOf();

	void setDctermsCreated(Map<String,List<String>> dctermsCreated);

	Map<String,List<String>> getDctermsCreated();

	Map<String,List<String>> getDctermsConformsTo();

	void setDctermsConformsTo(Map<String,List<String>> dctermsConformsTo);

	void setDctermsIssued(Map<String,List<String>> dctermsIssued);

	Map<String,List<String>> getDctermsIssued();

	Map<String,List<String>> getDcDescription();

	void setDcDescription(Map<String,List<String>> dcDescription);

	Map<String,List<String>> getDcFormat();

	void setDcFormat(Map<String,List<String>> dcFormat);

	Map<String,List<String>> getDcSource();

	void setDcSource(Map<String,List<String>> dcSource);

	Map<String,List<String>> getDctermsExtent();

	void setDctermsExtent(Map<String,List<String>> dctermsExtent);

}
