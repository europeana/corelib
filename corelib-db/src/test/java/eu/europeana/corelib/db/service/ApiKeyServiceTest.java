package eu.europeana.corelib.db.service;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.definitions.db.entity.relational.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml" })
// "/corelib-db-test.xml"
public class ApiKeyServiceTest {

	private static final long DEFAULT_USAGE_LIMIT = 10000;

	@Resource
	private UserService userService;

	@Resource
	private ApiKeyService apiKeyService;

	private final Logger log = Logger.getLogger(getClass().getName());

	@Test
	public void test() throws DatabaseException {
		String email = "test@kb.nl";
		String apiKey = generatePassPhrase(9);
		String privateKey = generatePassPhrase(9);
		String token = "test_token";
		String username = "test";
		String company = "test_company";
		String country = "europe";
		String firstName = "testName";
		String lastName= "testLastName";
		String website = "test_website";
		String address = "test_address";
		String phone = "test_phone";
		log.info(String.format("%s, %s", apiKey, privateKey));
		User user = userService.createApiKey(token, email, apiKey, privateKey,
				DEFAULT_USAGE_LIMIT, username, company, country, firstName,
				lastName, website, address, phone);
		log.info("user: " + user.getId());

		log.info("api keys: " + user.getApiKeys().iterator().next().getId());

		ApiKey createdApiKey = user.getApiKeys().iterator().next();
		assertEquals(apiKey, createdApiKey.getId());
		assertEquals(user.getId(), createdApiKey.getUser().getId());

		userService.remove(user);
	}

	private String generatePassPhrase(int length) {
		// This variable contains the list of allowable characters for the
		// pass phrase. Note that the number 0 and the letter 'O' have been
		// removed to avoid confusion between the two. The same is true
		// of 'I', 1, and 'l'.
		final char[] allowableCharacters = new char[] { 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
				'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4',
				'5', '6', '7', '8', '9' };

		final int max = allowableCharacters.length - 1;

		StringBuilder pass = new StringBuilder();

		for (int i = 0; i < length; i++) {
			pass.append(allowableCharacters[RandomUtils.nextInt(max)]);
		}

		return pass.toString();
	}
}
