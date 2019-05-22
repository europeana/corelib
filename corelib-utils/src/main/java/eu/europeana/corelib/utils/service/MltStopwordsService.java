package eu.europeana.corelib.utils.service;

/**
 * @deprecated July 2017 MoreLikeThis / SimilarItems for records is no longer being used (and doesn't work properly)
 */
@Deprecated
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
