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

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.*;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Timespan
 * @author gmamakis
 *
 */

@Entity("Timespan")
public class TimespanImpl implements
		eu.europeana.corelib.definitions.solr.entity.Timespan {

	@Id ObjectId timespanId;
	private String[][] prefLabel;
	private String[][] altLabel;
	private String[] note;
	private Date begin;
	private Date end;
	private String[] isPartOf;
	
	
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
	public Date getBegin() {
		return this.begin;
	}

	@Override
	public Date getEnd() {
		return this.end;
	}

	@Override
	public String[] getIsPartOf() {
		return this.isPartOf;
	}

	@Override
	public ObjectId getTimespanId() {
		return this.timespanId;
	}
	@Override
	public void setTimespanId(ObjectId timespanId) {
		this.timespanId = timespanId;
	}

	@Override
	public void setPrefLabel(String[][] prefLabel) {
		this.prefLabel = prefLabel;
	}
	
	@Override
	public void setAltLabel(String[][] altLabel) {
		this.altLabel = altLabel;
	}
	
	@Override
	public void setNote(String[] note) {
		this.note = note;
	}

	@Override
	public void setBegin(Date begin) {
		this.begin = begin;
	}

	@Override
	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public void setIsPartOf(String[] isPartOf) {
		this.isPartOf = isPartOf;
	}

}
