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

package eu.europeana.corelib.definitions.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.EuropeanaUserObject;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface SocialTag extends EuropeanaUserObject, Comparable<SocialTag> {

	String QUERY_CREATECLOUD_BYUSER = "SocialTag.createCloudByUser";
	String QUERY_FINDBY_USER_TAG = "SocialTag.FindByUserAndTag";
	String QUERY_FINDBY_USER_EUROPEANAID = "SocialTag.FindByUserAndEuropeanaId";
	String QUERY_FINDBY_USER_TAG_EUROPEANAID = "SocialTag.FindByUserAndTagAndEuropeanaId";

	String getTag();

	void setTag(String tag);
}