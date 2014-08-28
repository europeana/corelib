package eu.europeana.corelib.neo4j.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class CustomResponse {

	
	List<CustomResult> results;
	
	List<String> errors;

	public List<CustomResult> getResults() {
		return results;
	}

	public void setResults(List<CustomResult> results) {
		this.results = results;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
