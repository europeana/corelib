package eu.europeana.corelib.solr.entity;

import java.util.Map;

import com.google.code.morphia.annotations.Embedded;

import eu.europeana.corelib.definitions.solr.entity.ContextualClass;

@Embedded
public class ContextualClassImpl extends AbstractEdmEntityImpl implements ContextualClass {
	
	private Map<String,String> prefLabel;
	private Map<String,String> altLabel;
	private Map<String,String> hiddenLabel;
	private String[] note;

	@Override
	public Map<String, String> getPrefLabel() {
		return this.prefLabel;
	}

	@Override
	public Map<String, String> getHiddenLabel() {
		return this.hiddenLabel;
	}

	@Override
	public Map<String, String> getAltLabel() {
		return this.altLabel;
	}

	@Override
	public String[] getNote() {
		return (this.note!=null?this.note.clone():null);
	}

	@Override
	public void setAltLabel(Map<String, String> altLabel) {
		this.altLabel = altLabel;
	}

	@Override
	public void setPrefLabel(Map<String, String> prefLabel) {
		this.prefLabel = prefLabel;
	}

	@Override
	public void setHiddenLabel(Map<String, String> hiddenLabel) {
		this.hiddenLabel = hiddenLabel;
	}

	@Override
	public void setNote(String[] note) {
		this.note = note.clone();
	}
}
