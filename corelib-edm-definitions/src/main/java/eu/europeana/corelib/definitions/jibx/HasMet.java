
package eu.europeana.corelib.definitions.jibx;

/** 
 *  edm:hasMet relates a resource with the objects or phenomena that have
 happened to or have happened together with the resource under consideration. We can
 abstractly think of history and the present as a series of "meetings" between people
 and other things in space-time. Therefore we name this relationship as the things
 the object "has met" in the course of its existence. These meetings are events in
 the proper sense, in which other people and things participate in any role.
 Example:The location of an object may be due to a transport, move to a place, or
 because it has been created at that spot. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="hasMet"/>
 * </pre>
 */
public class HasMet extends ResourceType
{
}
