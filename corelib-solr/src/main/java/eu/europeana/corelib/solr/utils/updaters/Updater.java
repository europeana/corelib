package eu.europeana.corelib.solr.utils.updaters;

import eu.europeana.corelib.solr.MongoServer;

public interface Updater<T,U> {

	void update(T mongoEntity, U jibxEntity, MongoServer mongoServer);
}
