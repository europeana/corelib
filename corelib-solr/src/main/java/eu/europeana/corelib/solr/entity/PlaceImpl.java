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

import com.google.code.morphia.annotations.Embedded;

import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Place
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Embedded
public class PlaceImpl extends ContextualClassImpl implements Place {
	

	private String[] isPartOf;
	private float latitude;
	private float longitude;
	private float altitude;
	private float[] position;
	private String[] skosHiddenLabel;
	private String[] dcTermsHasPart;
	private String[] owlSameAs;
	
	


	
	

	@Override
	public String[] getIsPartOf() {
		return (StringArrayUtils.isNotBlank(isPartOf)?this.isPartOf.clone():null);
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
		if(o==null){
			return false;
		}
		if(o.getClass() == this.getClass()){
			return ((PlaceImpl) o).getAbout()!=null?this.getAbout().equals(((PlaceImpl) o).getAbout()):false;
		}
		return false;
	}
	
	@Override
	public int hashCode(){ 
		return (int) (this.getAbout()!=null?this.getAbout().hashCode():this.latitude*100+this.longitude);
	}

	@Override
	public void setAltitude(float altitude) {
		this.altitude = altitude;
		
	}

	@Override
	public float getAltitude() {
		return this.altitude;
	}

	@Override
	public void setPosition(float[] position) {
		this.position = position;
		
	}

	@Override
	public float[] getPosition() {
		return this.position!=null? this.position.clone(): null;
	}

	@Override
	public void setSkosHiddenLabel(String[] skosHiddenLabel) {
		this.skosHiddenLabel = skosHiddenLabel;
	}

	@Override
	public String[] getSkosHiddenLabel() {
		return (StringArrayUtils.isNotBlank(skosHiddenLabel) ? this.skosHiddenLabel.clone() : null);
	}

	@Override
	public void setDcTermsHasPart(String[] dcTermsHasPart) {
		this.dcTermsHasPart = dcTermsHasPart;
	}

	@Override
	public String[] getDcTermsHasPart() {
		return (StringArrayUtils.isNotBlank(dcTermsHasPart) ? this.dcTermsHasPart.clone() : null);
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
		
	}
	
	@Override
	public String[] getOwlSameAs(){
		return (StringArrayUtils.isNotBlank(owlSameAs) ? this.owlSameAs.clone() : null);
	}
}
