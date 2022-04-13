
package eu.europeana.corelib.definitions.jibx;

/** 
 * The EDM Content Type
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:EdmType" name="type"/>
 * </pre>
 */
public class Type2
{
    private EdmType type;

    /** 
     * Get the 'type' element value.
     * 
     * @return value
     */
    public EdmType getType() {
        return type;
    }

    /** 
     * Set the 'type' element value.
     * 
     * @param type
     */
    public void setType(EdmType type) {
        this.type = type;
    }
}
