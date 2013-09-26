/* JIXBUtil.java - created on Aug 12, 2011, Copyright (c) 2011 The European Library, all rights reserved */
package eu.europeana.uim.sugarcrmclient.ws.exceptions;
import eu.europeana.uim.sugarcrmclient.jibxbindings.ErrorValue;
/**
 * 
 * 
 * @author Rene Wiermer (rene.wiermer@kb.nl)
 * @date Aug 12, 2011
 */
public class JIXBUtil {
	
	/**
	 * Utility classes should not have a public or default constructor.
	 */
	private JIXBUtil(){
		
	}
	
    /**
     * Auxiliary method method accessible from all subclasses of this exception type.
     * It is used to formulate the exception description message... 
     * 
     * @param number
     * @param name
     * @param description
     * @return
     */
    static String generateMessageFromObject(ErrorValue err){ 
        StringBuffer sb = new StringBuffer();
        sb.append("Error Number: ");
        sb.append(err.getNumber());
        sb.append(" Error Name: ");
        sb.append(err.getName());
        sb.append(" Error Description: ");
        sb.append(err.getDescription());
        
        return sb.toString();
    }
}
