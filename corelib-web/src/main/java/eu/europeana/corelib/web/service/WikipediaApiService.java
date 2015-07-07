package eu.europeana.corelib.web.service;

import java.util.List;
import java.util.Map;

import eu.europeana.corelib.utils.model.LanguageVersion;

public interface WikipediaApiService {

	List<LanguageVersion> getVersionsInMultiLanguage(String title, List<String> languages);

	Map<String, String> getVersionsInLanguage(String title, String languageCode);
}
