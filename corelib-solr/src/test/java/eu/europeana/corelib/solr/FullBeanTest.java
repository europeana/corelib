package eu.europeana.corelib.solr;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;


import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.mongodb.MongoDBServer;



/**
 * 
 * Unit test for FullBean
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml" })
public class FullBeanTest
{
	@Resource(name = "corelib_solr_mongoServer")
	MongoDBServer mongoServer;
	
	
	@Test
	public void testRetrieve(){
		Datastore ds = mongoServer.getDatastore();
		assertNotNull("Error creating datastore",ds);
		Agent agent = createAgent(ds);
		assertNotNull ("Error creating agent", agent);
		
	}

/**
 * Create and Test an Agent
 * @param ds
 * @return the created agent
 */
	private Agent createAgent(Datastore ds) {
		Agent agent = new AgentImpl();
		agent.setNote(new String[]{"test note"});
		agent.setPrefLabel(new String[][]{{"en"},{"test prefLabel"}});
		agent.setAltLabel(new String[][]{{"en"},{"test altLabel"}});
		Date begin = new Date();
		agent.setBegin(begin);
		Date end = new Date();
		agent.setEnd(end);
		Key<Agent> agentKey = ds.save(agent);
		Agent testAgent = ds.find(AgentImpl.class).get();
		assertEquals(agent, testAgent);
		return agent;
	}
  
}
