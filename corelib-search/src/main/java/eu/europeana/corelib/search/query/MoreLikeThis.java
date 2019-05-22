package eu.europeana.corelib.search.query;

/**
 * Enumeration holding the MoreLikeThis fields
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * @deprecated July 2017 MoreLikeThis / SimilarItems for records is no longer being used (and doesn't work properly)
 */
@Deprecated
public enum MoreLikeThis {

	// CREATOR("creator"),
	// DESCRIPTION("description"),
	// SUBJECT("subject"),
	// CONTRIBUTOR("contributor"),
	TITLE("title"),
	WHO("who"),
	WHAT("what"),
	// WHERE("where"),
	// WHEN("when")
	;

	private String mlt;

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
