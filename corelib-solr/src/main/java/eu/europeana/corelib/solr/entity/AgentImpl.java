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

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Agent
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity ("Agent")
public class AgentImpl extends ContextualClassImpl implements Agent {

	private Map<String,String> begin;
	private Map<String,String> end;

	private String[] edmWasPresentAt;
	private Map<String,String> edmHasMet;
	private Map<String,String> edmIsRelatedTo;
	private String[] owlSameAs;
	private Map<String,String> foafName;
	private Map<String,String> dcDate;
	private Map<String,String> dcIdentifier;

	private Map<String,String> rdaGr2DateOfBirth;
	private Map<String,String> rdaGr2DateOfDeath;
	private Map<String,String> rdaGr2DateOfEstablishment;
	private Map<String,String> rdaGr2DateOfTermination;
	private Map<String,String> rdaGr2Gender;
	private Map<String,String> rdaGr2ProfessionOrOccupation;
	private Map<String,String> rdaGr2BiographicalInformation;

	@Override
	public Map<String,String> getBegin() {
		return this.begin;
	}

	@Override
	public Map<String,String> getEnd() {
		return this.end;
	}

	@Override
	public void setBegin(Map<String,String> begin) {
		this.begin = begin;
	}

	@Override
	public void setEnd(Map<String,String> end) {
		this.end = end;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null){
			return false;
		}
		if (o.getClass() == this.getClass()){
			return this.getAbout().equals(((AgentImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode(){ 
		return this.getAbout().hashCode();
	}

	@Override
	public void setEdmWasPresentAt(String[] edmWasPresentAt) {
		this.edmWasPresentAt = edmWasPresentAt;
	}

	@Override
	public String[] getEdmWasPresentAt() {
		return (StringArrayUtils.isNotBlank(this.edmWasPresentAt) ? this.edmWasPresentAt.clone() : null);
	}

	@Override
	public void setEdmHasMet(Map<String,String> edmHasMet) {
		this.edmHasMet = edmHasMet;
	}

	@Override
	public Map<String,String> getEdmHasMet() {
		return this.edmHasMet;
	}

	@Override
	public void setEdmIsRelatedTo(Map<String,String> edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
	}

	@Override
	public Map<String,String> getEdmIsRelatedTo() {
		return this.edmIsRelatedTo;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
	}

	@Override
	public String[] getOwlSameAs() {
		return (StringArrayUtils.isNotBlank(this.owlSameAs) ? this.owlSameAs.clone() : null);
	}

	@Override
	public void setFoafName(Map<String,String> foafName) {
		this.foafName = foafName;
	}

	@Override
	public Map<String,String> getFoafName() {
		return this.foafName;
	}

	@Override
	public void setDcDate(Map<String,String> dcDate) {
		this.dcDate = dcDate;
	}

	@Override
	public Map<String,String> getDcDate() {
		return this.dcDate;
	}

	@Override
	public void setDcIdentifier(Map<String,String> dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}

	@Override
	public Map<String,String> getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public void setRdaGr2DateOfBirth(Map<String,String> rdaGr2DateOfBirth) {
		this.rdaGr2DateOfBirth = rdaGr2DateOfBirth;
	}

	@Override
	public Map<String,String> getRdaGr2DateOfBirth() {
		return this.rdaGr2DateOfBirth;
	}

	@Override
	public void setRdaGr2DateOfDeath(Map<String,String> rdaGr2DateOfDeath) {
		this.rdaGr2DateOfDeath = rdaGr2DateOfDeath;
	}

	@Override
	public Map<String,String> getRdaGr2DateOfDeath() {
		return this.rdaGr2DateOfDeath;
	}

	@Override
	public void setRdaGr2DateOfEstablishment(Map<String,String> rdaGr2DateOfEstablishment) {
		this.rdaGr2DateOfEstablishment = rdaGr2DateOfEstablishment;
	}

	@Override
	public Map<String,String> getRdaGr2DateOfEstablishment() {
		return this.rdaGr2DateOfEstablishment;
	}

	@Override
	public void setRdaGr2DateOfTermination(Map<String,String> rdaGr2DateOfTermination) {
		this.rdaGr2DateOfTermination = rdaGr2DateOfTermination;
	}

	@Override
	public Map<String,String> getRdaGr2DateOfTermination() {
		return this.rdaGr2DateOfTermination;
	}

	@Override
	public void setRdaGr2Gender(Map<String,String> rdaGr2Gender) {
		this.rdaGr2Gender = rdaGr2Gender;
	}

	@Override
	public Map<String,String> getRdaGr2Gender() {
		return this.rdaGr2Gender;
	}

	@Override
	public void setRdaGr2ProfessionOrOccupation(Map<String,String> rdaGr2ProfessionOrOccupation) {
		this.rdaGr2ProfessionOrOccupation = rdaGr2ProfessionOrOccupation;
	}

	@Override
	public Map<String,String> getRdaGr2ProfessionOrOccupation() {
		return this.rdaGr2ProfessionOrOccupation;
	}

	@Override
	public void setRdaGr2BiographicalInformation(Map<String,String> rdaGr2BiographicalInformation) {
		this.rdaGr2BiographicalInformation = rdaGr2BiographicalInformation;
	}

	@Override
	public Map<String,String> getRdaGr2BiographicalInformation() {
		return this.rdaGr2BiographicalInformation;
	}
}
