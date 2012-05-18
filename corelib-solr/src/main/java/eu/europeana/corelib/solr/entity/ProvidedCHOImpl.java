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

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;
/**
 *  @see eu.europeana.corelib.definitions.solr.entity.ProvidedCHO
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@Embedded
public class ProvidedCHOImpl implements ProvidedCHO {

	private ObjectId id;
	@Indexed(unique=true, dropDups=true)
	private String about;
	private String[] owlSameAs;
	private String edmIsNextInSequence;
	

	@Override
	public ObjectId getId() {
		
		return this.id;
	}

	@Override
	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String[] getOwlSameAs() {
		return (this.owlSameAs!=null?this.owlSameAs.clone():null);
	}

	@Override
	public String getEdmIsNextInSequence() {
		return this.edmIsNextInSequence;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs.clone();
	}

	@Override
	public void setEdmIsNextInSequence(String edmIsNextInSequence) {
		this.edmIsNextInSequence = edmIsNextInSequence;
	}

	@Override
	public String getAbout() {
		return this.about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public boolean equals(Object o) {
		if(o==null){
			return false;
		}
		if(o.getClass() == this.getClass()){
			return this.getAbout().equals(((ProvidedCHOImpl) o).getAbout());
		}
		return false;
	}
	
	@Override
	public int hashCode(){ 
		return this.about.hashCode();
	}
}
