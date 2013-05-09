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

import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * DocType defines the different type Object types Europeana supports.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Borys Omelayenko
 */
public enum DocType {
	TEXT("doc", "pdf"), 
	IMAGE("jpeg", "jpg", "png", "tif"), 
	SOUND("mp3"), 
	VIDEO("avi", "mpg"), 
	_3D;

	String[] extentions;

	private DocType(String... extentions) {
		this.extentions = extentions;
	}

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
