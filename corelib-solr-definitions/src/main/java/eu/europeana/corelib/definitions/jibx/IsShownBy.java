
package eu.europeana.corelib.definitions.jibx;

/** 
 *  An unambiguous URL reference to the digital object on the provider's web
 site in the best available resolution/quality. See also edm:isShownAt. This is a URL
 that will be active in the Europeana interface. It will lead users to the digital
 object on the provider's website where they can view or play it. The digital object
 needs to be directly accessible by the URL and reasonably independent at that
 location. If the URL includes short copyright information with the pointer to the
 object it can be entered in edm:isShownBy. Use edm:isShownAt for digital objects
 embedded in HTML pages (even where the page is extremely simple). Example:
 &lt;edm:isShownBy&gt;http://resolver.kb.nl/resolve?urn=urn:gvn:RA01:30051001524450&lt;/edm:isShownBy&gt;

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="isShownBy"/>
 * </pre>
 */
public class IsShownBy extends ResourceType
{
}
