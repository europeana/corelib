
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="LanguageCodes">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="ar"/>
 *     &lt;xs:enumeration value="az"/>
 *     &lt;xs:enumeration value="be"/>
 *     &lt;xs:enumeration value="bg"/>
 *     &lt;xs:enumeration value="bs"/>
 *     &lt;xs:enumeration value="ca"/>
 *     &lt;xs:enumeration value="cs"/>
 *     &lt;xs:enumeration value="cy"/>
 *     &lt;xs:enumeration value="da"/>
 *     &lt;xs:enumeration value="de"/>
 *     &lt;xs:enumeration value="el"/>
 *     &lt;xs:enumeration value="en"/>
 *     &lt;xs:enumeration value="es"/>
 *     &lt;xs:enumeration value="et"/>
 *     &lt;xs:enumeration value="eu"/>
 *     &lt;xs:enumeration value="fi"/>
 *     &lt;xs:enumeration value="fr"/>
 *     &lt;xs:enumeration value="ga"/>
 *     &lt;xs:enumeration value="gd"/>
 *     &lt;xs:enumeration value="gl"/>
 *     &lt;xs:enumeration value="he"/>
 *     &lt;xs:enumeration value="hi"/>
 *     &lt;xs:enumeration value="hr"/>
 *     &lt;xs:enumeration value="hu"/>
 *     &lt;xs:enumeration value="hy"/>
 *     &lt;xs:enumeration value="ie"/>
 *     &lt;xs:enumeration value="is"/>
 *     &lt;xs:enumeration value="it"/>
 *     &lt;xs:enumeration value="ja"/>
 *     &lt;xs:enumeration value="ka"/>
 *     &lt;xs:enumeration value="ko"/>
 *     &lt;xs:enumeration value="lt"/>
 *     &lt;xs:enumeration value="lv"/>
 *     &lt;xs:enumeration value="mk"/>
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
 *     &lt;xs:enumeration value="sq"/>
 *     &lt;xs:enumeration value="sr"/>
 *     &lt;xs:enumeration value="sv"/>
 *     &lt;xs:enumeration value="tr"/>
 *     &lt;xs:enumeration value="uk"/>
 *     &lt;xs:enumeration value="yi"/>
 *     &lt;xs:enumeration value="zh"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum LanguageCodes {
    AR("ar"), AZ("az"), BE("be"), BG("bg"), BS("bs"), CA("ca"), CS("cs"), CY(
            "cy"), DA("da"), DE("de"), EL("el"), EN("en"), ES("es"), ET("et"), EU(
            "eu"), FI("fi"), FR("fr"), GA("ga"), GD("gd"), GL("gl"), HE("he"), HI(
            "hi"), HR("hr"), HU("hu"), HY("hy"), IE("ie"), IS("is"), IT("it"), JA(
            "ja"), KA("ka"), KO("ko"), LT("lt"), LV("lv"), MK("mk"), MT("mt"), MUL(
            "mul"), NL("nl"), NO("no"), PL("pl"), PT("pt"), RO("ro"), RU("ru"), SK(
            "sk"), SL("sl"), SQ("sq"), SR("sr"), SV("sv"), TR("tr"), UK("uk"), YI(
            "yi"), ZH("zh");
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
