
package eu.europeana.corelib.definitions.jibx;

/** 
 *  The name or identifier of the organisation that contributes data to
 Europeana. This element is specifically included to allow the name of the
 organisation who supplies data to Europeana indirectly via an aggregator to be
 recorded and displayed in the portal. Aggregator names are recorded in edm:provider.
 If an organisation provides data directly to Europeana (i.e. not via an aggregator)
 the values in edm:dataProvider and edm:provider will be the same. Although the range
 of this property is given as edm:Agent organisation names should be provided as an
 ordinary text string until a Europeana authority file for organisations has been
 established. At that point providers will be able to send an identifier from the
 file instead of a text string. The name provided should be the preferred form of the
 name in the language the provider chooses as the default language for display in the
 portal. Countries with multiple languages may prefer to concatenate the name in more
 than one language (See the example below.) Note: Europeana Data Provider is not
 necessarily the institution where the physical object is located. Example: The
 current &lt;edm:dataProvider&gt;Palais des Beaux Arts de
 Lille&lt;/edm:dataProvider&gt; could become &lt;edm:dataProvider&gt;http://
 www.pba-lille.fr/&lt;/edm:dataProvider&gt; 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceOrLiteralType" name="dataProvider"/>
 * </pre>
 */
public class DataProvider extends ResourceOrLiteralType
{
}
