package eu.europeana.corelib.db.entity.nosql;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.Indexes;
import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

/**
 * A refresh token for OAuth2
 *
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Entity("OAuth2RefreshToken")
@Deprecated
@Indexes({@Index(fields = {@Field("apiKey")})})
public class RefreshToken implements NoSqlEntity {

  @Id
  private String tokenId;

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
