package eu.europeana.corelib.db.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.entity.Token;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml","/corelib-db-test.xml"})
public class TokenServiceTest {
	
	@Resource(name="corelib_db_tokenService")
	private TokenService tokenService;

	@Test
	public void testCreate() {
		Token token = tokenService.create("test@europeana.eu");
		if (token == null) {
			fail("Unable to create token");
		}
	}

}
