package eu.europeana.corelib.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery.Query.Page;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery.Query.Page.WikiLangLink;
import eu.europeana.corelib.web.service.WikipediaApiService;

// import info.bliki.wiki.model

public class WikipediaApiServiceImpl extends JsonApiServiceImpl implements WikipediaApiService {

	private static final String LANGLINKS = "http://en.wikipedia.org/w/api.php?action=query&prop=langlinks&lllimit=200&redirects=&format=json";

	public static WikipediaApiService getBeanInstance() {
		return ApplicationContextContainer.getBean(WikipediaApiServiceImpl.class);
	}

	public WikipediaApiServiceImpl() {}

	public List<String> getLanguageLinks(String titles, List<String> languages) {
		Map<String, String> langVersions = getLanguageLinks(titles);
		List<String> translations = new ArrayList<String>();
		for (String language : languages) {
			if (langVersions.containsKey(language) && !translations.contains(langVersions.get(language))) {
				translations.add(langVersions.get(language));
			}
		}
		return translations;
	}

	@Override
	public Map<String, String> getLanguageLinks(String titles) {
		String url = buildLanguageLinksUrl(titles);
		ApiResult result = getJsonResponse(url);
		WikipediaQuery wikipediaQuery = parseJson(result);
		return extractLanguageVersions(wikipediaQuery);
	}

	private Map<String, String> extractLanguageVersions(WikipediaQuery query) {
		Map<String, String> languages = new HashMap<String, String>();
		Page page = query.getQuery().getPages().values().iterator().next();
		for (WikiLangLink langLinks : page.getLanglinks()) {
			languages.put(langLinks.getLang(), langLinks.getTranslation());
		}
		if (!languages.containsKey("en")) {
			languages.put("en", page.getTitle());
		}
		return languages;
	}

	private WikipediaQuery parseJson(ApiResult result) {
		Gson gson = new Gson();
		return gson.fromJson(result.getContent(), WikipediaQuery.class);
	}

	private String buildLanguageLinksUrl(String titles) {
		StringBuffer url = new StringBuffer(LANGLINKS);
		try {
			url.append("&titles=").append(URLEncoder.encode(titles, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url.toString();
	}
}
