/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.search.loader;

import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class SolrStarter {

	SolrServer server;

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
