
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 A related resource that is a version, edition, or adaptation of the described resource. Changes in version imply substantive changes in
 content rather than differences in format.
 Example:
 <hasVersion xmlns="http://www.w3.org/2001/XMLSchema"> The Sorcerer's Apprentice (translation by Edwin Zeydel, 1955) </hasVersion>
 . In this example the 1955 translation is a version of the described resource. Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://purl.org/dc/terms/" xmlns:ns1="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns1:ResourceOrLiteralType" name="hasVersion"/>
 * </pre>
 */
public class HasVersion extends ResourceOrLiteralType
{
}
