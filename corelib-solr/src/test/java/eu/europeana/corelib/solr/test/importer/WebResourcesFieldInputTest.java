package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.server.importer.util.WebResourcesFieldInput;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class WebResourcesFieldInputTest {
	@Resource(name = "corelib_solr_mongoServer")
	private MongoDBServer mongoServer;
	
	@Test
	public void testWebResource(){
		WebResourceType webResource = new WebResourceType();
		webResource.setAbout("test about");
		Rights rights = new Rights();
		rights.setResource("test resource");
		webResource.setRights(rights);
		List<Rights1> rightsList = new ArrayList<Rights1>();
		Rights1 rights1 = new Rights1();
		rights1.setResource("test rights");
		rightsList.add(rights1);
		webResource.setRightList(rightsList);
		//create mongo
		WebResourceImpl webResourceMongo = WebResourcesFieldInput.createWebResourceMongoField(webResource, mongoServer);
		assertEquals(webResource.getAbout(), webResourceMongo.getAbout());
		assertEquals(webResource.getRights().getResource(), webResourceMongo.getWebResourceEdmRights());
		assertEquals(webResource.getRightList().get(0).getResource(), webResourceMongo.getWebResourceDcRights()[0]);
		
		//create solr document
		SolrInputDocument solrDocument = new SolrInputDocument();
		try {
			solrDocument = WebResourcesFieldInput.createWebResourceSolrFields(webResource, solrDocument);
			assertEquals(webResource.getAbout(), solrDocument.getFieldValue(EdmLabel.EDM_WEB_RESOURCE.toString()));
			assertEquals(webResource.getRights().getResource(), solrDocument.getFieldValue(EdmLabel.WR_EDM_RIGHTS.toString()));
			assertEquals(webResource.getRightList().get(0).getResource(), solrDocument.getFieldValue(EdmLabel.WR_DC_RIGHTS.toString()));
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@After
	public void cleanup(){
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
