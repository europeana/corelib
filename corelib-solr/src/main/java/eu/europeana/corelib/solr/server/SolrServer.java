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

package eu.europeana.corelib.solr.server;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Interface implementing the SolrServer connection
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface SolrServer {

	/**
	 * 
	 * @param timeout
	 *            - Connection timeout
	 */
	void setConnectionTimeout(int timeout);

	/**
	 * 
	 * @param timeout
	 */
	void setSoTimeout(int timeout);

	/**
	 * 
	 * @param connections
	 *            - Maximum connections per host
	 */
	void setDefaultMaxConnectionsPerHost(int connections);

	/**
	 * 
	 * @param connections
	 */
	void setMaxTotalConnections(int connections);

	/**
	 * 
	 * @param suspendAfterTimeout
	 */
	void setSuspendAfterTimeout(int suspendAfterTimeout);

	/**
	 * 
	 * @return
	 */
	boolean isActive();

	/**
     * 
     */
	void suspend();

	/**
	 * 
	 * @return
	 */
	String getActiveFrom();

	/**
	 * 
	 * @return
	 */
	String getBaseURL();

	NamedList<Object> request(SolrRequest request) throws SolrServerException, IOException;

	QueryResponse query(SolrParams params) throws SolrServerException;
}
