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
 * Edm value not found exception
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class EdmValueNotFoundException extends ArrayIndexOutOfBoundsException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String field;
	private String recordId;
	
	public EdmValueNotFoundException(String field, String recordId) {
		this.field = field;
		this.recordId = recordId;
	}
	@Override
	public String getMessage() {
		return "Value for field: "+ field +" of record: "+ recordId +" does not exist";
	}

}
