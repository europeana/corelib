package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.corelib.utils.StringArrayUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * @see eu.europeana.corelib.definitions.edm.entity.Concept
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
@Entity("Concept")
public class ConceptImpl extends ContextualClassImpl implements Concept {

	private String[] broader;
	private String[] narrower;
	private String[] related;
	private String[] broadMatch;
	private String[] narrowMatch;
	private String[] exactMatch;
	private String[] relatedMatch;
	private String[] closeMatch;
	private Map<String,List<String>> notation;
	private String[] inScheme;

	@Override
	public String[] getBroader() {
		return (StringArrayUtils.isNotBlank(broader) ? this.broader.clone() : null);
	}

	@Override
	public void setBroader(String[] broader) {
		this.broader = broader!=null?broader.clone():null;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			return this.getAbout().equals(((ConceptImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getAbout().hashCode();
	}

	@Override
	public String[] getNarrower() {
		return (StringArrayUtils.isNotBlank(narrower) ? this.narrower.clone() : null);
	}

	@Override
	public void setNarrower(String[] narrower) {
		this.narrower = narrower != null ? narrower.clone() : null;
	}

	@Override
	public String[] getRelated() {
		return (StringArrayUtils.isNotBlank(related) ? this.related.clone() : null);
	}

	@Override
	public void setRelated(String[] related) {
		this.related = related != null ? related.clone() : null;
	}

	@Override
	public String[] getBroadMatch() {
		return (StringArrayUtils.isNotBlank(broadMatch) ? this.broadMatch.clone() : null);
	}

	@Override
	public void setBroadMatch(String[] broadMatch) {
		this.broadMatch = broadMatch != null ? broadMatch.clone() : null;
	}

	@Override
	public String[] getNarrowMatch() {
		return (StringArrayUtils.isNotBlank(narrowMatch) ? this.narrowMatch.clone() : null);
	}

	@Override
	public void setNarrowMatch(String[] narrowMatch) {
		this.narrowMatch = narrowMatch != null ? narrowMatch.clone() : null;
	}

	@Override
	public String[] getRelatedMatch() {
		return (StringArrayUtils.isNotBlank(relatedMatch) ? this.relatedMatch.clone() : null);
	}

	@Override
	public void setRelatedMatch(String[] relatedMatch) {
		this.relatedMatch = relatedMatch != null ? relatedMatch.clone() : null;
	}

	@Override
	public String[] getExactMatch() {
		return (StringArrayUtils.isNotBlank(exactMatch) ? this.exactMatch.clone() : null);
	}

	@Override
	public void setExactMatch(String[] exactMatch) {
		this.exactMatch = exactMatch != null?exactMatch.clone() : null;
	}

	@Override
	public String[] getCloseMatch() {
		return (StringArrayUtils.isNotBlank(closeMatch) ? this.closeMatch.clone() : null);
	}

	@Override
	public void setCloseMatch(String[] closeMatch) {
		this.closeMatch = closeMatch != null ? closeMatch.clone() : null;
	}

	@Override
	public Map<String,List<String>> getNotation() {
		return this.notation;
	}

	@Override
	public void setNotation(Map<String,List<String>> notation) {
		this.notation = notation;
	}

	@Override
	public String[] getInScheme() {
		return (StringArrayUtils.isNotBlank(inScheme) ? this.inScheme.clone() : null);
	}

	@Override
	public void setInScheme(String[] inScheme) {
		this.inScheme = inScheme != null ? inScheme.clone() : null;
	}
}
