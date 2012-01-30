/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */


package eu.europeana.corelib.solr.bean.impl;

import org.apache.solr.client.solrj.beans.Field;


import eu.europeana.corelib.solr.bean.IdBean;
import eu.europeana.corelib.solr.server.impl.SolrServerImpl;

/**
 * @see eu.europeana.corelib.solr.bean.IdBean
 * 
 * @author Yorgos.Mamakis@kb.nl
 *
 */
public class IdBeanImpl implements IdBean {
	@Field("uid") // temporary solution until we have finalized what will be in there
	String id;
	
	@Override
	public String getId() {
		return this.id;
	}

}
