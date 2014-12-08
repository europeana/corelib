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
package eu.europeana.corelib.edm.exceptions;

/**
 * Edm field not found exception
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class EdmFieldNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String field;
	private String recordId;
	
	/**
	 * Default constructor
	 * @param field The field that was not found
	 * @param recordId The record for which the id was not found
	 */
	public EdmFieldNotFoundException(String field, String recordId) {
		this.field = field;
		this.recordId = recordId;
	}
	
	
	@Override
	public String getMessage() {
		return "EDM Field not found exception: " + field + " for recordId: "+ recordId + " has not been mapped to an EDM field";
	}
}
