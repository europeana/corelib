package eu.europeana.corelib.definitions.edm.beans;

import java.util.Date;

public interface IdBean {

	/**
	 * Retrieve the Europeana object unique Id
	 * 
	 * @return The Europeana object UniqueID
	 */
	String getId();

	/**
	 * The date the record was created
	 * @return 
	 */
	Date getTimestampCreated();
	/**
	 * The date the record was updated
	 * @return 
	 */
	Date getTimestampUpdated();
}
