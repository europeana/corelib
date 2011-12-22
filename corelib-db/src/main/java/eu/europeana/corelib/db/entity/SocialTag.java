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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.europeana.corelib.db.entity.abstracts.UserConnectedEntity;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;
import eu.europeana.corelib.definitions.solr.DocType;

/**
 * @author Willem-Jan Boogerd <europeana [at] eledge.net>
 */
@Entity
@Table(name = DatabaseDefinition.TABLENAME_SOCIALTAGS)
public class SocialTag extends UserConnectedEntity<Long> implements DatabaseDefinition {
	private static final long serialVersionUID = -3635227115883742004L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = FIELDSIZE_TAG)
	private String tag;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSaved;

	@Column(length = FIELDSIZE_EUROPEANA_URI)
	private String europeanaUri;

	@Column(length = FIELDSIZE_TITLE)
	private String title;

	@Column(length = FIELDSIZE_EUROPEANA_OBJECT)
	private String europeanaObject;

	@Column(length = FIELDSIZE_DOCTYPE)
	@Enumerated(EnumType.STRING)
	private DocType docType = DocType.IMAGE;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public Long getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag.toLowerCase();
	}

	public Date getDateSaved() {
		return dateSaved;
	}

	public void setDateSaved(Date dateSaved) {
		this.dateSaved = dateSaved;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEuropeanaObject() {
		return europeanaObject;
	}

	public void setEuropeanaObject(String europeanaObject) {
		this.europeanaObject = europeanaObject;
	}

	public String getEuropeanaUri() {
		return europeanaUri;
	}

	public void setEuropeanaUri(String europeanaUri) {
		this.europeanaUri = europeanaUri;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

}