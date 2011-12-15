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

public interface Dao<E extends IdentifiedEntity<?>> {

	/*
	 * FINDERS
	 */

	/**
	 * Retrieve a row through a private key
	 * 
	 * @param pk
	 *            A private key object
	 * @return Returns a row
	 */
	E findByPK(final Serializable id);

	/**
	 * Retrieve all entities from a table.
	 * 
	 * @return a List with all entities
	 */
	List<E> findAll();

	/*
	 * MODIFIERS
	 */

	/**
	 * insert a new row into the database
	 * 
	 * @param object
	 *            The new row to insert
	 */
	E insert(E object);

	/**
	 * update a row in the database
	 * 
	 * @param object
	 *            Existing object to update
	 */
	E update(E object);

	/**
	 * Delete a existing object in the database
	 * 
	 * @param object
	 *            The existing row to delete
	 */
	void delete(E object);

	/**
	 * Only for internal (test) usage, clears a table...
	 */
	void deleteAll();

}
