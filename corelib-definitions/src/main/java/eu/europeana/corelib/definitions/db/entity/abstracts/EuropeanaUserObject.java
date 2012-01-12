package eu.europeana.corelib.definitions.db.entity.abstracts;

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;

public interface EuropeanaUserObject extends UserConnected<Long> {

	public abstract Long getId();

	public abstract Date getDateSaved();

	public abstract void setDateSaved(Date dateSaved);

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getEuropeanaObject();

	public abstract void setEuropeanaObject(String europeanaObject);

	public abstract String getEuropeanaUri();

	public abstract void setEuropeanaUri(String europeanaUri);

	public abstract DocType getDocType();

	public abstract void setDocType(DocType docType);

}