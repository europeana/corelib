package eu.europeana.corelib.db.service.impl;

import eu.europeana.corelib.db.entity.relational.ApiKeyImpl;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.web.service.EmailService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the {@link ApiKeyService}
 */
public class ApiKeyServiceImpl extends AbstractServiceImpl<ApiKey> implements ApiKeyService {

    @Resource(name = "corelib_web_emailService")
    private EmailService emailService;

    @Override
    public ApiKey findByID(Serializable id) throws DatabaseException {
        ApiKey apiKey = super.findByID(id);
        if ((apiKey != null) && (apiKey.getActivationDate() == null)) {
            apiKey.setActivationDate(new Date());
        }
        return apiKey;
    }

    @Override
    public List<ApiKey> findByEmail(String email) {
        if (StringUtils.isNotBlank(email)) {
            return getDao().findByNamedQuery(ApiKey.QUERY_FINDBY_EMAIL,
                    StringUtils.lowerCase(email));
        }
        return null;
    }

    @Override
    public List<ApiKey> findAllSortByDate(boolean asc) {
        return getDao().findAllOrderBy("registrationDate", asc);
    }

    @Override
    public List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit) {
        return getDao().findAllOrderBy("registrationDate", asc, offset, limit);
    }

    public void checkNotEmpty(ApiKey apiKey) throws DatabaseException {
        if (apiKey == null) {
            throw new DatabaseException(ProblemType.INVALIDARGUMENTS);
        }
    }

    @Override
    @Deprecated
    public void updateApplicationName(String apiKey, String applicationName) throws DatabaseException {
        ApiKey key = getDao().findByPK(apiKey);
        if (key != null) {
            key.setApplicationName(StringUtils.trimToNull(applicationName));
        }
    }

    @Override
    @Deprecated
    public ApiKey createApiKey(String token, String email, String apiKey,
                               String privateKey, Long limit, String appName, String username,
                               String company, String country, String firstName, String lastName,
                               String website, String address, String phone, String fieldOfWork)
            throws DatabaseException {
        ApiKey api = new ApiKeyImpl();
        api.setEmail(StringUtils.lowerCase(email));
        api.setApiKey(apiKey);
        api.setPrivateKey(privateKey);
        api.setUsageLimit(limit);
        api.setFirstName(firstName);
        api.setLastName(lastName);
        api.setCompany(company);
        api.setWebsite(website);
        api.setApplicationName(appName);
        api.setDescription(null);
        getDao().insert(api);

        return api;
    }

    @Override
    public ApiKey createApiKey(
            String email, Long limit, String appName, String company, String firstName,
            String lastName, String website, String description
    ) throws DatabaseException, EmailServiceException {

        String apiKey;
        do {
            apiKey = generatePassPhrase(9);
        } while (findByID(apiKey) != null);

        ApiKey key = new ApiKeyImpl();
        key.setEmail(email);
        key.setApiKey(apiKey);
        key.setPrivateKey(generatePassPhrase(9));
        key.setUsageLimit(limit);
        key.setFirstName(StringUtils.trimToNull(firstName));
        key.setLastName(StringUtils.trimToNull(lastName));
        key.setCompany(StringUtils.trimToNull(company));
        key.setWebsite(StringUtils.trimToNull(website));
        key.setApplicationName(StringUtils.trimToNull(appName));
        key.setDescription(StringUtils.trimToNull(description));
        getDao().insert(key);

        emailService.sendApiKeys(key);

        return key;
    }

    @Override
    public ApiKey updateApiKey(
            String apiKey, String email, Long limit, String application, String company, String firstName,
            String lastName, String website, String description
    ) throws DatabaseException {
        ApiKey key = getDao().findByPK(apiKey);
        if (key != null) {
            key.setEmail(StringUtils.defaultIfBlank(email, key.getEmail()));
            key.setUsageLimit(limit != null ? limit : key.getUsageLimit());
            key.setFirstName(StringUtils.defaultIfBlank(firstName, key.getFirstName()));
            key.setLastName(StringUtils.defaultIfBlank(lastName, key.getLastName()));
            key.setCompany(StringUtils.defaultIfBlank(company, key.getCompany()));
            key.setWebsite(StringUtils.defaultIfBlank(website, key.getWebsite()));
            key.setApplicationName(StringUtils.defaultIfBlank(application, key.getApplicationName()));
            key.setDescription(StringUtils.defaultIfBlank(description, key.getDescription()));
            return key;
        }
        throw new DatabaseException(ProblemType.NOT_FOUND);
    }

    @Override
    public ApiKey changeLimit(String apiKey, long limit) throws DatabaseException {
        ApiKey key = getDao().findByPK(apiKey);
        if (key != null) {
            key.setUsageLimit(limit);
            return key;
        }
        throw new DatabaseException(ProblemType.NOT_FOUND);
    }

    @Override
    public void removeApiKey(String apiKeyId) throws DatabaseException {
        ApiKey apiKey = getDao().findByPK(apiKeyId);
        if (apiKey != null) {
            getDao().delete(apiKey);
        } else {
            throw new DatabaseException(ProblemType.NOT_FOUND);
        }
    }

    private String generatePassPhrase(int length) {
        // This variable contains the list of allowable characters for the
        // pass phrase. Note that the number 0 and the letter 'O' have been
        // removed to avoid confusion between the two. The same is true
        // of 'I', 1, and 'l'.
        final char[] allowableCharacters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4',
                '5', '6', '7', '8', '9'};

        final int max = allowableCharacters.length - 1;

        StringBuilder pass = new StringBuilder();

        for (int i = 0; i < length; i++) {
            pass.append(allowableCharacters[RandomUtils.nextInt(max)]);
        }

        return pass.toString();
    }

}