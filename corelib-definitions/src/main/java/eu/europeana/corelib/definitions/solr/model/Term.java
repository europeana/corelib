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

import org.apache.commons.lang.math.NumberUtils;

/**
 * Container class for terms of suggestions. It has three properties:
 * 
 * <ul>
 * <li>term (String): the word</li>
 * <li>frequency (long): the number of occurances</li>
 * <li>field (String): name of field the term takes place</li>
 * </ul>
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Term implements Comparable<Term>{
	
	private String term;
	
	private long frequency;
	
	private String field;
	
	private String query;
	
	public Term(String term, long frequency, String field, String query) {
		this.term = term;
		this.frequency = frequency;
		this.field = field;
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
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
		if (o != null) {
			// Note this compare function is reversed on purpose!!!
			return NumberUtils.compare(o.frequency, frequency);
		}
		return 1;
	}
}
