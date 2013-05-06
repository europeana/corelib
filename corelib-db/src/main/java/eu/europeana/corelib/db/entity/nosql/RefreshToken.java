package eu.europeana.corelib.db.entity.nosql;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

@Entity("OAuth2RefreshToken")
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
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
