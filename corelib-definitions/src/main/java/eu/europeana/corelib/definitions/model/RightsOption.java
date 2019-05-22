package eu.europeana.corelib.definitions.model;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

/**
 * Class represents a set of rights that can be attached to an item in the Europeana website.
 *
 * @author kevinparkings
 * @author Andy MacLean
 */
public enum RightsOption {

    /* = = = | OPEN LICENCES | = = = = = = = = = = = = = = = = = = = = = = = = = = */

    /**
     * open
     * http://creativecommons.org/publicdomain/mark/
     */
    CC_NOC("http://creativecommons.org/publicdomain/mark/", "Public Domain Mark", "icon-pd", true, "mark"),

    /**
     * open
     * http://creativecommons.org/publicdomain/zero/
     */
    CC_ZERO("http://creativecommons.org/publicdomain/zero/", "CC0", "icon-cczero", true, "zero"),

    /**
     * open
     * http://creativecommons.org/licenses/by/
     */
    CC_BY("http://creativecommons.org/licenses/by/", "CC BY", "icon-cc icon-by",  true, "/by/"),

    /**
     * open
     * http://creativecommons.org/licenses/by-sa/
     */
    CC_BY_SA("http://creativecommons.org/licenses/by-sa/", "CC BY-SA", "icon-cc icon-by icon-sa", true, "/by-sa/"),


    /* = = = | RESTRICTED LICENCES | = = = = = = = = = = = = = = = = = = = = = = = */

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nc/
     */
    CC_BY_NC("http://creativecommons.org/licenses/by-nc/", "CC BY-NC", "icon-cc icon-by icon-nceu", true, "/by-nc/"),

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nc-sa/
     */
    CC_BY_NC_SA("http://creativecommons.org/licenses/by-nc-sa/", "CC BY-NC-SA", "icon-cc icon-by icon-nceu icon-sa", true, "/by-nc-sa/"),

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nc-nd/
     */
    CC_BY_NC_ND("http://creativecommons.org/licenses/by-nc-nd/", "CC BY-NC-ND", "icon-cc icon-by icon-nceu icon-nd", true, "/by-nc-nd/"),

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nd/
     */
    CC_BY_ND("http://creativecommons.org/licenses/by-nd/", "CC BY-ND", "icon-cc icon-by icon-nd", true, "/by-nd/"),

    /**
     * restricted
     * http://www.europeana.eu/rights/out-of-copyright-non-commercial/
     */
    @Deprecated
    EU_OOC_NC("http://www.europeana.eu/rights/out-of-copyright-non-commercial/",
            "Out of copyright - non commercial re-use", "icon-publicdomain icon-nceu", false, "out-of-copyright"),

    /**
     * restricted
     * http://rightsstatements.org/vocab/InC-EDU/1.0/
     */
    RS_INC_EDU("http://rightsstatements.org/vocab/InC-EDU/1.0/", "In Copyright - Educational Use Permitted", "", true, "InC-EDU"),

    /**
     * restricted
     * http://rightsstatements.org/vocab/NoC-NC/1.0/
     */
    RS_NOC_NC("http://rightsstatements.org/vocab/NoC-NC/1.0/", "No Copyright - non-commercial re-use only", "", true, "NoC-NC/"),

    /**
     * restricted
     * http://rightsstatements.org/vocab/NoC-OKLR/1.0/
     */
    RS_NOC_OKLR("http://rightsstatements.org/vocab/NoC-OKLR/1.0/", "No Copyright - Other Known Legal Restrictions", "", true, "NoC-OKLR/"),


    /* = = = | PERMISSION LICENCES | = = = = = = = = = = = = = = = = = = = = = = = */

    /**
     * permission
     * http://www.europeana.eu/rights/rr-f/
     * @deprecated see http://www.europeana.eu/rights/rr-f/
     */
    @Deprecated
    EU_RR_F("http://www.europeana.eu/rights/rr-f/", "Rights Reserved - Free Access", "icon-copyright", false , "/rr-f/"),

    /**
     * permission
     * http://www.europeana.eu/rights/rr-p/
     * @deprecated see http://www.europeana.eu/rights/rr-p/
     */
    @Deprecated
    EU_RR_P("http://www.europeana.eu/rights/rr-p/", "Rights Reserved - Paid Access", "icon-copyright", false, "/rr-p/"),

    /**
     * permission
     * http://www.europeana.eu/rights/rr-r/
     * @deprecated see also http://www.europeana.eu/rights/rr-r/
     */
    @Deprecated
    EU_RR_R("http://www.europeana.eu/rights/rr-r/", "Rights Reserved - Restricted Access", "icon-copyright", false, "/rr-r/"),

