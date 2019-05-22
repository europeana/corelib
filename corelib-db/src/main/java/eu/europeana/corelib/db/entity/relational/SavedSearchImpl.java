package eu.europeana.corelib.db.entity.relational;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.europeana.corelib.db.entity.relational.abstracts.UserConnectedImpl;
import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.SavedSearch;
import eu.europeana.corelib.utils.DateUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_SAVEDSEARCH)
@Deprecated
public class SavedSearchImpl extends UserConnectedImpl<Long> implements
		RelationalDatabase, SavedSearch {
	private static final long serialVersionUID = 667805541628354454L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	/*
	 * query is the just the input from the search box.
	 */
	@Column(length = FIELDSIZE_QUERY)
	private String query;

	/*
	 * QuerySring is the full path of query parameters.
	 */
	@Column(length = FIELDSIZE_QUERY_STRING)
	private String queryString;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSaved;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	@Override
	public Date getDateSaved() {
		return DateUtils.clone(dateSaved);
	}

	@Override
	public void setDateSaved(Date dateSaved) {
		this.dateSaved = DateUtils.clone(dateSaved);
	}

	@Override
	public int compareTo(SavedSearch o) {
		if (this.getDateSaved().before(o.getDateSaved())) {
			return 1;
		}
		if (this.getDateSaved().after(o.getDateSaved())) {
			return -1;
		}
		return 0;
	}
}