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

package eu.europeana.corelib.definitions.exception;

public enum ProblemType {
    MATCH_ALL_DOCS("org.apache.lucene.search.MatchAllDocsQuery", ProblemResponseAction.IGNORE),
    UNDEFINED_FIELD("Undefined field", ProblemResponseAction.IGNORE),
    RECORD_NOT_INDEXED("Requested Europeana record not indexed.", ProblemResponseAction.IGNORE),
    RECORD_NOT_FOUND("Requested Europeana record not found.", ProblemResponseAction.IGNORE),
    RECORD_REVOKED("Europeana record is revoked by the content provider.", ProblemResponseAction.IGNORE),
    MALFORMED_URL("Required parameters are missing from the request.", ProblemResponseAction.LOG),
    MALFORMED_QUERY("Query to Search Engine is malformed.", ProblemResponseAction.LOG),
    UNKNOWN("unknown", ProblemResponseAction.IGNORE),
    UNABLE_TO_CHANGE_LANGUAGE("We are unable to change the interface to the requested language.", ProblemResponseAction.LOG),
    TOKEN_INVALID("Europeana token has expired and is no longer valid.", ProblemResponseAction.IGNORE),
    UNKNOWN_TOKEN("Token does not exist.", ProblemResponseAction.IGNORE),
    TOKEN_OUTDATED("Token is outdated.", ProblemResponseAction.IGNORE),
    SOLR_UNREACHABLE("Unable to reach Solr Search Engine (Europeana Exception).", ProblemResponseAction.MAIL),
    UNABLE_TO_PARSE_JSON("Unable to parse JSON response.", ProblemResponseAction.LOG),
    MALFORMED_SPRING_TYPE_CONVERSION("org.springframework.beans.TypeMismatchException:", ProblemResponseAction.IGNORE),
    NONE("An exception occurred", ProblemResponseAction.MAIL),
    INVALIDARGUMENTS("Service is called with invalid argument(s)", ProblemResponseAction.MAIL),
    UNKNOWN_MONGO_DB_HOST("Unknown MongoDB host", ProblemResponseAction.MAIL),
    XMPMETADATACREATION("Unable to crate XMP metadata for thumbnail",ProblemResponseAction.IGNORE),
    XMPMETADATARETRIEVAL("Error while reading XMP metadata from thumbnail",ProblemResponseAction.IGNORE)
    ; 
    
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