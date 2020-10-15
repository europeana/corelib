package eu.europeana.corelib.record.config;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataSourceConfigLoaderTest {

    @Test
    public void shouldLoadConfigCorrectly() {
        DataSourceConfigLoader loader = new DataSourceConfigLoader(setupProperties(2, 2));
        loader.loadMongoConfig();
        assertEquals(2, loader.getMongoInstances().size());
        assertEquals(2, loader.getInstance(0).numSources());
        assertEquals(2, loader.getInstance(1).numSources());

        assertEquals("mongo://connection-string-1", loader.getInstance(0).getConnectionUrl());
        assertEquals("mongo1-source1-id", loader.getInstance(0).getSource(0).getId());
        assertEquals("mongo1-source1-recordDb", loader.getInstance(0).getSource(0).getRecordDbName().get());
        assertEquals("mongo1-source1-redirectDb", loader.getInstance(0).getSource(0).getRedirectDbName().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnMissingId() {
        Properties props = new Properties();
        // no data source ID in props
        props.setProperty("mongo1.connectionUrl", "mongo://connection-string");
        props.setProperty("mongo1.source1.record-dbname", "record-dbName");
        props.setProperty("mongo1.source1.redirect-dbname", "record-dbName");
        new DataSourceConfigLoader(props).loadMongoConfig();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnEmptyId() {
        Properties props = new Properties();
        props.setProperty("mongo1.source1.id", "");
        props.setProperty("mongo1.connectionUrl", "mongo://connection-string");
        props.setProperty("mongo1.source1.record-dbname", "record-dbName");
        props.setProperty("mongo1.source1.redirect-dbname", "record-dbName");
        new DataSourceConfigLoader(props).loadMongoConfig();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnEmptyConnectionString() {
        Properties props = new Properties();

        props.setProperty("mongo1.source1.id", "dsId");
        props.setProperty("mongo1.connectionUrl", "");
        props.setProperty("mongo1.source1.record-dbname", "record-dbName");
        props.setProperty("mongo1.source1.redirect-dbname", "");

        new DataSourceConfigLoader(props).loadMongoConfig();
    }

    @Test
    public void shouldIgnoreEmptyRedirectDbConfig() {
        Properties props = new Properties();

        props.setProperty("mongo1.source1.id", "dsId");
        props.setProperty("mongo1.connectionUrl", "mongo://connection-string");
        props.setProperty("mongo1.source1.record-dbname", "record-dbName");
        props.setProperty("mongo1.source1.redirect-dbname", "");

        DataSourceConfigLoader loader = new DataSourceConfigLoader(props);
        loader.loadMongoConfig();

        assertTrue(loader.getInstance(0).getSource(0).getRedirectDbName().isEmpty());
    }

    private Properties setupProperties(int numMongo, int numDataSources) {
        Properties props = new Properties();
        for (int i = 1; i <= numMongo; i++) {
            props.setProperty("mongo" + i + ".connectionUrl", "mongo://connection-string-" + i);

            for (int j = 1; j <= numDataSources; j++) {
                props.setProperty("mongo" + i + ".source" + j + ".id", "mongo" + i + "-source" + j + "-id");
                props.setProperty("mongo" + i + ".source" + j + ".record-dbname", "mongo" + i + "-source" + j + "-recordDb");
                props.setProperty("mongo" + i + ".source" + j + ".redirect-dbname", "mongo" + i + "-source" + j + "-redirectDb");
            }
        }

        return props;
    }
}