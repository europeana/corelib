package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml"})
public class SchemaOrgUtilsTest {

    /**
     * Test schema.org generation and serialiation
     */
    @Test
    public void toSchemaOrgTest() {
        FullBeanImpl bean = MockFullBean.mock();
        String output = SchemaOrgUtils.toSchemaOrg(bean);
        Assert.assertNotNull(output);
    }

}