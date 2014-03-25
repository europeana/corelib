package eu.europeana.corelib.solr.utils.updaters;

import eu.europeana.corelib.solr.MongoServer;
@Deprecated
public interface Updater<T,U> {

	void update(T mongoEntity, U jibxEntity, MongoServer mongoServer);
}
