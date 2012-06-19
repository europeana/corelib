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

import com.google.code.morphia.annotations.Embedded;

import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Agent
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Embedded
public class AgentImpl extends ContextualClassImpl implements Agent {

	private String begin;
	private String end;

	private String[] edmWasPresentAt;
	private String[] edmHasMet;
	private String[] edmIsRelatedTo;
	private String[] skosHiddenLabel;
	private String[] owlSameAs;
	private String[] foafName;
	private String[] dcDate;
	private String[] dcIdentifier;

	private String rdaGr2DateOfBirth;
	private String rdaGr2DateOfDeath;
	private String rdaGr2DateOfEstablishment;
	private String rdaGr2DateOfTermination;
	private String rdaGr2Gender;
	private String rdaGr2ProfessionOrOccupation;
	private String rdaGr2BiographicalInformation;

	@Override
	public String getBegin() {
		return this.begin;
	}

	@Override
	public String getEnd() {
		return this.end;
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
	public boolean equals(Object o) {
		if(o==null){
			return false;
		}
		if(o.getClass() == this.getClass()){
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
		return (StringArrayUtils.isNotBlank(this.edmWasPresentAt)?this.edmWasPresentAt.clone():null);
	}
	@Override
	public void setEdmHasMet(String[] edmHasMet) {
		this.edmHasMet = edmHasMet;
		
	}
	@Override
	public String[] getEdmHasMet() {
		return (StringArrayUtils.isNotBlank(this.edmHasMet)?this.edmHasMet.clone():null);
	}
	@Override
	public void setEdmIsRelatedTo(String[] edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
		
	}
	@Override
	public String[] getEdmIsRelatedTo() {
		return (StringArrayUtils.isNotBlank(this.edmIsRelatedTo)?this.edmIsRelatedTo.clone():null);
	}
	@Override
	public void setSkosHiddenLabel(String[] skosHiddenLabel) {
		this.skosHiddenLabel = skosHiddenLabel;
		
	}
	@Override
	public String[] getSkosHiddenLabel() {
		return (StringArrayUtils.isNotBlank(this.skosHiddenLabel)?this.skosHiddenLabel.clone():null);
	}
	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
		
	}
	@Override
	public String[] getOwlSameAs() {
		return (StringArrayUtils.isNotBlank(this.owlSameAs)?this.owlSameAs.clone():null);
	}
	@Override
	public void setFoafName(String[] foafName) {
		this.foafName = foafName;
		
	}
	@Override
	public String[] getFoafName() {
		return (StringArrayUtils.isNotBlank(this.foafName)?this.foafName.clone():null);
	}
	@Override
	public void setDcDate(String[] dcDate) {
		this.dcDate = dcDate;
		
	}
	@Override
	public String[] getDcDate() {
		return (StringArrayUtils.isNotBlank(this.dcDate)?this.dcDate.clone():null);
	}
	@Override
	public void setDcIdentifier(String[] dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
		
	}
	@Override
	public String[] getDcIdentifier() {
		return (StringArrayUtils.isNotBlank(this.dcIdentifier)?this.dcIdentifier.clone():null);
	}
	@Override
	public void setRdaGr2DateOfBirth(String rdaGr2DateOfBirth) {
		this.rdaGr2DateOfBirth = rdaGr2DateOfBirth;
		
	}
	@Override
	public String getRdaGr2DateOfBirth() {
		
		return this.rdaGr2DateOfBirth;
	}
	@Override
	public void setRdaGr2DateOfDeath(String rdaGr2DateOfDeath) {
		this.rdaGr2DateOfDeath = rdaGr2DateOfDeath;
		
	}
	@Override
	public String getRdaGr2DateOfDeath() {
		return this.rdaGr2DateOfDeath;
	}
	@Override
	public void setRdaGr2DateOfEstablishment(String rdaGr2DateOfEstablishment) {
		this.rdaGr2DateOfEstablishment = rdaGr2DateOfEstablishment;
		
	}
	@Override
	public String getRdaGr2DateOfEstablishment() {
		return this.rdaGr2DateOfEstablishment;
	}
	@Override
	public void setRdaGr2DateOfTermination(String rdaGr2DateOfTermination) {
		this.rdaGr2DateOfTermination = rdaGr2DateOfTermination;
	}
	@Override
	public String getRdaGr2DateOfTermination() {
		return this.rdaGr2DateOfTermination;
	}
	@Override
	public void setRdaGr2Gender(String rdaGr2Gender) {
		this.rdaGr2Gender = rdaGr2Gender;
		
	}
	@Override
	public String getRdaGr2Gender() {
		return this.rdaGr2Gender;
	}
	@Override
	public void setRdaGr2ProfessionOrOccupation(
			String rdaGr2ProfessionOrOccupation) {
		this.rdaGr2ProfessionOrOccupation = rdaGr2ProfessionOrOccupation;
	}
	@Override
	public String getRdaGr2ProfessionOrOccupation() {
		return this.rdaGr2ProfessionOrOccupation;
	}
	@Override
	public void setRdaGr2BiographicalInformation(
			String rdaGr2BiographicalInformation) {
		this.rdaGr2BiographicalInformation = rdaGr2BiographicalInformation;
		
	}
	@Override
	public String getRdaGr2BiographicalInformation() {
		return this.rdaGr2BiographicalInformation;
	}
}
