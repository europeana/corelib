package eu.europeana.corelib.record.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


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
    private static final String SEPARATOR = ".";
    private static final String CONNECTION_URL_PROP = "connectionUrl";
    private static final String RECORD_DB_PROP = "record-dbname";
    private static final String REDIRECT_DB_URL_PROP = "redirect-dbname";

    private static final String DEFAULT_DATASOURCE_ID = "default";

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
        return mongoConfigProperties;
    }

    public MongoConfigProperty getInstance(int index) {
        return mongoConfigProperties.get(index);
    }


    /**
     * Checks if a key with the given prefix is contained within a Properties object.
     *
     * @param properties Properties object
     * @param keyPrefix  key prefix to check for
     * @return true if prefix is contained within properties object, false otherwise.
     */
    private boolean containsKeyPrefix(Properties properties, String keyPrefix) {
        return properties.keySet().stream().anyMatch(k
                -> k.toString().startsWith(keyPrefix)
        );
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
                throw new IllegalArgumentException(String.format("Data source ID missing in .properties file. redirectDbName=%s, recordDbName=%s", redirectDbName, recordDbName));
            }

            this.id = id;
            this.redirectDbName = redirectDbName;
            this.recordDbName = recordDbName;
        }

        public String getId() {
            return id;
        }

        public Optional<String> getRedirectDbName() {
            return Optional.ofNullable(redirectDbName);
        }

        public Optional<String> getRecordDbName() {
            return Optional.ofNullable(recordDbName);
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
