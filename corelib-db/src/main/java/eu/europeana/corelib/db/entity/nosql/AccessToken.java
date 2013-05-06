package eu.europeana.corelib.db.entity.nosql;

import java.util.Date;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;

@Entity("OAuth2AccesssToken")
public class AccessToken extends RefreshToken {

	@Indexed(unique=true)
	private String authenticationId;

	@Indexed(unique=false)
	private String userName;
	
	@Indexed(unique=false)
	private String clientId;
	
	private String refreshToken;
	
	private Date expires;
	
	public AccessToken() {
	}
	
	public AccessToken(String id, Date expires) {
		super(id);
		this.expires = expires;
	}

	/**
	 * GETTERS & SETTTERS
	 */

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}
	
	public String getAuthenticationId() {
		return authenticationId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}
}
