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
package eu.europeana.corelib.neo4j.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

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
