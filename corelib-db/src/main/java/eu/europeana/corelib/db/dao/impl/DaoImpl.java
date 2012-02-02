/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.db.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.exception.ProblemType;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.db.dao.Dao
 */
public class DaoImpl<E extends IdentifiedEntity<?>> implements Dao<E> {

	@PersistenceContext(name = "corelib_db_entityManagerFactory")
	protected EntityManager entityManager;

	private Class<E> domainClazz = null;

	/**
	 * This constructor is used by bean configuration.
	 * 
	 * @param clazz
	 *            The class type references by the main generic type of the DAO instance.
	 */
	public DaoImpl(Class<E> clazz) {
		domainClazz = clazz;
	}

	public E findByPK(Serializable id) throws DatabaseException {
		return findByPK(domainClazz, id);
	}

	public <T extends IdentifiedEntity<?>> T findByPK(Class<T> clazz, Serializable id) throws DatabaseException {
		try {
			return entityManager.find(clazz, id);
		} catch (IllegalArgumentException e) {
			throw new DatabaseException(e, ProblemType.INVALIDARGUMENTS);
		}
	}

	@Override
	public List<E> findAll() {
		StringBuilder sb = new StringBuilder("SELECT e FROM ");
		sb.append(domainClazz.getSimpleName()).append(" e");
		return entityManager.createQuery(sb.toString(), domainClazz).getResultList();
	}

	private TypedQuery<E> createNamedQuery(String qName, Object... params) {
		int parnr = 1;
		TypedQuery<E> query = entityManager.createNamedQuery(qName, domainClazz);
		if ((params != null) && (params.length > 0)) {
			for (Object object : params) {
				query.setParameter(parnr++, object);
			}
		}
		return query;
	}

	@Override
	public List<E> findByNamedQuery(String qName, Object... params) {
		return createNamedQuery(qName, params).getResultList();
	}

	@Override
	public E findOneByNamedQuery(String qName, Object... params) {
		try {
			return createNamedQuery(qName, params).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public <T extends IdentifiedEntity<?>> T insert(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public <T extends IdentifiedEntity<?>> T update(T entity) {
		return entityManager.merge(entity);
	}

	public void delete(IdentifiedEntity<?> entity) {
		entityManager.remove(entity);
	}

	@Override
	@Transactional
	public void deleteAll() {
		List<E> list = findAll();
		for (E e : list) {
			delete(e);
		}
	}
}
