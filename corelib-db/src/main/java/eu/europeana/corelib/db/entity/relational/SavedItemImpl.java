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
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_SAVEDITEM)
@NamedQueries ({
	@NamedQuery(name=SavedItem.QUERY_FINDBY_OBJECTID, query="select e from SavedItemImpl e where e.user.id = ? and e.europeanaUri = ?")
})
@Deprecated
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
