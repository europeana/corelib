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

package eu.europeana.corelib.db.service.abstracts;

import java.io.Serializable;
import java.util.List;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

public abstract class AbstractServiceImpl<E extends IdentifiedEntity<?>>
		implements AbstractService<E> {

	protected Dao<E> dao;

	public final void setDao(Dao<E> dao) {
		this.dao = dao;
	}

	@Override
	public E store(E entity) {
		return dao.update(entity);
	}

	@Override
	public void remove(E entity) {
		dao.delete(entity);
	}

	@Override
	public E findByID(Serializable id) {
		return dao.findByPK(id);
	}

	@Override
	public List<E> findAll() {
		return dao.findAll();
	}

}
