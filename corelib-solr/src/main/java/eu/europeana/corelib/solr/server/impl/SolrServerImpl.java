package eu.europeana.corelib.solr.server.impl;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.util.NamedList;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import org.apache.solr.core.CoreContainer;

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

    CommonsHttpSolrServer makeRemoteSolrServer(String baseUrl) throws Exception {
        CommonsHttpSolrServer remoteSolrServer = new CommonsHttpSolrServer(baseUrl);
        remoteSolrServer.setFollowRedirects(false);
        return remoteSolrServer;
    }

    EmbeddedSolrServer makeLocalSolrServer(String solrHome) throws Exception {
        if (System.getProperty("solr.solr.home") == null) {
            System.setProperty("solr.solr.home", solrHome);
        }
        CoreContainer.Initializer initializer = new CoreContainer.Initializer();
        CoreContainer coreContainer = initializer.initialize();
        EmbeddedSolrServer localSolrServer = new EmbeddedSolrServer(coreContainer, "");
        return localSolrServer;
    }

    public void setConnectionTimeout(int timeout) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setConnectionTimeout(timeout);
        }
    }

    public void setSoTimeout(int timeout) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setSoTimeout(timeout);
        }
    }

    public void setDefaultMaxConnectionsPerHost(int connections) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setDefaultMaxConnectionsPerHost(connections);
        }
    }

    public void setMaxTotalConnections(int connections) {
        if (server instanceof CommonsHttpSolrServer) {
            ((CommonsHttpSolrServer)server).setMaxTotalConnections(connections);
        }
    }

    private long activeFrom = -1;

    private int suspendAfterTimeout = 0;

    public void setSuspendAfterTimeout(int suspendAfterTimeout) {
        this.suspendAfterTimeout = suspendAfterTimeout;
    }

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

    public void suspend() {
        activeFrom = new Date().getTime() + suspendAfterTimeout;
    }

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
