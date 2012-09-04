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

package eu.europeana.corelib.solr.bean.impl;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.solr.beans.IdBean;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.IdBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class IdBeanImpl implements IdBean {

	@Field("europeana_id")
	private String id;
	@Field("edm_previewNoDistribute")
	private boolean optOut;
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public Boolean isOptedOut(){
		return this.optOut;
	}
}
