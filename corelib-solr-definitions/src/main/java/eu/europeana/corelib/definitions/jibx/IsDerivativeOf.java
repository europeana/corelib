
package eu.europeana.corelib.definitions.jibx;

/** 
 * This property captures a narrower notion of derivation than
 edm:isSimilarTo, in the sense that it relates a resource to another one, obtained by
 reworking, reducing, expanding, parts or the whole contents of the former, and
 possibly adding some minor parts. Versions have an even narrower meaning, in that it
 requires common identity between the related resources. Translations, summaries,
 abstractions etc. do not qualify as versions, but do qualify as derivatives.
 Example:The Italian translation of Moby Dick is a derivation of the original work.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="isDerivativeOf"/>
 * </pre>
 */
public class IsDerivativeOf extends ResourceType
{
}
