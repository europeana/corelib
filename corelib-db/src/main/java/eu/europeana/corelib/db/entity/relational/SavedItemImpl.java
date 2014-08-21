/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.db.entity.relational;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import eu.europeana.corelib.db.entity.relational.abstracts.EuropeanaUserObjectImpl;
import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_SAVEDITEM)
@NamedQueries ({
	@NamedQuery(name=SavedItem.QUERY_FINDBY_OBJECTID, query="select e from SavedItemImpl e where e.user.id = ? and e.europeanaUri = ?")
})
public class SavedItemImpl extends EuropeanaUserObjectImpl implements SavedItem {
	private static final long serialVersionUID = -7059004310525816113L;

	@Column(length = FIELDSIZE_AUTHOR)
	private String author;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int compareTo(SavedItem o) {
		if (this.getDateSaved().before(o.getDateSaved())) {
			return 1;
		}
		if (this.getDateSaved().after(o.getDateSaved())) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "SavedItemImpl [author=" + author + ", getId()=" + getId()
				+ ", getDateSaved()=" + getDateSaved() + ", getTitle()=" + getTitle() + "]";
	}
}
