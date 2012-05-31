/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.definitions.solr.model;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Term implements Comparable<Term>{
	
	private String term;
	
	private long frequency;
	
	private String field;
	
	public Term(String term, long frequency, String field) {
		this.term = term;
		this.frequency = frequency;
		this.field = field;
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

	public String getField(){
		return this.field;
	}
	
	public void setField(String field){
		this.field = field;
	}

	@Override
	public int compareTo(Term o) {
		final int SMALLER=1;
		final int EQUALS=0;
		final int GREATER=-1;
		if(frequency<o.getFrequency()){
			return SMALLER;
		}
		if(frequency>o.getFrequency()){
			return GREATER;
		}
			return EQUALS;
		
	}
}
