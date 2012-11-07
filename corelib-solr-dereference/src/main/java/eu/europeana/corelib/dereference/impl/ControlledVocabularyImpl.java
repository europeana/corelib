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
package eu.europeana.corelib.dereference.impl;

import java.util.Map;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.ControlledVocabulary;

/**
 * 
 * @see eu.europeana.corelib.dereference.ControlledVocabulary.java
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
@Entity("ControlledVocabulary")
public class ControlledVocabularyImpl implements ControlledVocabulary {
	@Id
	private ObjectId id;
	
	@Indexed (unique = false)
	private String URI;
	@Indexed(unique = true)
	private String name;
	private String location;
	private String[] rules;
	private int iterations;


	// Used to denote special characteristics of the Resource. For example the
	// Geonames always point to URI/ResourceCode/about.rdf
	// rather than URI/ResourceCode which redirects to the Geonames website.
	private String suffix;
	private Map<String, EdmLabel> elements;

	public ControlledVocabularyImpl(){
		
	}

	public ControlledVocabularyImpl(String name) {
		
		this.name = name;
		
	}

	@Override
	public ObjectId getId(){
		return id;
	}
	@Override
	public void setId(ObjectId id){
		this.id = id;
	}
	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String getSuffix() {
		return this.suffix;
	}

	@Override
	public void setURI(String URI) {
		this.URI = URI;
	}

	@Override
	public String getURI() {
		return this.URI;
	}

	@Override
	public Map<String, EdmLabel> getElements() {
		return this.elements;
	}


	@Override
	public void setElements(Map<String,EdmLabel> elements){
		this.elements = elements;
	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String[] getRules() {
		return rules;
	}

	@Override
	public void setRules(String[] rules) {
		this.rules = rules!=null?rules.clone():null;
	}

	@Override
	public void setIterations(int iterations) {
		this.iterations=iterations;
	}

	@Override
	public int getIterations() {
		return this.iterations;
	}
	
	
}
