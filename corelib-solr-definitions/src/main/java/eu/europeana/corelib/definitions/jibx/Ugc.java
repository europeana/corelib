
package eu.europeana.corelib.definitions.jibx;

/** 
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
