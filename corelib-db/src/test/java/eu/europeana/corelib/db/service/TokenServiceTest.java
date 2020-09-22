//package eu.europeana.corelib.db.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.fail;
//
//import java.util.Calendar;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import eu.europeana.corelib.db.dao.RelationalDao;
//import eu.europeana.corelib.db.entity.relational.TokenImpl;
//import eu.europeana.corelib.db.exception.DatabaseException;
//import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
//import eu.europeana.corelib.definitions.db.entity.relational.Token;
//
///**
// * @author Willem-Jan Boogerd <www.eledge.net/contact>
// * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
//@Deprecated
//public class TokenServiceTest {
//
//	@Resource(name = "corelib_db_tokenDao")
//	private RelationalDao<TokenImpl> dao;
//
//	@Resource
//	private TokenService tokenService;
//
//	/**
//	 * Clears database tables before every test and at the end
//	 */
//	@Before
//	public void clearBetweenTest() {
//		dao.deleteAll();
//	}
//
//	@Test
//	public void testCreateRandomToken() {
//		String token = tokenService.createRandomToken();
//		assertNotNull("No token generated", StringUtils.trimToNull(token));
//		assertEquals("Token did not have the required length, current length=" + StringUtils.length(token),
//				RelationalDatabase.FIELDSIZE_TOKEN, StringUtils.length(token));
//	}
//
//	@Test
//	public void testCreate() throws DatabaseException {
//		Token token = tokenService.create("test@europeana.eu", "http://europeana.eu/");
//		assertNotNull("Unable to create token", token);
//		token = tokenService.findByID(token.getToken());
//		assertNotNull("Unable to retrieve created token", token);
//		assertNotNull("No token generated", StringUtils.trimToNull(token.getId()));
//		assertNotNull("No valid creation date set", token.getCreated());
//	}
//
//	@Test(expected=DatabaseException.class)
//	public void testCreateInvalidEmail() throws DatabaseException {
//		tokenService.create(" ", " ");
//		fail("This line should never be reached!!!");
//	}
//
//	@Test(expected=DatabaseException.class)
//	public void testExpiredToken() throws DatabaseException {
//		Calendar expired = Calendar.getInstance();
//		expired.add(Calendar.MILLISECOND, -TokenService.MAX_TOKEN_AGE);
//
//		Token token = tokenService.create("test@europeana.eu", "http://europeana.eu/");
//		assertNotNull("Unable to create token", token);
//		// change date to expired date
//		token.setCreated(expired.getTime());
//		tokenService.store(token);
//		tokenService.findByID(token.getToken());
//		fail("This line should never be reached!!!");
//		// assertNull("Token not flagged as expired", );
//	}
//}
