
package eu.europeana.corelib.definitions.jibx;

/** 
 *  This property relates a ORE aggregation about a CHO with a web resource
 providing a view of that CHO. Examples of view are: a thumbnail, a textual abstract
 and a table of contents. The ORE aggregation may be a Europeana aggregation, in
 which case the view is an object owned by Europeana (i.e., an instance of
 edm:EuropeanaObject) or an aggregation contributed by a content provider. In order
 to capture both these cases, the domain of edm:hasView is ore:Aggregation and its
 range is edm:WebResource Example: An ore:Aggregation of Mona Lisa contributed by
 Louvre may have as view a low resolution digital image of Mona Lisa. The issue
 number 56 of "Le Temps" contributed by BNF may have as view a text of some parts of
 the issue 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="hasView"/>
 * </pre>
 */
public class HasView extends ResourceType
{
}
