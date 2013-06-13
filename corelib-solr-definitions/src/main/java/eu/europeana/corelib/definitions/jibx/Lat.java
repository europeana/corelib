
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:float" name="lat"/>
 * </pre>
 */
public class Lat
{
    private Float lat;

    /** 
     * Get the 'lat' element value.
     * 
     * @return value
     */
    public Float getLat() {
        return lat;
    }

    /** 
     * Set the 'lat' element value.
     * 
     * @param lat
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }
}
