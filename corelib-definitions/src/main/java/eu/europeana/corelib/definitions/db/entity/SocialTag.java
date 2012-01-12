package eu.europeana.corelib.definitions.db.entity;

import eu.europeana.corelib.definitions.db.entity.abstracts.EuropeanaUserObject;

public interface SocialTag extends EuropeanaUserObject {

	public abstract String getTag();

	public abstract void setTag(String tag);

}