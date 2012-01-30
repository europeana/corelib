package eu.europeana.corelib.solr.server;

/**
 * Interface implementing the SolrServer connection
 * @author yorgos.mamakis@kb.nl
 *
 */
public interface SolrServer {
	/**
	 * 
	 * @param timeout - Connection timeout
	 */
	public void setConnectionTimeout(int timeout);
	/**
	 * 
	 * @param timeout
	 */
    public void setSoTimeout(int timeout);
    
    /**
     * 
     * @param connections - Maximum connections per host
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
}
