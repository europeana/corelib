package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.utils.StringArrayUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * @see eu.europeana.corelib.definitions.edm.entity.Timespan
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonInclude(Include.NON_EMPTY)
@Entity("Timespan")
public class TimespanImpl extends ContextualClassImpl implements
		eu.europeana.corelib.definitions.edm.entity.Timespan {

	private Map<String,List<String>> begin;
	private Map<String,List<String>> end;
	private Map<String,List<String>> isPartOf;
	private Map<String,List<String>> dctermsHasPart;

	private String[] owlSameAs;

	@Override
	public Map<String,List<String>> getBegin() {
		return this.begin;
	}

	@Override
	public Map<String,List<String>> getEnd() {
		return this.end;
	}

	@Override
	public Map<String,List<String>> getIsPartOf() {
		return this.isPartOf;
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
	public void setIsPartOf(Map<String,List<String>> isPartOf) {
		this.isPartOf = isPartOf;
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
	public Map<String,List<String>> getDctermsHasPart() {
		return this.dctermsHasPart;
	}

	@Override
	public void setDctermsHasPart(Map<String,List<String>> dctermsHasPart) {
		this.dctermsHasPart = dctermsHasPart;
	}

	@Override
	public String[] getOwlSameAs() {
		return (StringArrayUtils.isNotBlank(owlSameAs) ? this.owlSameAs.clone() : null);
	}

	@Override
	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs!=null?owlSameAs.clone():null;
	}

	
}
