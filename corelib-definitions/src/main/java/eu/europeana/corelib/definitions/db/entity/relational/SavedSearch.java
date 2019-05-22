package eu.europeana.corelib.definitions.db.entity.relational;

import java.util.Date;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.UserConnected;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface SavedSearch extends UserConnected<Long>, Comparable<SavedSearch> {

	String getQuery();

	void setQuery(String query);

	String getQueryString();

	void setQueryString(String queryString);

	Date getDateSaved();

	void setDateSaved(Date dateSaved);

}