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

package eu.europeana.corelib.solr.service.impl;



import javax.annotation.Resource;


import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.solr.bean.ApiBean;
import eu.europeana.corelib.solr.bean.BriefBean;
import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.bean.IdBean;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.impl.SolrServerImpl;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * @see eu.europeana.corelib.solr.service.SearchService
 * 
 * @author Yorgos.Mamakis@kb.nl
 *
 */
public class SearchServiceImpl implements SearchService {
	@Resource(name="corelib_solr_solrSelectServer1")
	private SolrServerImpl solrServer;
	
	@Override
	public FullBean findById(String europeanaObjectId) {
		
		return null;
	}

	@Override
	public <T extends IdBean> ResultSet<T> search(Class<T> beanClazz,
			Query query) throws SolrTypeException {
		
		if (beanClazz.isInstance(BriefBean.class)){
			//TODO: what will BriefBean contain
		}
		else if (beanClazz.isInstance(ApiBean.class)){
			//TODO: what will ApiBean contain
		}
		else{
			throw new SolrTypeException(ProblemType.INVALIDARGUMENTS);
		}
		return null;
	}

	
}
