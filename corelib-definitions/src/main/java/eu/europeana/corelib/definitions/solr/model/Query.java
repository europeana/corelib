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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import eu.europeana.corelib.definitions.solr.Facet;
import eu.europeana.corelib.utils.EuropeanaStringUtils;
import eu.europeana.corelib.utils.StringArrayUtils;
import eu.europeana.corelib.utils.model.LanguageVersion;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Query implements Cloneable {

	private final static String OR = " OR ";

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

	private List<LanguageVersion> queryTranslations;

	private Map<String, String> valueReplacements;

	private int start;

	private int pageSize;

	private static List<String> defaultFacets;
	static {
		defaultFacets = new ArrayList<String>();
		for (Facet facet : Facet.values()) {
			defaultFacets.add(facet.toString());
		}
	}

	private List<String> facets = new ArrayList<String>(defaultFacets);

	private List<String> allFacetList;

	private Map<String, String> parameters = new HashMap<String, String>();

	private String queryType;
	private String executedQuery;

	private List<String> searchRefinements;
	private List<String> facetRefinements;
	private List<String> filteredFacets;
	private List<QueryFacet> facetQueries;

	private boolean produceFacetUnion = true;

	private boolean allowSpellcheck = true;
	private boolean allowFacets = true;

	private boolean apiQuery = false;

	/**
	 * CONSTRUCTORS
	 */

	public Query(String query) {
		this.query = query;
		start = DEFAULT_START;
		pageSize = DEFAULT_PAGE_SIZE;
		createAllFacetList();
		// facets.type.method = enum
	}

	/**
	 * GETTERS & SETTTERS
	 */

	public String getQuery() {
		return query;
	}

	public String getQuery(boolean withTranslations) {
		StringBuffer concatenatedQuery = new StringBuffer();
		if (withTranslations && !CollectionUtils.isEmpty(getQueryTranslations())) {
			concatenatedQuery
				.append("(").append(getQuery()).append(")")
				.append(" OR ")
				.append(concatenateQueryTranslations());
		} else {
			concatenatedQuery.append(getQuery());
		}
		return concatenatedQuery.toString();
	}

	private String concatenateQueryTranslations() {
		return concatenateQueryTranslations(getQueryTranslations());
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

	public List<String> getFilteredFacets() {
		return filteredFacets;
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

	public Query setValueReplacements(Map<String, String> valueReplacements) {
		this.valueReplacements = valueReplacements;
		return this;
	}

	public List<LanguageVersion> getQueryTranslations() {
		return queryTranslations;
	}

	public Query setQueryTranslations(List<LanguageVersion> queryTranslations) {
		this.queryTranslations = queryTranslations;
		return this;
	}

	public Query addFacetQuery(QueryFacet queryFacet) {
		if (facetQueries == null) {
			facetQueries = new ArrayList<QueryFacet>();
		}
		facetQueries.add(queryFacet);
		return this;
	}

	public Query setFacetQueries(List<QueryFacet> queryFacets) {
		this.facetQueries = queryFacets;
		return this;
	}

	public List<String> getFacetQueries() {
		List<String> queries = new ArrayList<String>();
		if (facetQueries != null) {
			for (QueryFacet queryFacet : facetQueries) {
				queries.add(queryFacet.getQueryFacetString());
			}
		}
		return queries;
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

	public List<String> getFacets() {
		return facets;
	}

	public Query setFacets(String... facets) {
		if (facets != null) {
			this.facets = Arrays.asList(facets);
			replaceSpecialFacets();
		} else {
			this.facets = defaultFacets;
		}
		return this;
	}

	public Query setFacets(List<String> facets) {
		if (facets != null) {
			this.facets = facets;
			replaceSpecialFacets();
		} else {
			this.facets = defaultFacets;
		}
		return this;
	}

	/**
	 * Replace special facets.
	 * 
	 * Right now there are two special facets: DEFAULT and REUSABILITY. DEFAULT
	 * is replaced to the portal's default facet list. REUSABILITY will be skipped,
	 * because it is a special query facet
	 */
	private void replaceSpecialFacets() {
		List<String> additionalFacets = new ArrayList<String>();
		for (String facet : facets) {
			if (StringUtils.equals("DEFAULT", facet)) {
				additionalFacets.addAll(new ArrayList<String>(defaultFacets));
			} else if (StringUtils.equals("REUSABILITY", facet)) {
				// skip it
			} else {
				additionalFacets.add(facet);
			}
		}
		facets = additionalFacets;
	}

	public boolean isApiQuery() {
		return apiQuery;
	}

	public Query setApiQuery(boolean apiQuery) {
		this.apiQuery = apiQuery;
		return this;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public boolean hasParameter(String key) {
		return parameters.containsKey(key);
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

	@Override
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
			for (String facet : facets) {
				params.add("facet.field=" + facet);
			}
		}

		if (parameters != null) {
			for (Entry<String, String> parameter : parameters.entrySet()) {
				params.add(parameter.getKey() + "=" + parameter.getValue());
			}
		}

		if (getFacetQueries() != null) {
			for (String query : getFacetQueries()) {
				params.add("facet.query=" + query);
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

	public boolean isAllowSpellcheck() {
		return allowSpellcheck;
	}

	public Query setAllowSpellcheck(boolean allowSpellcheck) {
		this.allowSpellcheck = allowSpellcheck;
		return this;
	}

	public boolean isAllowFacets() {
		return allowFacets;
	}

	public Query setAllowFacets(boolean allowFacets) {
		this.allowFacets = allowFacets;
		return this;
	}

	public Query setProduceFacetUnion(boolean produceFacetUnion) {
		this.produceFacetUnion = produceFacetUnion;
		return this;
	}

	private void createAllFacetList() {
		allFacetList = new ArrayList<String>();
		allFacetList.addAll(facets);
	}

	public void removeFacet(Facet facetToRemove) {
		removeFacet(facetToRemove.toString());
	}

	public void removeFacet(String facetToRemove) {
		if (facets.contains(facetToRemove)) {
			facets.remove(facetToRemove);
		}
	}

	public void setFacet(String facet) {
		facets = new ArrayList<String>();
		facets.add(facet);
	}

	public String getExecutedQuery() {
		return executedQuery;
	}

	public void setExecutedQuery(String executedQuery) {
		try {
			this.executedQuery = URLDecoder.decode(executedQuery, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.executedQuery = executedQuery;
			e.printStackTrace();
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
				boolean replaced = false;
				String pseudoFacetName = null;
				if (valueReplacements != null && valueReplacements.containsKey(facetTerm)) {
					pseudoFacetName = facetTerm.substring(0, facetTerm.indexOf(":"));
					facetTerm = valueReplacements.get(facetTerm);
					replaced = true;
					if (StringUtils.isBlank(facetTerm)) {
						continue;
					}
				}

				int colon = facetTerm.indexOf(":");
				String facetName = facetTerm.substring(0, colon);
				boolean isTagged = false;
				if (facetName.contains("!tag")) {
					facetName = facetName.replaceFirst("\\{!tag=.*?\\}", "");
					isTagged = true;
				}

				if (allFacetList.contains(facetName)) {
					String key = pseudoFacetName == null ? facetName : pseudoFacetName;
					FacetCollector collector;
					if (register.containsKey(key)) {
						collector = register.get(key);
					} else {
						collector = new FacetCollector(facetName, isApiQuery());
						if (pseudoFacetName != null) {
							collector.setTagName(pseudoFacetName);
						}
						register.put(key, collector);
					}
					if (isTagged && !collector.isTagged()) {
						collector.setTagged(true);
					}
					collector.addValue(facetTerm.substring(colon + 1), replaced);
				} else {
					searchRefinements.add(facetTerm);
				}
			} else {
				searchRefinements.add(facetTerm);
			}
		}

		filteredFacets = new ArrayList<String>(register.keySet());
		for (FacetCollector collector : register.values()) {
			facetRefinements.add(collector.toString());
		}
	}

	public static String concatenateQueryTranslations(List<LanguageVersion> languageVersions) {
		List<String> queryTranslationTerms = new ArrayList<String>();
		for (LanguageVersion term : languageVersions) {
			String phrase = EuropeanaStringUtils.createPhraseValue(term.getText());
			if (!queryTranslationTerms.contains(phrase)) {
				queryTranslationTerms.add(phrase);
			}
		}
		return StringUtils.join(queryTranslationTerms, " OR ");
	}

	private class FacetCollector {
		private boolean isTagged = true;
		private String name;
		private String tagName;
		private List<String> values = new ArrayList<String>();
		private List<String> replacedValues = new ArrayList<String>();
		private boolean isApiQuery = false;
		private boolean replaced = false;

		public FacetCollector(String name) {
			this.name = name;
			this.tagName = name;
		}

		public FacetCollector(String name, boolean isApiQuery) {
			this(name);
			this.isApiQuery = isApiQuery;
		}

		public boolean isTagged() {
			return isTagged;
		}

		public void setTagged(boolean isTagged) {
			this.isTagged = isTagged;
		}

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		private boolean isAlreadyQuoted(String value) {
			return value.startsWith("\"") && value.endsWith("\"");
		}

		private boolean hasOr(String value) {
			return value.startsWith("(") && value.endsWith(")") && value.contains(" OR ");
		}

		public void addValue(String value, boolean isReplaced) {
			if (name.equals(Facet.RIGHTS.name())) {
				if (value.endsWith("*")) {
					value = value.replace(":", "\\:").replace("/", "\\/");
				} else if (!isAlreadyQuoted(value) && !hasOr(value)) {
					value = '"' + value + '"';
				}
			} else if (name.equals(Facet.TYPE.name())) {
				value = value.toUpperCase().replace("\"", "");
			} else {
				if (!isApiQuery && (value.indexOf(" ") > -1 || value.indexOf("!") > -1)) {
					if (!value.startsWith("\"")) {
						value = '"' + value;
					}
					if (!value.endsWith("\"")) {
						value += '"';
					}
				}
			}
			if (isReplaced) {
				replacedValues.add(value);
			} else {
				values.add(value);
			}
		}

		private String join(List<String> valueList, String booleanOperator) {
			if (valueList.size() == 0) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			if (valueList.size() > 1) {
				sb.append("(");
				sb.append(StringUtils.join(valueList, booleanOperator));
				sb.append(")");
			} else {
				sb.append(valueList.get(0));
			}

			return sb.toString();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (isTagged && !replaced) {
				sb.append("{!tag=").append(tagName).append("}");
			}
			sb.append(name);
			sb.append(":");

			String valuesString = join(values, OR);
			String replacedValuesString = join(replacedValues, OR);

			if (StringUtils.isNotBlank(valuesString)) {
				if (StringUtils.isNotBlank(replacedValuesString)) {
					sb.append(String.format("(%s AND %s)", valuesString, replacedValuesString));
				} else {
					sb.append(valuesString);
				}
			} else {
				if (StringUtils.isNotBlank(replacedValuesString)) {
					sb.append(replacedValuesString);
				}
			}

			return sb.toString();
		}
	}
}
