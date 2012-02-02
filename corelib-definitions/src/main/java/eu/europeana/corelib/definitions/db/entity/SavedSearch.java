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

package eu.europeana.corelib.definitions.db.entity;

import java.util.Date;

import eu.europeana.corelib.definitions.db.entity.abstracts.UserConnected;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface SavedSearch extends UserConnected<Long> {

	public abstract String getQuery();

	public abstract void setQuery(String query);

	public abstract String getQueryString();

	public abstract void setQueryString(String queryString);

	public abstract Date getDateSaved();

	public abstract void setDateSaved(Date dateSaved);

}