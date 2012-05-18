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

package eu.europeana.corelib.solr.entity;

import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.entity.Concept;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Concept
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Embedded
public class ConceptImpl implements Concept {

	private ObjectId id;

	private Map<String, String> prefLabel;
	private Map<String, String> altLabel;
	private String[] note;
	private String[] broader;
	@Indexed(unique = false)
	private String about;

	@Override
	public String getAbout() {
		return about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public Map<String, String> getPrefLabel() {
		return this.prefLabel;
	}

	@Override
	public Map<String, String> getAltLabel() {
		return this.altLabel;
	}

	@Override
	public String[] getNote() {
		return (this.note != null ? this.note.clone() : null);
	}

	@Override
	public String[] getBroader() {
		return (this.broader != null ? this.broader.clone() : null);
	}

	@Override
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public void setAltLabel(Map<String, String> altLabel) {
		this.altLabel = altLabel;

	}

	@Override
	public void setNote(String[] note) {
		this.note = note.clone();

	}

	@Override
	public void setPrefLabel(Map<String, String> prefLabel) {
		this.prefLabel = prefLabel;

	}

	@Override
	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public void setBroader(String[] broader) {
		this.broader = broader.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			return this.getAbout().equals(((ConceptImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.about.hashCode();
	}
}
