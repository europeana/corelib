/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

public abstract class AbstractServiceImpl<E extends IdentifiedEntity<?>> implements AbstractService<E> {
	
	protected Dao<E> dao;
	
	public final void setDao(Dao<E> dao) {
		this.dao = dao;
	}

	@Override
	public void store(E object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(E object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E findByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

}
