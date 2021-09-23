package eu.europeana.corelib.record.schemaorg.utils;

import eu.europeana.corelib.edm.model.schemaorg.SchemaOrgConstants;
import eu.europeana.corelib.record.schemaorg.utils.SchemaOrgTypeFactory;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Patrick Ehlert
 * Created on 10-09-2018
 */
public class SchemaOrgTypeFactoryTest {


    @Test
    public void testTypeFromProxy() {
        FullBeanImpl fb = MockFullBean.mock();
        Assert.assertEquals(SchemaOrgConstants.TYPE_PHOTOGRAPH, SchemaOrgTypeFactory.createObject(fb).getTypeName());
    }

    @Test
    public void testTypeFromConcepts() {
        // we clear all proxies in the bean, so the SchemaOrgTypeFactory has to rely on concepts to determine the type
        FullBeanImpl fb = MockFullBean.mock();
        fb.setProxies(null);
        Assert.assertEquals(SchemaOrgConstants.TYPE_VISUAL_ARTWORK, SchemaOrgTypeFactory.createObject(fb).getTypeName());
    }

}
