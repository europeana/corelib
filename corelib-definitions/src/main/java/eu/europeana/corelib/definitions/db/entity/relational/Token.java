package eu.europeana.corelib.definitions.db.entity.relational;

import java.util.Date;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface Token extends IdentifiedEntity<String> {

	String getToken();

	String getRedirect();

	String getEmail();

	long getCreated();

	void setCreated(Date created);

	void setEmail(String email);

	void setToken(String token);

	void setRedirect(String redirect);
}