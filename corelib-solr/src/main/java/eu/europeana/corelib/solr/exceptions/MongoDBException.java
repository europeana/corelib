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
 * Basic MongoDBException
 * 
 * @author yorgos.mamakis@kb.nl
 *
 */
public class MongoDBException extends EuropeanaException {
	private static final long serialVersionUID = 4785113365867141067L;
	
	public MongoDBException(ProblemType problem) {
		super(problem);
	}

	public MongoDBException(Throwable causedBy,ProblemType problem) {
		super(causedBy,problem);
	}
	

}
