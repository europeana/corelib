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
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import eu.europeana.corelib.definitions.solr.SolrFacetType;
import eu.europeana.corelib.definitions.solr.TechnicalFacetType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.utils.EuropeanaStringUtils;
import eu.europeana.corelib.utils.StringArrayUtils;
import eu.europeana.corelib.utils.model.LanguageVersion;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Query implements Cloneable {

    private String currentCursorMark;

    private final static String OR = " OR ";

    /**
     * Default start parameter for Solr
     */
    private static final int DEFAULT_START = 0;

    /**
     * Default number of items in the SERP
     */
    private static final int DEFAULT_PAGE_SIZE = 12;

    /**
     * Use these instead of the ones provided in the apache Solr package
     * in order to avoid introducing a dependency to that package in all modules
     * they're public because they are read from SearchServiceImpl
     */
    public static final int ORDER_DESC = 0;
    public static final int ORDER_ASC  = 1;


    private boolean produceFacetUnion      = true;
    private boolean spellcheckAllowed      = true;
    private boolean allowFacets            = true;
    private boolean apiQuery               = false;
    private boolean defaultFacetsRequested = false;


    private int start;
    private int pageSize;
    private int sortOrder = ORDER_DESC;

    private String query;
    private String queryType;
    private String executedQuery;
    private String sort;

    private QueryTranslation queryTranslation;

    private Map<String, String> valueReplacementMap;
    private Map<String, String> parameterMap = new HashMap<>();
    private Map<String, Integer> technicalFacetOffsetMap = new HashMap<>();
    private Map<String, Integer> technicalFacetLimitMap = new HashMap<>();

    private        String[]     refinementArray;
    private static List<String> defaultSolrFacetList;
    private static List<String> defaultTechnicalFacetList;
    static {
        defaultSolrFacetList = new ArrayList<>();
        for (SolrFacetType solrFacet : SolrFacetType.values()) {
            defaultSolrFacetList.add(solrFacet.toString());
        }
        defaultTechnicalFacetList = new ArrayList<>();
        for (TechnicalFacetType technicalFacet : TechnicalFacetType.values()) {
            defaultTechnicalFacetList.add(technicalFacet.toString());
        }
    }
    private List<String>     solrFacetList = new ArrayList<>();
    private List<String>     technicalFacetList;
    private List<String>     searchRefinementsList;
    private List<String>     facetedRefinementsList;
    private List<String>     facetsUsedInRefinementsList;
    private List<QueryFacet> queryFacetList;


    /**
     * CONSTRUCTORS
     */

    public Query(String query) {
        this.query = query;
        start = DEFAULT_START;
        pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * GETTERS & SETTERS
     */

    public String getQuery() {
        return query;
    }

    public String getQuery(boolean withTranslations) {
        if (withTranslations && queryTranslation != null && StringUtils.isNotBlank(queryTranslation.getModifiedQuery())) {
            return queryTranslation.getModifiedQuery();
        }
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
            return refinementArray;
        } else {
            divideRefinements();
            return (String[]) ArrayUtils.addAll(searchRefinementsList.toArray(new String[searchRefinementsList.size()]),
                    facetedRefinementsList.toArray(new String[facetedRefinementsList.size()]));
        }
    }

    public List<String> getFacetsUsedInRefinements() {
        return facetsUsedInRefinementsList;
    }

    public Query setRefinements(String... refinementArray) {
        if (refinementArray != null) {
            this.refinementArray = refinementArray.clone();
        } else {
            this.refinementArray = StringArrayUtils.EMPTY_ARRAY;
        }
        return this;
    }

    public String getCurrentCursorMark() {
        return this.currentCursorMark;
    }

    public Query setCurrentCursorMark(String currentCursorMark) {
        this.currentCursorMark = currentCursorMark;
        return this;
    }

    public Query addRefinement(String refinement) {
        if (this.refinementArray == null) {
            this.refinementArray = StringArrayUtils.EMPTY_ARRAY;
        }
        this.refinementArray = (String[]) ArrayUtils.add(this.refinementArray, refinement);
        return this;
    }

    public Query setValueReplacements(Map<String, String> valueReplacementMap) {
        this.valueReplacementMap = valueReplacementMap;
        return this;
    }

    public Query setQueryTranslation(QueryTranslation queryTranslation) {
        this.queryTranslation = queryTranslation;
        return this;
    }

    public QueryTranslation getQueryTranslation() {
        return queryTranslation;
    }

    public Query addFacetQuery(QueryFacet queryFacet) {
        if (queryFacetList == null) {
            queryFacetList = new ArrayList<>();
        }
        queryFacetList.add(queryFacet);
        return this;
    }

    public Query setQueryFacets(List<QueryFacet> queryFacets) {
        this.queryFacetList = queryFacets;
        return this;
    }

    public List<String> getQueryFacets() {
        List<String> queries = new ArrayList<>();
        if (queryFacetList != null) {
            for (QueryFacet queryFacet : queryFacetList) {
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

    public String getSort() {
        return sort;
    }

    public Query setSort(String sort) {
        String[] parts;
        if (sort == null || sort.isEmpty()) {
            this.sort = "";
        } else if (!sort.matches(".*\\s.*") && sort.length() > 0) {
            this.sort = sort;
        } else if (sort.matches(".+\\s(asc|ASC|desc|DESC)(ending|ENDING)?")) {
            parts = sort.split("\\s");
            this.sort = parts[0];
            if (parts[1].matches("(asc|ASC).*")) {
                this.sortOrder = ORDER_ASC;
            }
        } else {
            this.sort = "";
        }
        return this;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Query setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public List<String> getSolrFacets() {
        return this.solrFacetList;
    }

//    public Query setSolrFacets(boolean defaultFacetsRequested, String... solrFacets) {
//        if (defaultFacetsRequested) this.defaultFacetsRequested = true;
//        if (ArrayUtils.isNotEmpty(solrFacets)) setSolrFacets(Arrays.asList(solrFacets));
//        return this;

    public Query setDefaultSolrFacets(){
        this.solrFacetList = new ArrayList<>(this.defaultSolrFacetList);
        return this;
    }

    public Query setSolrFacets(String... solrFacets) {
        if (ArrayUtils.isNotEmpty(solrFacets)) setSolrFacets(Arrays.asList(solrFacets));
        return this;
    }

    public Query setSolrFacets(List<String> solrFacets) {
        if (solrFacets != null) return replaceSpecialSolrFacets(solrFacets);
        return this;
    }

    public Query addSolrFacets(List<String> solrFacets) {
        if (solrFacets != null) {
            ArrayList<String> oldSolrFacetList = new ArrayList<>(this.solrFacetList);
            replaceSpecialSolrFacets(solrFacets);
            for (String oldSolrFacet : oldSolrFacetList){
                if (!this.solrFacetList.contains(oldSolrFacet)) this.solrFacetList.add(oldSolrFacet);
            }
        }
        return this;
    }

    public void removeSolrFacet(SolrFacetType facetToRemove) {
        removeSolrFacet(facetToRemove.toString());
    }

    public void removeSolrFacet(String facetToRemove) {
        if (solrFacetList.contains(facetToRemove)) {
            solrFacetList.remove(facetToRemove);
        }
    }

    public void setSolrFacet(String facet) {
        solrFacetList.add(facet);
    }

    public Query setDefaultTechnicalFacets(){
        this.technicalFacetList = new ArrayList<>(this.defaultTechnicalFacetList);
        return this;
    }

    public Query setTechnicalFacets(String... technicalFacets) {
        return setTechnicalFacets(Arrays.asList(technicalFacets));
    }

    // extra check against enum type
    public Query setTechnicalFacets(List<String> technicalFacets) {
        this.technicalFacetList = new ArrayList<>();
        for (String technicalFacet : technicalFacets) {
            if (defaultTechnicalFacetList.contains(technicalFacet)) this.technicalFacetList.add(technicalFacet);
        }
        return this;
    }

//    public Query setTechnicalFacets(boolean defaultFacetsRequested, String... requestedTechnicalFacets) {
//        if (defaultFacetsRequested) this.technicalFacetList = defaultTechnicalFacetList;
//        else if (ArrayUtils.isNotEmpty(requestedTechnicalFacets)) setTechnicalFacets(requestedTechnicalFacets);
//        return this;
//    }

    public List<String> getTechnicalFacets() {
        return technicalFacetList;
    }

    public boolean areFacetsAllowed() {
        return allowFacets;
    }


    /**
     * Checks if there are any technical facets requested. If so, add FACET_TAGS to the list of Solr Facets, if not
     * already in there, because the technical facet values are contained therein
     *
     * @param allowFacets boolean
     * @return the Query object
     */
    public Query setFacetsAllowed(boolean allowFacets) {
        this.allowFacets = allowFacets;
        if (this.allowFacets && technicalFacetList != null && technicalFacetList.size() > 0 &&
                (solrFacetList == null || solrFacetList.size() == 0 || !solrFacetList.contains(SolrFacetType.FACET_TAGS.toString())
            )) {
            // (PE) 2017-09-24: SonarQube found a nullpointer problem which is fixed by adding the line below,
            // but I'm not sure if the rest of this code is correct. It seems to me that solrFacetList is never null so
            // this code is never executed
            solrFacetList = new ArrayList<>();
            solrFacetList.add(SolrFacetType.FACET_TAGS.toString());
        }
        return this;
    }

    // TODO this method is ONLY ever called in the QueryTest unittest, and only for it TRUE
    // TODO I suggest we remove this variable altogether
    public Query setProduceFacetUnion(boolean produceFacetUnion) {
        this.produceFacetUnion = produceFacetUnion;
        return this;
    }

    /**
     * Replace special (solr)facets.
     * <p>
     * Right now there are two special Solr facets: DEFAULT and REUSABILITY. DEFAULT
     * is handled now in the API. REUSABILITY will be skipped,
     * because it is a special query facet
     */
    private Query replaceSpecialSolrFacets(List<String> solrFacetList) {
        Set<String> replacedFacetSet = new HashSet<>();
        for (String solrFacet : solrFacetList) {
            if (StringUtils.equalsIgnoreCase("MEDIA", solrFacet)) {
                replacedFacetSet.add("has_media");
            } else if (StringUtils.equalsIgnoreCase("THUMBNAIL", solrFacet)) {
                replacedFacetSet.add("has_thumbnails");
            } else if (StringUtils.equalsIgnoreCase("TEXT_FULLTEXT", solrFacet)) {
                replacedFacetSet.add("is_fulltext");
            } else if (StringUtils.equalsIgnoreCase("REUSABILITY", solrFacet)) {
                continue;
            } else {
                replacedFacetSet.add(solrFacet);
            }
        }
        this.solrFacetList = new ArrayList<>(replacedFacetSet);
        return this;
    }

    public boolean isDefaultFacetsRequested() {
        return this.defaultFacetsRequested;
    }

    public Query setDefaultFacetsRequested(boolean defaultFacetsRequested) {
        this.defaultFacetsRequested = defaultFacetsRequested;
        return this;
    }

    private void generateFacetTagQuery(int... tags) {
        if (null == tags || 0 == tags.length) {
            return;
        }

        for (final int tag : tags) {
            addFacetQuery(new QueryFacet("facet_tags:" + tag, "facet_tags"));
        }
    }

    public boolean isApiQuery() {
        return apiQuery;
    }

    public Query setApiQuery(boolean apiQuery) {
        this.apiQuery = apiQuery;
        return this;
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public boolean hasParameter(String key) {
        return parameterMap.containsKey(key);
    }

    /**
     * Adds Solr parameterMap to the Query object
     *
     * @param key   The parameter name
     * @param value The value of the parameter
     * @return The Query object
     */
    public Query setParameter(String key, String value) {
        parameterMap.put(key, value);
        return this;
    }

    /**
     * Adds Solr parameterMap to the Query object
     *
     * @param parameters Map containing parameter key-value pairs to be added
     * @return The Query object
     */
    public Query setParameters(Map<String, String> parameters) {
        parameterMap.putAll(parameters);
        return this;
    }

    // funky java 8 stuff yippee
    public Query convertAndSetSolrParameters(Map<String, Integer> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            parameterMap.putAll(parameters.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue()))));
        }
        return this;
    }

    /**
     * Adds technical facet offsets to the Query object
     * @param technicalFacetOffsetMap  Map of String, Integer containing technical facet names + offsets
     * @return The Query object
     */
    public Query setTechnicalFacetOffsets(Map<String, Integer> technicalFacetOffsetMap) {
        if (technicalFacetOffsetMap != null && !technicalFacetOffsetMap.isEmpty()) this.technicalFacetOffsetMap = technicalFacetOffsetMap;
        return this;
    }

    /**
     * Retrieves technical facet offsets
     * @return   Map of String, Integer containing technical facet names + offsets
     */
    public Map<String, Integer> getTechnicalFacetOffsets() {
        return this.technicalFacetOffsetMap;
    }

    /**
     * Adds technical facet limits to the Query object
     * @param technicalFacetLimitMap  Map of String, Integer containing technical facet names + limits
     * @return The Query object
     */
    public Query setTechnicalFacetLimits(Map<String, Integer> technicalFacetLimitMap) {
        if (technicalFacetLimitMap != null && !technicalFacetLimitMap.isEmpty()) this.technicalFacetLimitMap = technicalFacetLimitMap;
        return this;
    }

    /**
     * Retrieves technical facet limit
     * @return   Map of String, Integer containing technical facet names + limits
     */
    public Map<String, Integer> getTechnicalFacetLimits() {
        return this.technicalFacetLimitMap;
    }

    @Override
    public Query clone() throws CloneNotSupportedException {
        return (Query) super.clone();
    }

    @Override
    public String toString() {
        List<String> params = new ArrayList<>();
        params.add("q=" + query);
        params.add("start=" + start);
        params.add("rows=" + pageSize);

        if (sort != null && sort.length() > 0) {
            params.add("sort=" + sort + " " + (sortOrder == ORDER_DESC ? "desc" : "asc"));
        }

        if (refinementArray != null) {
            for (String refinement : refinementArray) {
                params.add("qf=" + refinement);
            }
        }

        if (solrFacetList != null) {
            for (String facet : solrFacetList) {
                params.add("facet.field=" + facet);
            }
        }

        if (parameterMap != null) {
            for (Entry<String, String> parameter : parameterMap.entrySet()) {
                params.add(parameter.getKey() + "=" + parameter.getValue());
            }
        }

        if (getQueryFacets() != null) {
            for (String query : getQueryFacets()) {
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

    public boolean doProduceFacetUnion() {
        return produceFacetUnion;
    }

    public boolean isSpellcheckAllowed() {
        return spellcheckAllowed;
    }

    public Query setSpellcheckAllowed(boolean allowSpellcheck) {
        this.spellcheckAllowed = allowSpellcheck;
        return this;
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
        searchRefinementsList = new ArrayList<>();
        facetedRefinementsList = new ArrayList<>();

        if (null == refinementArray || refinementArray.length == 0) {
            return;
        }

        Map<String, FacetCollector> register = new LinkedHashMap<>();
        for (String facetTerm : refinementArray) {
            if (facetTerm.contains(":")) {
                boolean replaced        = false;
                String  pseudoFacetName = null;
                if (valueReplacementMap != null && valueReplacementMap.containsKey(facetTerm)) {
                    pseudoFacetName = facetTerm.substring(0, facetTerm.indexOf(":"));
                    facetTerm = valueReplacementMap.get(facetTerm);
                    replaced = true;
                    if (StringUtils.isBlank(facetTerm)) {
                        continue;
                    }
                }

                int     colon     = facetTerm.indexOf(":");
                String  facetName = facetTerm.substring(0, colon);
                boolean isTagged  = false;
                if (facetName.contains("!tag")) {
                    facetName = facetName.replaceFirst("\\{!tag=.*?\\}", "");
                    isTagged = true;
                }
                // changed to include both default and custom SOLR facets
                if (!defaultTechnicalFacetList.contains(facetName)) {
                    String         key = pseudoFacetName == null ? facetName : pseudoFacetName;
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
                    searchRefinementsList.add(facetTerm);
                }
            } else {
                searchRefinementsList.add(facetTerm);
            }
        }

        facetsUsedInRefinementsList = new ArrayList<String>(register.keySet());
        for (FacetCollector collector : register.values()) {
            facetedRefinementsList.add(collector.toString());
        }
    }

    public static String concatenateQueryTranslations(List<LanguageVersion> languageVersions) {
        List<String> queryTranslationTerms = new ArrayList<>();
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
        private List<String> values         = new ArrayList<>();
        private List<String> replacedValues = new ArrayList<>();
        private boolean      isApiQuery     = false;
        private boolean      replaced       = false;

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
            if (name.equals(SolrFacetType.RIGHTS.name())) {
                if (value.endsWith("*")) {
                    value = value.replace(":", "\\:").replace("/", "\\/");
                } else if (!isAlreadyQuoted(value) && !hasOr(value)) {
                    value = '"' + value + '"';
                }
            } else if (name.equals(SolrFacetType.TYPE.name())) {
                value = value.toUpperCase().replace("\"", "");
            } else {
                if (!isApiQuery && (value.contains(" ") || value.contains("!"))) {
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

            String valuesString         = join(values, OR);
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
