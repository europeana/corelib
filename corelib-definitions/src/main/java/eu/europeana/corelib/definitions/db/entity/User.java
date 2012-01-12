package eu.europeana.corelib.definitions.db.entity;

import java.util.Date;
import java.util.Set;

import eu.europeana.corelib.definitions.db.entity.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;

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