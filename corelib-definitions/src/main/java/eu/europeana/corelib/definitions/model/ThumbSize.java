package eu.europeana.corelib.definitions.model;

public enum ThumbSize {

	TINY(27,40),
	MEDIUM(110,160),
	LARGE(380,200);

	private int maxWidth;

	private int maxHeight;

	ThumbSize(int width, int height) {
		maxWidth = width;
		maxHeight = height;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}
}
