package eu.europeana.corelib.solr.derived;

import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Class for assembling and holding the data forming the (webresource-level) attribution snippet)
 * Created by luthien on 30/11/2015.
 */
public class AttributionSnippet {

    private StringBuilder textSnippet = new StringBuilder();
    private StringBuilder htmlSnippet = new StringBuilder();

    public AttributionSnippet() {
        //to avoid InstantiationException in Junit Test
    }

    public AttributionSnippet(WebResourceImpl wRes, String htmlCssSource) {
        AttributionConverter attributionConverter = new AttributionConverter();
        Attribution attribution = attributionConverter.createAttribution(wRes);
        assembleTextSnippet(attribution);
        assembleHtmlSnippet(attribution, htmlCssSource);
    }

    public String getTextSnippet() {
        return textSnippet.toString();
    }

    public String getHtmlSnippet() {
        return htmlSnippet.toString();
    }

    protected void assembleTextSnippet(Attribution attribution) {
        if (attribution.getTitle().size() > 0) {
            textSnippet.append(flattenThisMap(attribution.getTitle()));
            textSnippet.append(StringUtils.isNotBlank(attribution.getLandingPage()) ? " - " : "");
        }
        textSnippet.append(attribution.getLandingPage());
        textSnippet.append((attribution.getTitle().size() > 0) || StringUtils.isNotBlank(attribution.getLandingPage()) ? ". " : "");
        if (attribution.getCreator().size() > 0) {
            textSnippet.append(flattenThisMap(attribution.getCreator()));
            textSnippet.append(". ");
        }
        if (attribution.getProvider().size() > 0) {
            textSnippet.append(flattenThisMap(attribution.getProvider()));
            textSnippet.append(StringUtils.isNotBlank(attribution.getProviderUrl()) ? " - " : "");
        }
        textSnippet.append(attribution.getProviderUrl());
        textSnippet.append((attribution.getProvider().size() > 0 || StringUtils.isNotBlank(attribution.getProviderUrl())) ? ". " : "");
        textSnippet.append(StringUtils.isBlank(attribution.getRightsLabel()) ? AttributionConstants.CANNOT_DETERMINE_RIGHTS : attribution.getRightsLabel() + " - " + attribution.getRightsStatement());
    }

    protected void assembleHtmlSnippet(Attribution attribution, String htmlCssSource) {
        initializeHtmlSnippet(htmlCssSource);
        if (! attribution.getTitle().isEmpty()) {
            addTagName(AttributionConstants.TITLE);
        }
        int i = 1;
        for (Map.Entry<String, String> entry : attribution.getTitle().entrySet()) {
            // add the href for the first element only
            if (i == 1) {
                addTagValues(entry.getValue(), entry.getKey(), false, true, attribution.getItemUri(), null);
                i++;
            } else {
                addTagValues(entry.getValue(), entry.getKey(), false, false, null, null);
            }
        }
        if (! attribution.getCreator().isEmpty()) {
            addTagName(AttributionConstants.CREATOR);
        }
        for (Map.Entry<String, String> entry : attribution.getCreator().entrySet()) {
            addTagValues(entry.getValue(), entry.getKey(), false, false, null, null);
        }
        if (! attribution.getDate().isEmpty()) {
            addTagName(AttributionConstants.DATE);
        }
        for (Map.Entry<String, String> entry : attribution.getDate().entrySet()) {
            addTagValues(entry.getValue(), entry.getKey(), false, false, null, null);
        }
        if (! attribution.getProvider().isEmpty()) {
            addTagName(AttributionConstants.INSTITUTION);
        }
        for (Map.Entry<String, String> entry : attribution.getProvider().entrySet()) {
            addTagValues(entry.getValue(), entry.getKey(), false, true, attribution.getProviderUrl(), null);
        }
        if (! attribution.getCountry().isEmpty()) {
            addTagName(AttributionConstants.COUNTRY);
        }
        for (Map.Entry<String, String> entry : attribution.getCountry().entrySet()) {
            addTagValues(entry.getValue(), entry.getKey(), false, false, null, null);
        }
        if (! StringUtils.isEmpty(attribution.getRightsLabel())) {
            addTagName(AttributionConstants.RIGHTS);
            addTagValues(attribution.getRightsLabel(), null, true, true, attribution.getRightsStatement(), attribution.getRightsIcon());
        }
        htmlSnippet.append(AttributionConstants.HTML_CLOSE_TAG);
    }

    private void initializeHtmlSnippet(String htmlCssSource) {
        htmlSnippet.append(AttributionConstants.HTML_BEGIN_TAG);
        htmlSnippet.append(htmlCssSource);
        htmlSnippet.append(AttributionConstants.HTML_ATTRIBUTION_TAG);
    }

    //creates <dt>TYPE</dt>
    private void addTagName(String type) {
        if (StringUtils.isNotEmpty(type)) {
            htmlSnippet.append(AttributionConstants.TYPE_TAG_OPEN);
            htmlSnippet.append(type);
            htmlSnippet.append(AttributionConstants.TYPE_TAG_CLOSE);
        }
    }

    //creates <dd lang='lang'>tagValue</dd> and adds href and Icons
    private void addTagValues(String tagValue, String lang, boolean isRights, boolean isHref, String hrefType, String[] iconList) {
        if (StringUtils.isNotEmpty(tagValue)) {
            openValueTag(lang);
            // if href add that
            if (isHref && StringUtils.isNotEmpty(hrefType)) {
                addhref(hrefType, tagValue, isRights, iconList);
            } else {
                htmlSnippet.append(tagValue);
            }
            htmlSnippet.append(AttributionConstants.VALUE_TAG_CLOSE);
        }
    }

    //creates <a href="hrefTYPE" target="_blank" rel="noopener">hrefValue</a>
    private void addhref(String hrefType, String hrefValue, boolean isRights, String[] iconList) {
        htmlSnippet.append(AttributionConstants.BEGIN_HREF);
        htmlSnippet.append(hrefType);
        htmlSnippet.append(AttributionConstants.HREF);
        if (isRights && iconList != null) {
            addIcons(iconList);
        }
        htmlSnippet.append(hrefValue);
        htmlSnippet.append(AttributionConstants.CLOSE_HREF);
    }

    //creates <dd lang="lang">  OR <dd>
    private void openValueTag(String lang) {
        if (StringUtils.isNotEmpty(lang) && !StringUtils.equals(lang, AttributionConstants.DEF)) {
            htmlSnippet.append(AttributionConstants.LANG_TAG_OPEN);
            htmlSnippet.append(lang);
            htmlSnippet.append(AttributionConstants.LANG_TAG_CLOSE);
        } else {
            htmlSnippet.append(AttributionConstants.VALUE_TAG_OPEN);
        }
    }

    //creates <span class="europeana-icon-cc"/><span class="europeana-icon-by"/>
    private void addIcons(String[] iconsList) {
        if (iconsList != null) {
            for (String icon : iconsList) {
                htmlSnippet.append(AttributionConstants.SPAN_OPENING_TAG);
                htmlSnippet.append(icon);
                htmlSnippet.append(AttributionConstants.SPAN_CLOSING_TAG);
            }
        }
    }

    // flattens a language-aware Map<String, String> into a String, e.g. "(en) entry ; (de) eingabe"
    private StringBuilder flattenThisMap(Map<String, String> map) {
        StringBuilder retval = new StringBuilder();
        int i = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            retval.append((StringUtils.isNotBlank(entry.getKey()) ? "(" + entry.getKey() + ") " : ""));
            retval.append(entry.getValue() + (i < map.size() ? "; " : ""));
            i++;
        }
        return retval;
    }
}
