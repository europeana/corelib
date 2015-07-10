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

package eu.europeana.corelib.definitions.solr;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * DocType defines the different type Object types Europeana supports.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public enum DocType {
	TEXT("doc", "pdf"),
	IMAGE("jpeg", "jpg", "png", "tif"),
	SOUND("mp3"),
	VIDEO("avi", "mpg"),
	_3D;

	private String[] extentions;

	DocType(String... extentions) {
		this.extentions = extentions;
	}

	public static DocType safeValueOf(String[] strings) {
		if (StringArrayUtils.isNotBlank(strings)) {
			return safeValueOf(strings[0]);
		}
		return null;
	}

	/**
	 * Returns DocType object belongs to the name of that type (if any).
	 * @param string
	 *   The name of the DocType
	 * @return
	 *   The DocType object or null
	 */
	public static DocType safeValueOf(String string) {
		if (StringUtils.isNotBlank(string)) {
			for (DocType t : values()) {
				if (t.toString().equalsIgnoreCase(string)) {
					return t;
				}
			}
		}
		return null;
	}

	public static DocType getByExtention(String ext) {
		if (StringUtils.isNotBlank(ext)) {
			for (DocType t : values()) {
				if (StringArrayUtils.isNotBlank(t.extentions)) {
					for (String e : t.extentions) {
						e = StringArrayUtils.concat(".", e);
						if (StringUtils.endsWithIgnoreCase(ext, e)) {
							return t;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return StringUtils.stripStart(super.toString(), "_");
	}
}
