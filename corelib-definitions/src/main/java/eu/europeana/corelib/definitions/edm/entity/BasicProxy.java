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

package eu.europeana.corelib.definitions.edm.entity;

/**
 * Basic Proxy interface for Provider and Europeana specific proxies.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

public interface BasicProxy  extends PhysicalThing {
	/**
	 * Retrieve the ore:ProxyIn field for a Proxy (It always points to the rdf:About of an Aggregation)
	 * 
	 * @return	String containing the proxyIn of a Proxy
	 */
	String[] getProxyIn();

	/**
	 * Set the proxyIn field for a Proxy
	 * 
	 * @param proxyIn
	 * 			String containing the proxyIn of a Proxy
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
	
}
