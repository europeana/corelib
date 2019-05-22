package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.Agent;
import eu.europeana.corelib.utils.StringArrayUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * @see eu.europeana.corelib.definitions.edm.entity.Agent
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
@Entity ("Agent")
public class AgentImpl extends ContextualClassImpl implements Agent {

	private Map<String,List<String>> begin;
	private Map<String,List<String>> end;

	private String[] edmWasPresentAt;
	private Map<String,List<String>> edmHasMet;
	private Map<String,List<String>> edmIsRelatedTo;
	private String[] owlSameAs;
	private Map<String,List<String>> foafName;
	private Map<String,List<String>> dcDate;
	private Map<String,List<String>> dcIdentifier;

	private Map<String,List<String>> rdaGr2DateOfBirth;
	private Map<String,List<String>> rdaGr2DateOfDeath;
	private Map<String,List<String>> rdaGr2PlaceOfBirth;
	private Map<String,List<String>> rdaGr2PlaceOfDeath;
	private Map<String,List<String>> rdaGr2DateOfEstablishment;
	private Map<String,List<String>> rdaGr2DateOfTermination;
	private Map<String,List<String>> rdaGr2Gender;
	private Map<String,List<String>> rdaGr2ProfessionOrOccupation;
	private Map<String,List<String>> rdaGr2BiographicalInformation;

	@Override
	public Map<String,List<String>> getBegin() {
		return this.begin;
	}

	@Override
	public Map<String,List<String>> getEnd() {
		return this.end;
	}

	@Override
	public void setBegin(Map<String,List<String>> begin) {
		this.begin = begin;
	}

	@Override
	public void setEnd(Map<String,List<String>> end) {
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
		int hash = 7;
		
		hash = 31 * hash + (null == this.getAbout()? 0 : this.getAbout().hashCode());
		return hash;
	}

	@Override
	public void setEdmWasPresentAt(String[] edmWasPresentAt) {
		this.edmWasPresentAt = edmWasPresentAt!=null? edmWasPresentAt.clone():null;
	}

	@Override
	public String[] getEdmWasPresentAt() {
		return (StringArrayUtils.isNotBlank(this.edmWasPresentAt) ? this.edmWasPresentAt.clone() : null);
	}

	@Override
	public void setEdmHasMet(Map<String,List<String>> edmHasMet) {
		this.edmHasMet = edmHasMet;
	}

	@Override
	public Map<String,List<String>> getEdmHasMet() {
		return this.edmHasMet;
	}

	@Override
	public void setEdmIsRelatedTo(Map<String,List<String>> edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
	}

	@Override
	public Map<String,List<String>> getEdmIsRelatedTo() {
		return this.edmIsRelatedTo;
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs!=null?owlSameAs.clone():null;
	}

	@Override
	public String[] getOwlSameAs() {
		return (StringArrayUtils.isNotBlank(this.owlSameAs) ? this.owlSameAs.clone() : null);
	}

	@Override
	public void setFoafName(Map<String,List<String>> foafName) {
		this.foafName = foafName;
	}

	@Override
	public Map<String,List<String>> getFoafName() {
		return this.foafName;
	}

	@Override
	public void setDcDate(Map<String,List<String>> dcDate) {
		this.dcDate = dcDate;
	}

	@Override
	public Map<String,List<String>> getDcDate() {
		return this.dcDate;
	}

	@Override
	public void setDcIdentifier(Map<String,List<String>> dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
	}

	@Override
	public Map<String,List<String>> getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public void setRdaGr2DateOfBirth(Map<String,List<String>> rdaGr2DateOfBirth) {
		this.rdaGr2DateOfBirth = rdaGr2DateOfBirth;
	}

	@Override
	public Map<String,List<String>> getRdaGr2DateOfBirth() {
		return this.rdaGr2DateOfBirth;
	}

	@Override
	public void setRdaGr2DateOfDeath(Map<String,List<String>> rdaGr2DateOfDeath) {
		this.rdaGr2DateOfDeath = rdaGr2DateOfDeath;
	}

	@Override
	public Map<String,List<String>> getRdaGr2DateOfDeath() {
		return this.rdaGr2DateOfDeath;
	}

	public Map<String, List<String>> getRdaGr2PlaceOfBirth() {
		return rdaGr2PlaceOfBirth;
	}

	public void setRdaGr2PlaceOfBirth(Map<String, List<String>> rdaGr2PlaceOfBirth) {
		this.rdaGr2PlaceOfBirth = rdaGr2PlaceOfBirth;
	}

	public Map<String, List<String>> getRdaGr2PlaceOfDeath() {
		return rdaGr2PlaceOfDeath;
	}

	public void setRdaGr2PlaceOfDeath(Map<String, List<String>> rdaGr2PlaceOfDeath) {
		this.rdaGr2PlaceOfDeath = rdaGr2PlaceOfDeath;
	}

	@Override
	public void setRdaGr2DateOfEstablishment(Map<String,List<String>> rdaGr2DateOfEstablishment) {
		this.rdaGr2DateOfEstablishment = rdaGr2DateOfEstablishment;
	}

	@Override
	public Map<String,List<String>> getRdaGr2DateOfEstablishment() {
		return this.rdaGr2DateOfEstablishment;
	}

	@Override
	public void setRdaGr2DateOfTermination(Map<String,List<String>> rdaGr2DateOfTermination) {
		this.rdaGr2DateOfTermination = rdaGr2DateOfTermination;
	}

	@Override
	public Map<String,List<String>> getRdaGr2DateOfTermination() {
		return this.rdaGr2DateOfTermination;
	}

	@Override
	public void setRdaGr2Gender(Map<String,List<String>> rdaGr2Gender) {
		this.rdaGr2Gender = rdaGr2Gender;
	}

	@Override
	public Map<String,List<String>> getRdaGr2Gender() {
		return this.rdaGr2Gender;
	}

	@Override
	public void setRdaGr2ProfessionOrOccupation(Map<String,List<String>> rdaGr2ProfessionOrOccupation) {
		this.rdaGr2ProfessionOrOccupation = rdaGr2ProfessionOrOccupation;
	}

	@Override
	public Map<String,List<String>> getRdaGr2ProfessionOrOccupation() {
		return this.rdaGr2ProfessionOrOccupation;
	}

	@Override
	public void setRdaGr2BiographicalInformation(Map<String,List<String>> rdaGr2BiographicalInformation) {
		this.rdaGr2BiographicalInformation = rdaGr2BiographicalInformation;
	}

	@Override
	public Map<String,List<String>> getRdaGr2BiographicalInformation() {
		return this.rdaGr2BiographicalInformation;
	}
}
