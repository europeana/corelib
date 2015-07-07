package eu.europeana.corelib.definitions.model;

public enum Orientation {
	LANDSCAPE,PORTRAIT;

	public static String getValue(Orientation e){
		if (e.equals(LANDSCAPE)){
			return "landscape";
		} else if(e.equals(PORTRAIT)){
			return "portrait";
		}
		return null;
	}
}
