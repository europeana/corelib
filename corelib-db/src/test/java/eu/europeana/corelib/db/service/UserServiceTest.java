package eu.europeana.corelib.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.dao.Dao;
import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.entity.User;
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
	public void testCreate() {
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
	
	@Test
	public void testFindByEmail() {
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
	public void testAuthenticateUser() {
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

	private String hashPassword(String password) {
		return new ShaPasswordEncoder().encodePassword(password, null);
	}

}
