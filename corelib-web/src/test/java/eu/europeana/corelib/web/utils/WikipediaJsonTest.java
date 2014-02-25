package eu.europeana.corelib.web.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.gson.Gson;

import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery;

public class WikipediaJsonTest {

	@Test
	public void wikipediaJsonTest() throws IOException {
		String json = IOUtils.toString(getClass().getResourceAsStream("/testdata/wikipedia_langlinks.json"));
		Gson gson = new Gson();
		WikipediaQuery query = gson.fromJson(json, WikipediaQuery.class);
		assertNotNull("Parser returned null", query);
		assertNotNull("Query in results shouldn't be null", query.getQuery());
		assertNotNull("Map of pages shouldn't be null", query.getQuery().getPages());
		
		assertEquals("Not exactly 1 page in result", query.getQuery().getPages().size(), 1);
		
	}
	
}
