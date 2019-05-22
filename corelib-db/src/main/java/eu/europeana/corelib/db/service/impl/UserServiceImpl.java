package eu.europeana.corelib.db.service.impl;

import eu.europeana.corelib.db.entity.relational.SavedItemImpl;
import eu.europeana.corelib.db.entity.relational.SavedSearchImpl;
import eu.europeana.corelib.db.entity.relational.SocialTagImpl;
import eu.europeana.corelib.db.entity.relational.UserImpl;
import eu.europeana.corelib.db.entity.relational.custom.TagCloudItem;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.SavedItem;
import eu.europeana.corelib.definitions.db.entity.relational.SocialTag;
import eu.europeana.corelib.definitions.db.entity.relational.Token;
import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.definitions.db.entity.relational.abstracts.EuropeanaUserObject;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static eu.europeana.corelib.db.util.UserUtils.hashPassword;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.db.service.UserService
 * @see eu.europeana.corelib.db.entity.relational.UserImpl
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used*
 */
@Transactional
@Deprecated
public class UserServiceImpl extends AbstractServiceImpl<User> implements
        UserService {

    @Resource(type = TokenService.class)
    private TokenService tokenService;

    @Resource(type = SearchService.class)
    private SearchService searchService;

    @Resource(name = "corelib_web_emailService")
    private EmailService emailService;

    @Override
    public User create(
            String email, String username, String password, String company, String country, String firstName,
            String lastName, String website, String address, String phone, String fieldOfWork, String redirect, String activationUrl
    ) throws DatabaseException, EmailServiceException {

        if (StringUtils.isBlank(email)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        if (StringUtils.isBlank(redirect)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        if (StringUtils.isBlank(username)) {
            throw new DatabaseException(ProblemType.NO_USERNAME);
        }

        if (findByName(username) != null) {
            throw new DatabaseException(ProblemType.DUPLICATE);
        }

        if (StringUtils.isBlank(password)) {
            throw new DatabaseException(ProblemType.NO_PASSWORD);
        }

        User user = new UserImpl();
        user.setEmail(email);
        user.setUserName(username.toLowerCase());
        user.setPassword(hashPassword(password));
        user.setRegistrationDate(new Date());
        user.setCompany(company);
        user.setCountry(country);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setWebsite(website);
        user.setAddress(address);
        user.setPhone(phone);
        user.setFieldOfWork(fieldOfWork);

        user = getDao().insert(user);

        emailService.sendActivationToken(tokenService.create(email, redirect), activationUrl);

        return user;
    }

    @Override
    public User update(User user, String email, String username, String password, String company, String country,
                       String firstName, String lastName, String website, String address, String phone,
                       String fieldOfWork) throws DatabaseException {


        if (!user.getEmail().equalsIgnoreCase(email)){
            user.setEmail(email);
        }

        if (!user.getUserName().equalsIgnoreCase(username)){
            user.setUserName(username.toLowerCase());
        }

        if (!user.getPassword().equals(hashPassword(password))){
            user.setPassword(hashPassword(password));
        }

        if (StringUtils.isNotBlank(company)) {
            user.setCompany(company);
        }

        if (StringUtils.isNotBlank(country)) {
            user.setCountry(country);
        }

        if (StringUtils.isNotBlank(firstName)) {
            user.setFirstName(firstName);
        }

        if (StringUtils.isNotBlank(lastName)) {
            user.setLastName(lastName);
        }

        if (StringUtils.isNotBlank(website)) {
            user.setWebsite(website);
        }

        if (StringUtils.isNotBlank(address)) {
            user.setAddress(address);
        }

        if (StringUtils.isNotBlank(phone)) {
            user.setPhone(phone);
        }

        if (StringUtils.isNotBlank(fieldOfWork)) {
            user.setFieldOfWork(fieldOfWork);
        }

        user = getDao().update(user);
        return user;
    }

    @Override
    public void delete(User user) throws DatabaseException {
        if (null == user) {
            throw new DatabaseException(ProblemType.NO_USER);
        }
        getDao().delete(user);
    }

    @Override
    public Token activate(String email, String tokenString) throws DatabaseException {
        Token token = tokenService.findByID(tokenString);
        if (token == null) {
            throw new DatabaseException(ProblemType.TOKEN_INVALID);
        } else if (!StringUtils.equalsIgnoreCase(token.getEmail(), email)) {
            throw new DatabaseException(ProblemType.TOKEN_MISMATCH);
        }

        User user = findByEmail(email);
        if (user == null) {
            throw new DatabaseException(ProblemType.NO_USER);
        }
        user.setActivationDate(new Date());

        tokenService.remove(token);

        return token;
    }

    @Override
    public User sendResetPasswordToken(String email, String redirect, String activationUrl) throws DatabaseException, EmailServiceException {
        if (StringUtils.isBlank(email)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = findByEmail(email);
        if (user == null) {
            throw new DatabaseException(ProblemType.NO_USER);
        }
        emailService.sendNewPasswordToken(tokenService.create(email, redirect), activationUrl, friendlyUserName(user));
        return user;
    }

    @Override
    public String getRedirectFromToken(String email, String tokenString) throws DatabaseException {
        Token token = tokenService.findByID(tokenString);
        if (token == null) {
            throw new DatabaseException(ProblemType.TOKEN_INVALID);
        } else if (!StringUtils.equalsIgnoreCase(token.getEmail(), email)) {
            throw new DatabaseException(ProblemType.TOKEN_MISMATCH);
        }
        return token.getRedirect();
    }

    @Override
    public User resetPassword(String email, String tokenString, String newPassword) throws DatabaseException, EmailServiceException  {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(tokenString) || StringUtils.isBlank(newPassword)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = findByEmail(email);
        if (user == null) {
            throw new DatabaseException(ProblemType.NO_USER);
        }
        Token token = tokenService.findByID(tokenString);
        if (token == null) {
            throw new DatabaseException(ProblemType.TOKEN_INVALID);
        } else if (!StringUtils.equalsIgnoreCase(token.getEmail(), email)) {
            throw new DatabaseException(ProblemType.TOKEN_MISMATCH);
        }
        // user exists; token exists and matches on email address: go ahead
        user.setPassword(hashPassword(newPassword));
        tokenService.remove(token);
        emailService.sendPasswordResetConfirmation(user, friendlyUserName(user));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            return getDao().findOneByNamedQuery(User.QUERY_FINDBY_EMAIL, StringUtils.lowerCase(email));
        }
        return null;
    }

    @Override
    public User findByName(String userName) {
        if (StringUtils.isNotBlank(userName)) {
            return getDao().findOneByNamedQuery(User.QUERY_FINDBY_NAME, userName.toLowerCase());
        }
        return null;
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user = findByEmail(email);
        if ((user != null)
                && StringUtils.equals(user.getPassword(),
                hashPassword(password))) {
            return user;
        }
        return null;
    }

    //TODO - remove because this is implemented by sendResetPasswordToken and following
    @Override
    public User changePassword(Long userId, String oldPassword, String newPassword) throws DatabaseException {

        if (userId == null) {
            throw new DatabaseException(ProblemType.NO_USER_ID);
        }


        if (StringUtils.isBlank(newPassword)) {
            throw new DatabaseException(ProblemType.NO_PASSWORD);
        }

        if (StringUtils.isBlank(oldPassword)
                || StringUtils.isBlank(newPassword)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = getDao().findByPK(userId);

        if (user == null) {
            throw new DatabaseException(ProblemType.NO_USER);
        }

        if (!StringUtils.equals(user.getPassword(), oldPassword)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        user.setPassword(hashPassword(newPassword));
        return user;
    }

    @Override
    public User createSavedSearch(Long userId, String query, String queryString)
            throws DatabaseException {

        if ((userId == null) || StringUtils.isBlank(query)
                || StringUtils.isBlank(queryString)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        SavedSearchImpl savedSearch = new SavedSearchImpl();
        savedSearch.setUser(user);
        savedSearch.setDateSaved(new Date());
        savedSearch.setQuery(query);
        savedSearch.setQueryString(queryString);

        user.getSavedSearches().add(savedSearch);
        return user;
    }

    @Override
    public User updateSavedSearch(Long userId, Long savedSearchId, String query, String queryString)
            throws DatabaseException {

        if ((userId == null) || (savedSearchId==null) || StringUtils.isBlank(query)
                || StringUtils.isBlank(queryString)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        SavedSearchImpl savedSearch = getDao().findByPK(SavedSearchImpl.class,
                savedSearchId);
        if ((savedSearch != null)
                && savedSearch.getUser().getId().equals(userId)) {
            savedSearch.setUser(user);
            savedSearch.setDateSaved(new Date());
            savedSearch.setQuery(query);
            savedSearch.setQueryString(queryString);
        }
        return user;
    }

    @Override
    public User createSavedItem(Long userId, String europeanaObjectId) throws DatabaseException, Neo4JException {

        if ((userId == null) || StringUtils.isBlank(europeanaObjectId)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        if (findSavedItemByEuropeanaId(userId, europeanaObjectId) != null) {
            throw new DatabaseException(ProblemType.DUPLICATE);
        }

        SavedItem savedItem = new SavedItemImpl();
        FullBean bean;

        bean = populateEuropeanaUserObject(user, europeanaObjectId, savedItem);

        if (bean != null) {
            List<? extends Proxy> proxies = bean.getProxies();
            Proxy proxy = proxies.get(0);
            if (proxy != null && proxy.getDcPublisher() != null) {
                savedItem.setAuthor(StringUtils.abbreviate(proxy
                                .getDcPublisher().values().iterator().next().get(0),
                        RelationalDatabase.FIELDSIZE_AUTHOR));
            }
        }
        return user;
    }

    @Override
    public User createSocialTag(Long userId, String europeanaObjectId,
                                String tag) throws DatabaseException, Neo4JException {

        if ((userId == null) || StringUtils.isBlank(europeanaObjectId)
                || StringUtils.isBlank(tag)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        SocialTag socialTag = new SocialTagImpl();
        populateEuropeanaUserObject(user, europeanaObjectId, socialTag);

        socialTag.setTag(StringUtils.abbreviate(tag,
                RelationalDatabase.FIELDSIZE_TAG));

        return user;
    }


    @Override
    public void removeSavedSearch(Long userId, Long savedSearchId)
            throws DatabaseException {
        SavedSearchImpl savedSearch = getDao().findByPK(SavedSearchImpl.class,
                savedSearchId);
        if ((savedSearch != null)
                && savedSearch.getUser().getId().equals(userId)) {
            savedSearch.getUser().getSavedSearches().remove(savedSearch);
        }
    }

    @Override
    public void removeSavedItem(Long userId, Long savedItemId)
            throws DatabaseException {
        SavedItem savedItem = getDao().findByPK(SavedItemImpl.class,
                savedItemId);
        if ((savedItem != null) && savedItem.getUser().getId().equals(userId)) {
            savedItem.getUser().getSavedItems().remove(savedItem);
        }
    }

    @Override
    public void removeSavedItem(Long userId, String europeanaId) throws DatabaseException {
        SavedItem savedItem = findSavedItemByEuropeanaId(userId, europeanaId);
        if (savedItem != null) {
            savedItem.getUser().getSavedItems().remove(savedItem);
        }
    }

    @Override
    public void removeSocialTag(Long userId, Long socialTagId)
            throws DatabaseException {
        SocialTag socialTag = getDao().findByPK(SocialTagImpl.class,
                socialTagId);
        if ((socialTag != null) && socialTag.getUser().getId().equals(userId)) {
            socialTag.getUser().getSocialTags().remove(socialTag);
        }
    }

    @Override
    public void removeSocialTag(Long userId, String europeanaId, String tag) throws DatabaseException {
        List<SocialTag> results = null;
        if (StringUtils.isNotBlank(europeanaId) && StringUtils.isNotBlank(tag)) {
            results = getDao().findByNamedQuery(SocialTag.class, SocialTag.QUERY_FINDBY_USER_TAG_EUROPEANAID, userId, StringUtils.lowerCase(tag), europeanaId);

        } else if (StringUtils.isNotBlank(tag)) {
            results = findSocialTagsByTag(userId, tag);
        } else if (StringUtils.isNotBlank(europeanaId)) {
            results = findSocialTagsByEuropeanaId(userId, europeanaId);
        }
        if ((results != null) && !results.isEmpty()) {
            results.get(0).getUser().getSocialTags().removeAll(results);
        }

    }

    @Override
    public List<TagCloudItem> createSocialTagCloud(Long userId) throws DatabaseException {
        if (userId == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        return getDao().findByNamedQueryCustom(TagCloudItem.class, SocialTag.QUERY_CREATECLOUD_BYUSER, userId);
    }

    @Override
    public List<SocialTag> findSocialTagsByTag(Long userId, String tag) throws DatabaseException {
        if ((userId == null) || StringUtils.isBlank(tag)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        return getDao().findByNamedQuery(SocialTag.class, SocialTag.QUERY_FINDBY_USER_TAG, userId, StringUtils.lowerCase(tag));
    }

    @Override
    public List<SocialTag> findSocialTagsByEuropeanaId(Long userId, String europeanaId) throws DatabaseException {
        if ((userId == null) || StringUtils.isBlank(europeanaId)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        return getDao().findByNamedQuery(SocialTag.class, SocialTag.QUERY_FINDBY_USER_EUROPEANAID, userId, europeanaId);
    }

    @Override
    public SavedItem findSavedItemByEuropeanaId(Long userId, String europeanaId) throws DatabaseException {
        if ((userId == null) || StringUtils.isBlank(europeanaId)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        return getDao().findOneByNamedQuery(SavedItemImpl.class, SavedItem.QUERY_FINDBY_OBJECTID, userId, europeanaId);
    }

    @Override
    public User updateUserLanguagePortal(Long userId, String languageCode) throws DatabaseException {
        if (userId == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        user.setLanguagePortal(languageCode);
        return user;
    }

    @Override
    public User updateUserLanguageItem(Long userId, String languageCode) throws DatabaseException {
        if (userId == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        user.setLanguageItem(languageCode);
        return user;
    }

    @Override
    public User updateUserLanguageSearch(Long userId, String... languageCodes) throws DatabaseException {
        if (userId == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        user.setLanguageSearch(languageCodes);
        updateUserLanguageSearchApplied(userId, languageCodes.length > 0);
        return user;
    }

    @Override
    public User updateUserLanguageSearchApplied(Long userId, Boolean languageSearchApplied) throws DatabaseException {
        if (userId == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        User user = getDao().findByPK(userId);
        if (user == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
        user.setLanguageSearchApplied(languageSearchApplied);
        return user;
    }

    private FullBean populateEuropeanaUserObject(User user,
                                                 String europeanaObjectId, EuropeanaUserObject instance) throws DatabaseException, Neo4JException {

        FullBean bean;
        try {
            bean = searchService.findById(europeanaObjectId, false);
        } catch (EuropeanaException e) {
            throw new DatabaseException(e, e.getProblem());
        }

        if ((user == null) || (bean == null)) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }

        instance.setEuropeanaUri(bean.getAbout());
        String edmObject = null;
        for (Aggregation aggregation : bean.getAggregations()) {
            if (!StringUtils.isBlank(aggregation.getEdmObject())) {
                edmObject = aggregation.getEdmObject();
            }
        }
        instance.setEuropeanaObject(edmObject);

        instance.setDateSaved(new Date());

        String title = null;
        if (bean.getTitle() != null && bean.getTitle().length > 0) {
            for (String _title : bean.getTitle()) {
                if (!StringUtils.isBlank(_title)) {
                    title = _title;
                    break;
                }
            }
        }
        if (title == null) {
            for (Proxy proxy : bean.getProxies()) {
                if (proxy == null) {
                    continue;
                }
                if (proxy.getDcTitle() != null) {
                    for (List<String> items : proxy.getDcTitle().values()) {
                        for (String item : items) {
                            if (!StringUtils.isBlank(item)) {
                                title = item;
                                break;
                            }
                        }
                    }
                }
                if (title == null && proxy.getDcDescription() != null) {
                    for (List<String> items : proxy.getDcDescription().values()) {
                        for (String item : items) {
                            if (!StringUtils.isBlank(item)) {
                                title = item;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (title == null) {
            title = europeanaObjectId;
        }

        instance.setTitle(StringUtils.abbreviate(title, RelationalDatabase.FIELDSIZE_TITLE));
        instance.setDocType(bean.getType());
        instance.setUser(user);
        if (instance instanceof SavedItemImpl) {
            user.getSavedItems().add((SavedItem) instance);
        } else {
            user.getSocialTags().add((SocialTag) instance);
        }
        return bean;
    }

    private String friendlyUserName(User user){
        if (StringUtils.isBlank(user.getFirstName()) && StringUtils.isBlank(user.getLastName()) && StringUtils.isBlank(user.getUserName())){
            return "Sir / Madam";
        } else if (StringUtils.isBlank(user.getFirstName()) && StringUtils.isBlank(user.getLastName())) {
            return user.getUserName();
        } else if (StringUtils.isBlank(user.getFirstName())){
            return "Mr / Ms " + user.getLastName();
        } else if (StringUtils.isBlank(user.getLastName())) {
            return user.getFirstName();
        } else {
            return user.getFirstName() + " " + user.getLastName();
        }
    }
}
