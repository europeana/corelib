package eu.europeana.corelib.solr.derived;

import dev.morphia.annotations.Embedded;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Class for assembling and holding the data forming the (webresource-level) attribution snippet)
 * Created by luthien on 30/11/2015.
 */
@Embedded(useDiscriminator = false)
public class AttributionSnippet {

    private String textSnippet;
    private String htmlSnippet;

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
        final StringBuilder textSnippeBuilder = new StringBuilder();
        if (attribution.getTitle().size() > 0) {
            textSnippeBuilder.append(flattenThisMap(attribution.getTitle()));
        }
        if(StringUtils.isNotBlank(attribution.getLandingPage())) {
            textSnippeBuilder.append(" - ");
            textSnippeBuilder.append(attribution.getLandingPage());
        }
        textSnippeBuilder.append((attribution.getTitle().size() > 0) || StringUtils.isNotBlank(attribution.getLandingPage()) ? ". " : "");
        if (attribution.getCreator().size() > 0) {
            textSnippeBuilder.append(flattenThisMap(attribution.getCreator()));
            textSnippeBuilder.append(". ");
        }
        if (attribution.getProvider().size() > 0) {
            textSnippeBuilder.append(flattenThisMap(attribution.getProvider()));
        }
        if(StringUtils.isNotBlank(attribution.getProviderUrl())) {
            textSnippeBuilder.append(" - ");
            textSnippeBuilder.append(attribution.getProviderUrl());
        }
        textSnippeBuilder.append((attribution.getProvider().size() > 0 || StringUtils.isNotBlank(attribution.getProviderUrl())) ? ". " : "");
        textSnippeBuilder.append(StringUtils.isBlank(attribution.getRightsLabel()) ? AttributionConstants.CANNOT_DETERMINE_RIGHTS : attribution.getRightsLabel() + " - " + attribution.getRightsStatement());
        this.textSnippet = textSnippeBuilder.toString();
    }

    protected void assembleHtmlSnippet(Attribution attribution, String htmlCssSource) {
        final StringBuilder htmlSnippetBuilder = new StringBuilder();
        initializeHtmlSnippet(htmlSnippetBuilder, htmlCssSource);
        if (! attribution.getTitle().isEmpty()) {
            addTagName(htmlSnippetBuilder, AttributionConstants.TITLE);
        }
        int i = 1;
        for (Map.Entry<String, String> entry : attribution.getTitle().entrySet()) {
            // add the href for the first element only
            if (i == 1) {
                addTagValues(htmlSnippetBuilder, entry.getValue(), entry.getKey(), false, true, attribution.getItemUri(), null);
                i++;
            } else {
                addTagValues(htmlSnippetBuilder, entry.getValue(), entry.getKey(), false, false, null, null);
            }
        }
        if (! attribution.getCreator().isEmpty()) {
            addTagName(htmlSnippetBuilder, AttributionConstants.CREATOR);
        }
        for (Map.Entry<String, String> entry : attribution.getCreator().entrySet()) {
            addTagValues(htmlSnippetBuilder, entry.getValue(), entry.getKey(), false, false, null, null);
        }
        if (! attribution.getDate().isEmpty()) {
            addTagName(htmlSnippetBuilder, AttributionConstants.DATE);
        }
        for (Map.Entry<String, String> entry : attribution.getDate().entrySet()) {
            addTagValues(htmlSnippetBuilder, entry.getValue(), entry.getKey(), false, false, null, null);
        }
        if (! attribution.getProvider().isEmpty()) {
            addTagName(htmlSnippetBuilder, AttributionConstants.INSTITUTION);
        }
        for (Map.Entry<String, String> entry : attribution.getProvider().entrySet()) {
            addTagValues(htmlSnippetBuilder, entry.getValue(), entry.getKey(), false, true, attribution.getProviderUrl(), null);
        }
        if (! attribution.getCountry().isEmpty()) {
            addTagName(htmlSnippetBuilder, AttributionConstants.COUNTRY);
        }
        for (Map.Entry<String, String> entry : attribution.getCountry().entrySet()) {
            addTagValues(htmlSnippetBuilder, entry.getValue(), entry.getKey(), false, false, null, null);
        }
        if (! StringUtils.isEmpty(attribution.getRightsLabel())) {
            addTagName(htmlSnippetBuilder, AttributionConstants.RIGHTS);
            addTagValues(htmlSnippetBuilder, attribution.getRightsLabel(), null, true, true, attribution.getRightsStatement(), attribution.getRightsIcon());
        }
      htmlSnippetBuilder.append(AttributionConstants.HTML_CLOSE_TAG);
      this.htmlSnippet = htmlSnippetBuilder.toString();
    }

    private void initializeHtmlSnippet(StringBuilder htmlSnippetBuilder, String htmlCssSource) {
        htmlSnippetBuilder.append(AttributionConstants.HTML_BEGIN_TAG);
        htmlSnippetBuilder.append(htmlCssSource);
        htmlSnippetBuilder.append(AttributionConstants.HTML_ATTRIBUTION_TAG);
    }

    //creates <dt>TYPE</dt>
    private void addTagName(StringBuilder htmlSnippetBuilder, String type) {
        if (StringUtils.isNotEmpty(type)) {
          htmlSnippetBuilder.append(AttributionConstants.TYPE_TAG_OPEN);
          htmlSnippetBuilder.append(type);
          htmlSnippetBuilder.append(AttributionConstants.TYPE_TAG_CLOSE);
        }
    }

    //creates <dd lang='lang'>tagValue</dd> and adds href and Icons
    private void addTagValues(StringBuilder htmlSnippetBuilder, String tagValue,
        String lang, boolean isRights, boolean isHref, String hrefType, String[] iconList) {
        if (StringUtils.isNotEmpty(tagValue)) {
            openValueTag(htmlSnippetBuilder, lang);
            // if href add that
            if (isHref && StringUtils.isNotEmpty(hrefType)) {
                addhref(htmlSnippetBuilder, hrefType, tagValue, isRights, iconList);
            } else {
              htmlSnippetBuilder.append(tagValue);
            }
          htmlSnippetBuilder.append(AttributionConstants.VALUE_TAG_CLOSE);
        }
    }

    //creates <a href="hrefTYPE" target="_blank" rel="noopener">hrefValue</a>
    private void addhref(StringBuilder htmlSnippetBuilder, String hrefType,
        String hrefValue, boolean isRights, String[] iconList) {
      htmlSnippetBuilder.append(AttributionConstants.BEGIN_HREF);
      htmlSnippetBuilder.append(hrefType);
      htmlSnippetBuilder.append(AttributionConstants.HREF);
        if (isRights && iconList != null) {
            addIcons(htmlSnippetBuilder, iconList);
        }
      htmlSnippetBuilder.append(hrefValue);
      htmlSnippetBuilder.append(AttributionConstants.CLOSE_HREF);
    }

    //creates <dd lang="lang"> OR <dd>
    private void openValueTag(StringBuilder htmlSnippetBuilder, String lang) {
        if (lang == null) { // value will be null for rights tag
          htmlSnippetBuilder.append(AttributionConstants.VALUE_TAG_OPEN);
        } else {
          htmlSnippetBuilder.append(AttributionConstants.LANG_TAG_OPEN);
          htmlSnippetBuilder.append(lang);
          htmlSnippetBuilder.append(AttributionConstants.LANG_TAG_CLOSE);
        }
    }

    //creates <span class="europeana-icon-cc"/><span class="europeana-icon-by"/>
    private void addIcons(StringBuilder htmlSnippetBuilder, String[] iconsList) {
        if (iconsList != null) {
            for (String icon : iconsList) {
              htmlSnippetBuilder.append(AttributionConstants.SPAN_OPENING_TAG);
              htmlSnippetBuilder.append(icon);
              htmlSnippetBuilder.append(AttributionConstants.SPAN_CLOSING_TAG);
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
