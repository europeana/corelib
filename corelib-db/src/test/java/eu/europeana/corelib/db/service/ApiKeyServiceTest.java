package eu.europeana.corelib.db.service;

import eu.europeana.corelib.db.dao.RelationalDao;
import eu.europeana.corelib.db.entity.relational.UserImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
public class ApiKeyServiceTest {

    private static final long DEFAULT_USAGE_LIMIT = 10000;

    @Resource
    private ApiKeyService apiKeyService;

//    @Resource(name = "corelib_db_userDao")
//    private RelationalDao<UserImpl> userDao;

    @Resource
    private EmailService emailServiceMock;


    /**
     * Initialise the testing session
     *
     * @throws IOException
     */
    @Before
    public void setup() {
        reset(emailServiceMock);
    }

    /**
     * Initialise the testing session
     *
     * @throws IOException
     */
    @After
    public void tearDown() {
        //userDao.deleteAll();
    }

    @Test
    public void createApiKeyTest() throws DatabaseException, EmailServiceException {
        String email = "test@kb.nl";
        String appName = "test";
        String company = "test_company";
        String firstName = "testName";
        String lastName = "testLastName";
        String website = "test_website";
        String description = "test_description";

        ApiKey createdApiKey = apiKeyService.createApiKey(email, DEFAULT_USAGE_LIMIT, appName,
                company, firstName, lastName, website, description);
        assertNotNull(createdApiKey.getId());
        assertNotNull(createdApiKey.getPrivateKey());
        assertEquals(appName, createdApiKey.getApplicationName());
        assertEquals(email, createdApiKey.getEmail());

        verify(emailServiceMock, times(1)).sendApiKeys((ApiKey) anyObject());

        apiKeyService.remove(createdApiKey);
    }

}
