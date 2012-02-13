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

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

import eu.europeana.corelib.definitions.solr.entity.Place;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Place
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Entity("Place")
public class PlaceImpl implements Place {
	@Id
	ObjectId placeId;

	private Map<String, String> prefLabel;
	private Map<String, String> altLabel;
	private String[] note;
	private String[] isPartOf;
	private float latitude;
	private float longitude;

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
		return this.note;
	}

	@Override
	public String[] getIsPartOf() {
		return this.isPartOf;
	}

	@Override
	public float getLatitude() {
		return this.latitude;
	}

	@Override
	public float getLongitude() {
		return this.longitude;
	}

	@Override
	public ObjectId getPlaceId() {
		return this.placeId;
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
	public void setPlaceId(ObjectId placeId) {
		this.placeId = placeId;
	}

	@Override
	public void setIsPartOf(String[] isPartOf) {
		this.isPartOf = isPartOf.clone();
	}

	@Override
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	@Override
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object o) {
		return this.getPlaceId().equals(((PlaceImpl) o).getPlaceId());
	}
}
