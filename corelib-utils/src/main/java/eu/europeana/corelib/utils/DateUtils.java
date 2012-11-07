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
package eu.europeana.corelib.utils;

import java.util.Date;

/**
 * Date util classes
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class DateUtils {
	
	private DateUtils() {
		// do not allow instances of this class
	}
	
	/**
	 * Clone a date
	 * @param date
	 * @return
	 */
	public static Date clone(Date date) {
		if (date != null) {
			return new Date(date.getTime());
		}
		return null;
	}

}
