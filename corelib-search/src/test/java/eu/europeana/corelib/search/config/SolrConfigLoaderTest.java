package eu.europeana.corelib.search.config;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class SolrConfigLoaderTest {
    @Test
    public void shouldLoadConfigProperly() {
        Properties props = setupProperties();
        SolrConfigLoader loader = new SolrConfigLoader(props);
        loader.loadConfig();
        assertEquals(2, loader.getSolrInstances().size());

        assertEquals("solr-id-1", loader.getInstance(0).getId());
        assertEquals("solr-url-1", loader.getInstance(0).getUrl());
        assertEquals("zookeeper-1", loader.getInstance(0).getZookeeperUrl().get());
        assertEquals("collection-1", loader.getInstance(0).getCoreCollection().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnMissingId() {
        Properties props = new Properties();
        props.setProperty("solr1.url", "solr-url-1");
        props.setProperty("solr1.zookeeper.url", "zookeeper-1");
        props.setProperty("solr1.core", "collection-1");

        SolrConfigLoader loader = new SolrConfigLoader(props);
        loader.loadConfig();
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnMissingUrl() {
        Properties props = new Properties();
        props.setProperty("solr1.id", "id1");
        props.setProperty("solr1.zookeeper.url", "zookeeper-1");
        props.setProperty("solr1.core", "collection-1");

        SolrConfigLoader loader = new SolrConfigLoader(props);
        loader.loadConfig();
    }

    private Properties setupProperties() {
        Properties props = new Properties();
        for (int i = 1; i <= 2; i++) {
            props.setProperty("solr" + i + ".id", "solr-id-" + i);
            props.setProperty("solr" + i + ".url", "solr-url-" + i);
            props.setProperty("solr" + i + ".core", "collection-" + i);
            props.setProperty("solr" + i + ".zookeeper.url", "zookeeper-" + i);
        }
        return props;
    }
}