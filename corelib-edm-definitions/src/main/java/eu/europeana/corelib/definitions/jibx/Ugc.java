
package eu.europeana.corelib.definitions.jibx;

/** 
 * This element is used to identify user generated content (also called user
 created content). It should be applied to all digitised or born digital content
 contributed by the general public and collected by Europeana through a crowdsourcing
 initiative or project. The only value this element can take is "TRUE" to indicate
 that the object is user generated. It should be entered in uppercase. If the content
 is not user generated then the element should not be provided.
 Example:&lt;edm:UGC&gt;TRUE&lt;edm:UGC&gt; 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:UGCType" name="ugc"/>
 * </pre>
 */
public class Ugc
{
    private UGCType ugc;

    /** 
     * Get the 'ugc' element value.
     * 
     * @return value
     */
    public UGCType getUgc() {
        return ugc;
    }

    /** 
     * Set the 'ugc' element value.
     * 
     * @param ugc
     */
    public void setUgc(UGCType ugc) {
        this.ugc = ugc;
    }
}
