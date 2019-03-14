/*
 * Copyright 2007-2013 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

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
    CC_NOC("http://creativecommons.org/publicdomain/mark/", "Public Domain Mark", "icon-pd", true),

    /**
     * open
     * http://creativecommons.org/publicdomain/zero/
     */
    CC_ZERO("http://creativecommons.org/publicdomain/zero/", "CC0", "icon-cczero", true),

    /**
     * open
     * http://creativecommons.org/licenses/by/
     */
    CC_BY("http://creativecommons.org/licenses/by/", "CC BY", "icon-cc icon-by",  true),

    /**
     * open
     * http://creativecommons.org/licenses/by-sa/
     */
    CC_BY_SA("http://creativecommons.org/licenses/by-sa/", "CC BY-SA", "icon-cc icon-by icon-sa", true),


    /* = = = | RESTRICTED LICENCES | = = = = = = = = = = = = = = = = = = = = = = = */

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nc/
     */
    CC_BY_NC("http://creativecommons.org/licenses/by-nc/", "CC BY-NC", "icon-cc icon-by icon-nceu", true),

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nc-sa/
     */
    CC_BY_NC_SA("http://creativecommons.org/licenses/by-nc-sa/", "CC BY-NC-SA", "icon-cc icon-by icon-nceu icon-sa", true),

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nc-nd/
     */
    CC_BY_NC_ND("http://creativecommons.org/licenses/by-nc-nd/", "CC BY-NC-ND", "icon-cc icon-by icon-nceu icon-nd", true),

    /**
     * restricted
     * http://creativecommons.org/licenses/by-nd/
     */
    CC_BY_ND("http://creativecommons.org/licenses/by-nd/", "CC BY-ND", "icon-cc icon-by icon-nd", true),

    /**
     * restricted
     * http://www.europeana.eu/rights/out-of-copyright-non-commercial/
     */
    @Deprecated
    EU_OOC_NC("http://www.europeana.eu/rights/out-of-copyright-non-commercial/",
            "Out of copyright - non commercial re-use", "icon-publicdomain icon-nceu", false),

    /**
     * restricted
     * http://rightsstatements.org/vocab/InC-EDU/1.0/
     */
    RS_INC_EDU("http://rightsstatements.org/vocab/InC-EDU/1.0/", "In Copyright - Educational Use Permitted", "", true),

    /**
     * restricted
     * http://rightsstatements.org/vocab/NoC-NC/1.0/
     */
    RS_NOC_NC("http://rightsstatements.org/vocab/NoC-NC/1.0/", "No Copyright - non-commercial re-use only", "", true),

    /**
     * restricted
     * http://rightsstatements.org/vocab/NoC-OKLR/1.0/
     */
    RS_NOC_OKLR("http://rightsstatements.org/vocab/NoC-OKLR/1.0/", "No Copyright - Other Known Legal Restrictions", "", true),


    /* = = = | PERMISSION LICENCES | = = = = = = = = = = = = = = = = = = = = = = = */

    /**
     * permission
     * http://www.europeana.eu/rights/rr-f/
     * @deprecated see http://www.europeana.eu/rights/rr-f/
     */
    @Deprecated
    EU_RR_F("http://www.europeana.eu/rights/rr-f/", "Rights Reserved - Free Access", "icon-copyright", false),

    /**
     * permission
     * http://www.europeana.eu/rights/rr-p/
     * @deprecated see http://www.europeana.eu/rights/rr-p/
     */
    @Deprecated
    EU_RR_P("http://www.europeana.eu/rights/rr-p/", "Rights Reserved - Paid Access", "icon-copyright", false),

    /**
     * permission
     * http://www.europeana.eu/rights/rr-r/
     * @deprecated see also http://www.europeana.eu/rights/rr-r/
     */
    @Deprecated
    EU_RR_R("http://www.europeana.eu/rights/rr-r/", "Rights Reserved - Restricted Access", "icon-copyright", false),

    /**
     * permission
     * http://www.europeana.eu/rights/unknown/
     */
    @Deprecated
    EU_U("http://www.europeana.eu/rights/unknown/", "Unknown Copyright Status", "icon-unknown", false),

    /**
     * permission
     * http://www.europeana.eu/rights/test-orphan-work-test/
     */
    @Deprecated
    EU_ORPHAN("http://www.europeana.eu/rights/test-orphan-work-test/", "Orphan Work", "icon-unknown", false),

    /**
     * permission
     * http://rightsstatements.org/vocab/InC/1.0/
     */
    RS_INC("http://rightsstatements.org/vocab/InC/1.0/", "In Copyright", "", true),

    /**
     * permission
     * http://rightsstatements.org/vocab/InC-OW-EU/1.0/
     */
    RS_INC_OW_EU("http://rightsstatements.org/vocab/InC-OW-EU/1.0/", "In Copyright - EU Orphan Work", "", true),

    /**
     * permission
     * http://rightsstatements.org/vocab/CNE/1.0/
     */
    RS_CNE("http://rightsstatements.org/vocab/CNE/1.0/", "Copyright Not Evaluated", "", true);

    private String url = null;
    private String rightsText = null;
    private String rightsIcon = null;
    private boolean showExternalIcon = false;

    /**
     * Constructor for method
     *
     * @param url        Url associated with the rights for the object
     * @param rightsText Text associated with the rights
     * @param rightsIcon Icon associated with the rights
     */
    RightsOption(String url, String rightsText, String rightsIcon, boolean showExternalIconIn) {
        this.url = url;
        this.rightsText = rightsText;
        this.rightsIcon = rightsIcon;
        this.showExternalIcon = showExternalIconIn;
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
     * Returns the RightsOption that corresponds to the provided url, or null if no match was found
     * Note that the used protocol in the url (https://, ftp://) is ignored.
     * @param url rights url
     * @return RightsOption or null
     */
    public static RightsOption getValueByUrl(String url) {
        if (StringUtils.isNotBlank(url)) {

            // strip off http:// and https:// to prevent matching problems
            String urlToCheck = url.toLowerCase(Locale.GERMAN);
            if (url.startsWith("http://") || url.startsWith("https://")) {
                urlToCheck = StringUtils.substringAfter(url, "://");
            }
            // strip off any version numbering (e.g. creativecommons.org/publicdomain/mark/1.0/ )
            String[] urlToCheckParts = (urlToCheck.split("/"));
            if (urlToCheckParts.length >= 3) {
                // we add slashes at the front and end, to avoid accidental false matches with too short strings
                urlToCheck = "/" + urlToCheckParts[0] + "/" + urlToCheckParts[1] + "/" + urlToCheckParts[2] +"/";
            }

            for (RightsOption option : RightsOption.values()) {
                if (option.url.contains(urlToCheck)) {
                    return option;
                }
            }
        }
        return null;
    }

}
