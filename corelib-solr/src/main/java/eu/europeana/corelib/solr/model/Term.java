package eu.europeana.corelib.solr.model;

public class Term {
	
	private String term;
	
	private long frequency;
	
	public Term(String term, long frequency) {
		this.term = term;
		this.frequency = frequency;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

}
