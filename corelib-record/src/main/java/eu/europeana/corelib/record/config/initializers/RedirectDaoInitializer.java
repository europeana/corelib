package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.utils.LazyInitializer;
import eu.europeana.metis.mongo.RecordRedirectDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class manages an {@link RecordRedirectDao} object to which it lazily connects.
 */
public class RedirectDaoInitializer extends LazyInitializer<RecordRedirectDao> {

    private static final Logger LOG = LogManager.getLogger(RedirectDaoInitializer.class);

    private final MongoProviderInitializer connection;
    private final String dbName;

    public RedirectDaoInitializer(MongoProviderInitializer connection, String redirectDbName) {
        this.connection = connection;
        this.dbName = redirectDbName;
    }

    // TODO - remove the validation for redirectDb once we upgrade to metis-version 3
    /**
     * EA-2350 - will not validate the redirect Db on the first request
     * assuming the configuration value present is valid
     *
     * BUT until metis-common dependency issue for "java.lang.NoClassDefFoundError: javax/ws/rs/NotFoundException"
     * is not resolved. It's better to validate the first request and initialise the
     * RecordRedirectDao accordingly.
     *
     * Otherwise we would always get this error for a invalid record identifier,
     * if redirectDb is present at RecordServiceImpl.resolveId().
     *
     */
    @Override
    protected RecordRedirectDao initialize() {
        RecordRedirectDao db = new RecordRedirectDao(connection.get().getMongoClient(), dbName, false);
        return (validateRedirectDbConnection(db, dbName)) ? db : null;
       // return new RecordRedirectDao(connection.get().getMongoClient(), dbName, false);
    }


    /**
     * Check if the redirectDao works fine. If not we disable it.
     * This is a temporary hack so we can use the Record API without a redirect database (for Metis Sandbox)
     * When we switch to Spring-Boot we can implement a more elegant solution with @ConditionalOnProperty for example
     *
     * @param redirectDb     {@link RecordRedirectDao} instance
     * @param redirectDbName database name
     */
    private boolean validateRedirectDbConnection(RecordRedirectDao redirectDb, String redirectDbName) {
        try {
            redirectDb.getRecordRedirectsByOldId("/xx/yy");
            LOG.info("Connection to redirect database {} is okay.", redirectDbName);
            return true;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not authorized")) {
                // this is the expected behavior if we try to access a database that doesn't exist.
                LOG.warn("Not authorized to access redirect database {}. It may not exist.", redirectDbName);
                LOG.warn("Redirect functionality is now disabled");
            } else {
                LOG.error("Error accessing redirect database '{}'", redirectDbName, e);
            }
        } // TODO - remove this once we upgrade to metis-version 3
        // this is to avoid the known exception due to dependency issue
        catch (NoClassDefFoundError error) {
            if (error.getMessage().contains("javax/ws/rs/NotFoundException")) {
                LOG.error("Error while initialising redirect database {}", redirectDbName, error);
                LOG.warn("Redirect functionality is now disabled");
            }
        }
        return false;
    }
}
