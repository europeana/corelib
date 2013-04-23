package eu.europeana.corelib.utils.service;

public interface OptOutService {
	
	/**
	 * Checks whether a document ID refers to an opted out dataset
	 * 
	 * @param id
	 *            The document ID in the following formats:
	 *            "http://www.europeana.eu/portal/record/collectionId/recordId"
	 *            "/collectionId/recordId"
	 *            "collectionId"
	 * 
	 * @return TRUE if it is opted out, otherwise FALSE
	 */
	boolean check(String id);

}
