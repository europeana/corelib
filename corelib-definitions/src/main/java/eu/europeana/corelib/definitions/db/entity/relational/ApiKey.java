package eu.europeana.corelib.definitions.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.db.entity.relational.enums.ApiClientLevel;

import java.util.Date;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface ApiKey extends IdentifiedEntity<String> {

    String QUERY_FINDBY_EMAIL = "ApiKey.findByEmail";

    void setApiKey(String apiKey);

    String getPrivateKey();

    void setPrivateKey(String privateKey);

    String getApplicationName();

    void setApplicationName(String applicationName);

    Long getUsageLimit();

    void setUsageLimit(Long usageLimit);

    String getEmail();

    void setEmail(String email);

    ApiClientLevel getLevel();

    void setLevel(ApiClientLevel level);

    String getCompany();

    void setCompany(String company);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Date getRegistrationDate();

    String getDescription();

    void setDescription(String description);

    String getWebsite();

    void setWebsite(String website);

    Date getActivationDate();

    void setActivationDate(Date activationDate);
}