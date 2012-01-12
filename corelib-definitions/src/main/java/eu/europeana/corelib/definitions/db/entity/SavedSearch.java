package eu.europeana.corelib.definitions.db.entity;

import java.util.Date;

import eu.europeana.corelib.definitions.db.entity.abstracts.UserConnected;

public interface SavedSearch extends UserConnected<Long> {

	public abstract String getQuery();

	public abstract void setQuery(String query);

	public abstract String getQueryString();

	public abstract void setQueryString(String queryString);

	public abstract Date getDateSaved();

	public abstract void setDateSaved(Date dateSaved);

}