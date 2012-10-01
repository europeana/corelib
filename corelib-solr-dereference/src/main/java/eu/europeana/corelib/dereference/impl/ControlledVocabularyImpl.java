package eu.europeana.corelib.dereference.impl;

import java.util.HashMap;
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
	
}
