package eu.europeana.corelib.solr.entity;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.Event;
import eu.europeana.corelib.utils.StringArrayUtils;

@Entity("Event")
public class EventImpl extends ContextualClassImpl implements Event {

	//TODO: FIX LATER TO CONFORM TO OTHER ENTITIES
	private String[] edmHappenedAt;
	private String[] edmOccuredAt;
	private String[] sameAs;
	private String[] dcIdentifier;
	private String[] dctermsHasPart;
	private String[] dctermsIsPartOf;
	private String[] crmP120FOccursBefore;
	private String[] edmHasType;
	private String[] edmIsRelatedTo;
	
	@Override
	public void setEdmHappenedAt(String[] edmHappenedAt) {
		this.edmHappenedAt = edmHappenedAt;
	}

	@Override
	public String[] getEdmHappenedAt() {
		return (StringArrayUtils.isNotBlank(edmHappenedAt) ? this.edmHappenedAt.clone() : null);
	}

	@Override
	public void setEdmOccuredAt(String[] edmOccuredAt) {
		this.edmOccuredAt = edmOccuredAt;
	}

	@Override
	public String[] getEdmOccuredAt() {
		return (StringArrayUtils.isNotBlank(edmOccuredAt) ? this.edmOccuredAt.clone() : null);
	}

	@Override
	public String[] getSameAs() {
		return (StringArrayUtils.isNotBlank(sameAs) ? this.sameAs.clone() : null);
	}

	@Override
	public void setSameAs(String[] sameAs) {
		this.sameAs = sameAs;
	}

	@Override
	public String[] getDcIdentifier() {
		return (StringArrayUtils.isNotBlank(dcIdentifier) ? this.dcIdentifier.clone() : null);
	}

	@Override
	public void setDcIdentifier(String[] dcIdentifier) {
		this.dcIdentifier = dcIdentifier;
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
	public String[] getDctermsIsPartOf() {
		return (StringArrayUtils.isNotBlank(dctermsIsPartOf) ? this.dctermsIsPartOf.clone() : null);
	}

	@Override
	public void setDctermsIsPartOf(String[] dctermsIsPartOf) {
		this.dctermsIsPartOf = dctermsIsPartOf;
	}

	@Override
	public String[] getCrmP120FOccursBefore() {
		return (StringArrayUtils.isNotBlank(crmP120FOccursBefore) ? this.crmP120FOccursBefore.clone() : null);
	}

	@Override
	public void setCrmP120FOccursBefore(String[] crmP120FOccursBefore) {
		this.crmP120FOccursBefore = crmP120FOccursBefore;
	}

	@Override
	public String[] getEdmHasType() {
		return (StringArrayUtils.isNotBlank(edmHasType) ? this.edmHasType.clone() : null);
	}

	@Override
	public void setEdmHasType(String[] edmHasType) {
		this.edmHasType = edmHasType;
	}

	@Override
	public String[] getEdmIsRelatedTo() {
		return (StringArrayUtils.isNotBlank(edmIsRelatedTo) ? this.edmIsRelatedTo.clone() : null);
	}

	@Override
	public void setEdmIsRelatedTo(String[] edmIsRelatedTo) {
		this.edmIsRelatedTo = edmIsRelatedTo;
	}
}
