
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="LanguageCodes">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="bg"/>
 *     &lt;xs:enumeration value="ca"/>
 *     &lt;xs:enumeration value="cs"/>
 *     &lt;xs:enumeration value="da"/>
 *     &lt;xs:enumeration value="de"/>
 *     &lt;xs:enumeration value="el"/>
 *     &lt;xs:enumeration value="en"/>
 *     &lt;xs:enumeration value="es"/>
 *     &lt;xs:enumeration value="et"/>
 *     &lt;xs:enumeration value="fi"/>
 *     &lt;xs:enumeration value="fr"/>
 *     &lt;xs:enumeration value="ga"/>
 *     &lt;xs:enumeration value="gd"/>
 *     &lt;xs:enumeration value="hu"/>
 *     &lt;xs:enumeration value="ie"/>
 *     &lt;xs:enumeration value="is"/>
 *     &lt;xs:enumeration value="it"/>
 *     &lt;xs:enumeration value="lt"/>
 *     &lt;xs:enumeration value="lv"/>
 *     &lt;xs:enumeration value="mt"/>
 *     &lt;xs:enumeration value="mul"/>
 *     &lt;xs:enumeration value="nl"/>
 *     &lt;xs:enumeration value="no"/>
 *     &lt;xs:enumeration value="pl"/>
 *     &lt;xs:enumeration value="pt"/>
 *     &lt;xs:enumeration value="ro"/>
 *     &lt;xs:enumeration value="ru"/>
 *     &lt;xs:enumeration value="sk"/>
 *     &lt;xs:enumeration value="sl"/>
 *     &lt;xs:enumeration value="sr"/>
 *     &lt;xs:enumeration value="sv"/>
 *     &lt;xs:enumeration value="tr"/>
 *     &lt;xs:enumeration value="yi"/>
 *     &lt;xs:enumeration value="cy"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum LanguageCodes {
    BG("bg"), CA("ca"), CS("cs"), DA("da"), DE("de"), EL("el"), EN("en"), ES(
            "es"), ET("et"), FI("fi"), FR("fr"), GA("ga"), GD("gd"), HU("hu"), IE(
            "ie"), IS("is"), IT("it"), LT("lt"), LV("lv"), MT("mt"), MUL("mul"), NL(
            "nl"), NO("no"), PL("pl"), PT("pt"), RO("ro"), RU("ru"), SK("sk"), SL(
            "sl"), SR("sr"), SV("sv"), TR("tr"), YI("yi"), CY("cy");
    private final String value;

    private LanguageCodes(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static LanguageCodes convert(String value) {
        for (LanguageCodes inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
