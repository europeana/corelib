
package eu.europeana.corelib.definitions.jibx;

/** 
 *  edm:isNextInSequence relates two resources S and R that are ordered
 parts of the same resource A, and such that S comes immediately after R in the order
 created by their being parts of A. Example: Page 34 of the Gutenberg Bible is next
 in sequence to page 33 of the same title. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="isNextInSequence"/>
 * </pre>
 */
public class IsNextInSequence extends ResourceType
{
}
