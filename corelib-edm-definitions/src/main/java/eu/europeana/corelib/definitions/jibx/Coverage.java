
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 The spatial or temporal topic of the resource, the spatial applicability of the resource, or the jurisdiction under which the resource is relevant. This may be a named place, a
 location, a spatial coordinate, a period, date, date range or a named administrative entity. Example:
 <coverage xmlns="http://www.w3.org/2001/XMLSchema">1995-1996</coverage>
 <coverage xmlns="http://www.w3.org/2001/XMLSchema">Boston, MA</coverage>
 Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://purl.org/dc/elements/1.1/" xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns1:ResourceOrLiteralType" name="coverage"/>
 * </pre>
 */
public class Coverage extends ResourceOrLiteralType
{
}
