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

private String[][] prefLabel;
private String[][] altLabel;
private String[] note;
private String[] broader;
	@Override
	public String[][] getPrefLabel() {
		return this.prefLabel;
	}

	@Override
	public String[][] getAltLabel() {
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

}
