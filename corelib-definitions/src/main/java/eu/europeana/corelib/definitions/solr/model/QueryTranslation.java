package eu.europeana.corelib.definitions.solr.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import eu.europeana.corelib.utils.model.LanguageVersion;

public class QueryTranslation {

	private List<LanguageVersion> languageVersions;
	private Map<String, List<LanguageVersion>> languageVersionMap;
	private String modifiedQuery;

	public QueryTranslation() {
		languageVersions = new ArrayList<LanguageVersion>();
		languageVersionMap = new HashMap<String, List<LanguageVersion>>();
	}

	public void addLanguageVersions(String position, List<LanguageVersion> languageVersions) {
		this.languageVersions.addAll(languageVersions);
		languageVersionMap.put(position, languageVersions);
	}

	public void addLanguageVersion(String position, LanguageVersion languageVersion) {
		languageVersions.add(languageVersion);
		List<LanguageVersion> mappedVersions;
		if (languageVersionMap.containsKey(position)) {
			mappedVersions = languageVersionMap.get(position);
		} else {
			mappedVersions = new ArrayList<LanguageVersion>();
			languageVersionMap.put(position, mappedVersions);
		}
		mappedVersions.add(languageVersion);
	}

	public Map<String, List<LanguageVersion>> getSortedMap() {
		Map<String, List<LanguageVersion>> sortedMap = new LinkedHashMap<String, List<LanguageVersion>>();
		Map<Integer, String> keyResolver = new TreeMap<Integer, String>();
		for (String key : languageVersionMap.keySet()) {
			String[] parts = key.split(":");
			keyResolver.put(Integer.parseInt(parts[0]), key);
		}
		for (Integer i : keyResolver.keySet()) {
			sortedMap.put(keyResolver.get(i), languageVersionMap.get(keyResolver.get(i)));
		}
		return sortedMap;
	}

	public void sortLanguageVersions() {
		if (languageVersions != null && languageVersions.size() > 1) {
			Collections.sort(languageVersions);
		}
	}

	public String getModifiedQuery() {
		return modifiedQuery;
	}

	public void setModifiedQuery(String modifiedQuery) {
		this.modifiedQuery = modifiedQuery;
	}

	public List<LanguageVersion> getLanguageVersions() {
		return languageVersions;
	}

	public Map<String, List<LanguageVersion>> getLanguageVersionMap() {
		return languageVersionMap;
	}

	public void setLanguageVersionMap(
			Map<String, List<LanguageVersion>> languageVersionMap) {
		this.languageVersionMap = languageVersionMap;
	}

	public void setLanguageVersions(List<LanguageVersion> languageVersions) {
		this.languageVersions = languageVersions;
	}
}
