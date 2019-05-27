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
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@MappedSuperclass
@Deprecated
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
