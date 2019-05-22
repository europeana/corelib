package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;
import java.util.List;

import eu.europeana.corelib.db.dao.RelationalDao;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.service.abstracts.AbstractService
 */
public abstract class AbstractServiceImpl<E extends IdentifiedEntity<?>> implements AbstractService<E> {

	private RelationalDao<E> dao;

	@Override
	public E store(E entity) throws DatabaseException {
		if (entity.getId() != null && findByID(entity.getId()) != null) {
			return dao.update(entity);
		}
		return dao.insert(entity);
	}

	@Override
	public void remove(E entity) throws DatabaseException {
		E persEnity = dao.findByPK(entity.getId());
		dao.delete(persEnity);
	}

	@Override
	public E findByID(Serializable id) throws DatabaseException {
		return dao.findByPK(id);
	}

	@Override
	public long count() {
		return dao.count();
	}

	@Override
	public List<E> findAll() {
		return dao.findAll();
	}

	/**
	 * Used by Bean configuration to inject Entity based DAO.
	 * 
	 * @param dao
	 *            DAO object with entity based generic set
	 */
	public final void setDao(RelationalDao<E> dao) {
		this.dao = dao;
	}

	/**
	 * Getter for DAO, only available for internal usage.
	 * 
	 * @return Generic DAO class
	 */
	protected RelationalDao<E> getDao() {
		return dao;
	}
}