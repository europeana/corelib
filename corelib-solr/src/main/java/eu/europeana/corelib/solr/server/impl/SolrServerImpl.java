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
package eu.europeana.corelib.solr.server.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.util.NamedList;

import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

public class SolrServerImpl extends org.apache.solr.client.solrj.SolrServer implements eu.europeana.corelib.solr.server.SolrServer{
	
	private static final long serialVersionUID = 1962389067458619600L;

	org.apache.solr.client.solrj.SolrServer server;

    String baseUrl; 

    public SolrServerImpl(String baseUrl) throws Exception {
        this.server = isRemoteServer(baseUrl) ? makeRemoteSolrServer(baseUrl) : makeLocalSolrServer(baseUrl); 
        this.baseUrl = baseUrl;
    }
    
    private boolean isRemoteServer(String baseUrl) {
        return StringUtils.startsWith(baseUrl, "http://");
    }
    

    private CommonsHttpSolrServer makeRemoteSolrServer(String baseUrl) throws Exception {
        CommonsHttpSolrServer remoteSolrServer = new CommonsHttpSolrServer(baseUrl);
        remoteSolrServer.setFollowRedirects(false);
        return remoteSolrServer;
    }
    


    EmbeddedSolrServer makeLocalSolrServer(String solrHome) throws IOException, ParserConfigurationException, SAXException  {

    private EmbeddedSolrServer makeLocalSolrServer(String solrHome) throws Exception {

        if (System.getProperty("solr.solr.home") == null) {
            System.setProperty("solr.solr.home", solrHome);
        }
        CoreContainer.Initializer initializer = new CoreContainer.Initializer();
        CoreContainer coreContainer = initializer.initialize();
        EmbeddedSolrServer localSolrServer = new EmbeddedSolrServer(coreContainer, "");
        return localSolrServer;
    }

    @Override
    public void setConnectionTimeout(int timeout) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setConnectionTimeout(timeout);
        }
    }

    @Override
    public void setSoTimeout(int timeout) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setSoTimeout(timeout);
        }
    }

    @Override
    public void setDefaultMaxConnectionsPerHost(int connections) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setDefaultMaxConnectionsPerHost(connections);
        }
    }

    @Override
    public void setMaxTotalConnections(int connections) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setMaxTotalConnections(connections);
        }
    }

    private long activeFrom = -1;

    private int suspendAfterTimeout = 0;

    @Override
    public void setSuspendAfterTimeout(int suspendAfterTimeout) {
        this.suspendAfterTimeout = suspendAfterTimeout;
    }

    @Override
    public boolean isActive() {
        if (activeFrom < 0) {
            return true;
        }
        if (activeFrom < new Date().getTime()) {
            activeFrom = -1;
            return true;
        }
        return false; 
    }

    @Override
    public void suspend() {
        activeFrom = new Date().getTime() + suspendAfterTimeout;
    }

    @Override
    public String getActiveFrom() {
        if (activeFrom > 0) {
            return new Date(activeFrom).toString();
        } else {
            return "is active";
        }
    }


    @Override
    public NamedList<Object> request(SolrRequest request)
    throws SolrServerException, IOException {
        return server.request(request);
    }

    public String getBaseURL() {
        return baseUrl;
    }


}
