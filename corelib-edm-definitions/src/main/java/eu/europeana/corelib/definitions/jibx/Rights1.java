
package eu.europeana.corelib.definitions.jibx;

/** 
 * Information about copyright of the digital object as specified by
 isShownBy and isShownAt.The value in this element is a URL constructed according to
 the specifications in the "Specifications of the controlled values for edm:rights".
 The URLs are constructed by adding a code indicating the copyright status of an
 object to the domain name where that status is defined. The domain will be either
 the europeana.eu domain or the creativecommons.org domain. For users of Europeana.eu
 this copyright information also applies to the preview specified in edm:object. In
 order to allow organisations to manage the provision of this element, edm:rights has
 an obligation level of "recommended" in this version of EDM. It will be changed to
 "Mandatory" in a later version. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceType" name="rights"/>
 * </pre>
 */
public class Rights1 extends ResourceType
{
}
