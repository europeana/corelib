package eu.europeana.corelib.definitions.db.entity.relational.abstracts;

import java.io.Serializable;

import eu.europeana.corelib.definitions.db.entity.relational.User;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface UserConnected<I extends Serializable> extends IdentifiedEntity<I> {

	User getUser();

	void setUser(User user);

}