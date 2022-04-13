
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ColorSpaceType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="CMY"/>
 *     &lt;xs:enumeration value="CMYK"/>
 *     &lt;xs:enumeration value="grayscale"/>
 *     &lt;xs:enumeration value="HCL"/>
 *     &lt;xs:enumeration value="HCLp"/>
 *     &lt;xs:enumeration value="HSB"/>
 *     &lt;xs:enumeration value="HSI"/>
 *     &lt;xs:enumeration value="HSL"/>
 *     &lt;xs:enumeration value="HSV"/>
 *     &lt;xs:enumeration value="HWB"/>
 *     &lt;xs:enumeration value="Lab"/>
 *     &lt;xs:enumeration value="LCHab"/>
 *     &lt;xs:enumeration value="LCHuv"/>
 *     &lt;xs:enumeration value="LMS"/>
 *     &lt;xs:enumeration value="Log"/>
 *     &lt;xs:enumeration value="Luv"/>
 *     &lt;xs:enumeration value="OHTA"/>
 *     &lt;xs:enumeration value="Rec601Luma"/>
 *     &lt;xs:enumeration value="Rec601YCbCr"/>
 *     &lt;xs:enumeration value="Rec709Luma"/>
 *     &lt;xs:enumeration value="Rec709YCbCr"/>
 *     &lt;xs:enumeration value="RGB"/>
 *     &lt;xs:enumeration value="scRGB"/>
 *     &lt;xs:enumeration value="sRGB"/>
 *     &lt;xs:enumeration value="XYZ"/>
 *     &lt;xs:enumeration value="xyY"/>
 *     &lt;xs:enumeration value="YCbCr"/>
 *     &lt;xs:enumeration value="YDbDr"/>
 *     &lt;xs:enumeration value="YCC"/>
 *     &lt;xs:enumeration value="YIQ"/>
 *     &lt;xs:enumeration value="YPbPr"/>
 *     &lt;xs:enumeration value="YUV"/>
 *     &lt;xs:enumeration value="other"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ColorSpaceType {
    CMY("CMY"), CMYK("CMYK"), GRAYSCALE("grayscale"), HCL("HCL"), HC_LP("HCLp"), HSB(
            "HSB"), HSI("HSI"), HSL("HSL"), HSV("HSV"), HWB("HWB"), LAB("Lab"), LC_HAB(
            "LCHab"), LC_HUV("LCHuv"), LMS("LMS"), LOG("Log"), LUV("Luv"), OHTA(
            "OHTA"), REC601_LUMA("Rec601Luma"), REC601_Y_CB_CR("Rec601YCbCr"), REC709_LUMA(
            "Rec709Luma"), REC709_Y_CB_CR("Rec709YCbCr"), RGB("RGB"), SC_RGB(
            "scRGB"), S_RGB("sRGB"), XYZ("XYZ"), XY_Y("xyY"), Y_CB_CR("YCbCr"), Y_DB_DR(
            "YDbDr"), YCC("YCC"), YIQ("YIQ"), Y_PB_PR("YPbPr"), YUV("YUV"), OTHER(
            "other");
    private final String value;

    private ColorSpaceType(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static ColorSpaceType convert(String value) {
        for (ColorSpaceType inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
