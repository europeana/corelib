package eu.europeana.corelib.utils.service;

public interface MltStopwordsService {

	/**
	 * Checks whether a word is part of the stop word list
	 * 
	 * @param word
	 * 
	 * @return TRUE if it is listed in stopwords, otherwise FALSE
	 */
	boolean check(String word);
}
