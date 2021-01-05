package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.utils.LazyInitializer;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class manages an {@link RecordRedirectDao} object to which it lazily connects.
 */
public class RedirectDaoInitializer extends LazyInitializer<RecordRedirectDao> {

    private static final Logger LOG = LogManager.getLogger(RedirectDaoInitializer.class);

    private final MongoClientInitializer connection;
    private final String dbName;

    public RedirectDaoInitializer(MongoClientInitializer connection, String redirectDbName) {
        this.connection = connection;
        this.dbName = redirectDbName;
    }


    @Override
    protected RecordRedirectDao initialize() {
        RecordRedirectDao db = new RecordRedirectDao(connection.get(), dbName, false);
        return (validateRedirectDbConnection(db, dbName)) ? db : null;
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
        }
        return false;
    }
}
