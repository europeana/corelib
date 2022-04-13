
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 A related resource that supplants, displaces, or supersedes the described resource. Example:
 <isReplacedBy xmlns="http://www.w3.org/2001/XMLSchema"> http://dublincore.org/about/2009/01/05/bylaws/ </isReplacedBy>
 where the resource described is an older version (http://dublincore.org/about/2006/01/01/bylaws/) Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns1:ResourceOrLiteralType" name="isReplacedBy"/>
 * </pre>
 */
public class IsReplacedBy extends ResourceOrLiteralType
{
}
