package eu.europeana.corelib.definitions.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.EuropeanaUserObject;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface SavedItem extends EuropeanaUserObject, Comparable<SavedItem> {

	String QUERY_FINDBY_OBJECTID = "SavedItem.FindByObjectId";

	String getAuthor();

	void setAuthor(String author);

}