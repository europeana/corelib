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
import java.util.List;

import eu.europeana.corelib.definitions.db.entity.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface User extends IdentifiedEntity<Long> {

	public abstract void setEmail(String email);

	public abstract String getEmail();

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getApiKey();

	public abstract void setApiKey(String apiKey);

	public abstract Date getRegistrationDate();

	public abstract void setRegistrationDate(Date registrationDate);

	public abstract Date getLastLogin();

	public abstract void setLastLogin(Date lastLogin);

	public abstract Role getRole();

	public abstract void setRole(Role role);

	public abstract String getUserName();

	public abstract void setUserName(String userName);

	public abstract List<SavedItem> getSavedItems();

	public abstract List<SavedSearch> getSavedSearches();

	public abstract List<SocialTag> getSocialTags();

}