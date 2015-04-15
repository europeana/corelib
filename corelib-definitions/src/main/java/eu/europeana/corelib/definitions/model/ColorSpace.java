package eu.europeana.corelib.definitions.model;

public enum ColorSpace {
	SRGB,
    GRAYSCALE;
	public static String getValue(ColorSpace e){
		if (e.equals(SRGB)){
			return "sRGB";
		} else if(e.equals(GRAYSCALE)){
			return "grayscale";
		}
		return null;
	}
}
