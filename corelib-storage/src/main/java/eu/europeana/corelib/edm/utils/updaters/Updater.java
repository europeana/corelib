package eu.europeana.corelib.edm.utils.updaters;

import eu.europeana.corelib.MongoServer;
@Deprecated
public interface Updater<T,U> {

	void update(T mongoEntity, U jibxEntity, MongoServer mongoServer);
}
