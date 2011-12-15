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
import javax.persistence.PersistenceContext;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return entityManager.createQuery(
				"SELECT e FROM " + domainClazz.getSimpleName() + " e")
				.getResultList();
	}

	public E insert(E entity) {
		entityManager.persist(entity);
		return entity;
	}

	public E update(E object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(E object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		List<E> list = findAll();
		for (E e : list) {
			entityManager.remove(e);
		}
	}
}
