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

    protected void assembleTextSnippet(Attribution attribution){
        if (attribution.getTitle().size() > 0){
            textSnippet.append(flattenThisMap(attribution.getTitle()));
            textSnippet.append(StringUtils.isNotBlank(attribution.getLandingPage()) ? " - " : "");
        }
        textSnippet.append(attribution.getLandingPage());
        textSnippet.append((attribution.getTitle().size() > 0) || StringUtils.isNotBlank(attribution.getLandingPage()) ? ". " : "");
        if (attribution.getCreator().size() > 0){
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
        for (Map.Entry<String, String> entry : attribution.getTitle().entrySet()){
            spannify(AttributionConstants.TITLE, entry.getValue(), entry.getKey(), false, true, attribution.getItemUri(), null);
        }
        for (Map.Entry<String, String> entry : attribution.getCreator().entrySet()){
            spannify(AttributionConstants.CREATOR, entry.getValue(), entry.getKey(), false, false, null, null);
        }
        for (Map.Entry<String, String> entry : attribution.getDate().entrySet()){
            spannify(AttributionConstants.DATE, entry.getValue(), entry.getKey(), false, false, null, null);
        }
        for (Map.Entry<String, String> entry : attribution.getProvider().entrySet()){
            spannify(AttributionConstants.INSTITUTION, entry.getValue(), entry.getKey(), false, true, attribution.getProviderUrl(), null);
        }
        for (Map.Entry<String, String> entry : attribution.getCountry().entrySet()) {
            spannify(AttributionConstants.COUNTRY, entry.getValue(), entry.getKey(), false, false, null, null);
        }
        spannify(AttributionConstants.RIGHTS, attribution.getRightsLabel(),null, true, true, attribution.getRightsStatement(), attribution.getRightsIcon());
        htmlSnippet.append(AttributionConstants.DIV_CLOSE_TAG);
    }

    private void initializeHtmlSnippet(String htmlCssSource) {
        htmlSnippet.append(AttributionConstants.HTML_BEGIN_TAG);
        htmlSnippet.append(htmlCssSource);
        htmlSnippet.append(AttributionConstants.HTML_ATTRIBUTION_TAG);
    }

    //creates <span class="field"><span class="fname">SPANTYPE</span><span class="fvalue" xml:lang = "en">value</span> and adds href and rightIcon based on Input
    private void  spannify(String spanType, String spanValue, String lang, boolean isRights, boolean isHref, String hrefType, String[] iconList){
        if(StringUtils.isNotEmpty(spanValue)) {
            htmlSnippet.append(AttributionConstants.SPAN_FIELD);
            htmlSnippet.append(AttributionConstants.SPAN_FNAME);
            htmlSnippet.append(spanType);
            htmlSnippet.append(AttributionConstants.COLON);
            htmlSnippet.append(AttributionConstants.SPAN_CLOSING_TAG);
            setSpanFvalue(lang);
            if(isRights && iconList != null) {
                addIconsList(iconList);
            }
            if(isHref && StringUtils.isNotEmpty(hrefType)){
                href(hrefType, spanValue);
                htmlSnippet.append(AttributionConstants.SPAN_CLOSING_TAG);
            }
            else {
                htmlSnippet.append(spanValue);
                htmlSnippet.append(AttributionConstants.SPAN_CLOSING_TAG);
            }
            htmlSnippet.append(AttributionConstants.SPAN_CLOSING_TAG);
        }
    }

    //creates <span class="fvalue" xml:lang = "en">value</span>
    private void setSpanFvalue(String lang){
        if(StringUtils.isNotEmpty(lang) && !StringUtils.equals(lang, AttributionConstants.DEF)) {
            htmlSnippet.append(AttributionConstants.SPAN_FVALUE_LANG_TAG);
            htmlSnippet.append(lang);
            htmlSnippet.append(AttributionConstants.SPAN_FVALUE_LANG_CLOSE_TAG);
        } else {
            htmlSnippet.append(AttributionConstants.SPAN_FVALUE);
        }
    }
    //creates <a href="hrefTYPE" target="_blank" rel="noopener">hrefValue</a>
    private void href(String hrefType, String hrefValue){
        htmlSnippet.append(AttributionConstants.BEGIN_HREF);
        htmlSnippet.append(hrefType);
        htmlSnippet.append(AttributionConstants.HREF);
        htmlSnippet.append(hrefValue);
        htmlSnippet.append(AttributionConstants.CLOSE_HREF);
    }

    //<ul class="rights-list"><li class="RIGHTS_ICON"></li><li class="RIGHTS_ICON"></li><li class="RIGHTS_ICON"></li></ul>
    private void addIconsList(String [] iconsList) {
        if(iconsList != null) {
            htmlSnippet.append(AttributionConstants.RIGHTS_LIST_BEGIN);
            for (String icon : iconsList) {
                htmlSnippet.append(AttributionConstants.ICON_LIST_OPEN_TAG);
                htmlSnippet.append(icon);
                htmlSnippet.append(AttributionConstants.ICON_LIST_CLOSE_TAG);
            }
            htmlSnippet.append(AttributionConstants.RIGHTS_LIST_CLOSE);
        }
    }

    // flattens a language-aware Map<String, String> into a String, e.g. "(en) entry ; (de) eingabe"
    private StringBuilder flattenThisMap(Map<String, String> map){
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
