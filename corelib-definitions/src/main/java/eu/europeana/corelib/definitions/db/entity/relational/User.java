package eu.europeana.corelib.definitions.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.users.Role;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Deprecated
public interface User extends IdentifiedEntity<Long> {

    String QUERY_FINDBY_EMAIL = "User.findByEmail";
    String QUERY_FINDBY_NAME = "User.findByName";

    void setEmail(String email);

    String getEmail();

    String getPassword();

    void setPassword(String password);

    Date getRegistrationDate();

    void setRegistrationDate(Date registrationDate);

    Date getLastLogin();

    void setLastLogin(Date lastLogin);

    Role getRole();

    void setRole(Role role);

    String getUserName();

    void setUserName(String userName);

    Set<SavedItem> getSavedItems();

    Set<SavedSearch> getSavedSearches();

    Set<SocialTag> getSocialTags();

    List<SocialTag> getSocialTagsOrdered();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getCompany();

    String getCountry();

    void setCompany(String company);

    void setCountry(String country);

    String getPhone();

    void setPhone(String phone);

    String getAddress();

    void setAddress(String address);

    String getWebsite();

    void setWebsite(String website);

    String getFieldOfWork();

    void setFieldOfWork(String fieldOfWork);

    String getLanguagePortal();

    void setLanguagePortal(String languageCode);

    String getLanguageItem();

    void setLanguageItem(String languageCode);

    String[] getLanguageSearch();

    void setLanguageSearch(String... languageCodes);

    Boolean getLanguageSearchApplied();

    void setLanguageSearchApplied(Boolean languageSearchApplied);

    Date getActivationDate();

    void setActivationDate(Date activationDate);
}