package eu.europeana.corelib.record.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;

import static eu.europeana.corelib.utils.ConfigUtils.SEPARATOR;
import static eu.europeana.corelib.utils.ConfigUtils.containsKeyPrefix;


/**
 * Loads mongo configuration settings.
 * Configuration supports multiple mongo instances and sources (Record and Redirect database pair).
 * <p>
 * eg:
 * mongo1.connectionUrl
 * mongo1.source1.record-dbname
 * mongo1.source1.redirect-dbname
 * <p>
 * mongo1.source2.record-dbname
 * mongo1.source2.redirect-dbname
 */
@Configuration
public class DataSourceConfigLoader {
    private static final String CONNECTION_URL_PROP = "connectionUrl";
    private static final String RECORD_DB_PROP = "record-dbname";
    private static final String REDIRECT_DB_URL_PROP = "redirect-dbname";

    private Properties properties;

    @Value("#{europeanaProperties['mongodb.max.connection.idle.time']}")
    private String mongoMaxConnectionIdleTime;

    private final List<MongoConfigProperty> mongoConfigProperties = new ArrayList<>();


    public DataSourceConfigLoader(@Autowired @Qualifier("europeanaProperties") Properties properties) {
        this.properties = properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setMongoMaxConnectionIdleTime(String mongoMaxConnectionIdleTime) {
        this.mongoMaxConnectionIdleTime = mongoMaxConnectionIdleTime;
    }


    @PostConstruct
    void loadMongoConfig() {
        int instanceNo = 1;
        while (containsKeyPrefix(properties, "mongo" + instanceNo)) {
            String basePath = "mongo" + instanceNo + SEPARATOR;
            MongoConfigProperty instance = new MongoConfigProperty();
            instance.setConnectionUrl(properties.getProperty(basePath + CONNECTION_URL_PROP));

            int sourceNo = 1;
            while (containsKeyPrefix(properties, basePath + "source" + sourceNo)) {
                String dsBasePath = basePath + "source" + sourceNo + SEPARATOR;

                // null values fine here for db names, as getters return Optional<String>
                DataSourceConfigProperty dsConfig = new DataSourceConfigProperty(
                        properties.getProperty(dsBasePath + "id"),
                        properties.getProperty(dsBasePath + REDIRECT_DB_URL_PROP),
                        properties.getProperty(dsBasePath + RECORD_DB_PROP)
                );

                instance.addDataSource(dsConfig);
                sourceNo++;
            }

            instanceNo++;
            mongoConfigProperties.add(instance);
        }
    }

    public List<MongoConfigProperty> getMongoInstances() {
        return Collections.unmodifiableList(mongoConfigProperties);
    }

    public MongoConfigProperty getInstance(int index) {
        return mongoConfigProperties.get(index);
    }


    public String getMongoMaxConnectionIdleTime() {
        return mongoMaxConnectionIdleTime;
    }


    static class MongoConfigProperty {
        private String connectionUrl;
        private final List<DataSourceConfigProperty> sources = new ArrayList<>();

        public String getConnectionUrl() {
            return connectionUrl;
        }

        private void setConnectionUrl(String connectionUrl) {
            if (StringUtils.isEmpty(connectionUrl)) {
                throw new IllegalArgumentException("ConnectionUrl cannot be empty for Mongo config");
            }

            this.connectionUrl = connectionUrl;
        }

        public List<DataSourceConfigProperty> getSources() {
            return sources;
        }

        public int numSources() {
            return sources.size();
        }

        public DataSourceConfigProperty getSource(int index) {
            return sources.get(index);
        }

        public void addDataSource(DataSourceConfigProperty source) {
            sources.add(source);
        }

        @Override
        public String toString() {
            return "MongoInstance{" +
                    "connectionUrl='" + connectionUrl + '\'' +
                    ", sources=" + sources +
                    '}';
        }
    }


    static class DataSourceConfigProperty {
        private final String id;
        private final String redirectDbName;
        private final String recordDbName;

        private DataSourceConfigProperty(@Nonnull String id, String redirectDbName, String recordDbName) {
            if (StringUtils.isEmpty(id)) {
                throw new IllegalArgumentException(String.format("Data source ID missing in properties. redirectDbName=%s, recordDbName=%s", redirectDbName, recordDbName));
            }

            this.id = id;
            this.redirectDbName = redirectDbName;
            this.recordDbName = recordDbName;
        }

        public String getId() {
            return id;
        }

        /**
         * Gets the configured Redirect DB name.
         *
         * @return Optional containing configured value, or empty optional if value is empty.
         */
        public Optional<String> getRedirectDbName() {
            return Optional.ofNullable(redirectDbName).filter(Predicate.not(StringUtils::isEmpty));
        }

        /**
         * Gets the configured Record DB name
         *
         * @return Optional containing configured value, or empty optional if value is empty.
         */
        public Optional<String> getRecordDbName() {
            return Optional.ofNullable(recordDbName).filter(Predicate.not(StringUtils::isEmpty));
        }


        @Override
        public String toString() {
            return "DataSource{" +
                    "redirectDbName=" + redirectDbName +
                    ", recordDbName=" + recordDbName +
                    '}';
        }
    }
}
