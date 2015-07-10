package eu.europeana.corelib.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
	private static Map<String, List<String>> removableParts = new HashMap<>();
	static {
		removableParts.put("de", Arrays.asList("(Film)", "(Biologie)"));
		removableParts.put("en", Arrays.asList("(disambiguation)", "(film)"));
		removableParts.put("nl", Collections.singletonList("(film)"));
	}

	public static WikipediaApiService getBeanInstance() {
		try{
			return ApplicationContextContainer.getBean(WikipediaApiServiceImpl.class);			
		}
		catch(NullPointerException e){
			return new WikipediaApiServiceImpl();
		}
	}

	public WikipediaApiServiceImpl() {}

	/**
	 * Returns the list of language versions in multiple languages
	 */
	@Override
	public List<LanguageVersion> getVersionsInMultiLanguage(String title, List<String> languages) {
		List<LanguageVersion> translations = new ArrayList<>();
		List<String> smallCaseTranslations = new ArrayList<>();
		for (String languageCode : languages) {
			if (StringUtils.isBlank(languageCode)) {
				continue;
			}
			Map<String, String> langVersions = getVersionsInLanguage(title, languageCode);
			for (String language : languages) {
				if (langVersions.containsKey(language)
					&& StringUtils.isNotBlank(langVersions.get(language))) {
					String langVersion = String.format("%s (%s)", langVersions.get(language), language);
					if (!smallCaseTranslations.contains(langVersion.toLowerCase())) {
						smallCaseTranslations.add(langVersion.toLowerCase());
						translations.add(new LanguageVersion(langVersions.get(language), language));
					}
				}
			}
		}
		return translations;
	}

	/**
	 * Returns the map of language code - language version, 
	 * such as ("fr" - "Mona Lisa", "fr" - "La Joconde", ...)
	 */
	@Override
	public Map<String, String> getVersionsInLanguage(String title, String languageCode) {
		String url = buildLanguageLinksUrl(title, languageCode);
		ApiResult result = getJsonResponse(url);
		WikipediaQuery wikipediaQuery = parseJson(result);
		return extractLanguageVersions(wikipediaQuery, languageCode);
	}

	private Map<String, String> extractLanguageVersions(WikipediaQuery query, String languageCode) {
		Map<String, String> languageVersions = new HashMap<>();
		if (query != null && query.getQuery() != null && query.getQuery().getPages() != null) {
			Page page = query.getQuery().getPages().values().iterator().next();
			if (page != null && page.getPageid() != 0) {
				if (page.getLanglinks() != null && page.getLanglinks().size() > 0) {
					for (WikiLangLink langLinks : page.getLanglinks()) {
						languageVersions.put(langLinks.getLang(),
								clearRemovableParts(langLinks.getLang(), langLinks.getTranslation()));
					}
				}
				if (!languageVersions.containsKey(languageCode)) {
					languageVersions.put(languageCode, page.getTitle());
				}
			}
		}
		return languageVersions;
	}

	private String clearRemovableParts(String lang, String translation) {
		List<String> removables = removableParts.get(lang);
		if (removables != null) {
			for (String removable : removables) {
				translation = StringUtils.removeEnd(translation, removable).trim();
			}
		}
		return translation;
	}

	private WikipediaQuery parseJson(ApiResult result) {
		Gson gson = new Gson();
		return gson.fromJson(result.getContent(), WikipediaQuery.class);
	}

	private String buildLanguageLinksUrl(String titles, String languageCode) {
		StringBuilder url = new StringBuilder("http://");
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
