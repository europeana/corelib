package eu.europeana.corelib.definitions.db.entity.abstracts;

import java.io.Serializable;

import eu.europeana.corelib.definitions.db.entity.User;

public abstract interface UserConnected<I extends Serializable> extends IdentifiedEntity<I> {

	public abstract User getUser();

	public abstract void setUser(User user);

}