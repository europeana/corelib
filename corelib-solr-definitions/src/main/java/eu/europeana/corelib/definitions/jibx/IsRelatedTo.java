
package eu.europeana.corelib.definitions.jibx;

/** 
 * edm:isRelatedTo is the most general contextual property in EDM.
 Contextual properties have typically to do either with the things that have happened
 to or together with the object under consideration, or what the object refers to by
 its shape, form or features in a figural or encoded form. For sake of simplicity, we
 include in the contextual relationships also the scholarly classification, which may
 have either to do with the role and cultural connections of the object in the past,
 or its kind of structure, substance or contents as it can be verified at present.
 Example:Moby Dick is related to XIX century literature. Mona Lisa is related to
 Renaissance Art. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceOrLiteralType" name="isRelatedTo"/>
 * </pre>
 */
public class IsRelatedTo extends ResourceOrLiteralType
{
}
