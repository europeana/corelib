/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
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
import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

public class DaoImpl<E extends IdentifiedEntity<?>> implements Dao<E> {

	@PersistenceContext(name = "corelib_db_entityManagerFactory")
	protected EntityManager entityManager;

	private Class<E> domainClazz = null;

	public DaoImpl(Class<E> clazz) {
		domainClazz = clazz;
	}

	public E findByPK(Serializable id) {
		return entityManager.find(domainClazz, id);
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

	public E insert(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	public E update(E entity) {
		return entityManager.merge(entity);
	}

	public void delete(E entity) {
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
