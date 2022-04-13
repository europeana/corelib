
package eu.europeana.corelib.definitions.jibx;

/** 
 * An unambiguous URL reference to the digital object on the provider's web
 site in its full information context. See also edm:isShownBy.This is a URL that will
 be active in the Europeana interface. It will lead users to the digital object
 displayed on the provider's web site in its full information context. Use
 edm:isShownAt if you display the digital object with extra information (such as
 header, banner etc). Example:
 &lt;edm:isShownAt&gt;http://www.photo.rmn.fr/cf/htm/CPICZ.aspx?E=2C6NU0VFLVNY&lt;/edm:isShownAt&gt;

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="isShownAt"/>
 * </pre>
 */
public class IsShownAt extends ResourceType
{
}
