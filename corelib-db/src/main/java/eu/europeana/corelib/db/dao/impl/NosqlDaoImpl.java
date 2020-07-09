package eu.europeana.corelib.db.dao.impl;

import dev.morphia.Datastore;
import dev.morphia.dao.BasicDAO;
import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

import java.io.Serializable;
/**
 * Implementation of a NosqlDao
 *
 * @param <E>
 * @param <T>
 */
public class NosqlDaoImpl<E extends NoSqlEntity, T extends Serializable> extends BasicDAO<E, T> implements NosqlDao<E, T> {

	private Class<E> clazz;

	/**
	 * Default constructor
	 * @param datastore The datastore to connect t o
	 * @param clazz The implementation class
	 */
	public NosqlDaoImpl(Datastore datastore, Class<E> clazz) {
		super(clazz, datastore);
		datastore.getDB().slaveOk();
		this.clazz = clazz;
	}

	@Override
	public void deleteAll() {
		try {
			delete(clazz.newInstance());
		} catch (Exception ignored) {
		}
	}

}
