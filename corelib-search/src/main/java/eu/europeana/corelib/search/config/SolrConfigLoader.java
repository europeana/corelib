package eu.europeana.corelib.search.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static eu.europeana.corelib.utils.ConfigUtils.SEPARATOR;
import static eu.europeana.corelib.utils.ConfigUtils.containsKeyPrefix;

@Configuration
public class SolrConfigLoader {

    private static final String URL_PROP = "url";
    private static final String CORE_PROP = "core";
    private static final String ZOOKEEPER_URL_PROP = "zookeeper.url";
    private static final String ID_PROP = "id";

    private final Properties properties;

    private final List<SolrConfigProperty> solrInstances = new ArrayList<>();

    public SolrConfigLoader(@Autowired @Qualifier("europeanaProperties") Properties properties) {
        this.properties = properties;
    }

    @PostConstruct
    void loadMongoConfig() {
        int solrInstanceNo = 1;
        while (containsKeyPrefix(properties, "solr" + solrInstanceNo)) {
            String basePath = "solr" + solrInstanceNo + SEPARATOR;
            SolrConfigProperty solrConfig = new SolrConfigProperty(
                    properties.getProperty(basePath + ID_PROP),
                    properties.getProperty(basePath + URL_PROP),
                    properties.getProperty(basePath + CORE_PROP),
                    properties.getProperty(basePath + ZOOKEEPER_URL_PROP)
            );
            solrInstances.add(solrConfig);
            solrInstanceNo++;

        }
    }

    public List<SolrConfigProperty> getSolrInstances() {
        return solrInstances;
    }

    static class SolrConfigProperty {
        private final String id;
        private final String url;
        private final String coreCollection;
        private final String zookeeperUrl;


        public SolrConfigProperty(String id, String url, String coreCollection, String zookeeperUrl) {
            // if zookeeperUrl is specified, then core collection is also required
            if (StringUtils.isAnyBlank(id, url) || StringUtils.isAnyBlank(zookeeperUrl, coreCollection)) {
                throw new IllegalArgumentException(String.format(
                        "Invalid Solr config .properties file: id=%s, url=%s, core=%s, zookeeperUrl=%s", id, url, coreCollection, zookeeperUrl
                ));
            }
            this.id = id;
            this.url = url;
            this.coreCollection = coreCollection;
            this.zookeeperUrl = zookeeperUrl;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public Optional<String> getCoreCollection() {
            return Optional.ofNullable(coreCollection);
        }

        public Optional<String> getZookeeperUrl() {
            return Optional.ofNullable(zookeeperUrl);
        }

    }

}
