
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="UGCType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="true"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum UGCType {
    TRUE("true");
    private final String value;

    private UGCType(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static UGCType convert(String value) {
        for (UGCType inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
