
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 A related resource that is supplanted, displaced, or superseded by the described resource. Example:
 <replaces xmlns="http://www.w3.org/2001/XMLSchema"> http://dublincore.org/about/2006/01/01/bylaws/ </replaces>
 where the resource described is a newer version (http://dublincore.org/about/2009/01/05/bylaws/) Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns1:ResourceOrLiteralType" name="replaces"/>
 * </pre>
 */
public class Replaces extends ResourceOrLiteralType
{
}
