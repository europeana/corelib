package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.edm.model.schema.org.Thing;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SchemaOrgUtils {
    private static final Logger LOG = LogManager.getLogger(SchemaOrgUtils.class);

    private static final Logger LOG = LogManager.getLogger(SchemaOrgUtils.class);

    private static final String URL_PREFIX = "http://data.europeana.eu";

    private SchemaOrgUtils() { }

    /**
     * Convert full bean to schema.org jsonld
     * @param bean
     * @return
     */
    public static String toSchemaOrg(FullBeanImpl bean) {
        String jsonld = null;

        Thing object = SchemaOrgTypeFactory.createObject(bean);
        setId(object, bean);

        JsonLdSerializer serializer = new JsonLdSerializer();
        try {
            jsonld = serializer.serialize(object);
        } catch (IOException e) {
            LOG.error("Serialization to schema.org failed for " + object != null ? object.getId() : bean.getAbout(), e);
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
