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


/**
 * Error that is thrown when there is a problem in the configuration, e.g. when a mandatory field is empty
 */
public class WebConfigurationException extends EuropeanaException {
	private static final long serialVersionUID = 4129679235632668628L;

	/**
	 * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, String)
	 */
	public WebConfigurationException(ProblemType problem, String additionalInfo) {
		super(problem, additionalInfo);
	}

}
