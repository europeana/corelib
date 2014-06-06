package eu.europeana.corelib.solr.utils.construct;

import eu.europeana.corelib.solr.MongoServer;
import java.lang.reflect.InvocationTargetException;

/**
 * Updater of EDM contextual classes
 * @author Yorgos.Mamakis@ europeana.eu
 *
 * @param <T> The contextual class to update
 */
public interface Updater<T> {

	T update(T mongoEntity, T newEntity, MongoServer mongoServer) throws NoSuchMethodException, IllegalAccessException,InvocationTargetException;
}
