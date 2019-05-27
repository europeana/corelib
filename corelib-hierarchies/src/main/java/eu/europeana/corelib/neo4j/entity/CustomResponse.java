package eu.europeana.corelib.neo4j.entity;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class representing the hierarchy of from the Neo4j plugin
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
@JsonSerialize
public class CustomResponse {

	//The list of the results from the custom response
	private List<CustomResult> results;
	
	//The list error strings encountered
	private List<String> errors;

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
