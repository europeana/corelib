/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.europeana.corelib.solr.exceptions;

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

/**
 * Exception thrown when SearchService gets a bean other than BriefBean or ApiBean
 * 
 * @author Yorgos.Mamakis
 *
 */
public class SolrTypeException extends EuropeanaException {
	
	private static final long serialVersionUID = 1354282016526186556L;
	
	public SolrTypeException(ProblemType problemType) {
		super(problemType);
	}
	
	public SolrTypeException(Throwable causedBy, ProblemType problemType){
		super(causedBy,problemType);
	}
}
