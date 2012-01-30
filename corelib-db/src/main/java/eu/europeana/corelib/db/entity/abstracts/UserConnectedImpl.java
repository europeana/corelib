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

package eu.europeana.corelib.db.entity.abstracts;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import eu.europeana.corelib.db.entity.UserImpl;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.definitions.db.entity.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.db.entity.abstracts.UserConnected;

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
