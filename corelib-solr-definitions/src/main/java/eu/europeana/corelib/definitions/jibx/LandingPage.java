
package eu.europeana.corelib.definitions.jibx;

/** 
 * This property captures the relation between an aggregation representing a
 cultural heritage object and the Web resource representing that object on the
 provider's web site. Example: Mona Lisa, represented by the Europeana aggregation
 europeana:ea-monalisa, has landing page
 http://www.culture.gouv.fr/public/mistral/joconde_fr?ACTION=CHERCHER&amp;FIELD_1=REF&amp;VALUE_1=000PE025604
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="landingPage"/>
 * </pre>
 */
public class LandingPage extends ResourceType
{
}
