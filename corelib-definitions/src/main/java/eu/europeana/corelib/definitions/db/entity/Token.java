package eu.europeana.corelib.definitions.db.entity;

import java.util.Date;

import eu.europeana.corelib.definitions.db.entity.abstracts.IdentifiedEntity;

public interface Token extends IdentifiedEntity<String> {

	public abstract String getToken();

	public abstract String getEmail();

	public abstract Date getCreated();

	public abstract void setCreated(Date created);

	public abstract void setEmail(String email);

	public abstract void setToken(String token);

}