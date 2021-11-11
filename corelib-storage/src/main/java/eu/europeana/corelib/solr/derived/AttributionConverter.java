package eu.europeana.corelib.solr.derived;

import eu.europeana.corelib.definitions.edm.entity.*;
import eu.europeana.corelib.definitions.model.RightsOption;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.utils.ComparatorUtils;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttributionConverter{

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
        attribution.setItemUri(
                AttributionConstants.BASE_URL + ((AggregationImpl) wRes.getParentAggregation()).getParentBean()
                                                                                               .getAbout());
    }

    private void processTCD(Attribution attribution, WebResourceImpl wRes) {
        //map to store the creator values and process it
        Map<String, List<String>> creatorMap = new HashMap<>();
        // get the creator first from web resource level
        if (isNotBlank(wRes.getDcCreator())) {
            creatorMap.putAll(createCreatorTitleMap(wRes.getDcCreator()));
        }
        // if still empty get, title, creator and date from proxy
        checkProxy(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getProxies(), attribution,
                   creatorMap);
        // if there was no title found in the proxy, get it from the record object itself
        if (attribution.getTitle().isEmpty() && ArrayUtils.isNotEmpty(
                ((AggregationImpl) wRes.getParentAggregation()).getParentBean().getTitle())) {
            attribution.getTitle().put("", collectListInLines(Arrays.asList(
                    stripEmptyStrings(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getTitle()))));
        }
        //check europeana aggregation if TCD is still empty
        checkEuropeanaAggregration(attribution, ((AggregationImpl) wRes.getParentAggregation()).getParentBean()
                                                                                               .getEuropeanaAggregation(),
                                   creatorMap);
        // check for URI, labels and remove duplicate for creator
        attribution.setCreator(checkLabelInMaps(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getAgents(),
                   creatorMap, null));
    }

    private void checkProxy(List<? extends Proxy> proxies, Attribution attribution,
                            Map<String, List<String>> creatorMap) {
        if (! proxies.isEmpty()) {
            for (Proxy proxy : proxies) {
                if (creatorMap.isEmpty() && isNotBlank(proxy.getDcCreator())) {
                    creatorMap.putAll(createCreatorTitleMap(proxy.getDcCreator()));
                }
                if (attribution.getTitle().isEmpty() && isNotBlank(proxy.getDcTitle())) {
                    attribution.setTitle(concatLangAwareMap(createCreatorTitleMap(proxy.getDcTitle()), true));
                }
                if (attribution.getDate().isEmpty() && isNotBlank(proxy.getYear())) {
                    attribution.setDate(concatLangAwareMap(proxy.getYear(), true));
                }
            }
        }
    }

    private void checkEuropeanaAggregration(Attribution attribution, EuropeanaAggregation euAgg,
                                            Map<String, List<String>> creatorMap) {
        if (euAgg != null) {
            // get EuropeanaAggregation's edm:landingPage
            attribution.setLandingPage(euAgg.getEdmLandingPage());
            // if creatorMap is empty still, check on the Europeana aggregation
            if (creatorMap.isEmpty() && isNotBlank(euAgg.getDcCreator())) {
                creatorMap.putAll(createCreatorTitleMap(euAgg.getDcCreator()));
            } // ditto, if rights is still empty
            if (StringUtils.isEmpty(attribution.getRightsStatement()) && isNotBlank(euAgg.getEdmRights())) {
                attribution.setRightsStatement(squeezeMap(euAgg.getEdmRights()));
            }
        }
    }

    private void processProvider(Attribution attribution, WebResourceImpl wRes) {
        //provider : edmDataProvider
        if (isNotBlank(wRes.getParentAggregation().getEdmDataProvider())) {
            // get edmLanguage, and only pick first one
            List<String> edmLanguage = getEdmLanguage(wRes);
            String edmLang = edmLanguage.isEmpty() ? null : edmLanguage.get(0);
            // get the edmProvider Map
            Map<String, List<String>> providerMap = concatLangAwareMap(wRes.getParentAggregation().getEdmDataProvider(), false);
            // check the map for uri's and their labels
            attribution.setProvider(checkLabelInMaps(((AggregationImpl) wRes.getParentAggregation()).getParentBean().getOrganizations(),
                    providerMap ,edmLang));
        }
        //providerUrl : edmShownAt
        attribution.setProviderUrl(wRes.getParentAggregation().getEdmIsShownAt());
    }

    /**
     * Returns the language list of the edm:languages
     * @param wRes
     * @return
     */
    public List<String> getEdmLanguage(WebResourceImpl wRes) {
        List<String> lang = new ArrayList<>();
        Map<String,List<String>> edmLanguage = ((AggregationImpl) wRes.getParentAggregation()).getParentBean().getEuropeanaAggregation().getEdmLanguage();
        for (Map.Entry<String, List<String>> entry : edmLanguage.entrySet()) {
            for (String languageAbbreviation : entry.getValue()) {
                lang.add(languageAbbreviation);
            }
        }
        return lang;
    }

    /**
     * Returns the final map with preflabels for uri and non-uri values.
     * Checks the map for uri values and adds theirs labels from entity.
     * Adds the NonURI values too
     * @param entities
     * @param initialMap
     * @param edmLang
     * @return
     */
    public Map<String, String> checkLabelInMaps(List<? extends ContextualClass> entities, Map<String, List<String>> initialMap, String edmLang) {
        Map<String, List<String>> finalMap = new HashMap<>();
        if (!initialMap.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : initialMap.entrySet()) {
                // add all the labels and non uri values in the list for the each key
                List<String> labelsAndNonUriValues = new ArrayList<>();
                for (String value : entry.getValue()) {
                    getLabelsAndNonUriValues(entities, value, labelsAndNonUriValues, edmLang);
                }
                //remove any duplicates
                ComparatorUtils.removeDuplicates(labelsAndNonUriValues);
                finalMap.put(entry.getKey(), labelsAndNonUriValues);
            }
        }
        return concatLangAwareMap(finalMap, true);
    }

    /**
     * creates a list of values including labels for URI values and non URI values.
     * @param entities Entities to check for labels
     * @param valueToCheck value to be checked if uri or non uri
     * @param finalValues Final list of values. Labels and non-uri values are added in this list
     * @param edmLang edm language
     */
    private void getLabelsAndNonUriValues(List<? extends ContextualClass> entities, String valueToCheck, List<String> finalValues, String edmLang) {
        if (EuropeanaUriUtils.isUri(valueToCheck)) {
            if (!entities.isEmpty()) {
                for (ContextualClass entity : entities) {
                    if (StringUtils.equals(valueToCheck, entity.getAbout())) {
                        finalValues.addAll(getLabelsFromEntity(entity, edmLang));
                    }
                }
            }
        } else {
            finalValues.add(valueToCheck);
        }
    }

    /**
     * get the prefLabel from Entity.
     * Now if prefLabel are empty, then the method will check the altLabels as well
     *
     * @param entity
     * @return
     */
    private List<String> getLabelsFromEntity(ContextualClass entity, String edmLang) {
        List<String> labels = new ArrayList<>();
        if (entity.getPrefLabel() != null) {
            labels = getPrefLabel(entity, edmLang);
        } else if (entity.getAltLabel() != null) {
            labels = getAltLabelEnOrFirstAvailable(entity);
        }
        return labels;
    }

    /**
     * Picks the prefLabel from the entity.
     *  1. if prefLabel size ==1 , pick that one
     *  2. Otherwise, pick the edmLang one
     *  3. Otherwise, Find the 'en' label
     *  4. Otherwise, pick the first 'lang' available
     * NOTE : ideally there should be only one value present in the lang List.
     *        But there are cases with multiple values. It should pick only one
     * For #Creator edmLang is null.
     * @param entity
     * @param edmLang
     * @return
     */
    private List<String> getPrefLabel(ContextualClass entity, String edmLang) {
        List<String> labels = new ArrayList<>();
        // 1. if only one prefLabel, pick that one.
        if (entity.getPrefLabel().size() == 1) {
            Map.Entry<String, List<String>> entry = entity.getPrefLabel().entrySet().iterator().next();
            labels.add(entry.getValue().get(0));
        }
        // 2. pick the one that matches edmLanguage
        else if (edmLang != null && entity.getPrefLabel().get(edmLang) != null) {
            labels.add(String.valueOf(stripEmptyStrings(entity.getPrefLabel().get(edmLang)).get(0)));
        }
        // 3. Find 'en' one and pick the first value
        else if (entity.getPrefLabel().get(AttributionConstants.EN) != null) {
            List<String> enList = stripEmptyStrings(entity.getPrefLabel().get(AttributionConstants.EN));
            labels.add(enList.get(0));
        } else {
            //other first one available
            for (Map.Entry<String, List<String>> langMap : entity.getPrefLabel().entrySet()) {
                List<String> langList = stripEmptyStrings(langMap.getValue());
                labels.add(langList.get(0));
                if (labels.size() == 1) {
                    break;  // conditional break
                }
            }
        }
        return labels;
    }

    /**
     * Picks the altLabel from the entity.
     *  1. Find the 'en' label and pick the first value
     *  2. Otherwise pick the first 'lang' available
     * @param entity
     * @return
     */
    private List<String> getAltLabelEnOrFirstAvailable(ContextualClass entity) {
        List<String> labels = new ArrayList<>();
        if (entity.getAltLabel().get(AttributionConstants.EN) != null) {
            List<String> enList = stripEmptyStrings(entity.getAltLabel().get(AttributionConstants.EN));
            //ideally there should be only one value present. But there are cases with multiple values. It should should pick only one
            labels.add(enList.get(0));
        } else {
            //other first one available
            for (Map.Entry<String, List<String>> langMap : entity.getAltLabel().entrySet()) {
                List<String> langList = stripEmptyStrings(langMap.getValue());
                //ideally there should be only one value present. But there are cases with multiple values. It should should pick only one
                labels.add(langList.get(0));
                if (labels.size() == 1) {
                    break;  // conditional break
                }
            }
        }
        return labels;
    }

    private void processCountry(Attribution attribution, WebResourceImpl wRes) {
        //country :edmCountry
        EuropeanaAggregation euAgg = ((AggregationImpl) wRes.getParentAggregation()).getParentBean()
                                                                                    .getEuropeanaAggregation();
        if (euAgg != null && attribution.getCountry().isEmpty() && isNotBlank(euAgg.getEdmCountry())) {
            attribution.getCountry().putAll(concatLangAwareMap(euAgg.getEdmCountry(), true));
        }
    }

    private void processRights(Attribution attribution, WebResourceImpl wRes) {
        //rightsStatement : first check webresource:edmRights
        if (isNotBlank(wRes.getWebResourceEdmRights())) {
            attribution.setRightsStatement(squeezeMap(wRes.getWebResourceEdmRights()));
        }
        // If rightsStatement is still empty, check on the aggregation
        if (StringUtils.isEmpty(attribution.getRightsStatement()) && isNotBlank(
                wRes.getParentAggregation().getEdmRights())) {
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
            LogManager.getLogger(AttributionConverter.class).warn("Unable to find label for rights URL {}",
                                                                  attribution.getRightsStatement());
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
        return map != null && ! map.isEmpty();
    }

    // daisy-chains Strings in a List, separated with ;
    private String collectListInLines(List<String> list) {
        StringBuilder value = new StringBuilder();
        int           i     = 1;
        for (String langString : list) {
            value.append(cleanUp(langString)).append(i < list.size() ? "; " : "");
            i++;
        }
        return value.toString();
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
    private List<String> stripEmptyStrings(List<String> swissCheese) {
        swissCheese.removeAll(Arrays.asList("", null));
        return swissCheese;
    }


    /**
     * Returns the lang Aware map.
     * if concat true : takes a language-aware Map<String, List<String>> and daisy-chains the Strings in the
     *                   value List<String>, separated with ; . It also strips out 'def' language tags.
     * if concat false : returns the lang aware map
     * @param bulkyMap
     * @return
     */
    private Map  concatLangAwareMap(Map<String, List<String>> bulkyMap, boolean concat) {
        Map<String, List<String>> langAwareMap = new HashMap<>();
        // get the desired map
        if (bulkyMap.get(AttributionConstants.DEF) != null) {
            langAwareMap.put("", stripEmptyStrings(bulkyMap.get(AttributionConstants.DEF)));
        } else {
            for (Map.Entry<String, List<String>> langMap : bulkyMap.entrySet()) {
                langAwareMap.put(langMap.getKey(), stripEmptyStrings(langMap.getValue()));
            }
        }
        // check if concatenation required
        if (concat) {
            return concatMap(langAwareMap);
        }
        return langAwareMap;
    }

    /**
     * takes a language-aware Map<String, List<String>> and daisy-chains the Strings in the
     * value List<String>, separated with ;
     * @param langAwareMap
     * @return
     */
    private Map<String, String> concatMap(Map<String, List<String>> langAwareMap) {
        Map<String, String> flatMap = new HashMap<>();
        for (Map.Entry<String, List<String>> langMap : langAwareMap.entrySet()) {
            flatMap.put(langMap.getKey(), collectListInLines(langMap.getValue()));
        }
        return flatMap;
    }

    // saves the creator and Title values in a language aware map
    protected Map<String, List<String>> createCreatorTitleMap(Map<String, List<String>> bulkyMap) {
        Map<String, List<String>> creatorMap = new HashMap<>();
        if (bulkyMap.get(AttributionConstants.DEF) != null) {
            List<String> defList = stripEmptyStrings(bulkyMap.get(AttributionConstants.DEF));
            ComparatorUtils.removeDuplicates(defList);
            creatorMap.put("", defList);
        }
        if (bulkyMap.get(AttributionConstants.EN) != null) {
            List<String> enList = stripEmptyStrings(bulkyMap.get(AttributionConstants.EN));
            getFinalMap(creatorMap, enList, AttributionConstants.EN);
        } else {
                getOtherLanguageMap(bulkyMap, creatorMap);
         }
        return creatorMap;
    }

    // checks for languages staring with "en", if not present look for other language.
    private Map<String, List<String>> getOtherLanguageMap (Map<String, List<String>> bulkyMap,  Map<String, List<String>> creatorMap) {
        boolean langValues = false;
        //check for any language which starts with "en" like "en-GB"
        for (Map.Entry<String, List<String>> langMap : bulkyMap.entrySet()) {
            if (StringUtils.startsWith(langMap.getKey(), AttributionConstants.EN)) {
                List<String> langList = stripEmptyStrings(langMap.getValue());
                getFinalMap(creatorMap, langList, langMap.getKey());
                langValues = true;
                    break;
          }
        }
        if (! langValues) {
            for (Map.Entry<String, List<String>> langMap : bulkyMap.entrySet()) {
                if (! StringUtils.equals(langMap.getKey(), AttributionConstants.DEF)) {
                    List<String> langList = stripEmptyStrings(langMap.getValue());
                    getFinalMap(creatorMap, langList, langMap.getKey());
                    break; // pick only one doesn't matter which one
               }
            }
        }
        return creatorMap;
    }

    //removes duplicates across the list and across creatorMap existing values
    private static Map<String, List<String>> getFinalMap(Map<String, List<String>> creatorMap, List<String> langList, String key) {
        ComparatorUtils.removeDuplicates(langList);
        if (! removeDuplicatesFromOneList(creatorMap, langList).isEmpty()) {
            creatorMap.put(key, langList);
        }
        return creatorMap;
    }

    //removes Duplicates from one list by comparing with another
    private static List<String> removeDuplicatesFromOneList (Map<String, List<String>> map, List<String> listWithDuplicates) {
        for (Map.Entry<String, List<String>> creatorMap : map.entrySet()) {
            List<String> values= creatorMap.getValue();
            listWithDuplicates.removeAll(new HashSet(values));
        }
        return  listWithDuplicates;
    }

    // returns only the first non-empty value found in a Map<String, List<String>> (used for edm:rights)
    private String squeezeMap(Map<String, List<String>> map) {
        StringBuilder retval = new StringBuilder();
        for (Map.Entry<String, List<String>> entrySet : map.entrySet()) {
            List<String> entryList = stripEmptyStrings(entrySet.getValue());
            if (! entryList.isEmpty()) {
                retval.append(cleanUp(entryList.get(0)));
                break; // needed ernly wernce
            }
        }
        return retval.toString();
    }

    //	removes empty Strings from String arrays
    private String[] stripEmptyStrings(String[] swissCheese) {
        List<String> solidCheese = new ArrayList<>();
        for (String cheeseOrHole : swissCheese) {
            if (StringUtils.isNotBlank(cheeseOrHole)) {
                solidCheese.add(cheeseOrHole);
            }
        }
        return solidCheese.toArray(new String[0]);
    }
}
