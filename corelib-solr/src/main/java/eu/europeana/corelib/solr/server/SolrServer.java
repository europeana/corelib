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
	public void setConnectionTimeout(int timeout);

	/**
	 * 
	 * @param timeout
	 */
	public void setSoTimeout(int timeout);

	/**
	 * 
	 * @param connections
	 *            - Maximum connections per host
	 */
	public void setDefaultMaxConnectionsPerHost(int connections);

	/**
	 * 
	 * @param connections
	 */
	public void setMaxTotalConnections(int connections);

	/**
	 * 
	 * @param suspendAfterTimeout
	 */
	public void setSuspendAfterTimeout(int suspendAfterTimeout);

	/**
	 * 
	 * @return
	 */
	public boolean isActive();

	/**
     * 
     */
	public void suspend();

	/**
	 * 
	 * @return
	 */
	public String getActiveFrom();

	/**
	 * 
	 * @return
	 */
	public String getBaseURL();

	public NamedList<Object> request(SolrRequest request)
			throws SolrServerException, IOException;

	public QueryResponse query(SolrParams params) throws SolrServerException;
}
