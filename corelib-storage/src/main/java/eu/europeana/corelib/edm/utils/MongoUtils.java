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

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.storage.MongoServer;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Checks if a string is contained in a string array
     *
     * @param str1 A string array
     * @param str2 The string to be checked
     * @return true if it is contained false otherwise
     */
    public static boolean contains(String[] str1, String str2) {
        if (str1 != null) {
            for (String str : str1) {
                if (StringUtils.equals(str, str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that checks whether a specific key contains a specific value in a
     * Map
     *
     * @param map The map to search on
     * @param key The key field
     * @param val The value
     * @return true if the specified key has the value, false otherwise
     */
    public static boolean contains(Map<String, List<String>> map, String key, String val) {
        if (map.keySet().contains(key)) {
            for (String str : map.get(key)) {
                if (StringUtils.equals(str, val)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that deletes a class from the Mongo storage
     *
     * @param clazz       The class to delete
     * @param about       The about field
     * @param mongoServer The mongo server to use
     */
    public static void delete(Class<?> clazz, String about,
                              EdmMongoServer mongoServer) {
        mongoServer.getDatastore().delete(
                mongoServer.getDatastore().createQuery(clazz)
                        .filter("about", about));
    }

    /**
     * Method that updates an entity in MongoDB storage
     *
     * @param clazz       The class type of the object
     * @param about       The search field
     * @param mongoServer The server to be used
     * @param field       The field to update
     * @param value       The value
     */
    @SuppressWarnings("unchecked")
    public static <T> void update(Class<T> clazz, String about, MongoServer mongoServer, String field, Object value) {
        if (value != null) {
            Query<T> updateQuery = mongoServer.getDatastore()
                    .createQuery(clazz).field("about").equal(about);
            UpdateOperations<T> ops;

			/*
             * If the value is an ArrayList it must be converted to String Array
			 * unless it refers to Web Resources
			 */
            if (value instanceof List && !(StringUtils.equals("webResources", field))) {
                ops = mongoServer
                        .getDatastore()
                        .createUpdateOperations(clazz)
                        .set(field,
                                ((List<String>) value)
                                        .toArray(new String[((List<String>) value)
                                                .size()]));
            } else {
                ops = mongoServer.getDatastore().createUpdateOperations(clazz)
                        .set(field, value);
            }
            mongoServer.getDatastore().update(updateQuery, ops);
        }
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
     * Checks whether two maps contain exactly the same key,value combinations
     *
     * @param mapA
     * @param mapB
     * @return
     */
    public static boolean mapEquals(Map<String, List<String>> mapA,
                                    Map<String, List<String>> mapB) {
        if (mapA != null && mapB != null && mapA.keySet().equals(mapB.keySet())) {
            boolean equals = true;
            for (String keyA : mapA.keySet()) {
                List<String> listA = mapA.get(keyA);
                List<String> listB = mapB.get(keyA);
                if (listA != null && listB != null) {
                    Collections.sort(listA);
                    Collections.sort(listB);
                    equals = equals && listA.equals(listB);
                }
            }
            return equals;
        }
        return mapA == null && mapB == null;
    }

    /**
     * Checks whether two maps contain exactly the same key,value combinations
     *
     * @param mapA
     * @param mapB
     * @return
     */
    public static boolean mapRefEquals(Map<String, String> mapA,
                                       Map<String, String> mapB) {
        if (mapA != null && mapB != null && mapA.keySet().equals(mapB.keySet())) {
            for (String keyA : mapA.keySet()) {
                String strA = mapA.get(keyA);
                String strB = mapB.get(keyA);
                return StringUtils.equals(strA, strB);
            }
        }
        return false;
    }

    /**
     * Check if two arrays contain the same values
     *
     * @param arrA
     * @param arrB
     * @return
     */
    public static boolean arrayEquals(String[] arrA, String[] arrB) {
        if (arrA == null && arrB == null) {
            return true;
        } else if (arrA == null || arrB == null) {
            return false;
        }
        if (arrA.length == arrB.length) {
            List<String> listA = new ArrayList<>(Arrays.asList(arrA));
            List<String> listB = new ArrayList<>(Arrays.asList(arrB));
            Collections.sort(listA);
            Collections.sort(listB);
            return listA.equals(listB);
        }
        return false;
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
    public static <T extends ResourceOrLiteralType> Map<String, String> createResourceOrLiteralRefFromString(T obj) {
        Map<String, String> retMap = new HashMap<>();
        if (obj != null) {
            if (obj.getLang() != null
                    && StringUtils.isNotEmpty(obj.getLang().getLang())) {
                if (obj.getString() != null
                        && StringUtils.trimToNull(obj.getString()) != null) {
                    String val = StringUtils.trim(obj.getString());
                    retMap.put(obj.getLang().getLang(), val);
                }
                if (obj.getResource() != null) {
                    String val = obj.getResource().getResource();
                    retMap.put(obj.getLang().getLang(), val);
                }
            } else {
                if (obj.getString() != null
                        && StringUtils.trimToNull(obj.getString()) != null) {
                    String val = StringUtils.trim(obj.getString());
                    retMap.put("def", val);
                }
                if (obj.getResource() != null) {
                    String val = obj.getResource().getResource();
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

    public static <T> boolean updateMapRef(T saved, T updated, String updateField, UpdateOperations ops) throws MongoUpdateException {
        try {
            Method getter = saved.getClass().getMethod("get" + StringUtils.capitalize(updateField));
            Method setter = saved.getClass().getMethod("set" + StringUtils.capitalize(updateField), Map.class);
            Map<String, String> savedValues = (Map<String, String>) getter.invoke(saved);
            Map<String, String> updatedValues = (Map<String, String>) getter.invoke(updated);

            if (updatedValues != null) {
                if (savedValues == null || !MongoUtils.mapRefEquals(updatedValues, savedValues)) {
                    ops.set(updateField, updatedValues);
                    setter.invoke(saved, updatedValues);
                    return true;
                }
            } else {
                if (saved != null) {
                    ops.unset(updateField);
                    setter.invoke(saved, (Object) null);
                    return true;
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MongoUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new MongoUpdateException("Error doing " + ops + " on field " +updateField, ex);
        }
        return false;
    }

    public static <T> boolean updateMap(T saved, T updated, String updateField, UpdateOperations ops) throws MongoUpdateException {
        try {
            Method getter = saved.getClass().getMethod("get" + StringUtils.capitalize(updateField));
            Method setter = saved.getClass().getMethod("set" + StringUtils.capitalize(updateField), Map.class);
            Map<String, List<String>> savedValues = (Map<String, List<String>>) getter.invoke(saved);
            Map<String, List<String>> updatedValues = (Map<String, List<String>>) getter.invoke(updated);

            if (updatedValues != null) {
                if (savedValues == null || !MongoUtils.mapEquals(updatedValues, savedValues)) {
                    ops.set(updateField, updatedValues);
                    setter.invoke(saved, updatedValues);
                    return true;
                }
            } else {
                if (saved != null) {
                    ops.unset(updateField);
                    setter.invoke(saved, (Object) null);
                    return true;
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MongoUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new MongoUpdateException("Error doing " + ops + " on field " +updateField, ex);
        }
        return false;
    }

    public static <T> boolean updateArray(T saved, T updated, String updateField, UpdateOperations ops) throws MongoUpdateException {
        try {
            Method getter = saved.getClass().getMethod("get" + StringUtils.capitalize(updateField));
            Method setter = saved.getClass().getMethod("set" + StringUtils.capitalize(updateField), String[].class);
            String[] savedValues = (String[]) getter.invoke(saved);
            String[] updatedValues = (String[]) getter.invoke(updated);

            if (updatedValues != null) {
                if (savedValues == null || !MongoUtils.arrayEquals(updatedValues, savedValues)) {
                    for (int i = 0; i < updatedValues.length; i++) {
                        if (StringUtils.isNotBlank(updatedValues[i])) {
                            updatedValues[i] = updatedValues[i].trim();
                        }
                    }
                    ops.set(updateField, updatedValues);
                    setter.invoke(saved, new Object[]{updatedValues});
                    return true;
                }
            } else {
                if (saved != null) {
                    ops.unset(updateField);
                    setter.invoke(saved, (Object) null);
                    return true;
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MongoUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new MongoUpdateException("Error doing " + ops + " on field " +updateField, ex);
        }
        return false;
    }

    public static <T> boolean updateString(T saved, T updated, String updateField, UpdateOperations ops) throws MongoUpdateException {
        try {
            Method getter = saved.getClass().getMethod("get" + StringUtils.capitalize(updateField));
            Method setter = saved.getClass().getMethod("set" + StringUtils.capitalize(updateField), String.class);
            String savedValues = (String) getter.invoke(saved);
            String updatedValues = (String) getter.invoke(updated);

            if (updatedValues != null) {
                if (savedValues == null || !StringUtils.equals(updatedValues, savedValues)) {
                    ops.set(updateField, updatedValues.trim());
                    setter.invoke(saved, updatedValues.trim());
                    return true;
                }
            } else {
                if (saved != null) {
                    ops.unset(updateField);
                    setter.invoke(saved, (Object) null);
                    return true;
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MongoUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new MongoUpdateException("Error doing " + ops + " on field " +updateField, ex);
        }
        return false;
    }
}
