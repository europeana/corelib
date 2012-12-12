package eu.europeana.corelib.dereference.impl;

import com.google.code.morphia.annotations.Entity;

@Entity
public class EdmMappedField {

	private String label;
	
	private String attribute;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
}
