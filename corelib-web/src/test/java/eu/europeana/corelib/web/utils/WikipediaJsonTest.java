package eu.europeana.corelib.web.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.google.gson.Gson;

import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery.Query.Page;

public class WikipediaJsonTest {

	@Test
	public void wikipediaJsonTest() throws IOException {
		String json = IOUtils.toString(getClass().getResourceAsStream(
				"/testdata/wikipedia_langlinks.json"));
		Gson gson = new Gson();
		WikipediaQuery query = gson.fromJson(json, WikipediaQuery.class);
		assertNotNull("Parser returned null", query);
		assertNotNull("Query in results shouldn't be null", query.getQuery());
		assertNotNull("Map of pages shouldn't be null", query.getQuery().getPages());

		assertEquals("Not exactly 1 page in result", 1, query.getQuery().getPages().size());

		Page page = query.getQuery().getPages().values().iterator().next();
		assertNotNull("Page is null", page);
		assertEquals("Not exactly 174 langlinks", 174, page.getLanglinks().size());
		assertEquals("First langlink lang is not ace", "ace", page.getLanglinks().get(0).getLang());
		assertEquals("First langlink translation is not Albert Einstein", "Albert Einstein", page.getLanglinks().get(0).getTranslation());
	}
}
