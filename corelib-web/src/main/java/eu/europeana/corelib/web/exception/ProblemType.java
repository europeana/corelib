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

package eu.europeana.corelib.web.exception;

public enum ProblemType {

	// TODO clean up unused error messages

	NOT_FOUND("Entity doesn't exists", ProblemResponseAction.IGNORE),
	MATCH_ALL_DOCS("org.apache.lucene.search.MatchAllDocsQuery", ProblemResponseAction.IGNORE),
	UNDEFINED_FIELD("Undefined field", ProblemResponseAction.IGNORE),
	RECORD_NOT_INDEXED("Requested Europeana record not indexed.", ProblemResponseAction.IGNORE),
	RECORD_NOT_FOUND("Requested Europeana record not found.", ProblemResponseAction.IGNORE),
	PAGE_NOT_FOUND("Requested Europeana page not found.", ProblemResponseAction.IGNORE),
	RECORD_REVOKED("Europeana record is revoked by the content provider.", ProblemResponseAction.IGNORE),
	MALFORMED_URL("Required parameters are missing from the request.", ProblemResponseAction.LOG),
	MALFORMED_QUERY("Invalid query parameter.", ProblemResponseAction.LOG),
	SOLR_ERROR("Error querying Solr Search Engine.", ProblemResponseAction.LOG),
	CANT_CONNECT_SOLR("Unable to connect to Solr Search Engine.", ProblemResponseAction.MAIL),
	CANT_CONNECT_ZOOKEEPER("Unable to connect to Solr Zookeeper.", ProblemResponseAction.MAIL),
	NO_LIVE_SOLR("There's something wrong with Solr Search Engine.", ProblemResponseAction.IGNORE),
	UNKNOWN("unknown", ProblemResponseAction.IGNORE),
	UNABLE_TO_CHANGE_LANGUAGE("We are unable to change the interface to the requested language.", ProblemResponseAction.LOG),
	TOKEN_INVALID("Europeana token has expired and is no longer valid.", ProblemResponseAction.IGNORE),
	TOKEN_EXPIRED("Europeana token has expired and is no longer valid.", ProblemResponseAction.IGNORE),
	TOKEN_MISMATCH("This Europeana token is not associated with the supplied email address.", ProblemResponseAction.LOG),
	UNKNOWN_TOKEN("Token does not exist.", ProblemResponseAction.IGNORE),
	TOKEN_OUTDATED("Token is outdated.", ProblemResponseAction.IGNORE),
	UNABLE_TO_PARSE_CURSORMARK("The provided cursor value is invalid, please make sure you encode the value before passing back on to the API.", ProblemResponseAction.LOG),
	UNABLE_TO_PARSE_JSON("Unable to parse JSON response.", ProblemResponseAction.LOG),
	MALFORMED_SPRING_TYPE_CONVERSION("org.springframework.beans.TypeMismatchException:", ProblemResponseAction.IGNORE),
	NONE("An exception occurred", ProblemResponseAction.MAIL),
	INCONSISTENT_DATA("Inconsistent data", ProblemResponseAction.MAIL),
	INVALIDARGUMENTS("Service is called with invalid argument(s)", ProblemResponseAction.MAIL),
	INVALIDCLASS("Service is called with invalid bean class.", ProblemResponseAction.MAIL),
	UNKNOWN_MONGO_DB_HOST("Unknown Mongo database host", ProblemResponseAction.MAIL),
	MONGO_UNREACHABLE("Cannot connect to Mongo database host", ProblemResponseAction.MAIL),
	NEO4J_404("No hierarchical data found for record", ProblemResponseAction.LOG),
	NEO4J_502("Inconsistency in hierarchical data for record", ProblemResponseAction.MAIL),
	NEO4J_500("Error processing hierarchical data for record", ProblemResponseAction.MAIL),
	NEO4J_GENERAL_FAILURE("Error connecting to hierarchical data storage", ProblemResponseAction.MAIL),
	XMPMETADATACREATION("Unable to crate XMP metadata for thumbnail", ProblemResponseAction.IGNORE),
	XMPMETADATARETRIEVAL("Error while reading XMP metadata from thumbnail", ProblemResponseAction.IGNORE),
	NO_USERNAME("User name does not exist.", ProblemResponseAction.IGNORE),
	NO_PASSWORD("Password does not exist.", ProblemResponseAction.IGNORE),
	NO_OLD_PASSWORD("Old password does not exist.", ProblemResponseAction.IGNORE),
	NO_USER_ID("User id does not exist.", ProblemResponseAction.IGNORE),
	NO_USER("User does not exist.", ProblemResponseAction.IGNORE),
	MISSING_PARAM_USERNAME("Required parameter 'username' missing.", ProblemResponseAction.IGNORE),
	MISSING_PARAM_EMAIL("Required parameter 'email' missing.", ProblemResponseAction.IGNORE),
	MISSING_PARAM_PASSWORD("Required parameter 'password' missing.", ProblemResponseAction.IGNORE),
	DUPLICATE("Record already exists.", ProblemResponseAction.IGNORE),
	RECORD_RETRIEVAL_ERROR("Record retrieval error", ProblemResponseAction.LOG),
	INVALID_URL("Url is invalid", ProblemResponseAction.LOG),
	INVALID_THEME("Theme does not exist", ProblemResponseAction.LOG),
	PAGINATION_LIMIT_REACHED("Sorry! It is not possible to paginate beyond the first 1000 search results. Please use the cursor-based pagination to paginate beyond the first 1000 search results.", ProblemResponseAction.LOG);

	private String message;

	private ProblemResponseAction action;

	ProblemType(String message, ProblemResponseAction action) {
		this.message = message;
		this.action = action;
	}

	public static ProblemType get(String message) {
		for (ProblemType problem : values()) {
			if (message.toLowerCase().startsWith(problem.toString().toLowerCase())) {
				return problem;
			}
		}
		return UNKNOWN;
	}

	public String getMessage() {
		return message;
	}

	public ProblemResponseAction getAction() {
		return action;
	}
}