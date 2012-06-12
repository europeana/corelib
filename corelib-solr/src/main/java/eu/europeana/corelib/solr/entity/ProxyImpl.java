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
package eu.europeana.corelib.solr.entity;

import com.google.code.morphia.annotations.Embedded;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.model.Proxy
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */

@Embedded
public class ProxyImpl extends BasicProxyImpl implements Proxy {

	private DocType edmType;
	private String[] edmUnstored;
	
	@Override
	public String[] getEdmUnstored() {
		return (StringArrayUtils.isNotBlank(edmUnstored)?this.edmUnstored.clone():null);
	}

	@Override
	public void setEdmUnstored(String[] edmUnstored) {
		this.edmUnstored = edmUnstored;
		
	}
	
	@Override
	public void setEdmType(DocType edmType) {
		this.edmType = edmType;
	}
	
	@Override
	public DocType getEdmType() {
		return this.edmType;
	}
}
