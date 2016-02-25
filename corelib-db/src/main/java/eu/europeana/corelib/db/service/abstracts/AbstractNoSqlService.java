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
