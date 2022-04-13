
package eu.europeana.corelib.definitions.jibx;

/** 
 * This property captures the relation between the continuation of a
 resource and that resource. This applies to a story, a serial, a journal etc. No
 content of the successor resource is identical or has a similar form with that of
 the precursor. The similarity is only in the context, subjects and figures of a
 plot. Successors typically form part of a common whole - such as a trilogy, a
 journal, etc. Example: "The Two Towers" is a successor of "Fellowship of the Ring".
 The issue 57 of "Le Temps" is a successor of issue 56 of the Le Temps.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="isSuccessorOf"/>
 * </pre>
 */
public class IsSuccessorOf extends ResourceType
{
}
