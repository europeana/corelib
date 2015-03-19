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

/**
 * Collection utility classes
 *
 */
public class CollectionUtils {
	
	/**
	 * Return the first element of an array, or a predefined object
	 * @param list The array
	 * @param empty The default object
	 * @return
	 */
	public static <T> T returnFirst(T[] list, T empty) {
		if (list == null || list.length == 0) {
			return empty;
		}
		return list[0];
	}
}
