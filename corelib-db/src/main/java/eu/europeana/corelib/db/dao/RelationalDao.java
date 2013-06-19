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

package eu.europeana.corelib.db.dao;

import java.io.Serializable;
import java.util.List;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;

/**
 * Generic DAO service layer. Used in combination with a DAO instance for every type
 * of object, although some methods are generic and can be used for every entity.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface RelationalDao<E extends IdentifiedEntity<?>> {

	/*
	 * FINDERS
	 */

	/**
	 * Retrieve a row through a private key
	 * 
	 * @param id
	 *            A private key object
	 * @return Returns a row
	 * @throws DatabaseException 
	 */
	E findByPK(final Serializable id) throws DatabaseException;

	/**
	 * Generic variation of findByPK where the required entity can be given...
	 * 
	 * @param clazz
	 *            The class of the Entity type to retrieve
	 * @param id
	 *            A private key object
	 * @return Returns a row
	 * @throws DatabaseException 
	 */
	<T extends IdentifiedEntity<?>> T findByPK(Class<T> clazz, final Serializable id) throws DatabaseException;

	/**
	 * Counts all records
	 * 
	 * @return the number of records
	 */
	long count();

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
	
	List<E> findByNamedQueryLimited(String query, int offset, int limit, Object... params);
	
	/**
	 * 
	 * 
	 * @param clazz
	 *            The class of the Entity type to retrieve
	 * @param qName
	 *            Name of the Named Query
	 * @param params
	 *            Parameters in order as marked in Named Query
	 * @return
	 */
	<T> List<T> findByNamedQuery(Class<T> clazz, String qName, Object... params);

	/**
	 * Workaround version for a bug in hibernate to use custom output objects in named queries
	 * 
	 * bug report:
	 * https://hibernate.atlassian.net/browse/HHH-6304
	 * 
	 * @param clazz
	 *            The class of the Entity type to retrieve
	 * @param qName
	 *            Name of the Named Query
	 * @param params
	 *            Parameters in order as marked in Named Query
	 * @return
	 */
	<T> List<T> findByNamedQueryCustom(Class<T> clazz, String qName, Object... params);
	
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
	<T extends IdentifiedEntity<?>> T insert(T entity);

	/**
	 * update a existing row in the database
	 * 
	 * (This method is generic and can be used for other entities as well.)
	 * 
	 * @param object
	 *            Existing object to update
	 */
	<T extends IdentifiedEntity<?>> T update(T entity);

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
