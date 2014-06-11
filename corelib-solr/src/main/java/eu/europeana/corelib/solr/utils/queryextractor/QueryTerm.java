package eu.europeana.corelib.solr.utils.queryextractor;

public class QueryTerm {
	private int start;
	private int end;
	private String transformed;
	private String original;

	public QueryTerm(int start, int end, String transformed, String original) {
		this.start = start;
		this.end = end;
		this.transformed = transformed;
		this.original = original;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getTransformed() {
		return transformed;
	}

	public void setTransformed(String transformed) {
		this.transformed = transformed;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}
}
