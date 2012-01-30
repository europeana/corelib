package eu.europeana.corelib.solr.server;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.util.NamedList;


public interface SolrServer {
	
	public void setConnectionTimeout(int timeout);
	
    public void setSoTimeout(int timeout);
    
    public void setDefaultMaxConnectionsPerHost(int connections);
    
    public void setMaxTotalConnections(int connections);
    
    public void setSuspendAfterTimeout(int suspendAfterTimeout);
    
    public boolean isActive();
    
    public void suspend();
    
    public String getActiveFrom();
    
    public NamedList<Object> request(SolrRequest request) throws SolrServerException, IOException;
    
    public String getBaseURL();
}
