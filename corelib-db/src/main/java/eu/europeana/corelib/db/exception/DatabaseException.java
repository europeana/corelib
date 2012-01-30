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

package eu.europeana.corelib.db.exception;

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

/**
 * Exception dedicated for reporting database problems
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class DatabaseException extends EuropeanaException {
	private static final long serialVersionUID = 5060479670279882199L;


	public DatabaseException(ProblemType problem) {
		super(problem);
	}
	
	public DatabaseException(Throwable causedBy, ProblemType problem) {
		super(causedBy, problem);
	}

}
