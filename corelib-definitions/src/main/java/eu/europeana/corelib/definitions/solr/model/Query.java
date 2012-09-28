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
import java.util.LinkedHashMap;
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

	private List<String> allFacetList;

	private Map<String, String> parameters = new HashMap<String, String>();

	private String queryType;

	private List<String> searchRefinements;
	private List<String> facetRefinements;

	private boolean produceFacetUnion = true;

	/**
	 * CONSTRUCTORS
	 */

	public Query(String query) {
		this.query = query;
		start = DEFAULT_START;
		pageSize = DEFAULT_PAGE_SIZE;
		createAllFacetList();
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
		return getRefinements(false);
	}

	public String[] getRefinements(boolean useDividedRefinements) {
		if (!useDividedRefinements) {
			return refinements;
		} else {
			divideRefinements();
			return (String[])ArrayUtils.addAll(
				searchRefinements.toArray(new String[searchRefinements.size()]), 
				facetRefinements.toArray(new String[facetRefinements.size()])
			);
		}
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

	public boolean isProduceFacetUnion() {
		return produceFacetUnion;
	}

	public Query setProduceFacetUnion(boolean produceFacetUnion) {
		this.produceFacetUnion = produceFacetUnion;
		return this;
	}

	private void createAllFacetList() {
		allFacetList = new ArrayList<String>();
		for (Facet facet : facets) {
			allFacetList.add(facet.toString());
		}
	}
	public void divideRefinements() {
		searchRefinements = new ArrayList<String>();
		facetRefinements = new ArrayList<String>();

		if (refinements == null) {
			return;
		}

		Map<String, FacetCollector> register = new LinkedHashMap<String, FacetCollector>();
		for (String facetTerm : refinements) {
			if (facetTerm.contains(":")) {
				int colon = facetTerm.indexOf(":");
				String facetName = facetTerm.substring(0, colon);
				boolean isTagged = false;
				if (facetName.contains("!tag")) {
					facetName = facetName.replaceFirst("\\{!tag=.*?\\}", "");
					isTagged = true;
				}

				if (!allFacetList.contains(facetName)) {
					searchRefinements.add(facetTerm);
					continue;
				} else {
					FacetCollector collector;
					if (register.containsKey(facetName)) {
						collector = register.get(facetName);
					} else {
						collector = new FacetCollector(facetName);
						register.put(facetName, collector);
					}
					if (isTagged && !collector.isTagged()) {
						collector.setTagged(true);
					}
					collector.addValue(facetTerm.substring(colon + 1));
				}
			} else {
				searchRefinements.add(facetTerm);
			}
		}

		for (FacetCollector collector : register.values()) {
			facetRefinements.add(collector.toString());
		}
	}
	
	private class FacetCollector {
		private boolean isTagged = true;
		private String name;
		private List<String> values = new ArrayList<String>();
		
		public FacetCollector(String name) {
			this.name = name;
		}

		public boolean isTagged() {
			return isTagged;
		}

		public void setTagged(boolean isTagged) {
			this.isTagged = isTagged;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getValues() {
			return values;
		}

		public void setValues(List<String> values) {
			this.values = values;
		}

		public void addValue(String value) {
			values.add(value);
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (isTagged) {
				sb.append("{!tag=").append(name).append("}");
			}
			sb.append(name);
			sb.append(":");
			if (values.size() > 1) {
				sb.append("(");
				sb.append(StringUtils.join(values, " OR "));
				sb.append(")");
			} else {
				sb.append(values.get(0));
			}
			return sb.toString();
		}
	}
}
