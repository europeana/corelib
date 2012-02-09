package eu.europeana.corelib.solr;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.server.SolrServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml" })
public class BriefBeanTest {
	@Resource(name="corelib_solr_solrSelectServer1")
	SolrServer solrServer;
	
	@Test
	public void testBriefBean(){
		assertNotNull(solrServer);
		assertTrue(solrServer.isActive());
		BriefBean briefBean = new BriefBeanImpl();
		assertNull(briefBean.getId());
	}
}
