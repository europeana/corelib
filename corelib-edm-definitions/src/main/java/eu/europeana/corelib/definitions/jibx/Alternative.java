
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 An alternative name for the resource. This can be any form of the title that is used as a substitute or an alternative to the formal
 title of the resource including abbreviations or
 translations of the title. Example:
 <alternative xmlns="http://www.w3.org/2001/XMLSchema">Ocho semanas</alternative>
 (When
 <title xmlns="http://www.w3.org/2001/XMLSchema">Eight weeks</title>
 ) Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns1:LiteralType" name="alternative"/>
 * </pre>
 */
public class Alternative extends LiteralType
{
}
