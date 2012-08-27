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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Timespan
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("Timespan")
public class TimespanImpl extends ContextualClassImpl implements
		eu.europeana.corelib.definitions.solr.entity.Timespan {

	private String begin;
	private String end;
	private String[] isPartOf;
	private String[] dctermsHasPart;

	private String[] owlSameAs;
	private String[] crmP79FBeginningIsQualifiedBy;
	private String[] crmP80FEndIsQualifiedBy;

	@Override
	public String getBegin() {
		return this.begin;
	}

	@Override
	public String getEnd() {
		return this.end;
	}

	@Override
	public String[] getIsPartOf() {
		return (StringArrayUtils.isNotBlank(isPartOf) ? this.isPartOf.clone() : null);
	}

	@Override
	public void setBegin(String begin) {
		this.begin = begin;
	}

	@Override
	public void setEnd(String end) {
		this.end = end;
	}

	@Override
	public void setIsPartOf(String[] isPartOf) {
		this.isPartOf = isPartOf.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null){
			return false;
		}
		if (o.getClass() == this.getClass()){
			return this.getAbout().equals(((TimespanImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode(){ 
		return this.getAbout().hashCode();
	}

	@Override
	public String[] getDctermsHasPart() {
		return (StringArrayUtils.isNotBlank(dctermsHasPart) ? this.dctermsHasPart.clone() : null);
	}

	@Override
	public void setDctermsHasPart(String[] dctermsHasPart) {
		this.dctermsHasPart = dctermsHasPart;
	}

	@Override
	public String[] getOwlSameAs() {
		return (StringArrayUtils.isNotBlank(owlSameAs) ? this.owlSameAs.clone() : null);
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
	}

	@Override
	public String[] getCrmP79FBeginningIsQualifiedBy() {
		return (StringArrayUtils.isNotBlank(crmP79FBeginningIsQualifiedBy) ? this.crmP79FBeginningIsQualifiedBy.clone() : null);
	}

	@Override
	public String[] getCrmP80FEndIsQualifiedBy() {
		return (StringArrayUtils.isNotBlank(crmP80FEndIsQualifiedBy) ? this.crmP80FEndIsQualifiedBy.clone() : null);
	}

	@Override
	public void setCrmP80FEndIsQualifiedBy(String[] crmP80FEndIsQualifiedBy) {
		this.crmP80FEndIsQualifiedBy = crmP80FEndIsQualifiedBy;
	}

	@Override
	public void setCrmP97FBeginningIsQualifiedBy(String[] crmP79FBeginningIsQualifiedBy) {
		this.crmP79FBeginningIsQualifiedBy = crmP79FBeginningIsQualifiedBy;
	}
}
