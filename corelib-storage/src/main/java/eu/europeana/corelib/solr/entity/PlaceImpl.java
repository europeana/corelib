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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.Place;
import eu.europeana.corelib.utils.StringArrayUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * @see eu.europeana.corelib.definitions.edm.entity.Place
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
@Entity("Place")
public class PlaceImpl extends ContextualClassImpl implements Place {

	private Map<String,List<String>> isPartOf;
	private Float latitude;
	private Float longitude;
	private Float altitude;
	private Map<String,Float> position;
	private Map<String,List<String>> dcTermsHasPart;
	private String[] owlSameAs;

	@Override
	public Map<String,List<String>> getIsPartOf() {
		return this.isPartOf;
	}

	@Override
	public Float getLatitude() {
		if (this.latitude == null || this.longitude == null || (this.latitude == 0 && this.longitude == 0)) {
			return null;
		}
		return this.latitude;
	}

	@Override
	public Float getLongitude() {
		if (this.latitude == null || this.longitude == null || (this.latitude == 0 && this.longitude == 0)) {
			return null;
		}
		return this.longitude;
	}

	@Override
	public void setIsPartOf(Map<String,List<String>> isPartOf) {
		this.isPartOf = isPartOf;
	}

	@Override
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	@Override
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null){
			return false;
		}
		if (o.getClass() == this.getClass()){
			return ((PlaceImpl) o).getAbout() != null ? this.getAbout().equals(((PlaceImpl) o).getAbout()) : false;
		}
		return false;
	}

	@Override
	public int hashCode(){ 
		return (int) (this.getAbout() != null ? this.getAbout().hashCode() : this.latitude * 100 + this.longitude);
	}

	@Override
	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}

	@Override
	public Float getAltitude() {
		if(this.latitude==null|| this.longitude==null||(this.latitude==0&& this.longitude==0)){
			return null;
		}
		return this.altitude;
	}

	@Override
	public void setPosition(Map<String, Float> position) {
		this.position = position;
	}

	@Override
	public Map<String,Float> getPosition() {
		return this.position;
	}

	@Override
	public void setDcTermsHasPart(Map<String,List<String>> dcTermsHasPart) {
		this.dcTermsHasPart = dcTermsHasPart;
	}

	@Override
	public Map<String,List<String>> getDcTermsHasPart() {
		return this.dcTermsHasPart;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs!=null?owlSameAs.clone():null;
	}

	@Override
	public String[] getOwlSameAs(){
		return (StringArrayUtils.isNotBlank(owlSameAs) ? this.owlSameAs.clone() : null);
	}
}
