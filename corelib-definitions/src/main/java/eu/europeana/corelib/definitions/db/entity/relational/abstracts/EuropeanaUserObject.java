package eu.europeana.corelib.definitions.db.entity.relational.abstracts;

import java.util.Date;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
public interface EuropeanaUserObject extends UserConnected<Long> {

	Date getDateSaved();

	void setDateSaved(Date dateSaved);

	String getTitle();

	void setTitle(String title);

	String getEuropeanaObject();

	void setEuropeanaObject(String europeanaObject);

	String getEuropeanaUri();

	void setEuropeanaUri(String europeanaUri);

	String getDocType();

	void setDocType(String docType);

}