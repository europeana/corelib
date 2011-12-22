/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.europeana.corelib.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.europeana.corelib.db.entity.abstracts.UserConnectedEntity;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;

/**
 * @author Willem-Jan Boogerd <europeana [at] eledge.net>
 */
@Entity
@Table(name = DatabaseDefinition.TABLENAME_SAVEDSEARCH)
public class SavedSearch extends UserConnectedEntity<Long> implements DatabaseDefinition {
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Date getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(Date dateSaved) {
        this.dateSaved = dateSaved;
    }
}