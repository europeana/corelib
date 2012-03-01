package eu.europeana.corelib.solr.service.query;

/**
 * Enumeration holding the MoreLikeThis fields
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public enum MoreLikeThis {
	CREATOR("creator"), DESCRIPTION("description"), SUBJECT("subject"), CONTRIBUTOR(
			"contributor"), TITLE("title"), WHO("who"), WHAT("what"), WHERE(
			"where"), WHEN("when");

	String mlt;

	MoreLikeThis(String mlt) {
		this.mlt = mlt;
	}

	/**
	 * Return the MoreLikeThis field
	 */
	public String toString() {
		return this.mlt;
	}
}
