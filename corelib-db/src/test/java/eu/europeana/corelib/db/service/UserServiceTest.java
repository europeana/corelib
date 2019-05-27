package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.dao.RelationalDao;
import eu.europeana.corelib.db.entity.relational.TokenImpl;
import eu.europeana.corelib.db.entity.relational.UserImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.*;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.users.Role;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.service.EmailService;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.europeana.corelib.db.util.UserUtils.hashPassword;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
@Deprecated
public class UserServiceTest {

    public static final String[] TITLE = new String[]{"Mock Title"};
    public static final String[] AUTHOR = new String[]{"Mock Author"};
    public static final String[] THUMBNAIL = new String[]{"MockThumbnail.jpg"};

    @Resource
    private UserService userService;

    @Resource
    private SearchService searchService;

    @Resource
    private EmailService emailServiceMock;

    @Resource(name = "corelib_db_tokenDao")
    private RelationalDao<TokenImpl> tokenDao;

    @Resource(name = "corelib_db_userDao")
    private RelationalDao<UserImpl> userDao;

    @Before
    public void prepareDatabase() {
        userDao.deleteAll();
        tokenDao.deleteAll();
        reset(emailServiceMock);
    }

    @Test
    public void testCreate() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testCreate@europeana.eu";
        final String USERNAME = "testCreate";
        final String PASSWORD = "test";
        final String HASH = hashPassword(PASSWORD);

        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);

        verify(emailServiceMock, times(1)).sendActivationToken((Token) anyObject(), anyString());

        user = userService.findByID(user.getId());
        assertNotNull("Unable to retrieve user", user);
        assertEquals("Email address not stored correctly.", EMAIL.toLowerCase(), user.getEmail());
        assertEquals("Username not stored correctly.", USERNAME.toLowerCase(), user.getUserName());
        assertFalse("Password not encoded at all.", StringUtils.equals(PASSWORD, user.getPassword()));
        assertEquals("Password not correctly encoded.", HASH, user.getPassword());
        assertNotNull("No User ID generated", user.getId());
        assertNotNull("User registration date should have value", user.getRegistrationDate());
        assertNull("User activation date should have no value", user.getActivationDate());
        assertNull("User last login should by null for new user", user.getLastLogin());
        assertEquals("User should have USER role by default", Role.ROLE_USER, user.getRole());
        assertEquals("No token generated", 1, tokenDao.findAll().size());

        // activate account
        Token token = tokenDao.findAll().get(0);
        assertEquals("Email address not stored correctly in token.", EMAIL.toLowerCase(), token.getEmail());
        assertNotNull("No token generated", token.getToken());

        Token token2 = userService.activate(EMAIL, token.getToken());
        User user2 = userService.findByID(user.getId());
        assertNotNull("User activation date should have value", user2.getActivationDate());
        assertEquals("Token should be removed", 0, tokenDao.findAll().size());
    }

    @Test
    public void testFindByEmail() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testFindByEmail@europeana.eu";
        final String USERNAME = "testFindByEmail";
        final String PASSWORD = "test";
        final String HASH = hashPassword(PASSWORD);

        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);

        assertEquals("Count doesn't match", 1, userDao.count());
        assertEquals("FindAll doesn't match", 1, userDao.findAll().size());

        user = userService.findByEmail(EMAIL);
        assertNotNull("Unable to retrieve user by email adres", user);
        assertEquals("Password not correctly encoded.", HASH, user.getPassword());
        assertNotNull("No User ID generated", user.getId());

    }

    @Test
    public void testChangePassword() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testChangePassword@europeana.eu";
        final String USERNAME = "testChangePassword";
        final String PASSWORD1 = "test";
        final String PASSWORD2 = "changed";
        final String HASH1 = hashPassword(PASSWORD1);
        final String HASH2 = hashPassword(PASSWORD2);

        User user = userService.create(EMAIL, USERNAME, PASSWORD1, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);
        assertEquals("Password not correctly encoded.", user.getPassword(), HASH1);

        user = userService.changePassword(user.getId(), HASH1, PASSWORD2);
        assertEquals("Password not correctly changed/encoded.",
                user.getPassword(), HASH2);

        try {
            // try changing with wrong password
            userService.changePassword(user.getId(), PASSWORD1, PASSWORD2);
            fail("This line should never be reached!!!");
        } catch (DatabaseException ignore) {
            // expecting this
        }

        try {
            // try changing with wrong new password
            userService.changePassword(user.getId(), PASSWORD2, "");
            fail("This line should never be reached!!!");
        } catch (DatabaseException ignore) {
            // expecting this
        }

        try {
            // try changing with wrong user id
            userService.changePassword(Long.MAX_VALUE, PASSWORD1, PASSWORD2);
            fail("This line should never be reached!!!");
        } catch (DatabaseException ignore) {
            // expecting this
        }

        user = userService.authenticateUser(EMAIL, PASSWORD2);
        assertNotNull("Authenticate method is NOT return user with valid password", user);

    }

    @Test
    public void testAuthenticateUser() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testAuthenticateUser@europeana.eu";
        final String USERNAME = "testAuthenticateUser";
        final String PASSWORD = "test";

        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);

        user = userService.authenticateUser(EMAIL, "invalidPassword");
        assertNull("Authenticate method is return user with invalid password??", user);

        user = userService.authenticateUser("invalidEmail@europeana.eu", PASSWORD);
        assertNull("Authenticate method is return user with invalid email??", user);

        user = userService.authenticateUser(EMAIL, PASSWORD);
        assertNotNull("Authenticate method is NOT return user with valid password", user);
    }

    @Test
    public void testCreateSavedSearch() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testCreateSavedSearch@europeana.eu";
        final String USERNAME = "testCreateSavedSearch";
        final String PASSWORD = "test";
        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);
        assertTrue("Saved Searches list should be empty!", user.getSavedSearches().size() == 0);

        try {
            userService.createSavedSearch(Long.MAX_VALUE, "ignore", "ignore");
            fail("This line should never be reached!!!");
        } catch (DatabaseException e) {
            // expecting this
        }

        try {
            userService.createSavedSearch(null, "ignore", "ignore");
            fail("This line should never be reached!!!");
        } catch (DatabaseException e) {
            // expecting this
        }

        try {
            userService.createSavedSearch(user.getId(), " ", "ignore");
            fail("This line should never be reached!!!");
        } catch (DatabaseException e) {
            // expecting this
        }

        userService.createSavedSearch(user.getId(), "query1", "queryString1");

        user = userService.findByEmail(EMAIL);
        assertTrue("Saved Searches list should have 3 elements!", user.getSavedSearches().size() == 1);

        SavedSearch savedSearch = user.getSavedSearches().iterator().next();
        assertEquals("query1", savedSearch.getQuery());
        assertEquals("queryString1", savedSearch.getQueryString());
        assertNotNull("No creation date set", savedSearch.getDateSaved());

        userService.createSavedSearch(user.getId(), "query2", "queryString2");
        userService.createSavedSearch(user.getId(), "query3", "queryString3");

        user = userService.findByEmail(EMAIL);
        assertTrue("Saved Searches list should have 3 elements!", user.getSavedSearches().size() == 3);

        SavedSearch[] savedSearches =
                user.getSavedSearches().toArray(new SavedSearch[user.getSavedSearches().size()]);
        userService.removeSavedSearch(user.getId(), savedSearches[1].getId());
        user = userService.findByEmail(EMAIL);
        assertTrue("Saved Searches list should be one less!", user
                .getSavedSearches().size() == 2);
    }

    @Test
    public void testUpdateSavedSearch() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testCreateSavedSearch@europeana.eu";
        final String USERNAME = "testCreateSavedSearch";
        final String PASSWORD = "test";
        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);

        userService.createSavedSearch(user.getId(), "query1", "queryString1");

        user = userService.findByEmail(EMAIL);
        SavedSearch savedSearch = user.getSavedSearches().iterator().next();
        assertEquals("query1", savedSearch.getQuery());
        assertEquals("queryString1", savedSearch.getQueryString());
        assertNotNull("No creation date set", savedSearch.getDateSaved());


        userService.updateSavedSearch(user.getId(),savedSearch.getId(),"updatedQuery","updatedQueryString");

        user = userService.findByEmail(EMAIL);
        SavedSearch updatedSavedSearch = user.getSavedSearches().iterator().next();
        assertEquals("updatedQuery", updatedSavedSearch.getQuery());
        assertEquals("updatedQueryString", updatedSavedSearch.getQueryString());
        assertNotNull("No creation date set", updatedSavedSearch.getDateSaved());
    }

    @Test
    public void testCreateSavedItem() throws EuropeanaException {
        final String EMAIL = "testCreateSavedItem@europeana.eu";
        final String USERNAME = "testCreateSavedItem";
        final String PASSWORD = "test";
        final String EUROPEANA_ID = "testCreateSavedItem";

        setupSeachServiceMock(EUROPEANA_ID);

        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);
        assertTrue("Saved Items list should be empty!", user.getSavedItems().size() == 0);

        try {
            userService.createSavedItem(Long.MAX_VALUE, EUROPEANA_ID);
            fail("This line should never be reached!!!");
        } catch (DatabaseException e) {
            // expecting this
        }

        userService.createSavedItem(user.getId(), EUROPEANA_ID);
        user = userService.findByEmail(EMAIL);
        assertTrue("Saved Items list should be one!", user.getSavedItems().size() == 1);

        SavedItem item = user.getSavedItems().iterator().next();
        assertEquals(EUROPEANA_ID, item.getEuropeanaUri());
        assertEquals(THUMBNAIL[0], item.getEuropeanaObject());
        assertEquals(TITLE[0], item.getTitle());
        assertEquals(AUTHOR[0], item.getAuthor());
        assertEquals(DocType.TEXT, item.getDocType());
        assertNotNull("No creation date set", item.getDateSaved());

        userService.removeSavedItem(user.getId(), item.getId());
        user = userService.findByEmail(EMAIL);
        assertTrue("Saved Items list should be empty!", user.getSavedItems().size() == 0);
    }

    @Test
    public void testCreateSocialTag() throws EuropeanaException {
        final String EMAIL = "testCreateSocialTag@europeana.eu";
        final String USERNAME = "testCreateSocialTag";
        final String PASSWORD = "test";
        final String EUROPEANA_ID = "testCreateSocialTag";
        final String TAG = "testCreateSocialTag";

        setupSeachServiceMock(EUROPEANA_ID);
        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);
        assertTrue("SocialTag list should be empty!", user.getSocialTags().size() == 0);

        try {
            userService.createSocialTag(Long.MAX_VALUE, EUROPEANA_ID, "ignore");
            fail("This line should never be reached!!!");
        } catch (DatabaseException e) {
            // expecting this
        }

        userService.createSocialTag(user.getId(), EUROPEANA_ID, TAG);
        user = userService.findByEmail(EMAIL);
        assertTrue("SocialTag list should be one!", user.getSocialTags().size() == 1);

        SocialTag tag = user.getSocialTags().iterator().next();
        assertEquals(EUROPEANA_ID, tag.getEuropeanaUri());
        assertEquals(THUMBNAIL[0], tag.getEuropeanaObject());
        assertEquals(StringUtils.lowerCase(TAG), tag.getTag());
        assertEquals(TITLE[0], tag.getTitle());
        assertNotNull("No creation date set", tag.getDateSaved());

        userService.removeSocialTag(user.getId(), tag.getId());
        user = userService.findByEmail(EMAIL);
        assertTrue("SocialTag list should be empty!", user.getSocialTags().size() == 0);
    }

    @Test
    public void testUserLanguageSettings() throws DatabaseException, EmailServiceException {
        final String EMAIL = "testCreateSocialTag@europeana.eu";
        final String USERNAME = "testCreateSocialTag";
        final String PASSWORD = "test";

        final String LANGCODE = "nl";
        final String[] LANGCODES_ARRAY = {"nl", "en", "de"};
        final String LANGCODES = "fi|es|it|fr";

        User user = userService.create(EMAIL, USERNAME, PASSWORD, null, null, null, null, null, null, null, null, "http://europeana.eu", null);
        assertNotNull("Unable to create user", user);
        assertNull("By default the value should be empty", user.getLanguagePortal());
        assertNull("By default the value should be empty", user.getLanguageItem());
        assertNotNull("By default the value should be empty", user.getLanguageSearch());
        assertTrue("By default it should return an empty array", user.getLanguageSearch().length == 0);

        user = userService.updateUserLanguagePortal(user.getId(), LANGCODE);
        assertEquals("Value should be set to NL", LANGCODE, user.getLanguagePortal());

        user = userService.updateUserLanguageItem(user.getId(), LANGCODE);
        assertEquals("Value should be set to NL", LANGCODE, user.getLanguageItem());

        user = userService.updateUserLanguageSearch(user.getId(), LANGCODES);
        assertNotNull("Should never return null", user.getLanguageSearch());
        assertEquals("Shoud contain all values", 4, user.getLanguageSearch().length);

        user = userService.updateUserLanguageSearch(user.getId(), LANGCODES_ARRAY);
        assertNotNull("Should never return null", user.getLanguageSearch());
        assertEquals("Shoud contain all values", LANGCODES_ARRAY.length, user.getLanguageSearch().length);

        user = userService.updateUserLanguagePortal(user.getId(), null);
        user = userService.updateUserLanguageItem(user.getId(), null);
        user = userService.updateUserLanguageSearch(user.getId(), (String) null);

        assertNotNull("Unable to create user", user);
        assertNull("By default the value should be empty", user.getLanguagePortal());
        assertNull("By default the value should be empty", user.getLanguageItem());
        assertNotNull("By default the value should be empty", user.getLanguageSearch());
        assertTrue("By default it should return an empty array", user.getLanguageSearch().length == 0);
    }

    @SuppressWarnings("unchecked")
    private void setupSeachServiceMock(String europeanaObjectId) throws EuropeanaException {
        FullBean mockBean = mock(FullBean.class);
        Proxy proxy = mock(Proxy.class);
        Aggregation aggregation = mock(Aggregation.class);

        Map<String, List<String>> dcPublisher = new HashMap<>();
        dcPublisher.put("def", Collections.singletonList(AUTHOR[0]));

        when(mockBean.getTitle()).thenReturn(TITLE);
        when(mockBean.getId()).thenReturn(europeanaObjectId);
        when(mockBean.getType()).thenReturn(DocType.TEXT);
        when(mockBean.getAbout()).thenReturn(europeanaObjectId);
        when((List<Aggregation>) mockBean.getAggregations()).thenReturn(Collections.singletonList(aggregation));
        when((List<Proxy>) mockBean.getProxies()).thenReturn(Collections.singletonList(proxy));

        when(proxy.getDcPublisher()).thenReturn(dcPublisher);
        when(aggregation.getEdmObject()).thenReturn(THUMBNAIL[0]);

        when(searchService.findById(anyString(), anyBoolean())).thenReturn(mockBean);
    }
}
