
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 The duration of a track or a signal expressed in ms. Example:
 <duration xmlns="http://www.w3.org/2001/XMLSchema">270000</duration>

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="duration"/>
 * </pre>
 */
public class Duration
{
    private String duration;

    /** 
     * Get the 'duration' element value.
     * 
     * @return value
     */
    public String getDuration() {
        return duration;
    }

    /** 
     * Set the 'duration' element value.
     * 
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
