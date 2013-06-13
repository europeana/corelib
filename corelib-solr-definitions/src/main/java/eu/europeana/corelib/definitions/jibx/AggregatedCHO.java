
package eu.europeana.corelib.definitions.jibx;

/** 
 *  This property associates an ORE aggregation with the cultural heritage
 object(s) (CHO for short) it is about. In Europeana, an aggregation aggregates at
 least one CHO. Typically in an aggregation there will be exactly one aggregated
 object, but some aggregations, e.g. those representing archive finding aids, may
 refer to more than one object. Conversely, a CHO may be aggregated by several
 aggregations. Typically, in the data maintained by Europeana, a CHO would be
 aggregated by one EuropeanaAggregation, and at least one provider Aggregation.
 Example:The aggregation of Mona Lisa edm:aggregatedCHO Mona Lisa. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="aggregatedCHO"/>
 * </pre>
 */
public class AggregatedCHO extends ResourceType
{
}
