package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.edm.model.schema.org.SchemaOrgConstants;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Patrick Ehlert
 * Created on 10-09-2018
 */
public class SchemaOrgTypeFactoryTest {


    @Test
    public void testTypeFromProxy() {
        FullBeanImpl fb = MockFullBean.mock();
        assertEquals(SchemaOrgConstants.TYPE_PHOTOGRAPH, SchemaOrgTypeFactory.createObject(fb).getTypeName());
    }

    @Test
    public void testTypeFromConcepts() {
        // we clear all proxies in the bean, so the SchemaOrgTypeFactory has to rely on concepts to determine the type
        FullBeanImpl fb = MockFullBean.mock();
        fb.setProxies(null);
        assertEquals(SchemaOrgConstants.TYPE_VISUAL_ARTWORK, SchemaOrgTypeFactory.createObject(fb).getTypeName());
    }

}
