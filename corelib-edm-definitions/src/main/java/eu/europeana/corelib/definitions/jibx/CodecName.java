
package eu.europeana.corelib.definitions.jibx;

/** 
 * The name of a device or computer program capable of encoding or decoding a digital data stream or signal (i.e. coder-decoder). Example:
 <codecName xmlns="http://www.w3.org/2001/XMLSchema">mp4v</codecName>

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="xs:string" name="codecName"/>
 * </pre>
 */
public class CodecName
{
    private String codecName;

    /** 
     * Get the 'codecName' element value.
     * 
     * @return value
     */
    public String getCodecName() {
        return codecName;
    }

    /** 
     * Set the 'codecName' element value.
     * 
     * @param codecName
     */
    public void setCodecName(String codecName) {
        this.codecName = codecName;
    }
}