    /**
     * permission
     * http://www.europeana.eu/rights/unknown/
     */
    @Deprecated
    EU_U("http://www.europeana.eu/rights/unknown/", "Unknown Copyright Status", "icon-unknown", false, "unknown"),

    /**
     * permission
     * http://www.europeana.eu/rights/test-orphan-work-test/
     */
    @Deprecated
    EU_ORPHAN("http://www.europeana.eu/rights/test-orphan-work-test/", "Orphan Work", "icon-unknown", false, "orphan"),

    /**
     * permission
     * http://rightsstatements.org/vocab/InC/1.0/
     */
    RS_INC("http://rightsstatements.org/vocab/InC/1.0/", "In Copyright", "", true, "/InC/"),

    /**
     * permission
     * http://rightsstatements.org/vocab/InC-OW-EU/1.0/
     */
    RS_INC_OW_EU("http://rightsstatements.org/vocab/InC-OW-EU/1.0/", "In Copyright - EU Orphan Work", "", true, "/InC-OW-EU/"),

    /**
     * permission
     * http://rightsstatements.org/vocab/CNE/1.0/
     */
    RS_CNE("http://rightsstatements.org/vocab/CNE/1.0/", "Copyright Not Evaluated", "", true, "/CNE/");

    private String url = null;
    private String rightsText = null;
    private String rightsIcon = null;
    private boolean showExternalIcon = false;
    protected String shortMatchPattern = null;

    /**
     * Constructor for method
     *
     * @param url        Url associated with the rights for the object
     * @param rightsText Text associated with the rights
     * @param rightsIcon Icon associated with the rights
     * @param shortMatchPattern minimal pattern for checking if there's a match with the provided rights url
     *                          (see also getValueByUrl() method )
     */
    RightsOption(String url, String rightsText, String rightsIcon, boolean showExternalIconIn, String shortMatchPattern) {
        this.url = url;
        this.rightsText = rightsText;
        this.rightsIcon = rightsIcon;
        this.showExternalIcon = showExternalIconIn;
        this.shortMatchPattern = shortMatchPattern;
    }

    /**
     * Returns the full Url associated with the rights
     *
     * @return Full url associated with the rights
     */
    public String getUrl() {
        return url;
    }

    /**
     * Return text associated with the rights
     *
     * @return text associated with the results
     */
    public String getRightsText() {
        return rightsText;
    }

    /**
     * Returns the url of the icon associated with the results
     *
     * @return url of icon associated with the results
     */
    public String getRightsIcon() {
        return rightsIcon;
    }

    /**
     * Returns the url of the icon associated with the results
     *
     * @return url of icon associated with the results
     */
    public boolean getShowExternalIcon() {
        return showExternalIcon;
    }

    /**
     * Returns the RightsOption that corresponds to the provided url, or null if no match was found.
     * We always use case-insensitive matching with either http or https protocol, regardless if the provided exactMatch
     * is true or not
     * @param url rights url
     * @param exactMatch if true we check if the provided url contains the rights url,
     *                   if false we merely check if the provided url contains the short matchPattern string.
     * @return RightsOption or null
     */
    public static RightsOption getValueByUrl(String url, boolean exactMatch) {
        if (StringUtils.isNotBlank(url)) {
            String urlToMatch = url.toLowerCase(Locale.GERMAN).trim();
            if (urlToMatch.startsWith("https://")) {
                urlToMatch = urlToMatch.replaceFirst("https://", "http://");
            }

            if (exactMatch) {
                for (RightsOption option : RightsOption.values()) {
                    // We check if the urlToMatch contains the rights url because provided urls often include extra's
                    // such as a different version number, or a specific language
                    // for example https://creativecommons.org/licenses/by-nc-nd/4.0/deed.es_ES
                    if (urlToMatch.contains(option.url.toLowerCase(Locale.GERMAN))) {
                        return option;
                    }
                }
            } else {
                // Short match is done because there are quite a bit of incorrect urls in the database
                // see also RightsOptionTest.getValueByUrlShortMatchTest()
                // The downside is that we also match completely incorrect patterns such as http://www.iam/mark/
                for (RightsOption option : RightsOption.values()) {
                    if (urlToMatch.contains(option.shortMatchPattern.toLowerCase(Locale.GERMAN))) {
                        return option;
                    }
                }
            }
        }
        return null;
    }

}
