package eu.europeana.corelib.search.queryextractor;

public class QueryModification {

	private int start;
	private int end;
	private String modification;

	public QueryModification(int start, int end, String modification) {
		super();
		this.start = start;
		this.end = end;
		this.modification = modification;
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

	public String getModification() {
		return modification;
	}

	public void setModification(String modification) {
		this.modification = modification;
	}

	@Override
	public String toString() {
		return String.format("QueryModification: %s (%d-%d)", modification, start, end);
	}
}
