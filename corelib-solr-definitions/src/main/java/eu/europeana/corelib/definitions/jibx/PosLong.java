
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:float" name="pos_long"/>
 * </pre>
 */
public class PosLong
{
    private Float posLong;

    /** 
     * Get the 'pos_long' element value.
     * 
     * @return value
     */
    public Float getPosLong() {
        return posLong;
    }

    /** 
     * Set the 'pos_long' element value.
     * 
     * @param posLong
     */
    public void setPosLong(Float posLong) {
        this.posLong = posLong;
    }
}
