package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.model.metainfo.ImageOrientation;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.model.metainfo.ImageMetaInfoImpl;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SchemaOrgUtilsTest {

    @Test
    public void toSchemaOrgTest() {
        FullBeanImpl bean = MockFullBean.mock();
        String output = SchemaOrgUtils.toSchemaOrg(bean);
        Assert.assertNotNull(output);
    }

}