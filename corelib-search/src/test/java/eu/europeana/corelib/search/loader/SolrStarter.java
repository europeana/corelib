package eu.europeana.corelib.search.loader;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.MalformedURLException;
import java.util.Properties;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class SolrStarter {

	SolrClient server;

	ApplicationContext context;

	Properties props;

	public SolrStarter() {
		this(new ClassPathXmlApplicationContext(new String[] { "corelib-solr-context.xml", "corelib-solr-test.xml",
				"resources/solr/search/conf/solrconfig.xml" }));
	}

	public SolrStarter(ApplicationContext context) {
		context = new ClassPathXmlApplicationContext(
				new String[] { "corelib-solr-context.xml", "corelib-solr-test.xml" });
		props = context.getBean("europeanaProperties", Properties.class);
	}

	public void start() throws MalformedURLException {
		//server = context.getBean(eu.europeana.corelib.solr.server.SolrServer.class);
	}

	public static void main(String[] args) throws Exception {
		SolrStarter starter = new SolrStarter();
		starter.start();

	}

}
