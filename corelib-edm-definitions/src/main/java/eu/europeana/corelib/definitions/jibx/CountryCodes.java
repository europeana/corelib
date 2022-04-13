
package eu.europeana.corelib.definitions.jibx;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://www.europeana.eu/schemas/edm/" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="CountryCodes">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="Albania"/>
 *     &lt;xs:enumeration value="Andorra"/>
 *     &lt;xs:enumeration value="Armenia"/>
 *     &lt;xs:enumeration value="Austria"/>
 *     &lt;xs:enumeration value="Azerbaijan"/>
 *     &lt;xs:enumeration value="Australia"/>
 *     &lt;xs:enumeration value="Belarus"/>
 *     &lt;xs:enumeration value="Belgium"/>
 *     &lt;xs:enumeration value="Bosnia and Herzegovina"/>
 *     &lt;xs:enumeration value="Bulgaria"/>
 *     &lt;xs:enumeration value="Canada"/>
 *     &lt;xs:enumeration value="China"/>
 *     &lt;xs:enumeration value="Croatia"/>
 *     &lt;xs:enumeration value="Cyprus"/>
 *     &lt;xs:enumeration value="Czech Republic"/>
 *     &lt;xs:enumeration value="Denmark"/>
 *     &lt;xs:enumeration value="Estonia"/>
 *     &lt;xs:enumeration value="Europe"/>
 *     &lt;xs:enumeration value="Finland"/>
 *     &lt;xs:enumeration value="France"/>
 *     &lt;xs:enumeration value="Georgia"/>
 *     &lt;xs:enumeration value="Germany"/>
 *     &lt;xs:enumeration value="Greece"/>
 *     &lt;xs:enumeration value="Holy See (Vatican City State)"/>
 *     &lt;xs:enumeration value="Hungary"/>
 *     &lt;xs:enumeration value="Iceland"/>
 *     &lt;xs:enumeration value="India"/>
 *     &lt;xs:enumeration value="Ireland"/>
 *     &lt;xs:enumeration value="Italy"/>
 *     &lt;xs:enumeration value="Israel"/>
 *     &lt;xs:enumeration value="Japan"/>
 *     &lt;xs:enumeration value="Kazakhstan"/>
 *     &lt;xs:enumeration value="Korea, Republic of"/>
 *     &lt;xs:enumeration value="Latvia"/>
 *     &lt;xs:enumeration value="Lebanon"/>
 *     &lt;xs:enumeration value="Liechtenstein"/>
 *     &lt;xs:enumeration value="Lithuania"/>
 *     &lt;xs:enumeration value="Luxembourg"/>
 *     &lt;xs:enumeration value="Macedonia"/>
 *     &lt;xs:enumeration value="Malta"/>
 *     &lt;xs:enumeration value="Moldova"/>
 *     &lt;xs:enumeration value="Monaco"/>
 *     &lt;xs:enumeration value="Montenegro"/>
 *     &lt;xs:enumeration value="Netherlands"/>
 *     &lt;xs:enumeration value="Norway"/>
 *     &lt;xs:enumeration value="Poland"/>
 *     &lt;xs:enumeration value="Portugal"/>
 *     &lt;xs:enumeration value="Romania"/>
 *     &lt;xs:enumeration value="Russia"/>
 *     &lt;xs:enumeration value="San Marino"/>
 *     &lt;xs:enumeration value="Serbia"/>
 *     &lt;xs:enumeration value="Slovakia"/>
 *     &lt;xs:enumeration value="Slovenia"/>
 *     &lt;xs:enumeration value="Spain"/>
 *     &lt;xs:enumeration value="Sweden"/>
 *     &lt;xs:enumeration value="Switzerland"/>
 *     &lt;xs:enumeration value="Turkey"/>
 *     &lt;xs:enumeration value="Ukraine"/>
 *     &lt;xs:enumeration value="United Kingdom"/>
 *     &lt;xs:enumeration value="United States of America"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum CountryCodes {
    ALBANIA("Albania"), ANDORRA("Andorra"), ARMENIA("Armenia"), AUSTRIA(
            "Austria"), AZERBAIJAN("Azerbaijan"), AUSTRALIA("Australia"), BELARUS(
            "Belarus"), BELGIUM("Belgium"), BOSNIAAND_HERZEGOVINA(
            "Bosnia and Herzegovina"), BULGARIA("Bulgaria"), CANADA("Canada"), CHINA(
            "China"), CROATIA("Croatia"), CYPRUS("Cyprus"), CZECH_REPUBLIC(
            "Czech Republic"), DENMARK("Denmark"), ESTONIA("Estonia"), EUROPE(
            "Europe"), FINLAND("Finland"), FRANCE("France"), GEORGIA("Georgia"), GERMANY(
            "Germany"), GREECE("Greece"), HOLY_SEE_VATICAN_CITY_STATE(
            "Holy See (Vatican City State)"), HUNGARY("Hungary"), ICELAND(
            "Iceland"), INDIA("India"), IRELAND("Ireland"), ITALY("Italy"), ISRAEL(
            "Israel"), JAPAN("Japan"), KAZAKHSTAN("Kazakhstan"), KOREA_REPUBLICOF(
            "Korea, Republic of"), LATVIA("Latvia"), LEBANON("Lebanon"), LIECHTENSTEIN(
            "Liechtenstein"), LITHUANIA("Lithuania"), LUXEMBOURG("Luxembourg"), MACEDONIA(
            "Macedonia"), MALTA("Malta"), MOLDOVA("Moldova"), MONACO("Monaco"), MONTENEGRO(
            "Montenegro"), NETHERLANDS("Netherlands"), NORWAY("Norway"), POLAND(
            "Poland"), PORTUGAL("Portugal"), ROMANIA("Romania"), RUSSIA(
            "Russia"), SAN_MARINO("San Marino"), SERBIA("Serbia"), SLOVAKIA(
            "Slovakia"), SLOVENIA("Slovenia"), SPAIN("Spain"), SWEDEN("Sweden"), SWITZERLAND(
            "Switzerland"), TURKEY("Turkey"), UKRAINE("Ukraine"), UNITED_KINGDOM(
            "United Kingdom"), UNITED_STATESOF_AMERICA(
            "United States of America");
    private final String value;

    private CountryCodes(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static CountryCodes convert(String value) {
        for (CountryCodes inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
