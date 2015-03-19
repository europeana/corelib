
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 A related resource that requires the described resource to support its function, delivery or coherence. Example:
 <isRequiredBy xmlns="http://www.w3.org/2001/XMLSchema"> http://www.myslides.com/myslideshow.ppt </isRequiredBy>
 where the image being described is required for an online slideshow. Type: String

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:ns1="http://purl.org/dc/terms/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ResourceOrLiteralType" name="isRequiredBy"/>
 * </pre>
 */
public class IsRequiredBy extends ResourceOrLiteralType
{
}
