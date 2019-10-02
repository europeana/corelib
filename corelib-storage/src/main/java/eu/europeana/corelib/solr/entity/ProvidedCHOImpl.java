package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.ProvidedCHO;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * ProvidedCHO (edm:ProvidedCHO) means provided cultural heritage object
 * 
 * @see eu.europeana.corelib.definitions.edm.entity.ProvidedCHO
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("ProvidedCHO")
public class ProvidedCHOImpl extends AbstractEdmEntityImpl implements ProvidedCHO {

	private String[] owlSameAs;

	@Override
	public String[] getOwlSameAs() {
		return (this.owlSameAs!=null ? this.owlSameAs.clone() : null);
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			return this.getAbout().equals(((ProvidedCHOImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.about.hashCode();
	}
}
