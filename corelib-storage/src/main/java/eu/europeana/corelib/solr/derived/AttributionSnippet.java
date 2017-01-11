package eu.europeana.corelib.solr.derived;

/*
 * Copyright 2007-2012 The Europeana Foundation
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

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.License;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for assembling and holding the data forming the (webresource-level) attribution snippet)
 * Created by luthien on 30/11/2015.
 */
public class AttributionSnippet {

    private String textSnippet = "";
    private String htmlSnippet = "";
    String landingPage = "", shownAt = "", rights = "", ccDeprecatedOn = "";
    Map <String, String> creatorMap      = new HashMap<>();
    Map <String, String> titleMap        = new HashMap<>();
    Map <String, String> dataProviderMap = new HashMap<>();



    public AttributionSnippet(WebResourceImpl wRes) {

        // webresource-level dc:creator & rights
        if (isNotBlank(wRes.getDcCreator())){
            creatorMap.putAll(concatLangawareMap(wRes.getDcCreator()));
        }
        // get Proxy via Webresource -> Aggregation -> Bean -> Proxy; check for dc:creator / dc:title data
        checkProxy(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getProxies());

        if (isNotBlank(wRes.getWebResourceEdmRights())){
            rights += squeezeMap(wRes.getWebResourceEdmRights());
        }
        // get the aggregation's edm:dataprovider
        if (isNotBlank(wRes.getParentAggregation().getEdmDataProvider())){
            dataProviderMap = concatLangawareMap(wRes.getParentAggregation().getEdmDataProvider());
        }
        // get the aggregation's shownAt link
        if (StringUtils.isNotBlank(wRes.getParentAggregation().getEdmIsShownAt())){
            shownAt = wRes.getParentAggregation().getEdmIsShownAt();
        }
        // If edm:rights is still empty, check on the aggregation
        if ("".equals(rights) && isNotBlank(wRes.getParentAggregation().getEdmRights())) {
            rights += squeezeMap(wRes.getParentAggregation().getEdmRights());
        }
        // get the EuropeanaAggregation via Webresource -> Aggregation -> Bean -> check for dc:creator & rights data
        checkEuropeanaAggregration(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getEuropeanaAggregation());

        // if there was no title found in the proxy, get it from the record object itself
        if (titleMap.size() == 0 && ArrayUtils.isNotEmpty(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getTitle())) {
            titleMap.put("", collectListLines(Arrays.asList(stripEmptyStrings(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getTitle()))));
        }
        // if the record has a copyright value of 'out of copyright - no commercial re-use', fetch end date
        if (StringUtils.containsIgnoreCase(rights, "out-of-copyright")){
            for (License license : ((AggregationImpl) wRes.getParentAggregation()).getParentBean().getLicenses()){
                if (license.getCcDeprecatedOn() != null){
//                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//                    In order to make things less confusing. Or maybe more.
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    ccDeprecatedOn = df.format(license.getCcDeprecatedOn());
                    break;
                }
            }
        }
        assembleTextSnippet();
        assembleHtmlSnippet();
    }

    public String getTextSnippet() {
        return textSnippet;
    }
    public String getHtmlSnippet() {
        return htmlSnippet;
    }


    private void assembleTextSnippet(){
        if (titleMap.size() > 0){
            textSnippet += flattenThisMap(titleMap);
            textSnippet += StringUtils.isNotBlank(landingPage) ? " - " : "";
        }
        textSnippet += landingPage;
        textSnippet += (titleMap.size() > 0) || StringUtils.isNotBlank(landingPage) ? ". " : "";
        if (creatorMap.size() > 0){
            textSnippet += flattenThisMap(creatorMap);
            textSnippet += ". ";
        }
        if (dataProviderMap.size() > 0) {
            textSnippet += flattenThisMap(dataProviderMap);
            textSnippet += StringUtils.isNotBlank(shownAt) ? " - " : "";
        }
        textSnippet += shownAt;
        textSnippet += (dataProviderMap.size() > 0 || StringUtils.isNotBlank(shownAt)) ? ". " : "";
        textSnippet += StringUtils.isNotBlank(rights) ? getRightsLabel(rights) + " - " + rights : ".";
    }

