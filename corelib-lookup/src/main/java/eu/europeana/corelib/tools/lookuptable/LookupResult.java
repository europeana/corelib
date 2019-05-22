package eu.europeana.corelib.tools.lookuptable;

/**
 *
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * @since 3 Oct 2012
 */
public class LookupResult {

	private String europeanaID;
	
	private LookupState state;
	
	/**
	 * @return the europeanaID
	 */
	public String getEuropeanaID() {
		return europeanaID;
	}

	/**
	 * @param europeanaID the europeanaID to set
	 */
	public void setEuropeanaID(String europeanaID) {
		this.europeanaID = europeanaID;
	}

	/**
	 * 
	 */
	public LookupResult() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the state
	 */
	public LookupState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(LookupState state) {
		this.state = state;
	}

}
