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

package eu.europeana.corelib.db.entity.relational.abstracts;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import eu.europeana.corelib.db.entity.relational.UserImpl;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.UserConnected;

/**
 * Generic super class for entities connected to a certain user.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @param <I>
 *            A Serializable type used as primary key, like a Long or String
 */
@MappedSuperclass
public abstract class UserConnectedImpl<I extends Serializable> implements IdentifiedEntity<I>, UserConnected<I> {
	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}
}
