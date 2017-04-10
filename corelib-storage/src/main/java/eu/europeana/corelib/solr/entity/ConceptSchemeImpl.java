package eu.europeana.corelib.solr.entity;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.ConceptScheme;
import eu.europeana.corelib.utils.StringArrayUtils;

@Entity("ConceptScheme")
public class ConceptSchemeImpl extends AbstractEdmEntityImpl implements ConceptScheme {

	//TODO: FIX LATER TO CONFORM TO OTHER ENTITIES
	private String[] dcTitle;
	private String[] dcCreator;
	private String[] note;
	private String[] hasTopConceptOf;



	@Override
	public String[] getDcTitle() {
		return (StringArrayUtils.isNotBlank(dcTitle) ? this.dcTitle.clone() : null);
	}

	@Override
	public void setDcTitle(String[] dcTitle) {
		this.dcTitle = dcTitle;
	}

	@Override
	public String[] getDcCreator() {
		return (StringArrayUtils.isNotBlank(dcCreator) ? this.dcCreator.clone() : null);
	}

	@Override
	public void setDcCreator(String[] dcCreator) {
		this.dcCreator = dcCreator;
	}

	@Override
	public String[] getHasTopConceptOf() {
		return (StringArrayUtils.isNotBlank(hasTopConceptOf) ? this.hasTopConceptOf.clone() : null);
	}

	@Override
	public void setHasTopConceptOf(String[] hasTopConceptOf) {
		this.hasTopConceptOf = hasTopConceptOf;
	}

	@Override
	public String[] getNote() {
		return (StringArrayUtils.isNotBlank(note) ? this.note.clone() : null);
	}

	@Override
	public void setNote(String[] note) {
		this.note = note;
	}
}
