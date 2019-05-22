package eu.europeana.corelib.db.entity.nosql;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

/**
 * A refresh token for OAuth2
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Entity("OAuth2RefreshToken")
@Deprecated
public class RefreshToken implements NoSqlEntity {

	@Id
	private String tokenId;

	@Indexed(unique=false)
	private String apiKey;

	private byte[] token;

	private byte[] authentication;
	
	public RefreshToken() {
	}
	
	public RefreshToken(String id) {
		this.tokenId = id;
	}
	
	/**
	 * GETTERS & SETTTERS
	 */

	public String getId() {
		return tokenId;
	}

	public byte[] getToken() {
		return token.clone();
	}

	public void setToken(byte[] token) {
		this.token = token.clone();
	}

	public byte[] getAuthentication() {
		return authentication.clone();
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication.clone();
	}

	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
