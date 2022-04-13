
package eu.europeana.corelib.definitions.jibx;

/** 
 * The color space of an image resource i.e. 'grayscale' or 'sRGB'. Example:
 <hasColorSpace xmlns="http://www.w3.org/2001/XMLSchema">grayscale</hasColorSpace>

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:ColorSpaceType" name="hasColorSpace"/>
 * </pre>
 */
public class HasColorSpace
{
    private ColorSpaceType hasColorSpace;

    /** 
     * Get the 'hasColorSpace' element value.
     * 
     * @return value
     */
    public ColorSpaceType getHasColorSpace() {
        return hasColorSpace;
    }

    /** 
     * Set the 'hasColorSpace' element value.
     * 
     * @param hasColorSpace
     */
    public void setHasColorSpace(ColorSpaceType hasColorSpace) {
        this.hasColorSpace = hasColorSpace;
    }
}
