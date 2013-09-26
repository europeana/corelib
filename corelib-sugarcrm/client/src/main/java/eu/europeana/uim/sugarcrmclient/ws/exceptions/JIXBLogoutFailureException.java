/**
 * 
 */
package eu.europeana.uim.sugarcrmclient.ws.exceptions;



/**
 * Exception thrown in case of a Logout Error
 * 
 * @author Georgios Markakis
 */
public class JIXBLogoutFailureException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * This constructor takes as an argument an ErrorValue object
	 * @param err the ErrorValue message
	 */
	public JIXBLogoutFailureException(String err) {
		
		super(err);

	}
}
