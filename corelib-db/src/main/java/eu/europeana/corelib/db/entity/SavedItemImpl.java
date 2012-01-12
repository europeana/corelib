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

import eu.europeana.corelib.db.entity.abstracts.EuropeanaUserObjectImpl;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;
import eu.europeana.corelib.definitions.db.entity.SavedItem;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@Table(name = DatabaseDefinition.TABLENAME_SAVEDITEM)
public class SavedItemImpl extends EuropeanaUserObjectImpl implements SavedItem {
	private static final long serialVersionUID = -7059004310525816113L;

	@Column(length = FIELDSIZE_AUTHOR)
	private String author;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}
}
