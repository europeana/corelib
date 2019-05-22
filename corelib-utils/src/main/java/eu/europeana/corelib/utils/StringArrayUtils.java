package eu.europeana.corelib.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * String array util classes
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class StringArrayUtils {

	public static final String[] EMPTY_ARRAY = new String[] {};

	/**
	 * Check if an array is not empty
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNotBlank(String[] array) {
		return ((array != null) && (array.length > 0)) && StringUtils.join(array).trim().length() > 0;
	}

	/**
	 * Check if an array is not empty
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNotBlankList(List<String> array) {
		return ((array != null) && (array.size() > 0)) && StringUtils.join(array, "").trim().length() > 0;
	}

	/**
	 * Check if an array is empty
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isBlank(String[] array) {
		return !isNotBlank(array);
	}

	/**
	 * Convert a list to array
	 * 
	 * @param list
	 * @return
	 */
	public static String[] toArray(List<String> list) {
		if (list != null && list.size()>0) {
			return list.toArray(new String[list.size()]);
		}
		return EMPTY_ARRAY;
	}

	public static List<String> toList(String... items) {
		List<String> list = new ArrayList<String>();
		if (isNotBlank(items)) {
			for (String s : items) {
				if (StringUtils.isNotBlank(s)) {
					list.add(s);
				}
			}
		}
		return list;
	}

	public static Set<String> toSet(String... items) {
		Set<String> list = new HashSet<String>();
		if (isNotBlank(items)) {
			for (String s : items) {
				if (StringUtils.isNotBlank(s)) {
					list.add(s);
				}
			}
		}
		return list;
	}

	public static String[] toArray(String... items) {
		return items;
	}

	/**
	 * Adds a string array to alist of strings
	 * 
	 * @param list
	 * @param toAdd
	 */
	public static void addToList(List<String> list, String[] toAdd) {
		if ((list != null) && (isNotBlank(toAdd))) {
			for (String string : toAdd) {
				if (StringUtils.isNotBlank(string)) {
					list.add(string);
				}
			}
		}
	}

	/**
	 * Create a string representation of a string array
	 * 
	 * @param items
	 * @return
	 */
	public static String formatList(String[] items) {
		if (isNotBlank(items)) {
			if (items.length == 1) {
				return StringUtils.trim(items[0]);
			}
			StringBuilder sb = new StringBuilder();
			for (String item : items) {
				if (sb.length() > 0) {
					sb.append("| ");
				}
				sb.append(StringUtils.trim(item));
			}
			int p = sb.lastIndexOf("|");
			if (p > 0) {
				sb.replace(p, p + 1, " &");
			}
			return sb.toString().replace("|", ",");
		}
		return "";
	}

	/**
	 * Adds a string to a string array. If the array is null it creates it
	 * 
	 * @param items
	 *            The array to append data in
	 * @param str
	 *            The string to append
	 * @return The modified array
	 */
	public static String[] addToArray(String[] items, String str) {
		List<String> itemList;
		if (items == null) {
			itemList = new ArrayList<String>();
		} else {
			itemList = new ArrayList<String>(Arrays.asList(items));
		}
		if (str != null) {
			itemList.add(str);
		}
		return itemList.toArray(new String[itemList.size()]);
	}

	public static String concat(String... strings) {
		if (isNotBlank(strings)) {
			StringBuilder sb = new StringBuilder();
			for (String string : strings) {
				sb.append(string);
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * Remove all leading/trailing whitespace, and replace multiple spaces between text to a single space
	 * 
	 * @param s
	 * @return
	 */
	public static String clean(String s) {
		s = StringUtils.trim(s);
		if (StringUtils.isNotEmpty(s)) {
			String regex = "\\s{2,}";
			s = s.replaceAll(regex, " ");
		}
		return s;
	}

	/**
	 * Create a normalized array from web parameters, which can be array of strings concatenated by space, + or ,.
	 * 
	 * @param array
	 *   The array of parameters
	 * @return
	 *   The normalized array
	 */
	public static String[] splitWebParameter(String[] array) {
		if (isBlank(array)) {
			return EMPTY_ARRAY;
		}
		List<String> result = new ArrayList<String>();
		for (String item : array) {
			if (StringUtils.isNotBlank(item)) {
				String[] items = StringUtils.split(item, " +,"); 
				addToList(result, items);
			}
		}
		return toArray(result);
	}
}
