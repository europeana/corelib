package eu.europeana.corelib.solr.utils.construct;

import eu.europeana.corelib.solr.MongoServer;

public interface Updater<T> {

	void update(T mongoEntity, T newEntity, MongoServer mongoServer);
}
