package eu.europeana.corelib.solr.model;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.SpellCheckResponse;

import eu.europeana.corelib.solr.bean.IdBean;

public class ResultSet<T extends IdBean> {
	
	private Query query;

	private List<T> results;
	
	private List<FacetField> facetFields;
	
	private SpellCheckResponse spellcheck;

	/**
	 * GETTERS & SETTTERS
	 */

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public List<FacetField> getFacetFields() {
		return facetFields;
	}

	public void setFacetFields(List<FacetField> facetFields) {
		this.facetFields = facetFields;
	}

	public SpellCheckResponse getSpellcheck() {
		return spellcheck;
	}

	public void setSpellcheck(SpellCheckResponse spellcheck) {
		this.spellcheck = spellcheck;
	}
	
}
