package eu.europeana.corelib.solr.bean.impl;

import eu.europeana.corelib.definitions.edm.beans.IdBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.definitions.edm.beans.IdBean
 */
public class IdBeanImpl implements IdBean {

    @Field("europeana_id")
    protected String id;

    @Field("timestamp_created")
    protected Date timestampCreated;

    @Field("timestamp_update")
    protected Date timestampUpdated;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Date getTimestampCreated() {
        return this.timestampCreated;
    }

    @Override
    public Date getTimestampUpdated() {
        return this.timestampUpdated;
    }

    private static Map<Class<? extends IdBeanImpl>, String> fieldMap = new HashMap<>(3);

    @SuppressWarnings("unchecked")
    public static String getFields(Class<? extends IdBeanImpl> clazz) {
        if (fieldMap.containsKey(clazz)) {
            return fieldMap.get(clazz);
        }
        java.lang.reflect.Field[] imp = clazz.getDeclaredFields();

        List<String> fields = new ArrayList<>();
        for (java.lang.reflect.Field ann : imp) {

            Annotation annotation = ann.getAnnotation(Field.class);
            if (annotation != null) {
                fields.add(((Field) annotation).value());
            }
        }
        String result = StringUtils.join(fields, " ");

        if ((clazz.getSuperclass() != null) && clazz.getSuperclass().getSimpleName().contains("BeanImpl")) {
            result = getFields((Class<? extends IdBeanImpl>) clazz.getSuperclass()) + " " + result;
        }
        fieldMap.put(clazz, result);
        return result;
    }

}
