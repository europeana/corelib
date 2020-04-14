package eu.europeana.corelib.utils;

import java.util.*;

/**
 * Class for removing Duplicates
 * Created by Srishti on 25-03-2020.
 */
public class ComparatorUtils implements Comparator<String> {

    private static final String PUNCTUATION_REGEX = "[\\p{Punct}]+";
    /**
     * Method for removing Punctuations from string
     * This will help in sorting and comparing better
     *
     * @return String without punctuations
     * Note: the regex used can be modified to exclude few punctuations that we want
     * ex: "[\\p{Punct}&&[^_-]]+" , this excludes two punctuations _ (underscore) , - (hyphen)
     */
    public static String stripPunctuation(String value) {
        return value.replaceAll(PUNCTUATION_REGEX, "");
    }

    /**
     * Method for removing Punctuations from list of strings
     * This will help in sorting and comparing better
     *
     * @return List<String> without Punctuations
     */
    public static List<String> stripPunctuations(List<String> list) {
        List<String> listWithoutPunctuation = new ArrayList<>();
        for (String value : list) {
            listWithoutPunctuation.add(stripPunctuation(value));
        }
        return listWithoutPunctuation;
    }

    /**
     * Method to compare the Strings ignoring case and punctuations.
     * It will remove the duplicate values with different punctuations present in the list
     *
     * @return the list without any duplicates.
     */
    public static List<String> removeDuplicates(List<String> listWithDuplicates) {
        Set<String> set = new TreeSet(new ComparatorUtils());
        set.addAll(listWithDuplicates);
        listWithDuplicates.clear();
        listWithDuplicates.addAll(set);
        return listWithDuplicates;
    }

    @Override
    public int compare(String o1, String o2) {
        if (stripPunctuation(o1).equalsIgnoreCase(stripPunctuation(o2))) {
            return 0;
        }
        return 1;
    }

}


