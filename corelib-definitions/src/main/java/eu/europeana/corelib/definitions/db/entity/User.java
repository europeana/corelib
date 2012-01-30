/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
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

	public abstract Set<SavedItem> getSavedItems();

	public abstract Set<SavedSearch> getSavedSearches();

	public abstract Set<SocialTag> getSocialTags();

}