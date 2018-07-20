package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.edm.model.schema.org.SchemaOrgConstants;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SchemaOrgUtils {

    private static final String URL_PREFIX = "http://data.europeana.eu";

    private static final Map<String, String> conceptTypes;

    static {
        conceptTypes = new HashMap<>();
        conceptTypes.put("http://data.europeana.eu/concept/base/6", SchemaOrgConstants.TYPE_BOOK);
        conceptTypes.put("http://data.europeana.eu/concept/base/43", SchemaOrgConstants.TYPE_MAP);
        conceptTypes.put("http://data.europeana.eu/concept/base/47", SchemaOrgConstants.TYPE_PAINTING);
        conceptTypes.put("http://data.europeana.eu/concept/base/48", SchemaOrgConstants.TYPE_PHOTOGRAPH);
        conceptTypes.put("http://data.europeana.eu/concept/base/51", SchemaOrgConstants.TYPE_SCULPTURE);
        conceptTypes.put("http://data.europeana.eu/concept/base/190", SchemaOrgConstants.TYPE_VISUAL_ARTWORK);
        conceptTypes.put("http://data.europeana.eu/concept/base/18", SchemaOrgConstants.TYPE_NEWSPAPER);
    }

    private SchemaOrgUtils() { }

    /**
     * Convert full bean to schema.org jsonld
     * @param bean
     * @return
     */
    public static String toSchemaOrg(FullBeanImpl bean) {
        String jsonld = null;

        eu.europeana.corelib.edm.model.schema.org.Thing object = new eu.europeana.corelib.edm.model.schema.org.Thing();
        setId(object, bean);

        eu.europeana.corelib.edm.utils.JsonLdSerializer serializer = new eu.europeana.corelib.edm.utils.JsonLdSerializer();
        try {
            jsonld = serializer.serialize(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonld;
    }

    private static void setId(eu.europeana.corelib.edm.model.schema.org.Thing ob, FullBeanImpl bean) {
        if (bean.getProvidedCHOs() != null &&
                !bean.getProvidedCHOs().isEmpty() &&
                bean.getProvidedCHOs().get(0) != null) {
            // @id
            ob.setId(URL_PREFIX + bean.getProvidedCHOs().get(0).getAbout());
        }
    }
}
