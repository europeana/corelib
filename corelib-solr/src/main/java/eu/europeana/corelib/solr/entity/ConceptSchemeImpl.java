package eu.europeana.corelib.solr.entity;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.solr.entity.ConceptScheme;
import eu.europeana.corelib.utils.StringArrayUtils;

@Embedded
public class ConceptSchemeImpl implements ConceptScheme{

	
	private ObjectId id;
	@Indexed(unique = false)
	private String about;
	private String[] dcTitle;
	private String[] dcCreator;
	private String[] note;
	private String[] hasTopConceptOf;
	
	@Override
	public ObjectId getId() {
		
		return this.id;
	}

	@Override
	public void setId(ObjectId id) {
		this.id = id;
		
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
