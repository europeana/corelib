package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;

import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface AbstractNoSqlService<E extends NoSqlEntity, T extends Serializable> {

	E store(E object);

	void remove(final T id);

	/*
	 * FINDERS
	 */

	/**
	 * Returns a count of all records
	 * 
	 * @return the number of total records
	 */
	long count();

	/**
	 * Find all elements for this service.
	 * 
	 * @return All entities for the defined entity type
	 */
	 Iterable<E> findAll();

	E findByID(final T id);

	/**
	 * Checks if a entity with the given ID exists.
	 *
	 */
	boolean exists(final T id);

}
