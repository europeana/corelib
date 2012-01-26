package eu.europeana.corelib.definitions.db.entity;

import eu.europeana.corelib.definitions.db.entity.abstracts.EuropeanaUserObject;

public interface SavedItem extends EuropeanaUserObject {

	public abstract String getAuthor();

	public abstract void setAuthor(String author);

}