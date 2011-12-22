package eu.europeana.corelib.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.SavedSearch;
import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.entity.User;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.users.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml", "/corelib-db-test.xml" })
public class UserServiceTest {

	@Resource
	private UserService userService;

	@Resource
	private TokenService tokenService;

	@Resource(name = "corelib_db_tokenDao")
	private Dao<Token> tokenDao;

	@Resource(name = "corelib_db_tokenDao")
	private Dao<Token> userDao;

	@Before
	public void prepareDatabase() {
		userDao.deleteAll();
		tokenDao.deleteAll();
	}

	@Test
	public void testCreate() throws DatabaseException{
		final String EMAIL = "testCreate@europeana.eu";
		final String USERNAME = "testCreate";
		final String PASSWORD = "test";
		final String HASH = hashPassword(PASSWORD);

		Token token = tokenService.create(EMAIL);
		assertNotNull("Unable to create token", token);

		User user = userService.create(token, USERNAME, PASSWORD);
		assertNotNull("Unable to create user", user);

		user = userService.findByID(user.getId());
		assertNotNull("Unable to retrieve user", user);
		assertEquals("Email address not copied from token.", user.getEmail(), token.getEmail());
		assertEquals("Username not stored correctly.", user.getUserName(), USERNAME);
		assertFalse("Password not encoded at all.", StringUtils.equals(user.getPassword(), PASSWORD));
		assertEquals("Password not correctly encoded.", user.getPassword(), HASH);
		assertNotNull("No User ID generated", user.getId());
		assertNotNull("User registration date should have value", user.getRegistrationDate());
		assertNull("User last login should by null for new user", user.getLastLogin());
		assertEquals("User should have USER role by default", user.getRole(), Role.ROLE_USER);
		assertNull("Token not removed from database", tokenService.findByID(token.getToken()));
	}

	@Test(expected=DatabaseException.class)
	public void testCreateWithoutToken() throws DatabaseException {
		userService.create(null, "ignore", "ignore");
		fail("This line should never be reached!!!");
	}

	@Test(expected=DatabaseException.class)
	public void testCreateWithInvalidToken() throws DatabaseException {
		final String EMAIL = "testCreateWithInvalidToken@europeana.eu";

		Token token = new Token();
		token.setCreated(new Date());
		token.setEmail(EMAIL);
		// token is invalid because the token itself is created on store
		// and we are not actually storing this token.
		
		userService.create(token, "ignore", "ignore");
		fail("This line should never be reached!!!");
	}
	
	@Test
	public void testFindByEmail() throws DatabaseException {
		final String EMAIL = "testFindByEmail@europeana.eu";
		final String USERNAME = "testFindByEmail";
		final String PASSWORD = "test";
		final String HASH = hashPassword(PASSWORD);

		Token token = tokenService.create(EMAIL);
		assertNotNull("Unable to create token", token);

		User user = userService.create(token, USERNAME, PASSWORD);
		assertNotNull("Unable to create user", user);
		
		user = userService.findByEmail(EMAIL);
		assertNotNull("Unable to retrieve user by email adres", user);
		assertEquals("Password not correctly encoded.", user.getPassword(), HASH);
		assertNotNull("No User ID generated", user.getId());
		
	}
	
	@Test
	public void testAuthenticateUser() throws DatabaseException {
		final String EMAIL = "testAuthenticateUser@europeana.eu";
		final String USERNAME = "testAuthenticateUser";
		final String PASSWORD = "test";

		Token token = tokenService.create(EMAIL);
		assertNotNull("Unable to create token", token);

		User user = userService.create(token, USERNAME, PASSWORD);
		assertNotNull("Unable to create user", user);

		user = userService.authenticateUser(EMAIL, "invalidPassword");
		assertNull("Authenticate method is return user with invalid password??", user);

		user = userService.authenticateUser("invalidEmail@europeana.eu", PASSWORD);
		assertNull("Authenticate method is return user with invalid email??", user);
		
		user = userService.authenticateUser(EMAIL, PASSWORD);
		assertNotNull("Authenticate method is NOT return user with valid password", user);
	}
	
	@Test
	public void testCreateSavedSearch() throws DatabaseException {
		final String EMAIL = "testCreateSavedSearch@europeana.eu";
		final String USERNAME = "testCreateSavedSearch";
		final String PASSWORD = "test";

		Token token = tokenService.create(EMAIL);
		assertNotNull("Unable to create token", token);

		User user = userService.create(token, USERNAME, PASSWORD);
		assertNotNull("Unable to create user", user);
		assertTrue("Saved Searches list should be empty!", user.getSavedSearches().size() == 0);
		
		try {
			userService.createSavedSearch(new User(), "ignore", "ignore");
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
			userService.createSavedSearch(user, " ", "ignore");
			fail("This line should never be reached!!!");
		} catch (DatabaseException e) {
			// expecting this
		}
		
		user = userService.createSavedSearch(user, "query1", "queryString1");
		user = userService.createSavedSearch(user, "query2", "queryString2");
		user = userService.createSavedSearch(user, "query3", "queryString3");
		
		user = userService.findByEmail(EMAIL);
		assertTrue("Saved Searches list should have 3 elements!", user.getSavedSearches().size() == 3);
		
		SavedSearch[] savedSearches = user.getSavedSearches().toArray(new SavedSearch[user.getSavedSearches().size()]);
		userService.removeSavedSearch(savedSearches[1].getId());
		user = userService.findByEmail(EMAIL);
		assertTrue("Saved Searches list should be one less!", user.getSavedSearches().size() == 2);
	}

	private String hashPassword(String password) {
		return new ShaPasswordEncoder().encodePassword(password, null);
	}

}
