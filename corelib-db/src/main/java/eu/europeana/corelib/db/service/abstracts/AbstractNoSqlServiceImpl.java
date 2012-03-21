package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;

import com.google.code.morphia.Key;

import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

public abstract class AbstractNoSqlServiceImpl<E extends NoSqlEntity, T extends Serializable> implements AbstractNoSqlService<E, T> {
	
	private NosqlDao<E,T> dao;
	
	@Override
	public void remove(T id) {
		dao.deleteById(id);
	}
	
	@Override
	public E findByID(T id) {
		return dao.get(id);
	}
	
	@Override
	public Iterable<E> findAll() {
		return dao.find().asList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E store(E object) {
		Key<E> key = dao.save(object);
		return dao.get((T) key.getId());
	}
	
	@Override
	public boolean exists(T id) {
		// TODO
		return false;
	}


	/**
	 * Used by Bean configuration to inject Entity based DAO.
	 * 
	 * @param dao
	 *            DAO object with entity based generic set
	 */
	public final void setDao(NosqlDao<E,T> dao) {
		this.dao = dao;
	}
	
	/**
	 * Getter for DAO, only available for internal usage.
	 * 
	 * @return Generic DAO class
	 */
	protected NosqlDao<E,T> getDao() {
		return dao;
	}
	
}
