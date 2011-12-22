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

package eu.europeana.corelib.db.dao;

import java.io.Serializable;
import java.util.List;

import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

/**
 * Generic DAO service layer. Used in combination with a DAO instance for every type
 * of object, although some methods are generic and can be used for every entity.
 * 
 * @author Willem-Jan Boogerd <europeana [at] eledge.net>
 */
public interface Dao<E extends IdentifiedEntity<?>> {

	/*
	 * FINDERS
	 */

	/**
	 * Retrieve a row through a private key
	 * 
	 * @param id
	 *            A private key object
	 * @return Returns a row
	 */
	E findByPK(final Serializable id);

	/**
	 * Generic variation of findByPK where the required entity can be given...
	 * 
	 * @param clazz
	 *            The class of the Entity type to retrieve
	 * @param id
	 *            A private key object
	 * @return Returns a row
	 */
	<T extends IdentifiedEntity<?>> T findByPK(Class<T> clazz, final Serializable id);

	/**
	 * Retrieve all entities from a table.
	 * 
	 * @return a List with all entities
	 */
	List<E> findAll();

	/**
	 * Find entities by named query. Given params will be inserted into query in order of params.
	 * 
	 * @param query
	 *            Name of the Named Query
	 * @param params
	 *            Parameters in order as marked in Named Query
	 * @return Search results, list is empty when no results match
	 */
	List<E> findByNamedQuery(String query, Object... params);

	/**
	 * Find entity by named query. Given params will be inserted into query in order of params.
	 * 
	 * @param query
	 *            Name of the Named Query
	 * @param params
	 *            Params in order as marked in Named Query
	 * @return First matching entity, null if no matches are found
	 */
	E findOneByNamedQuery(String query, Object... params);

	/*
	 * MODIFIERS
	 */

	/**
	 * insert a row in the database.
	 * 
	 * (This method is generic and can be used for other entities as well.)
	 * 
	 * @param object
	 *            Existing object to update
	 */
	public <T extends IdentifiedEntity<?>> T insert(T entity);

	/**
	 * update a existing row in the database
	 * 
	 * (This method is generic and can be used for other entities as well.)
	 * 
	 * @param object
	 *            Existing object to update
	 */
	public <T extends IdentifiedEntity<?>> T update(T entity);

	/**
	 * Delete a existing object in the database
	 * 
	 * (This method is generic and can be used for other entities as well.)
	 * 
	 * @param object
	 *            The existing row to delete
	 */
	void delete(IdentifiedEntity<?> entity);

	/**
	 * Only for internal (test) usage, clears a table...
	 */
	void deleteAll();

}
