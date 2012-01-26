/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.definitions.exception;

/**
 * @author Borys Omelayenko
 * @author Gerald de Jong <geralddejong@gmail.com>
 */

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
    SOLR_UNREACHABLE("Unable to reach Solr Search Engine (Europeana Exception).", ProblemResponseAction.MAIL),
    UNABLE_TO_PARSE_JSON("Unable to parse JSON response.", ProblemResponseAction.LOG),
    MALFORMED_SPRING_TYPE_CONVERSION("org.springframework.beans.TypeMismatchException:", ProblemResponseAction.IGNORE),
    NONE("An exception occurred", ProblemResponseAction.MAIL),
    INVALIDARGUMENTS("Service is called with invalid argument(s)", ProblemResponseAction.MAIL)
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