
package eu.europeana.corelib.definitions.jibx;

/** 
 * 
 <div xmlns="http://www.w3.org/1999/xhtml">

 <h3>space (as an attribute name)</h3>
 <p>
 denotes an attribute whose
 value is a keyword indicating what whitespace processing
 discipline is intended for the content of the element; its
 value is inherited.  This name is reserved by virtue of its
 definition in the XML specification.</p>

 </div>

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:attribute xmlns:ns="http://www.w3.org/XML/1998/namespace" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="space">
 *   &lt;xs:simpleType>
 *     &lt;!-- Reference to inner class SpaceInner -->
 *   &lt;/xs:simpleType>
 * &lt;/xs:attribute>
 * </pre>
 */
public class Space
{
    private SpaceInner space;

    /** 
     * Get the 'space' attribute value.
     * 
     * @return value
     */
    public SpaceInner getSpace() {
        return space;
    }

    /** 
     * Set the 'space' attribute value.
     * 
     * @param space
     */
    public void setSpace(SpaceInner space) {
        this.space = space;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:simpleType xmlns:xs="http://www.w3.org/2001/XMLSchema">
     *   &lt;xs:restriction base="xs:string">
     *     &lt;xs:enumeration value="default"/>
     *     &lt;xs:enumeration value="preserve"/>
     *   &lt;/xs:restriction>
     * &lt;/xs:simpleType>
     * </pre>
     */
    public static enum SpaceInner {
        DEFAULT("default"), PRESERVE("preserve");
        private final String value;

        private SpaceInner(String value) {
            this.value = value;
        }

        public String xmlValue() {
            return value;
        }

        public static SpaceInner convert(String value) {
            for (SpaceInner inst : values()) {
                if (inst.xmlValue().equals(value)) {
                    return inst;
                }
            }
            return null;
        }
    }
}
