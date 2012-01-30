/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.db.entity.abstracts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eu.europeana.corelib.definitions.db.DatabaseDefinition;
import eu.europeana.corelib.definitions.db.entity.abstracts.EuropeanaUserObject;
import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Super class Saved europeana objects in my Europeana
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@MappedSuperclass
public abstract class EuropeanaUserObjectImpl extends UserConnectedImpl<Long> implements DatabaseDefinition, EuropeanaUserObject {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = FIELDSIZE_EUROPEANA_URI)
	private String europeanaUri;

	@Column(length = FIELDSIZE_TITLE)
	private String title;

	@Column(length = FIELDSIZE_EUROPEANA_OBJECT)
	private String europeanaObject;

	@Column(length = FIELDSIZE_DOCTYPE)
	@Enumerated(EnumType.STRING)
	private DocType docType = DocType.IMAGE;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSaved;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Date getDateSaved() {
		return dateSaved;
	}

	@Override
	public void setDateSaved(Date dateSaved) {
		this.dateSaved = dateSaved;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getEuropeanaObject() {
		return europeanaObject;
	}

	@Override
	public void setEuropeanaObject(String europeanaObject) {
		this.europeanaObject = europeanaObject;
	}

	@Override
	public String getEuropeanaUri() {
		return europeanaUri;
	}

	@Override
	public void setEuropeanaUri(String europeanaUri) {
		this.europeanaUri = europeanaUri;
	}

	@Override
	public DocType getDocType() {
		return docType;
	}

	@Override
	public void setDocType(DocType docType) {
		this.docType = docType;
	}
	
}
