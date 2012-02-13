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

package eu.europeana.corelib.solr.service.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;

import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.definitions.solr.QueryType;
import eu.europeana.corelib.definitions.solr.beans.ApiBean;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.server.SolrServer;
import eu.europeana.corelib.solr.service.SearchService;
import eu.europeana.corelib.solr.utils.SolrUtil;

/**
 * @see eu.europeana.corelib.solr.service.SearchService
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */

public class SearchServiceImpl implements SearchService {

	@Resource(name = "corelib_solr_solrSelectServer1")
	private SolrServer solrServer1;

	@Resource(name = "corelib_solr_solrSelectServer2")
	private SolrServer solrServer2;

	@Value("#{europeanaProperties['solr1.facetLimit']}")
	private int facetLimit;

	@Value("#{europeanaProperties['solr1.rowLimit']}")
	private int rowLimit;

	@Value("#{europeanaProperties['solr1.timeout']}")
	private int timeout;

	@Value("#{europeanaProperties['solr1.suspendAfterTimeout']}")
	private int suspendAfterTimeout;

	@Resource(name = "corelib_solr_mongoServer")
	MongoDBServer mongoServer;

	@Override
	public FullBean findById(String europeanaObjectId) throws SolrTypeException {
		ObjectId id = ObjectId.massageToObjectId(europeanaObjectId);
		if (!solrServer1.isActive() && !solrServer2.isActive()) {
			throw new SolrTypeException(ProblemType.SOLR_UNREACHABLE);
		}
		SolrQuery solrQuery = new SolrQuery().setQuery("europeana_id:\""+europeanaObjectId+"\"");
		solrQuery.setQueryType(QueryType.MORE_LIKE_THIS.toString());
		
		QueryResponse queryResponse = null;
		
		try {
			queryResponse = getSolrServer().query(solrQuery);
		} catch (SolrServerException e) {
			throw new SolrTypeException(e, ProblemType.UNKNOWN);
		}
		
		FullBean fullBean = mongoServer.getFullBean(id);
		
		fullBean.setRelatedItems(queryResponse.getBeans(BriefBean.class));
		return fullBean;
	}

	@Override
	public <T extends IdBean> ResultSet<T> search(Class<T> beanClazz,Query query) throws SolrTypeException {
		ResultSet<T> resultSet = new ResultSet<T>();

		if (!solrServer1.isActive() && !solrServer2.isActive()) {
			throw new SolrTypeException(ProblemType.SOLR_UNREACHABLE);
		}
		if (beanClazz.isInstance(BriefBean.class)|| beanClazz.isInstance(ApiBean.class)) {
			String[] refinements = query.getRefinements();
			if (SolrUtil.checkTypeFacet(refinements)) {
				SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery());
				solrQuery.setFacet(true);
				for (String refinement : refinements) {
					solrQuery.addFacetField(refinement);
				}
				solrQuery.setFacetLimit(facetLimit);
				solrQuery.setRows(rowLimit);
				solrQuery.setStart(query.getStart());
				solrQuery.setQueryType(QueryType.ADVANCED.toString());
				SolrServer solrServer = getSolrServer();
				solrServer.setConnectionTimeout(timeout);
				solrServer.setSuspendAfterTimeout(suspendAfterTimeout);
				solrServer.setMaxTotalConnections(1000);
				solrServer.setSoTimeout(timeout);
				solrServer.setDefaultMaxConnectionsPerHost(1);
				try {
					QueryResponse queryResponse = solrServer.query(solrQuery);
					resultSet.setResults(queryResponse.getBeans(beanClazz));
					resultSet.setFacetFields(queryResponse.getFacetFields());
					resultSet.setResultSize(queryResponse.getResults().size());
					resultSet.setSearchTime(queryResponse.getElapsedTime());
					resultSet.setSpellcheck(queryResponse.getSpellCheckResponse());
				} catch (SolrServerException e) {
					throw new SolrTypeException(ProblemType.MALFORMED_QUERY);
				}

			} else {
				throw new SolrTypeException(ProblemType.INVALIDARGUMENTS);
			}

		} else {
			throw new SolrTypeException(ProblemType.INVALIDARGUMENTS);
		}
		return resultSet;
	}

	/**
	 * Get the active SolrServer defaults to solrServer1
	 * @return The solrServer to query
	 */
	private SolrServer getSolrServer() {
		return solrServer1.isActive() ? solrServer1 : solrServer2;
	}

}
