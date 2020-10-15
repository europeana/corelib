package eu.europeana.corelib.search.config;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class SearchServerConfig {
    private static final Logger LOG = LogManager.getLogger(SearchServerConfig.class);

    @Value("#{europeanaProperties['solr.zk.connect.timeout']}")
    private int zkConnectTimeout;

    @Value("#{europeanaProperties['solr.zk.connect.timeout']}")
    private int solrTimeout;

    @Value("#{europeanaProperties['solr.connect.timeout']}")
    private int solrConnectTimeout;
    @Value("#{europeanaProperties['solr.so.timeout']}")
    private int solrSocketTimeout;

    @Value("#{europeanaProperties['solr.username']}")
    private String username;
    @Value("#{europeanaProperties['solr.password']}")
    private String password;

    private final Map<String, SolrClient> solrClientById = new HashMap<>();

    private final SolrConfigLoader configLoader;

    public SearchServerConfig(@Autowired SolrConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @PostConstruct
    private void setupSolrConnections() {
        for (SolrConfigLoader.SolrConfigProperty instance : configLoader.getSolrInstances()) {
            // zookeeper clients must specify a host and default collection
            if (instance.getZookeeperUrl().isEmpty() || instance.getCoreCollection().isEmpty()) {
                //for single solr server, we add authentication
                HttpSolrClient singleSolrClient = new HttpSolrClient.Builder(instance.getUrl()).build();

                singleSolrClient.setConnectionTimeout(solrConnectTimeout);
                singleSolrClient.setSoTimeout(solrSocketTimeout);
                AbstractHttpClient client = (AbstractHttpClient) singleSolrClient.getHttpClient();
                client.addRequestInterceptor(new PreEmptiveBasicAuthenticator(username, password));

                LOG.info("Registered SolrClient without zookeeper - id:{}, url: {}", instance.getId(), instance.getUrl());
                solrClientById.put(instance.getId(), singleSolrClient);
            } else {
                CloudSolrClient zkClient = new CloudSolrClient.Builder()
                        .withZkHost(instance.getZookeeperUrl().get())
                        .build();
                zkClient.setDefaultCollection(instance.getCoreCollection().get());
                zkClient.setZkConnectTimeout(zkConnectTimeout);
                zkClient.setSoTimeout(solrTimeout);

                LOG.info("Registered SolrClient with zookeeper - id:{}, url:{}, zookeeperUrl:{}, core:{}",
                        instance.getId(), instance.getUrl(), instance.getZookeeperUrl().get(), instance.getCoreCollection().get());
                solrClientById.put(instance.getId(), zkClient);
            }
        }
    }

    /**
     * Gets the Solr client configured with the given ID
     *
     * @param id configured solr ID
     * @return Optional containing matching client
     */
    public Optional<SolrClient> getSolrClientById(String id) {
        return Optional.ofNullable(solrClientById.get(id));
    }


    /**
     * Invoked by Spring on application exit.
     */
    @PreDestroy
    private void closeConnections() {
        LOG.info("Closing Solr connections...");
        solrClientById.values().forEach(solrClient -> {
            try {
                solrClient.close();
            } catch (IOException e) {
                LOG.warn("Error closing Solr connection...", e);
            }
        });
    }


    private static class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
        private final UsernamePasswordCredentials credentials;

        public PreEmptiveBasicAuthenticator(String user, String pass) {
            credentials = new UsernamePasswordCredentials(user, pass);
        }

        /**
         * @see HttpRequestInterceptor#process(HttpRequest, HttpContext)
         */
        @Override
        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            request.addHeader(BasicScheme.authenticate(credentials, "US-ASCII", false));
        }
    }

}
