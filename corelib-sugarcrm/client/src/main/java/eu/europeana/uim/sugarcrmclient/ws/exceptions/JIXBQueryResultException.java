/**
 * 
 */
package eu.europeana.uim.sugarcrmclient.ws.exceptions;



import eu.europeana.uim.sugarcrmclient.jibxbindings.ErrorValue;

/**
 * This exception occurs when the returned message indicates an error
 * in the search process.
 * 
 * @author Georgios Markakis
 */
public class JIXBQueryResultException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * This constructor takes as an argument an ErrorValue object
	 * @param err the ErrorValue message
	 */
	public JIXBQueryResultException(ErrorValue err) {
		
		super(JIXBUtil.generateMessageFromObject(err));

	}

	/**
	 * Creates a new instance of this class.
	 * @param string
	 */
	public JIXBQueryResultException(String string) {
		super(string);
	}
}
