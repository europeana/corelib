
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 A related resource that is required by the described resource to support its function, delivery or coherence. Example:
 <requires xmlns="http://www.w3.org/2001/XMLSchema"> http://ads.ahds.ac.uk/project/userinfo/css/oldbrowsers.css </requires>
 where the resource described is a HTML file at http://ads.ahds.ac.uk/project/userinfo/digitalTextArchiving.html Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns1:ResourceOrLiteralType" name="requires"/>
 * </pre>
 */
public class Requires extends ResourceOrLiteralType
{
}
