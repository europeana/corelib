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
	String[] getWebResourceDcRights();

	/**
	 * Retrieve the edm:rights field of a WebResource
	 * 
	 * @return String representing the edm:rights fields of a WebResource
	 */
	String getWebResourceEdmRights();

	/**
	 * Set the dc:rights fields of a WebResource
	 * 
	 * @param webResourceDcRights
	 *            String array representing the dc:rights fields of a
	 *            WebResource
	 */
	void setWebResourceDcRights(String[] webResourceDcRights);

	/**
	 * Set the edm:rights field of a WebResource
	 * 
	 * @param webResourceEdmRights
	 *            String representing the edm:rights fields of a WebResource
	 */
	void setWebResourceEdmRights(String webResourceEdmRights);

	void setIsNextInSequence(String isNextInSequence);

	String getIsNextInSequence();

	void setDctermsHasPart(String[] dctermsHasPart);

	String[] getDctermsHasPart();

	void setDctermsIsFormatOf(String[] dctermsIsFormatOf);

	String[] getDctermsIsFormatOf();

	void setDctermsCreated(String[] dctermsCreated);

	String[] getDctermsCreated();

	String[] getDctermsConformsTo();

	void setDctermsConformsTo(String[] dctermsConformsTo);

	void setDctermsIssued(String[] dctermsIssued);

	String[] getDctermsIssued();

	String[] getDcDescription();

	void setDcDescription(String[] dcDescription);

	String[] getDcFormat();

	void setDcFormat(String[] dcFormat);

	String[] getDcSource();

	void setDcSource(String[] dcSource);

	String[] getDctermsExtent();

	void setDctermsExtent(String[] dctermsExtent);

}
