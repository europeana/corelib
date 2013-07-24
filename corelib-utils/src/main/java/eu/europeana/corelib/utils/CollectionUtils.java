package eu.europeana.corelib.utils;

public class CollectionUtils {

	public static <T> T returnFirst(T[] list, T empty) {
		if (list == null || list.length == 0) {
			return empty;
		}
		return list[0];
	}
	
}
