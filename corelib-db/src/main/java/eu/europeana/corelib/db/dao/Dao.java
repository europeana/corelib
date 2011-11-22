package eu.europeana.corelib.db.dao;

import java.io.Serializable;

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

}
