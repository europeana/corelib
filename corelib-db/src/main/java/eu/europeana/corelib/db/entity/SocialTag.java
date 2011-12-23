/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
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

package eu.europeana.corelib.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import eu.europeana.corelib.db.entity.abstracts.EuropeanaUserObject;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;

/**
 * @author Willem-Jan Boogerd <www.eledge.net>
 */
@Entity
@Table(name = DatabaseDefinition.TABLENAME_SOCIALTAGS)
public class SocialTag extends EuropeanaUserObject {
	private static final long serialVersionUID = -3635227115883742004L;

	@Column(length = FIELDSIZE_TAG)
	private String tag;

	/**
	 * GETTERS & SETTTERS
	 */

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag.toLowerCase();
	}
}