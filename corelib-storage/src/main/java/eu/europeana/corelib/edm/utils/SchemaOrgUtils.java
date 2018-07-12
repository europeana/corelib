package eu.europeana.corelib.edm.utils;

import com.google.schemaorg.JsonLdSerializer;
import com.google.schemaorg.JsonLdSyntaxException;
import com.google.schemaorg.core.CoreConstants;
import com.google.schemaorg.core.CoreFactory;
import com.google.schemaorg.core.CreativeWork;
import com.google.schemaorg.core.Thing;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.edm.model.schema.org.MultilingualString;
import eu.europeana.corelib.edm.model.schema.org.MultilingualStringWithReference;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaOrgUtils {
    private static final String DEFAULT_LANGUAGE = "def";

    private static final String URL_PREFIX = "http://data.europeana.eu";

    private static final Map<String, String> conceptTypes;
    private static final String LANGUAGE = "\"@language\"";
    private static final String VALUE = "\"@value\"";

    static {
        conceptTypes = new HashMap<>();
        conceptTypes.put("http://data.europeana.eu/concept/base/6", CoreConstants.TYPE_BOOK);
        conceptTypes.put("http://data.europeana.eu/concept/base/43", CoreConstants.TYPE_MAP);
        conceptTypes.put("http://data.europeana.eu/concept/base/47", CoreConstants.TYPE_PAINTING);
        conceptTypes.put("http://data.europeana.eu/concept/base/48", CoreConstants.TYPE_PHOTOGRAPH);
        conceptTypes.put("http://data.europeana.eu/concept/base/51", CoreConstants.TYPE_SCULPTURE);
        conceptTypes.put("http://data.europeana.eu/concept/base/190", CoreConstants.TYPE_VISUAL_ARTWORK);
        conceptTypes.put("http://data.europeana.eu/concept/base/18", "https://bib.schema.org/Newspaper");
    }

    private SchemaOrgUtils() { }

    /**
     * Convert full bean to schema.org jsonld
     * @param bean
     * @return
     */
    public static String toSchemaOrgJackson(FullBeanImpl bean) {
        String jsonld = null;
        eu.europeana.corelib.edm.model.schema.org.CreativeWork object = new eu.europeana.corelib.edm.model.schema.org.CreativeWork();
        setId(object, bean);

        for (Proxy proxy : bean.getProxies()) {
            // dc:title
            object.setName(prepareMultilingualStrings(proxy.getDcTitle()));
            // dc:publisher
            object.setPublisher(prepareMultilingualStringsWithReference(proxy.getDcPublisher()));
        }

        eu.europeana.corelib.edm.utils.JsonLdSerializer serializer = new eu.europeana.corelib.edm.utils.JsonLdSerializer();
        try {
            jsonld = serializer.serialize(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonld;
    }

    private static List<MultilingualStringWithReference> prepareMultilingualStringsWithReference(Map<String, List<String>> map) {
        List<MultilingualStringWithReference> values = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String value : entry.getValue()) {
                    if (!value.isEmpty()) {
                        MultilingualStringWithReference ls = new MultilingualStringWithReference();
                        if (entry.getKey().equalsIgnoreCase(DEFAULT_LANGUAGE)) {
                            ls.setLanguage("");
                        } else {
                            ls.setLanguage(entry.getKey());
                        }
                        ls.setValue(value);
                        values.add(ls);
                    }
                }
            }
        }
        return values;
    }

    private static List<MultilingualString> prepareMultilingualStrings(Map<String, List<String>> map) {
        List<MultilingualString> values = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (String value : entry.getValue()) {
                    if (!value.isEmpty()) {
                        MultilingualString ls = new MultilingualString();
                        if (entry.getKey().equalsIgnoreCase(DEFAULT_LANGUAGE)) {
                            ls.setLanguage("");
                        } else {
                            ls.setLanguage(entry.getKey());
                        }
                        ls.setValue(value);
                        values.add(ls);
                    }
                }
            }
        }
        return values;
    }

    /**
     * Convert full bean to schema.org jsonld
     * @param bean
     * @return
     */
    public static String toSchemaOrg(FullBeanImpl bean) {
        String jsonld = null;
        JsonLdSerializer serializer = new JsonLdSerializer(true /* setPrettyPrinting */);

        List<Thing> objects = new ArrayList<>();

        List<Thing> webResources = new ArrayList<>();

        Thing.Builder ob = createBuilder(bean);
        setId(ob, bean);
        // @type is filled automatically based on the interface

        // proxies
        addProxies(bean, (CreativeWork.Builder) ob);

//        // providedCHOs
//        addProvidedCHOs(bean, ob);
//
//        // aggregations
//        addAggregations(bean, webResources, ob);
//
//        // Europeana aggregation
//        addEuropeanaAggregation(bean, ob);
//
//        // Agents
//        addAgents(bean, ob);
//
//        // Places
//        addPlaces(bean, ob);
//
//        List<Thing> concepts = new ArrayList<>();
//        // Concept
//        addConcepts(bean, ob, concepts);
//
        objects.add(ob.build());
//        objects.addAll(webResources);
//        objects.addAll(concepts);
//
        try {
            jsonld = serializer.serialize(objects);
        } catch (JsonLdSyntaxException e) {
            e.printStackTrace();
        }
        return jsonld;
    }

    private static void setId(eu.europeana.corelib.edm.model.schema.org.CreativeWork ob, FullBeanImpl bean) {
        if (bean.getProvidedCHOs() != null &&
                !bean.getProvidedCHOs().isEmpty() &&
                bean.getProvidedCHOs().get(0) != null) {
            // @id
            ob.setId(URL_PREFIX + bean.getProvidedCHOs().get(0).getAbout());
        }
    }

    private static void setId(Thing.Builder ob, FullBeanImpl bean) {
        if (bean.getProvidedCHOs() != null &&
            !bean.getProvidedCHOs().isEmpty() &&
            bean.getProvidedCHOs().get(0) != null) {
            // @id
            ob.setJsonLdId(URL_PREFIX + bean.getProvidedCHOs().get(0).getAbout());
        }
    }

    /**
     * Create builder based on the specific property. Currently it always returns CreativeWork.Builder
     * @param bean the bean containing specific properties on which the respective type should be chosen
     * @return specific schema.org type Builder
     */
    private static Thing.Builder createBuilder(FullBeanImpl bean) {
        return CoreFactory.newCreativeWorkBuilder();
    }

    private static void addProxies(FullBean bean, CreativeWork.Builder ob) {
        for (Proxy proxy : bean.getProxies()) {
            // dc:title
            addProperty(ob, proxy.getDcTitle(), CoreConstants.PROPERTY_NAME);
            // dc:description
            addProperty(ob, proxy.getDcDescription(), CoreConstants.PROPERTY_DESCRIPTION);
            // dc:creator
            addProperty(ob, proxy.getDcCreator(), CoreConstants.PROPERTY_CREATOR);
            // dc:contributor
            addProperty(ob, proxy.getDcContributor(), CoreConstants.PROPERTY_CONTRIBUTOR);
            // dc:coverage
            addProperty(ob, proxy.getDcCoverage(), CoreConstants.PROPERTY_ABOUT);
            // dc:language
            addProperty(ob, proxy.getDcLanguage(), CoreConstants.PROPERTY_IN_LANGUAGE);
            // dc:publisher
            addProperty(ob, proxy.getDcPublisher(), CoreConstants.PROPERTY_PUBLISHER);
            // dc:subject
            addProperty(ob, proxy.getDcSubject(), CoreConstants.PROPERTY_ABOUT);
            // dc:type
            addProperty(ob, proxy.getDcType(), CoreConstants.PROPERTY_ABOUT);
            // dcterms:alternative
            addProperty(ob, proxy.getDctermsAlternative(), CoreConstants.PROPERTY_ALTERNATE_NAME);
            // dcterms:created
            addProperty(ob, proxy.getDctermsCreated(), CoreConstants.PROPERTY_DATE_CREATED);
            // dcterms:hasPart
            addProperty(ob, proxy.getDctermsHasPart(), CoreConstants.PROPERTY_HAS_PART);
            // dcterms:isFormatOf
            addProperty(ob, proxy.getDctermsIsFormatOf(), CoreConstants.PROPERTY_EXAMPLE_OF_WORK);
            // dcterms:isPartOf
            addProperty(ob, proxy.getDctermsIsPartOf(), CoreConstants.PROPERTY_IS_PART_OF);
            // dcterms:issued
            addProperty(ob, proxy.getDctermsIssued(), CoreConstants.PROPERTY_DATE_PUBLISHED);
            // dcterms:references
            addProperty(ob, proxy.getDctermsReferences(), CoreConstants.PROPERTY_MENTIONS);
            // dcterms:spatial - cannot map to spatialCoverage
            // dcterms:temporal
            addProperty(ob, proxy.getDctermsTemporal(), CoreConstants.PROPERTY_ABOUT);
            // edm:hasType
            addProperty(ob, proxy.getEdmHasType(), CoreConstants.PROPERTY_ABOUT);
            // edm:incorporates
            addProperty(ob, proxy.getEdmIncorporates(), CoreConstants.PROPERTY_HAS_PART);
            // edm:isDerivativeOf
            addProperty(ob, proxy.getEdmIsDerivativeOf(), CoreConstants.PROPERTY_IS_BASED_ON_URL);
            // edm:isNextInSequence
            addProperty(ob, proxy.getEdmIsNextInSequence(), CoreConstants.PROPERTY_PREVIOUS_ITEM);
            // edm:isRepresentationOf
            addProperty(ob, proxy.getEdmIsRepresentationOf(), CoreConstants.PROPERTY_ABOUT);
            // edm:isSuccessorOf
            addProperty(ob, proxy.getEdmIsSuccessorOf(), CoreConstants.PROPERTY_PREVIOUS_ITEM);
            // edm:realizes
            addProperty(ob, proxy.getEdmRealizes(), CoreConstants.PROPERTY_EXAMPLE_OF_WORK);
            // edm:year
            addProperty(ob, proxy.getYear(), CoreConstants.PROPERTY_DATE_CREATED);
        }
    }

    private static void addProperty(Thing.Builder ob, Map<String, List<String>> map, String property) {
        if (map != null) {
            if (map.size() == 1 && map.keySet().contains(DEFAULT_LANGUAGE)) {
                // there is only one list of values in a default language, add them as not multilingual
                addProperty(ob, map.values().iterator().next(), property);
            } else if (map.size() > 0) {
                //ob.addProperty(property, getMultilingualValues(map));
                ob.setJsonLdId("");
                ob.addProperty(property, CoreFactory.newThingBuilder().addName("ABC"));
                ob.addProperty(property, CoreFactory.newThingBuilder().addName("XYZ"));
            }
        }
    }

    private static String getMultilingualValues(Map<String, List<String>> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                if (!value.isEmpty()) {
                    sb.append("{ ").append(LANGUAGE).append(": \"").append(entry.getKey());
                    sb.append("\", ").append(VALUE).append(": \"").append(value).append("\"},");
                }
            }
        }
        if (sb.lastIndexOf(",") == sb.length() - 1) {
            sb.replace(sb.length() - 1, sb.length(), "]");
        }
        return sb.toString();
    }

    private static void addProperty(Thing.Builder ob, List<String> values, String property) {
        if (values != null) {
            for (String value : values) {
                if (!value.isEmpty()) {
                    ob.addProperty(property, value);
                }
            }
        }
    }

    private static void addProperty(Thing.Builder ob, String[] values, String property) {
        if (values != null) {
            for (String value : values) {
                if (!value.isEmpty()) {
                    ob.addProperty(property, value);
                }
            }
        }
    }

    private static void addProperty(Thing.Builder ob, String value, String property) {
        if (value != null && !value.isEmpty()) {
            ob.addProperty(property, value);
        }
    }

    private static void addProperty(Thing.Builder ob, Long value, String property) {
        if (value != null) {
            ob.addProperty(property, String.valueOf(value));
        }
    }

    private static void addProperty(Thing.Builder ob, Integer value, String property) {
        if (value != null) {
            ob.addProperty(property, String.valueOf(value));
        }
    }
}
