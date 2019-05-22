package eu.europeana.corelib.db.entity.relational;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;

/**
 * Token table contains three columns:
 * - token: a unique identifier, used in registration. The user should send back the same token to confirm the validity of the email address
 * - email: an email address
 * - created: when the token created.
 * 
 * When a user is registered an email is sent out (EmailServiceImpl.sendToken()) and he/she should clikc the URL:
 *   ${url}?token=${token}
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_TOKEN)
@Deprecated
public class TokenImpl implements IdentifiedEntity<String>, RelationalDatabase, Token {
	private static final long serialVersionUID = -9185878608713327601L;

	/**
	 * The identifier
	 */
	@Id
	@Column(length = FIELDSIZE_TOKEN, nullable = false)
	private String token;

	/**
	 * The identifier
	 */
	@Column(length = FIELDSIZE_REDIRECT, nullable = false)
	private String redirect;

	/**
	 * Email address
	 */
	@Column(length = FIELDSIZE_PERSONAL, nullable = false)
	private String email;

	/**
	 * The date the token created
	 */
	@Column(nullable = false)
	private long created;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public String getId() {
		return token;
	}

	@Override
	public String getToken() {
		return token;
	}

	@Override
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = StringUtils.lowerCase(email);
	}

	@Override
	public long getCreated() {
		return created;
	}

	@Override
	public void setCreated(Date created) {
		this.created = created.getTime();
	}

	@Override
	public String getRedirect() {
		return redirect;
	}

	@Override
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
}