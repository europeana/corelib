package eu.europeana.corelib.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
		final String EMAIL = "test@europeana.eu";
		final String PASSWORD = "test";
		
		Token token = tokenService.create(EMAIL);
		assertNotNull("Unable to create token", token);

		User user = userService.create(token, PASSWORD);
		assertNotNull("Unable to create user", user);
		user = userService.findByID(user.getId());
		assertNotNull("Unable to retrieve user", user);
		assertEquals("Email address not copied from token.",user.getEmail(), token.getEmail());
		assertNotNull("No User ID generated", user.getId());
		assertNotNull("User registration date should have value", user.getRegistrationDate());
		assertNull("User last login should by null for new user", user.getLastLogin());
		assertEquals("User should have USER role by default", user.getRole(), Role.ROLE_USER);
		assertNull("Token not removed from database", tokenService.findByID(token.getToken()));
	}

}
