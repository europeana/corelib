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
import java.util.Set;

import eu.europeana.corelib.definitions.db.entity.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface User extends IdentifiedEntity<Long> {

	abstract void setEmail(String email);

	abstract String getEmail();

	abstract String getPassword();

	abstract void setPassword(String password);

	abstract String getApiKey();

	abstract void setApiKey(String apiKey);

	abstract Date getRegistrationDate();

	abstract void setRegistrationDate(Date registrationDate);

	abstract Date getLastLogin();

	abstract void setLastLogin(Date lastLogin);

	abstract Role getRole();

	abstract void setRole(Role role);

	abstract String getUserName();

	abstract void setUserName(String userName);

	abstract Set<SavedItem> getSavedItems();

	abstract Set<SavedSearch> getSavedSearches();

	abstract Set<SocialTag> getSocialTags();

}