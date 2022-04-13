
package eu.europeana.corelib.definitions.jibx;

/** 
 *  Name of the organization that delivers data to Europeana. The
 edm:provider is the organization that sends the data to Europeana, and this is not
 necessarily the institution that holds or owns the original or digitised object.
 Where data is being supplied by an aggregator or project edm:provider is the name of
 aggregator/project. The name of the content holder can be recorded in
 edm:dataProvider. If the content holder supplies data directly to Europeana then the
 name should also appear in this element. Although the range of this property is
 given as edm:Agent, organisation names should be provided as an ordinary text string
 until a Europeana authority file for organisations has been established. At that
 point providers will be able to send an identifier from the file instead of a text
 string. The name should be in the original language(s). Example: The current
 &lt;edm:provider&gt;Geheugen van Nederland&lt;/edm:provider&gt; could become
 &lt;edm:provider&gt;http://www.geheugenvannederland.nl/&lt;/edm:provider&gt;

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceOrLiteralType" name="provider"/>
 * </pre>
 */
public class Provider extends ResourceOrLiteralType
{
}
