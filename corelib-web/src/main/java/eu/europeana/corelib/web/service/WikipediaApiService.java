package eu.europeana.corelib.web.service;

import java.util.List;
import java.util.Map;

public interface WikipediaApiService {

	public List<String> getLanguageLinks(String titles, List<String> languages);

	public Map<String, String> getLanguageLinks(String titles, String languageCode);
}
