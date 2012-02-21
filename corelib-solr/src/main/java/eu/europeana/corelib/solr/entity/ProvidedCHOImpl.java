package eu.europeana.corelib.solr.entity;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;

@Entity("ProvidedCHO")
public class ProvidedCHOImpl implements ProvidedCHO {

	@Id ObjectId id;
	@Indexed String about;
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
		return this.owlSameAs;
	}

	@Override
	public String getEdmIsNextInSequence() {
		return this.edmIsNextInSequence;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
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
		return this.getId().equals(((ProvidedCHOImpl) o).getId());
	}
}
