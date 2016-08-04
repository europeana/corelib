/*
 * Copyright 2007-2016 The Europeana Foundation
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

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

/**
 * Basic Neo4JException
 * 
 * @author luthien (maike.dulk@europeana.eu)

 */
public class Neo4JException extends EuropeanaException {

	private static final long serialVersionUID = 662625433933164897L;

	public Neo4JException(ProblemType problem) {
		super(problem);
	}

	public Neo4JException(Throwable causedBy, ProblemType problem) {
		super(causedBy,problem);
	}
}
