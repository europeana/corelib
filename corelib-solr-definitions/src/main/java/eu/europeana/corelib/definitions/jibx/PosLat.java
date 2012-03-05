
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:float" name="pos_lat"/>
 * </pre>
 */
public class PosLat
{
    private Float posLat;

    /** 
     * Get the 'pos_lat' element value.
     * 
     * @return value
     */
    public Float getPosLat() {
        return posLat;
    }

    /** 
     * Set the 'pos_lat' element value.
     * 
     * @param posLat
     */
    public void setPosLat(Float posLat) {
        this.posLat = posLat;
    }
}
