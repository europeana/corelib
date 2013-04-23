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

package eu.europeana.corelib.definitions.db.entity.relational.abstracts;

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EuropeanaUserObject extends UserConnected<Long> {

	Date getDateSaved();

	void setDateSaved(Date dateSaved);

	String getTitle();

	void setTitle(String title);

	String getEuropeanaObject();

	void setEuropeanaObject(String europeanaObject);

	String getEuropeanaUri();

	void setEuropeanaUri(String europeanaUri);

	DocType getDocType();

	void setDocType(DocType docType);

}