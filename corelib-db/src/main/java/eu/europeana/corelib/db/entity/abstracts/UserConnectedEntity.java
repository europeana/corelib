package eu.europeana.corelib.db.entity.abstracts;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import eu.europeana.corelib.db.entity.User;

/**
 * Generic super class for entities connected to a certain user.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net>
 * 
 * @param <I>
 *            A Serializable type used as primary key, like a Long or String
 */
@MappedSuperclass
public abstract class UserConnectedEntity<I extends Serializable> implements IdentifiedEntity<I> {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
