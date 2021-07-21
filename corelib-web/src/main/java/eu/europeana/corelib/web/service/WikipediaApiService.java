package eu.europeana.corelib.web.service;

import java.util.List;
import java.util.Map;

import eu.europeana.corelib.utils.model.LanguageVersion;

/**
 * @deprecated will be replaced by new translation services
 */
@Deprecated(since = "July 2021")
public interface WikipediaApiService {

	List<LanguageVersion> getVersionsInMultiLanguage(String title, List<String> languages);

	Map<String, String> getVersionsInLanguage(String title, String languageCode);
}
