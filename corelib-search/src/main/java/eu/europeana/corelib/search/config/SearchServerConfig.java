package eu.europeana.corelib.search.config;

import eu.europeana.corelib.search.impl.SearchServiceImpl;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

@Configuration
public class SearchServerConfig {

    @Value("#{europeanaProperties['zookeeper.url']}")
    private String zookeeperHost;

    @Value("#{europeanaProperties['solr.url']}")
    private String solrBaseUrl;

    @Value("#{europeanaProperties['solr.core']}")
    private String solrDefaultCollection;

    @Value("#{europeanaProperties['solr.zk.connect.timeout']}")
    private int zkConnectTimeout;

    @Value("#{europeanaProperties['solr.zk.connect.timeout']}")
    private int solrTimeout;

    @Bean
    @Lazy
    public CloudSolrClient defaultSolrClient() {
        CloudSolrClient solrClient = new CloudSolrClient.Builder()
                .withZkHost(zookeeperHost)
                .build();

        solrClient.setDefaultCollection(solrDefaultCollection);
        solrClient.setZkConnectTimeout(zkConnectTimeout);
        solrClient.setSoTimeout(solrTimeout);

        return solrClient;
    }

    @Bean
    public SearchServiceImpl defaultSearchService() {
        SearchServiceImpl searchServiceImpl = new SearchServiceImpl();
        return searchServiceImpl;
    }

    /**
     * Configures a single Solr instance with no Zookeeper
     */
    @Bean
    @Profile("single-solr")
    @Lazy
    public SolrClient singleSolrClient() {
        return new HttpSolrClient.Builder(solrBaseUrl).build();
    }


    @Bean
    @Profile("single-solr")
    public SearchServiceImpl singleSolrSearchService() {
        SearchServiceImpl searchServiceImpl = new SearchServiceImpl();
        return searchServiceImpl;
    }
}
