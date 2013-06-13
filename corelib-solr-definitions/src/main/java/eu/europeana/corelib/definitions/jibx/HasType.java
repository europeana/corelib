
package eu.europeana.corelib.definitions.jibx;

/** 
 * This property relates a resource with the concepts it belongs to in a
 suitable type system such as MIME or any thesaurus that captures categories of
 objects in a given field (e.g., the "Objects" facet in Getty's Art and Architecture
 Thesaurus). It does not capture aboutness. Example:The type of Mona Lisa is (AAT)
 Painting. The type of a digital image of Mona Lisa may be JPEG. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceOrLiteralType" name="hasType"/>
 * </pre>
 */
public class HasType extends ResourceOrLiteralType
{
}
