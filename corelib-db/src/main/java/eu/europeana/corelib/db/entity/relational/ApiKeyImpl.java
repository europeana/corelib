package eu.europeana.corelib.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.enums.ApiClientLevel;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_APIKEY)
@NamedQuery(name = ApiKey.QUERY_FINDBY_EMAIL, query = "select a from ApiKeyImpl a where a.email = ?")
public class ApiKeyImpl implements RelationalDatabase, ApiKey {
    private static final long serialVersionUID = -1717717883751281497L;

    @Id
    @Column(length = FIELDSIZE_APIKEY, nullable = false)
    private String apiKey;

    @Column(length = FIELDSIZE_APIKEY, nullable = false)
    private String privateKey;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate = new Date();

    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date activationDate;

    @Column(length = FIELDSIZE_PERSONAL, nullable = false)
    @Index(name = "apikey_email_index")
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ApiClientLevel level = ApiClientLevel.CLIENT;

    @Column
    private Long usageLimit;

    @Column(length = FIELDSIZE_NAME, nullable = true)
    private String firstName;

    @Column(length = FIELDSIZE_SURNAME, nullable = true)
    private String lastName;

    @Column(length = FIELDSIZE_COMPANY, nullable = true)
    private String company;

    @Column(length = FIELDSIZE_WEBSITE, nullable = true)
    private String website;

    @Column(name = "appName", nullable = true)
    private String applicationName;

    @Column(nullable = true)
    private String description;

    /**
     * GETTERS & SETTTERS
     */

    @Override
    public String getId() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public Long getUsageLimit() {
        return usageLimit;
    }

    @Override
    public void setUsageLimit(Long usageLimit) {
        this.usageLimit = usageLimit;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public ApiClientLevel getLevel() {
        return level;
    }

    @Override
    public void setLevel(ApiClientLevel level) {
        this.level = level;
    }

    @Override
    public String getCompany() {
        return company;
    }

    @Override
    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public Date getActivationDate() {
        return activationDate;
    }

    @Override
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }
}
