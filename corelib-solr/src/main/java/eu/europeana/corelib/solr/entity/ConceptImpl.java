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

package eu.europeana.corelib.solr.entity;

import java.util.HashMap;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.entity.Concept;

import com.google.code.morphia.annotations.*;

import org.bson.types.*;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Concept
 * @author yorgos.mamakis@kb.nl
 *
 */
@Entity("Concept")
public class ConceptImpl implements Concept {
	


@Id ObjectId conceptId;

private Map<String,String> prefLabel;
private Map<String,String> altLabel;
private String[] note;
private String[] broader;
	@Override
	public Map<String,String> getPrefLabel() {
		return this.prefLabel;
	}

	@Override
	public Map<String,String> getAltLabel() {
		return this.altLabel;
	}

	@Override
	public String[] getNote() {
		return this.note;
	}

	@Override
	public String[] getBroader() {
		return this.broader;
	}

	@Override
	public ObjectId getConceptId() {
		return this.conceptId;
	}

	@Override
	public void setAltLabel(Map<String,String> altLabel) {
		this.altLabel=altLabel;
		
	}

	@Override
	public void setNote(String[] note) {
		this.note=note;
		
	}

	@Override
	public void setPrefLabel(Map<String,String> prefLabel) {
		this.prefLabel = prefLabel;
		
	}
	@Override
	public void setConceptId(ObjectId conceptId) {
		this.conceptId = conceptId;
	}
	@Override
	public void setBroader(String[] broader) {
		this.broader = broader;
	}

	@Override
	public boolean equals(Object o){
		return this.getConceptId().equals(((ConceptImpl)o).getConceptId());
	}
}
