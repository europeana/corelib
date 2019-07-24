package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.edm.entity.ContextualClass;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class ContextualClassImpl extends AbstractEdmEntityImpl implements ContextualClass {
	
	private Map<String,List<String>> prefLabel;
	private Map<String,List<String>> altLabel;
	private Map<String,List<String>> hiddenLabel;
	private Map<String,List<String>> note;
	private String foafDepiction;
	

	@Override
	public Map<String, List<String>> getPrefLabel() {
		return this.prefLabel;
	}

	@Override
	public Map<String, List<String>> getHiddenLabel() {
		return this.hiddenLabel;
	}

	@Override
	public Map<String, List<String>> getAltLabel() {
		return this.altLabel;
	}

	@Override
	public Map<String,List<String>> getNote() {
		return this.note;
	}

	@Override
	public void setAltLabel(Map<String, List<String>> altLabel) {
		this.altLabel = altLabel;
	}

	@Override
	public void setPrefLabel(Map<String, List<String>> prefLabel) {
		this.prefLabel = prefLabel;
	}

	@Override
	public void setHiddenLabel(Map<String, List<String>> hiddenLabel) {
		this.hiddenLabel = hiddenLabel;
	}

	@Override
	public void setNote(Map<String,List<String>>note) {
		this.note = note;
	}

	@Override
	public String getFoafDepiction() {
		return foafDepiction;
	}

	@Override
	public void setFoafDepiction(String foafDepiction) {
		this.foafDepiction = foafDepiction;
	}

	@JsonIgnore
	@Override
	public String getEntityIdentifier() {
		String[] splitArray = this.getAbout().split("/");
		return splitArray[splitArray.length-1];
	}
}
