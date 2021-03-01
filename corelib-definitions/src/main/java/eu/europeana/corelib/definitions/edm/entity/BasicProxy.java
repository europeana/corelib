package eu.europeana.corelib.definitions.edm.entity;

/**
 * Basic Proxy interface for Provider and Europeana specific proxies.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

public interface BasicProxy extends PhysicalThing {
	/**
	 * Retrieve the ore:ProxyIn field for a Proxy (It always points to the rdf:About of an Aggregation)
	 * 
	 * @return	String array containing the proxyIn of a Proxy
	 */
	String[] getProxyIn();

	/**
	 * Set the proxyIn field for a Proxy
	 * 
	 * @param proxyIn
	 * 			String array containing the proxyIn of a Proxy
	 */
	void setProxyIn(String[] proxyIn);

	/**
	 * Retrieve the ore:ProxyFor field for a Proxy (It always points to the rdf:About of an ProvidedCHO)
	 * 
	 * @return	String containing the proxyFor of a Proxy
	 */
	String getProxyFor();

	/**
	 * Set the proxyFor field for a Proxy
	 * 
	 * @param proxyFor
	 * 			String containing the proxyIn of a Proxy
	 */
	void setProxyFor(String proxyFor);

	/**
	 * Retrieve the ore:lineage field for a Proxy (It always points to the rdf:About of another Proxy)
	 *
	 * @return	String array containing the lineage of a Proxy
	 */
	String[] getLineage();

	/**
	 * Set the lineage field for a Proxy
	 *
	 * @param lineage
	 * 			String array containing the lineage of a Proxy
	 */
	void setLineage(String[] lineage);
}