    private void assembleHtmlSnippet(){
        String rightsPage = "xhv:license http://www.europeana.eu/schemas/edm/rights";
        String resPdUsgGd = "http://www.europeana.eu/rights/pd-usage-guide/";
        String span = "</span>";
        if (titleMap.size() > 0){
            if (StringUtils.isNotBlank(landingPage)) {
                htmlSnippet += spannify("about", portal2Data(landingPage), "", "") + aHreficate(landingPage, "", "", "");
            }
            for (Map.Entry<String, String> titleEntry : titleMap.entrySet()){
                htmlSnippet += spannify("property", "dc:title", titleEntry.getKey(), "") + titleEntry.getValue() + span;
            }
            if (StringUtils.isNotBlank(landingPage)) {
                htmlSnippet += "</a>";
            }
            htmlSnippet += ". ";
        }
        if (creatorMap.size() > 0){
            for (Map.Entry<String, String> creatorEntry : creatorMap.entrySet()){
                htmlSnippet += spannify("property", "dc:creator", creatorEntry.getKey(), "") + creatorEntry.getValue() + ". " + span;
            }
        }
        if (dataProviderMap.size() > 0){
            for (Map.Entry<String, String> dataProviderEntry : dataProviderMap.entrySet()) {
                htmlSnippet += aHreficate(shownAt, dataProviderEntry.getValue(), dataProviderEntry.getKey(), "") + ". ";
            }
        }
        if (StringUtils.isNotBlank(rights)){
            htmlSnippet += aHreficate(rights, getRightsLabel(rights), "", rightsPage);
            htmlSnippet += spannify("rel", "cc:useGuidelines", "", resPdUsgGd) + "." + span;
        }
        if (StringUtils.isNotBlank(landingPage)) {
            htmlSnippet += span; // close opening <span about ...>
        }
    }

    private void checkProxy(List<? extends Proxy> prxs){
        if (!prxs.isEmpty()){
            for (Proxy prx : prxs) {
                if (creatorMap.size() == 0 && isNotBlank(prx.getDcCreator())){
                    creatorMap = concatLangawareMap(prx.getDcCreator());
                }
                if (titleMap.size() == 0 && isNotBlank(prx.getDcTitle())){
                    titleMap = concatLangawareMap(prx.getDcTitle());
                }
            }
        }
    }

    private void checkEuropeanaAggregration(EuropeanaAggregation euAgg){
        if (euAgg != null) {
            // get EuropeanaAggregation's edm:landingPage
            landingPage = "".equals(euAgg.getEdmLandingPage()) ? "" : euAgg.getEdmLandingPage();
            // if creatorMap is empty still, check on the Europeana aggregation
            if (creatorMap.size() == 0 && isNotBlank(euAgg.getDcCreator())) {
                creatorMap.putAll(concatLangawareMap(euAgg.getDcCreator()));
            } // ditto, if rights is still empty
            if ("".equals(rights) && isNotBlank(euAgg.getEdmRights())) {
                rights += squeezeMap(euAgg.getEdmRights());
            }
        }
    }

    private String getRightsLabel(String rightsURL) {
        String        rightsLabel   = "could not determine rights label";
        String        rightsPattern = "zero|mark|/by/|/by-sa/|/by-nd/|/by-nc/|/by-nc-sa/|/by-nc-nd/|" +
                "orphan|rr-p|rr-f|out-of-copyright|unknown|vocab/InC/|vocab/InC-OW-EU/|vocab/CNE/|" +
                "vocab/InC-EDU/|vocab/NoC-NC/|vocab/NoC-OKLR/";
        final Matcher m             = Pattern.compile(rightsPattern).matcher(rightsURL);
        if (m.find())
            switch (m.group()) {
                case "vocab/InC/": rightsLabel = "In Copyright"; break;
                case "vocab/InC-OW-EU/": rightsLabel = "In Copyright - EU Orphan Work"; break;
                case "vocab/CNE/": rightsLabel = "Copyright Not Evaluated"; break;
                case "vocab/InC-EDU/": rightsLabel = "In Copyright - Educational Use Permitted"; break;
                case "vocab/NoC-NC/": rightsLabel = "Public Domain"; break;
                case "vocab/NoC-OKLR/": rightsLabel = "Public Domain"; break;
                case "zero": rightsLabel = "Public Domain"; break;
                case "mark": rightsLabel = "Public Domain"; break;
                case "/by/": rightsLabel = "CC BY"; break;
                case "/by-sa/": rightsLabel = "CC BY-SA"; break;
                case "/by-nd/": rightsLabel = "CC BY-ND"; break;
                case "/by-nc/": rightsLabel = "CC BY-NC"; break;
                case "/by-nc-sa/": rightsLabel = "CC BY-NC-SA"; break;
                case "/by-nc-nd/": rightsLabel = "CC BY-NC-ND"; break;
                case "orphan": rightsLabel = "Orphan Work"; break;
                case "rr-p": rightsLabel = "Rights Reserved - Paid Access"; break;
                case "rr-f": rightsLabel = "Rights Reserved - Free Access"; break;
                case "out-of-copyright": rightsLabel = "Out of copyright - non commercial re-use" +
                        (StringUtils.isNotBlank(ccDeprecatedOn) ? " until " + ccDeprecatedOn : ""); break;
                case "unknown": rightsLabel = "Unknown"; break;
            }
        return rightsLabel;
    }

