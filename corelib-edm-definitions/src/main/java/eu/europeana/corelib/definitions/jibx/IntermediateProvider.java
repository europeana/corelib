
package eu.europeana.corelib.definitions.jibx;

/** 
 * The name or identifier of the intermediate organization that selects, collates, or curates data
 from a Data Provider that is then aggregated by a Provider from which Europeana harvests.
 The Intermedatiate Provider must be distinct from both the Data Provider and the Provider
 in the data supply chain.
 Example:
 &lt;edm:intermediateProvider&lt; Erfgoedplus.be&lt;/edm:intermediateProvider&lt;
 &lt;edm:intermediateProvider rdf:resource=”www.erfgoedplus.be/”/&lt;

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceOrLiteralType" name="intermediateProvider"/>
 * </pre>
 */
public class IntermediateProvider extends ResourceOrLiteralType
{
}
