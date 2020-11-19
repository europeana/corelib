package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.morphia.annotations.Entity;
import eu.europeana.corelib.utils.StringArrayUtils;

import java.util.List;
import java.util.Map;

/**
 * @see eu.europeana.corelib.definitions.edm.entity.Timespan
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonInclude(Include.NON_EMPTY)
@Entity(value = "Timespan", useDiscriminator = false)
public class TimespanImpl extends ContextualClassImpl implements
		eu.europeana.corelib.definitions.edm.entity.Timespan {

	private Map<String,List<String>> begin;
	private Map<String,List<String>> end;
	private String beginString;
	private String endString;
	private String[] dctermsIsPartOf;

	private String[] owlSameAs;
	private String[] edmIsNextInSequence;

	@Override
	public Map<String,List<String>> getBegin() {
		return this.begin;
	}

	@Override
	public Map<String,List<String>> getEnd() {
		return this.end;
	}

	@Override
	public String[] getIsPartOfArray() {
		return this.dctermsIsPartOf;
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
	public void setIsPartOfArray(String[] isPartOf) {
		this.dctermsIsPartOf = isPartOf;
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
		return this.dctermsIsPartOf;
	}

	@Override
	public void setDctermsHasPart(String[] dctermsHasPart) {
		this.dctermsIsPartOf = dctermsHasPart;
	}

	@Override
	public String[] getOwlSameAs() {
		return (StringArrayUtils.isNotBlank(owlSameAs) ? this.owlSameAs.clone() : null);
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs!=null?owlSameAs.clone():null;
	}

	@Override
	public String[] getIsNextInSequence() {
		return edmIsNextInSequence;
	}

	@Override
	public void setIsNextInSequence(String[] isNextInSequence) {
		this.edmIsNextInSequence = isNextInSequence;
	}

	@Override
	public String getBeginString() {
		// TODO Auto-generated method stub
		return beginString;
	}

	@Override
	public String getEndString() {
		// TODO Auto-generated method stub
		return endString;
	}

	@Override
	public void setBegin(String begin) {
		this.beginString = begin;
		
	}

	@Override
	public void setEnd(String end) {
		this.endString = end;
		
	}
}
