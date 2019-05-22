package eu.europeana.corelib.utils;

/**
 * Collection utility classes
 *
 */
public class CollectionUtils {
	
	/**
	 * Return the first element of an array, or a predefined object
	 * @param list The array
	 * @param empty The default object
	 * @return
	 */
	public static <T> T returnFirst(T[] list, T empty) {
		if (list == null || list.length == 0) {
			return empty;
		}
		return list[0];
	}
}
