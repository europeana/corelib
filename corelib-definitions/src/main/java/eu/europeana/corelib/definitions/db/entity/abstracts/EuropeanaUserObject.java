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

package eu.europeana.corelib.definitions.db.entity.abstracts;

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface EuropeanaUserObject extends UserConnected<Long> {

	abstract Long getId();

	abstract Date getDateSaved();

	abstract void setDateSaved(Date dateSaved);

	abstract String getTitle();

	abstract void setTitle(String title);

	abstract String getEuropeanaObject();

	abstract void setEuropeanaObject(String europeanaObject);

	abstract String getEuropeanaUri();

	abstract void setEuropeanaUri(String europeanaUri);

	abstract DocType getDocType();

	abstract void setDocType(DocType docType);

}