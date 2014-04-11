package eu.europeana.corelib.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.utils.model.LanguageVersion;
import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery.Query.Page;
import eu.europeana.corelib.web.model.wikipedia.WikipediaQuery.Query.Page.WikiLangLink;
import eu.europeana.corelib.web.service.WikipediaApiService;

public class WikipediaApiServiceImpl extends JsonApiServiceImpl implements WikipediaApiService {

	private static final String LANGLINKS = ".wikipedia.org/w/api.php?action=query&prop=langlinks&lllimit=200&redirects=&format=json";

	public static WikipediaApiService getBeanInstance() {
		return ApplicationContextContainer.getBean(WikipediaApiServiceImpl.class);
	}

	public WikipediaApiServiceImpl() {}

	public List<LanguageVersion> getLanguageLinks(String titles, List<String> languages) {
		List<LanguageVersion> translations = new ArrayList<LanguageVersion>();
		List<String> smallCaseTranslations = new ArrayList<String>();
		for (String languageCode : languages) {
			Map<String, String> langVersions = getLanguageLinks(titles, languageCode);
			for (String language : languages) {
				if (langVersions.containsKey(language)) {
					String langVersion = langVersions.get(language);
					if (!smallCaseTranslations.contains(langVersion.toLowerCase())) {
						smallCaseTranslations.add(langVersion.toLowerCase());
						translations.add(new LanguageVersion(langVersion, language));
					}
				}
			}
		}
		return translations;
	}

	@Override
	public Map<String, String> getLanguageLinks(String titles, String languageCode) {
		String url = buildLanguageLinksUrl(titles, languageCode);
		ApiResult result = getJsonResponse(url);
		WikipediaQuery wikipediaQuery = parseJson(result);
		return extractLanguageVersions(wikipediaQuery, languageCode);
	}

	private Map<String, String> extractLanguageVersions(WikipediaQuery query, String languageCode) {
		Map<String, String> languageVersions = new HashMap<String, String>();
		Page page = query.getQuery().getPages().values().iterator().next();
		if (page != null && page.getPageid() != 0) {
			if (page.getLanglinks() != null && page.getLanglinks().size() > 0) {
				for (WikiLangLink langLinks : page.getLanglinks()) {
					languageVersions.put(langLinks.getLang(), langLinks.getTranslation());
				}
			}
			if (!languageVersions.containsKey(languageCode)) {
				languageVersions.put(languageCode, page.getTitle());
			}
		}
		return languageVersions;
	}

	private WikipediaQuery parseJson(ApiResult result) {
		Gson gson = new Gson();
		return gson.fromJson(result.getContent(), WikipediaQuery.class);
	}

	private String buildLanguageLinksUrl(String titles, String languageCode) {
		StringBuffer url = new StringBuffer("http://");
		url.append(checkLanguageCode(languageCode, "en"));
		url.append(LANGLINKS);
		try {
			url.append("&titles=").append(URLEncoder.encode(titles, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url.toString();
	}

	private String checkLanguageCode(String languageCode, String defaultLanguageCode) {
		if (isValidLanguageCode(languageCode)) {
			return languageCode;
		}
		return defaultLanguageCode;
	}

	private boolean isValidLanguageCode(String languageCode) {
		return StringUtils.isNotBlank(languageCode)
				&& StringUtils.isAllLowerCase(languageCode)
				&& languageCode.length() == 2;
	}
}
