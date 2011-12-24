/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or - as soon they
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

package eu.europeana.corelib.definitions.solr;

import org.apache.commons.lang.StringUtils;


/**
 * DocType defines the different type Object types Europeana supports.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Borys Omelayenko
 */
public enum DocType {
	TEXT, IMAGE, SOUND, VIDEO, _3D;

	public static DocType get(String[] strings) {
		return get(strings[0]);
	}

	public static DocType get(String string) {
		for (DocType t : values()) {
			if (t.toString().equalsIgnoreCase(string)) {
				return t;
			}
		}
		throw new IllegalArgumentException("Did not recognize DocType: [" + string + "]");
	}

	@Override
	public String toString() {
		return StringUtils.stripStart(super.toString(), "_");
	}
}
