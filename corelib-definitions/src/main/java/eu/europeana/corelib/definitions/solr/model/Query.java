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

package eu.europeana.corelib.definitions.solr.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.solr.Facet;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Query implements Cloneable {

	/**
	 * Default start parameter for Solr
	 */
	private static final int DEFAULT_START = 0;

	/**
	 * Default number of items in the SERP
	 */
	private static final int DEFAULT_PAGE_SIZE = 12;

	private String query;

	private String[] refinements;

	private int start;

	private int pageSize;

	private Facet[] facets = Facet.values();

	private Map<String, String> parameters = new HashMap<String, String>();

	private String queryType;

	/**
	 * CONSTRUCTORS
	 */

	public Query(String query) {
		this.query = query;
		start = DEFAULT_START;
		pageSize = DEFAULT_PAGE_SIZE;
	}

	/**
	 * GETTERS & SETTTERS
	 */

	public String getQuery() {
		return query;
	}

	public Query setQuery(String query) {
		this.query = query;
		return this;
	}

	public String[] getRefinements() {
		return refinements;
	}

	public Query setRefinements(String... refinements) {
		if (refinements != null) {
			this.refinements = refinements.clone();
		} else {
			this.refinements = StringArrayUtils.EMPTY_ARRAY;
		}
		return this;
	}

	public Query addRefinement(String refinement) {
		if (this.refinements == null) {
			this.refinements = StringArrayUtils.EMPTY_ARRAY;
		}
		this.refinements = (String[]) ArrayUtils.add(this.refinements, refinement);
		return this;
	}

	public Integer getStart() {
		return start;
	}

	public Query setStart(int start) {
		this.start = start;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Query setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Facet[] getFacets() {
		return facets;
	}

	public Query setFacets(Facet[] facets) {
		if (facets != null) {
			this.facets = facets.clone();
		} else {
			this.facets = Facet.values();
		}
		return this;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Adds Solr parameters to the Query object
	 *
	 * @param key
	 *   The parameter name
	 * @param value
	 *   The value of the parameter
	 * @return 
	 *   The Query object
	 */
	public Query setParameter(String key, String value) {
		parameters.put(key, value);
		return this;
	}

	@Override
	public Query clone() throws CloneNotSupportedException {
		return (Query) super.clone();
	}

	public String toString() {
		List<String> params = new ArrayList<String>();
		params.add("q=" + query);
		params.add("start=" + start);
		params.add("rows=" + pageSize);

		if (refinements != null) {
			for (String refinement : refinements) {
				params.add("qf=" + refinement);
			}
		}

		if (facets != null) {
			for (Facet facet : facets) {
				params.add("facet.field=" + facet);
			}
		}

		if (parameters != null) {
			for (Entry<String, String> parameter : parameters.entrySet()) {
				params.add(parameter.getKey() + "=" + parameter.getValue());
			}
		}

		return StringUtils.join(params, "&");
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}
