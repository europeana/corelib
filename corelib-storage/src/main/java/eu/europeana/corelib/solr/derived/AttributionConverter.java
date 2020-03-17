package eu.europeana.corelib.solr.derived;

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.License;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.model.RightsOption;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttributionConverter {

    public Attribution createAttribution(WebResourceImpl wRes) {
        Attribution attribution = new Attribution();
        processItemURI(attribution, wRes);
        processTCD(attribution, wRes);
        processProvider(attribution, wRes);
        processCountry(attribution, wRes);
        processRights(attribution, wRes);
        return attribution;
    }

    private void processItemURI(Attribution attribution, WebResourceImpl wRes) {
        attribution.setItemUri(AttributionConstants.BASE_URL+((AggregationImpl) wRes.getParentAggregation()).getParentBean().getAbout());
    }

    private void processTCD(Attribution attribution, WebResourceImpl wRes) {
        //title, creator and date from proxy
        checkProxy(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getProxies(), attribution);
        // if there was no title found in the proxy, get it from the record object itself
        if (attribution.getTitle().size() == 0 && ArrayUtils.isNotEmpty(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getTitle())) {
            attribution.getTitle().put("", collectListInLines(Arrays.asList(stripEmptyStrings(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getTitle()))));
        }
        // if there was no creator found in the proxy, get it from webresource-level dc:creator
        if (attribution.getCreator().size() == 0 && isNotBlank(wRes.getDcCreator())) {
            attribution.getCreator().putAll(concatLangawareMap(wRes.getDcCreator()));
        }
        //check europeana aggregation if TCD is still empty
        checkEuropeanaAggregration(attribution, ((AggregationImpl) wRes.getParentAggregation()).getParentBean().getEuropeanaAggregation());
    }

    private void checkProxy(List<? extends Proxy> proxies, Attribution attribution) {
        if (!proxies.isEmpty()) {
            for (Proxy proxy : proxies) {
                if (attribution.getCreator().size() == 0 && isNotBlank(proxy.getDcCreator())) {
                    attribution.setCreator(concatLangawareMap(proxy.getDcCreator()));
                }
                if (attribution.getTitle().size() == 0 && isNotBlank(proxy.getDcTitle())) {
                    attribution.setTitle(concatLangawareMap(proxy.getDcTitle()));
                }
                if (attribution.getDate().size() == 0 && isNotBlank(proxy.getYear())) {
                    attribution.setDate(concatLangawareMap(proxy.getYear()));
                }
            }
        }
    }

    private void checkEuropeanaAggregration(Attribution attribution, EuropeanaAggregation euAgg) {
        if (euAgg != null) {
            // get EuropeanaAggregation's edm:landingPage
            attribution.setLandingPage(euAgg.getEdmLandingPage());
            // if creatorMap is empty still, check on the Europeana aggregation
            if (attribution.getCreator().size() == 0 && isNotBlank(euAgg.getDcCreator())) {
                attribution.getCreator().putAll(concatLangawareMap(euAgg.getDcCreator()));
            } // ditto, if rights is still empty
            if (StringUtils.isEmpty(attribution.getRightsStatement()) && isNotBlank(euAgg.getEdmRights())) {
                attribution.setRightsStatement(squeezeMap(euAgg.getEdmRights()));
            }
        }
    }

    private void processProvider(Attribution attribution, WebResourceImpl wRes) {
        //provider : edmDataProvider
        if (isNotBlank(wRes.getParentAggregation().getEdmDataProvider())) {
            attribution.setProvider(concatLangawareMap(wRes.getParentAggregation().getEdmDataProvider()));
        }
        //providerUrl : edmShownAt
        attribution.setProviderUrl(wRes.getParentAggregation().getEdmIsShownAt());
    }

    private void processCountry(Attribution attribution, WebResourceImpl wRes) {
        //country :
        if (((AggregationImpl) wRes.getParentAggregation()).getParentBean().getCountry() != null &&
                ((AggregationImpl) wRes.getParentAggregation()).getParentBean().getCountry().length != 0) {
            attribution.setCountry(collectListInLines(Arrays.asList(stripEmptyStrings(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getCountry()))));
        }
    }

    private void processRights(Attribution attribution, WebResourceImpl wRes) {
        //rightsStatement : first check webresource:edmRights
        if (isNotBlank(wRes.getWebResourceEdmRights())) {
            attribution.setRightsStatement(squeezeMap(wRes.getWebResourceEdmRights()));
        }
        // If rightsStatement is still empty, check on the aggregation
        if (StringUtils.isEmpty(attribution.getRightsStatement()) && isNotBlank(wRes.getParentAggregation().getEdmRights())) {
            attribution.setRightsStatement(squeezeMap(wRes.getParentAggregation().getEdmRights()));
        }
        processDeprecatedDate(attribution, wRes);
        //rightsLabel
        attribution.setRightsLabel(getRightsLabelAndIcon(attribution));
    }

    private void processDeprecatedDate(Attribution attribution, WebResourceImpl wRes) {
        // if the record has a copyright value of 'out of copyright - no commercial re-use', fetch end date
        if (StringUtils.containsIgnoreCase(attribution.getRightsStatement(), AttributionConstants.OUT_OF_COPYRIGHT)) {
            for (License license : ((AggregationImpl) wRes.getParentAggregation()).getParentBean().getLicenses()) {
                if (license.getCcDeprecatedOn() != null) {
                    DateFormat df = new SimpleDateFormat(AttributionConstants.DATE_FORMAT);
                    attribution.setCcDeprecatedOn(df.format(license.getCcDeprecatedOn()));
                    break;
                }
            }
        }
    }

    private String getRightsLabelAndIcon(Attribution attribution) {
        RightsOption option = RightsOption.getValueByUrl(attribution.getRightsStatement(), false);
        if (option != null) {
            attribution.setRightsIcon(getRightsIcon(option));
            if (option == RightsOption.EU_OOC_NC && StringUtils.isNotBlank(attribution.getCcDeprecatedOn())) {
                return option.getRightsText() + " until " + attribution.getCcDeprecatedOn();
            }
            return option.getRightsText();
        } else if (attribution.getRightsStatement() != null) {
            LogManager.getLogger(AttributionConverter.class).warn("Unable to find label for rights URL {}", attribution.getRightsStatement());
        }
        return null;
    }

    private static String[] getRightsIcon(RightsOption option) {
        String rightsIcon = option.getRightsIcon();
        if (StringUtils.isNotEmpty(rightsIcon)) {
            return rightsIcon.split(" ");
        }
        return new String[0];
    }

    // utility to check whether of not a Map is empty or null
    private boolean isNotBlank(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    // daisy-chains Strings in a List, separated with ;
    private String collectListInLines(List<String> list) {
        String value = "";
        int i = 1;
        for (String langString : list) {
            value += cleanUp(langString) + (i < list.size() ? "; " : "");
            i++;
        }
        return value;
    }

    // removes surrounding whitespaces plus a possible trailing period
    private String cleanUp(String input) {
        if (input.endsWith(".")) {
            return input.substring(0, input.length() - 1).trim();
        } else {
            return input.trim();
        }
    }

    //	removes "" and null elements from String Lists
    private List stripEmptyStrings(List swissCheese) {
        swissCheese.removeAll(Arrays.asList("", null));
        return swissCheese;
    }

    // takes a language-aware Map<String, List<String>> and and daisy-chains the Strings in the
    // value List<String>, separated with ; . It also strips out 'def' language tags.
    private Map concatLangawareMap(Map<String, List<String>> bulkyMap) {
        Map<String, String> flatMap = new HashMap<>();
        if (bulkyMap.get(AttributionConstants.DEF) != null) {
            List<String> defList = stripEmptyStrings(bulkyMap.get(AttributionConstants.DEF));
            flatMap.put("", collectListInLines(defList));
        } else {
            for (Map.Entry<String, List<String>> langMap : bulkyMap.entrySet()) {
                List<String> langList = stripEmptyStrings(langMap.getValue());
                flatMap.put(langMap.getKey(), collectListInLines(langList));
            }
        }
        return flatMap;
    }

    // returns only the first non-empty value found in a Map<String, List<String>> (used for edm:rights)
    private String squeezeMap(Map<String, List<String>> map) {
        String retval = "";
        for (Map.Entry<String, List<String>> entrySet : map.entrySet()) {
            List<String> entryList = stripEmptyStrings(entrySet.getValue());
            if (!entryList.isEmpty()) {
                retval += cleanUp(entryList.get(0));
                break; // needed ernly wernce
            }
        }
        return retval;
    }

    //	removes empty Strings from String arrays
    private String[] stripEmptyStrings(String[] swissCheese) {
        List<String> solidCheese = new ArrayList<>();
        for (String cheeseOrHole : swissCheese) {
            if (StringUtils.isNotBlank(cheeseOrHole)) {
                solidCheese.add(cheeseOrHole);
            }
        }
        return solidCheese.toArray(new String[solidCheese.size()]);
    }
}
