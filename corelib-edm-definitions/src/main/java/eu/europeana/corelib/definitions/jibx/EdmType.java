
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="EdmType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="TEXT"/>
 *     &lt;xs:enumeration value="VIDEO"/>
 *     &lt;xs:enumeration value="IMAGE"/>
 *     &lt;xs:enumeration value="SOUND"/>
 *     &lt;xs:enumeration value="3D"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum EdmType {
    TEXT("TEXT"), VIDEO("VIDEO"), IMAGE("IMAGE"), SOUND("SOUND"), _3_D("3D");
    private final String value;

    private EdmType(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static EdmType convert(String value) {
        for (EdmType inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
