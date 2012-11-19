/**
 * 
 */
package eu.europeana.corelib.dereference.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

import eu.europeana.corelib.definitions.model.EdmLabel;

/**
 * @author gmamakis
 *
 */
public class VocabularyMongoServerTest {
	VocabularyMongoServer server;
	@Test
	public void test(){
		try {
			server = new VocabularyMongoServer(new Mongo("localhost",27017), "vocTest");
			ControlledVocabularyImpl voc = new ControlledVocabularyImpl();
			voc.setName("name");
			voc.setLocation("location");
			voc.setSuffix("rdf");
			voc.setURI("http://test_uri/");
			Map<String,List<EdmLabel>> elements = new HashMap<String, List<EdmLabel>>();
			List<EdmLabel> lst = new ArrayList<EdmLabel>();
			lst.add(EdmLabel.AG_DC_DATE);
			elements.put("test",lst);
			voc.setElements(elements);
			voc.setRules(new String[]{"*"});
			server.getDatastore().save(voc);
			Assert.assertNotNull(server.getControlledVocabulary("URI", "http://test_uri/"));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@After
	public void cleanUp(){
		server.getDatastore().getDB().dropDatabase();
	}
}
