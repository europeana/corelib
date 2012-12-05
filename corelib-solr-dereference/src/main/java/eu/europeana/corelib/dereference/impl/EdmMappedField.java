package eu.europeana.corelib.dereference.impl;

import eu.europeana.corelib.definitions.model.EdmLabel;

public class EdmMappedField {

	private EdmLabel label;
	
	private String attribute;

	public EdmLabel getLabel() {
		return label;
	}

	public void setLabel(EdmLabel label) {
		this.label = label;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
}
