
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 The main MIME types as defined by IANA: e.g. audio, video, text, application, or a container MIME type. Example:
 <hasMimeType xmlns="http://www.w3.org/2001/XMLSchema">video/mp4</hasMimeType>

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="hasMimeType"/>
 * </pre>
 */
public class HasMimeType
{
    private String hasMimeType;

    /** 
     * Get the 'hasMimeType' element value.
     * 
     * @return value
     */
    public String getHasMimeType() {
        return hasMimeType;
    }

    /** 
     * Set the 'hasMimeType' element value.
     * 
     * @param hasMimeType
     */
    public void setHasMimeType(String hasMimeType) {
        this.hasMimeType = hasMimeType;
    }
}
