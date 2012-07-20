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

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.solr.entity.Concept
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
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
	private String[] notation;
	private String[] inScheme;

	@Override
	public String[] getBroader() {
		return (StringArrayUtils.isNotBlank(broader) ? this.broader.clone() : null);
	}

	@Override
	public void setBroader(String[] broader) {
		this.broader = broader.clone();
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
		this.narrower=narrower;
		
	}

	@Override
	public String[] getRelated() {
		return (StringArrayUtils.isNotBlank(related) ? this.related.clone() : null);
	}

	@Override
	public void setRelated(String[] related) {
		this.related = related;
		
	}

	@Override
	public String[] getBroadMatch() {
		
		return (StringArrayUtils.isNotBlank(broadMatch) ? this.broadMatch.clone() : null);
	}

	@Override
	public void setBroadMatch(String[] broadMatch) {
		this.broadMatch = broadMatch;
		
	}

	@Override
	public String[] getNarrowMatch() {
		
		return (StringArrayUtils.isNotBlank(narrowMatch) ? this.narrowMatch.clone() : null);
	}

	@Override
	public void setNarrowMatch(String[] narrowMatch) {
		this.narrowMatch = narrowMatch;
		
	}

	@Override
	public String[] getRelatedMatch() {
		return (StringArrayUtils.isNotBlank(relatedMatch) ? this.relatedMatch.clone() : null);
	}

	@Override
	public void setRelatedMatch(String[] relatedMatch) {
		this.relatedMatch = relatedMatch;
	}

	@Override
	public String[] getExactMatch() {
		return (StringArrayUtils.isNotBlank(exactMatch) ? this.exactMatch.clone() : null);
	}

	@Override
	public void setExactMatch(String[] exactMatch) {
		this.exactMatch = exactMatch;
		
	}

	@Override
	public String[] getCloseMatch() {
		return (StringArrayUtils.isNotBlank(closeMatch) ? this.closeMatch.clone() : null);
	}

	@Override
	public void setCloseMatch(String[] closeMatch) {
		this.closeMatch = closeMatch;
	}

	@Override
	public String[] getNotation() {
		return (StringArrayUtils.isNotBlank(notation) ? this.notation.clone() : null);
	}

	@Override
	public void setNotation(String[] notation) {
		this.notation = notation;
	}

	@Override
	public String[] getInScheme() {
		return (StringArrayUtils.isNotBlank(inScheme) ? this.inScheme.clone() : null);
	}

	@Override
	public void setInScheme(String[] inScheme) {
		this.inScheme = inScheme;
	}
}
