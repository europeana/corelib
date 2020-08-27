package eu.europeana.corelib.db.entity.nosql;

/**
 * Only for internal (test) usage, clears a table...
 */

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;
import java.util.Date;

/**
 * Class representing an OAuth2AccessToken
 *
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
@Entity("OAuth2AccesssToken")
@Indexes({
    @Index(fields = {@Field("authenticationId")}, options = @IndexOptions(unique = true)),
    @Index(fields = {@Field("userName")}),
    @Index(fields = {@Field("clientId")}),
})
public class AccessToken extends RefreshToken {

  private String authenticationId;

  private String userName;

  private String clientId;

  private String refreshToken;

  private Date expires;

  /**
   * Empty constructor
   */
  public AccessToken() {
  }

  /**
   * Default constructor
   *
   * @param id The id of the token
   * @param expires The expiration date of the token
   */
  public AccessToken(String id, Date expires) {
    super(id);
    this.expires = new Date(expires.getTime());
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
    return new Date(expires.getTime());
  }

  public void setExpires(Date expires) {
    this.expires = new Date(expires.getTime());
  }
}