    // creates a <span spantype="spanning" [xml:lang="lang"]> tag
    private String spannify(String spanType, String spanning, String lang, String resource){
        return "<span " + spanType + "='" + spanning + "'"
                + (StringUtils.isNotBlank(lang) ? " xml:lang='" + lang + "'"  : "")
                + (StringUtils.isNotBlank(resource) ? " resource='" + resource + "'" : "")
                + ">";
    }

    // creates an <a href="url" [xml:lang="lang"] [rel="rel"]>text</a> tag
    // NOTE if text is empty, it does not close the tag (= on purpose)
    private String aHreficate(String url, String text, String lang, String rel){
        if (StringUtils.isBlank(url)) {
            return text;
        } else if (StringUtils.isBlank(text)) {
            return "<a"  + " href='" + url + "'>";
        } else {
            return "<a"  + " href='" + url + "'"
                    + (StringUtils.isNotBlank(lang) ? " xml:lang='" + lang + "'" : "")
                    + (StringUtils.isNotBlank(rel) ? " rel='" + rel + "'" : "")
                    + ">" + text + "</a>";
        }
    }

    // takes a language-aware Map<String, List<String>> and and daisy-chains the Strings in the
    // value List<String>, separated with ; . It also strips out 'def' language tags.
    private Map concatLangawareMap(Map<String, List<String>> bulkyMap){
        Map <String, String> flatMap = new HashMap<>();
        if (bulkyMap.get("def") != null) {
            List<String> defList = stripEmptyStrings(bulkyMap.get("def"));
            flatMap.put("", collectListLines(defList));
        } else {
            for (Map.Entry<String, List<String>> langMap : bulkyMap.entrySet()) {
                List<String> langList = stripEmptyStrings(langMap.getValue());
                flatMap.put(langMap.getKey(), collectListLines(langList));
            }
        }
        return flatMap;
    }

    // returns only the first non-empty value found in a Map<String, List<String>> (used for edm:rights)
    private String squeezeMap(Map<String, List<String>> map){
        String retval = "";
        for (Map.Entry<String, List<String>> entrySet : map.entrySet()) {
            List<String> entryList = stripEmptyStrings(entrySet.getValue());
            if (entryList != null && !entryList.isEmpty()){
                retval += cleanUp(entryList.get(0));
                break; // needed ernly wernce
            }
        }
        return retval;
    }

    // flattens a language-aware Map<String, String> into a String, e.g. "(en) entry ; (de) eingabe"
    private String flattenThisMap(Map<String, String> map){
        String retval = "";
        int i = 1;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            retval += (StringUtils.isNotBlank(entry.getKey()) ? "(" + entry.getKey() + ") " : "")
                    + entry.getValue() + (i < map.size() ? "; " : "");
            i++;
        }
        return retval;
    }

    // daisy-chains Strings in a List, separated with ;
    private String collectListLines(List<String> list){
        String value = "";
        int i = 1;
        for (String langString : list) {
            value += cleanUp(langString) + (i < list.size() ? "; " : "");
            i++;
        }
        return value;
    }

    // utility to check whether of not a Map is empty or null
    private boolean isNotBlank(Map<?, ?> map){
        return map != null && !map.isEmpty();
    }

    //	removes empty Strings from String arrays
    private String[] stripEmptyStrings(String[] swissCheese){
        List<String> solidCheese = new ArrayList<String>();
        for (String cheeseOrHole : swissCheese){
            if (!"".equals(cheeseOrHole)){
                solidCheese.add(cheeseOrHole);
            }
        }
        return solidCheese.toArray(new String[ solidCheese.size() ]);
    }

    //	removes "" and null elements from String Lists
    private List stripEmptyStrings(List swissCheese){
        swissCheese.removeAll(Arrays.asList("", null));
        return swissCheese;
    }

    // removes surrounding whitespaces plus a possible trailing period
    private String cleanUp(String input){
        if (input.endsWith(".")){
            return input.substring(0, input.length() - 1).trim();
        } else {
            return input.trim();
        }
    }

    private String portal2Data(String url){
        String retval = url.replaceAll("\\://europeana\\.eu/portal/record/", "://data.europeana.eu/item/");
        return retval.replaceAll("\\.html$", "");
    }
}
