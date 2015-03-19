
package eu.europeana.corelib.definitions.jibx;

/** 
 * This property captures the use of some resource to add value to another
 resource. Such resources may be nested, such as performing a theater play text, and
 then recording the performance, or creating an artful edition of a collection of
 poems or just aggregating various poems in an anthology. There may be no single part
 that contains ultimately the incorporated object, which may be dispersed in the
 presentation. Therefore, incorporated resources do in general not form proper parts.
 Incorporated resources are not part of the same resource, but are taken from other
 resources, and have an independent history. Therefore edm:incorporates is not a
 sub-property of dcterm:hasPart. Example:The movie "A Clockwork Orange" incorporates
 Rossini's symphony from "La Gazza Ladra" in its original soundtrack. "E.A.Poe, The
 Raven (poem)" is incorporated in "Emerson Lake &amp; Palmers Tales of Mystery
 (music)" which is incorporated in "Concert Recording 1973 (vinyl)". 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="incorporates"/>
 * </pre>
 */
public class Incorporates extends ResourceType
{
}
