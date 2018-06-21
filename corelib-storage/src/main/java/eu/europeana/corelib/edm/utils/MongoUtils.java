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
package eu.europeana.corelib.edm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;

/**
 * Class with util methods for Mongo objects
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public final class MongoUtils {

    private MongoUtils() {
        // Constructor must be private
    }

    /**
     * Method that converts a LiteralType.class object to a multilingual map of
     * strings
     *
     * @param obj The LiteralType object
     * @return A Map of strings. The keys are the languages and the values are
     * lists of strings for the corresponding language. If the object is
     * null, the method returns null. In case a language is missing the
     * def notation is used as key
     */
    public static <T extends LiteralType> Map<String, List<String>> createLiteralMapFromString(T obj) {
        Map<String, List<String>> retMap = new HashMap<>();
        if (obj != null) {
            if (obj.getLang() != null && StringUtils.isNotBlank(obj.getLang().getLang())) {
                List<String> val = new ArrayList<>();
                if (StringUtils.isNotBlank(obj.getString())) {
                    val.add(obj.getString());
                    retMap.put(obj.getLang().getLang(), val);
                }
            } else {
                List<String> val = new ArrayList<>();
                if (StringUtils.isNotBlank(obj.getString())) {
                    val.add(obj.getString());
                    retMap.put("def", val);
                }
            }
            return retMap.isEmpty() ? null : retMap;
        }

        return null;
    }

    /**
     * Method that converts a Enum object to a multilingual map of strings
     *
     * @param obj
     * @return
     */
    public static Map<String, List<String>> createLiteralMapFromString(String obj) {
        Map<String, List<String>> retMap = new HashMap<>();

        if (obj != null) {
            List<String> val = new ArrayList<>();
            if (StringUtils.isNotBlank(obj)) {
                val.add(obj);
                retMap.put("def", val);
            }
            return retMap.isEmpty() ? null : retMap;
        }

        return null;
    }

    /**
     * Method that converts a ResourceOrLiteralType.class object to a
     * multilingual map of strings
     *
     * @param obj The ResourceOrLiteralType object
     * @return A Map of strings. The keys are the languages and the values are
     * lists of strings for the corresponding language. If the object is
     * null, the method returns null. In case a language is missing the
     * def notation is used as key
     */
    public static <T extends ResourceOrLiteralType> Map<String, List<String>> createResourceOrLiteralMapFromString(T obj) {
        Map<String, List<String>> retMap = new HashMap<>();
        if (obj != null) {
            if (obj.getLang() != null
                    && StringUtils.isNotEmpty(obj.getLang().getLang())) {
                if (obj.getString() != null
                        && StringUtils.trimToNull(obj.getString()) != null) {
                    List<String> val = new ArrayList<>();
                    val.add(StringUtils.trim(obj.getString()));
                    retMap.put(obj.getLang().getLang(), val);
                }
                if (obj.getResource() != null) {
                    List<String> val = retMap.get(obj.getLang().getLang()) != null ? retMap
                            .get(obj.getLang().getLang())
                            : new ArrayList<>();

                    val.add(obj.getResource().getResource());

                    retMap.put(obj.getLang().getLang(), val);
                }
            } else {
                if (StringUtils.isNotBlank(StringUtils.trimToNull(obj
                        .getString()))) {
                    List<String> val = retMap.get("def") != null ? retMap
                            .get("def") : new ArrayList<>();
                    val.add(obj.getString());
                    retMap.put("def", val);
                }
                if (obj.getResource() != null
                        && StringUtils.isNotBlank(StringUtils.trimToNull(obj
                        .getResource().getResource()))) {
                    List<String> val = retMap.get("def") != null ? retMap
                            .get("def") : new ArrayList<>();
                    val.add(StringUtils.trim(obj.getResource().getResource()));
                    retMap.put("def", val);
                }
            }
            return retMap;
        }

        return null;
    }

    public static <T extends ResourceType> Map<String, List<String>> createResourceMapFromString(T obj) {
        Map<String, List<String>> retMap = new HashMap<>();
        if (obj != null) {

            if (StringUtils.isNotBlank(StringUtils.trimToNull(obj
                    .getResource()))) {
                List<String> val = retMap.get("def") != null ? retMap
                        .get("def") : new ArrayList<>();
                val.add(obj.getResource());
                retMap.put("def", val);
            }
            return retMap;

        }
        return null;
    }

    /**
     * Method that converts a LiteralType.class list to a multilingual map of
     * strings
     *
     * @param list The LiteralType list
     * @return A Map of strings. The keys are the languages and the values are
     * lists of strings for the corresponding language. If the object is
     * null, the method returns null. In case a language is missing the
     * def notation is used as key
     */
    public static <T extends LiteralType> Map<String, List<String>> createLiteralMapFromList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            Map<String, List<String>> retMap = new HashMap<>();
            for (T obj : list) {
                if (obj.getLang() != null
                        && StringUtils.isNotBlank(obj.getLang().getLang())) {
                    String lang = obj.getLang().getLang();
                    List<String> val = retMap.get(lang);
                    if (val == null) {
                        val = new ArrayList<>();
                    }
                    val.add(StringUtils.trim(obj.getString()));
                    retMap.put(lang, val);
                } else {
                    List<String> val = retMap.get("def");
                    if (val == null) {
                        val = new ArrayList<>();
                    }
                    if (StringUtils.isNotBlank(StringUtils.trimToNull(obj.getString()))) {
                        val.add(obj.getString());
                        retMap.put("def", val);
                    }
                }
            }
            return retMap.isEmpty() ? null : retMap;
        }
        return null;
    }

    public static <T extends ResourceType> Map<String, List<String>> createResourceMapFromList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            Map<String, List<String>> retMap = new HashMap<>();
            for (T obj : list) {

                List<String> val = retMap.get("def");
                if (val == null) {
                    val = new ArrayList<>();
                }
                if (StringUtils.isNotBlank(StringUtils.trimToNull(obj.getResource()))) {
                    val.add(obj.getResource());
                    retMap.put("def", val);
                }

            }
            return retMap.isEmpty() ? null : retMap;
        }
        return null;
    }

    /**
     * Method that converts a ResourceOrLiteralType.class list to a multilingual
     * map of strings
     *
     * @param list The ResourceOrLiteralType list
     * @return A Map of strings. The keys are the languages and the values are
     * lists of strings for the corresponding language. If the object is
     * null, the method returns null. In case a language is missing the
     * def notation is used as key
     */
    public static <T extends ResourceOrLiteralType> Map<String, List<String>> createResourceOrLiteralMapFromList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            Map<String, List<String>> retMap = new HashMap<>();
            for (T obj : list) {
                if (obj.getString() != null
                        && StringUtils.isNotBlank(obj.getString())) {
                    if (obj.getLang() != null
                            && StringUtils.isNotBlank(obj.getLang().getLang())) {
                        List<String> val = retMap
                                .get((obj.getLang().getLang()));
                        if (val == null) {
                            val = new ArrayList<>();

                        }
                        if (StringUtils.isNotBlank(StringUtils.trimToNull(obj.getString()))) {
                            val.add(obj.getString());
                            retMap.put(obj.getLang().getLang(), val);
                        }

                    } else {
                        List<String> val = retMap.get("def");
                        if (val == null) {
                            val = new ArrayList<>();
                        }
                        if (StringUtils.isNotBlank(StringUtils.trimToNull(obj.getString()))) {
                            val.add(obj.getString());
                            retMap.put("def", val);
                        }
                    }
                }
                if (obj.getResource() != null
                        && StringUtils.isNotBlank(obj.getResource()
                        .getResource())) {
                    if (obj.getLang() != null) {
                        String lang = obj.getLang().getLang();
                        List<String> val;
                        if (retMap.containsKey(lang)) {
                            val = retMap.get(lang);

                        } else {
                            val = new ArrayList<>();
                        }
                        if (StringUtils.isNotBlank(StringUtils.trimToNull(obj.getResource()
                                .getResource()))) {
                            val.add(obj.getResource().getResource());
                            retMap.put(lang, val);
                        }
                    } else {
                        List<String> val = retMap.get("def");
                        if (val == null) {
                            val = new ArrayList<>();
                        }
                        if (StringUtils.isNotBlank(StringUtils.trimToNull(obj.getResource()
                                .getResource()))) {
                            val.add(obj.getResource().getResource());
                            retMap.put("def", val);
                        }
                    }
                }
            }
            if (!retMap.isEmpty()) {
                return retMap;
            }
        }
        return null;
    }
}
