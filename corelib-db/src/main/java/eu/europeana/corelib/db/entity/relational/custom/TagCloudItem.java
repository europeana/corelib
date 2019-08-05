package eu.europeana.corelib.db.entity.relational.custom;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Tag Cloud item
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@JsonInclude(Include.NON_EMPTY)
@Deprecated
public class TagCloudItem {
	
	private String label;
	
	private long count;
	
	/**
	 * CONSTRUCTOR
	 */
	public TagCloudItem(String label, Long count) {
		this.label = label;
		this.count = count.longValue();
	}
	
	/**
	 * GETTERS and SETTERS
	 */
	public String getLabel() {
		return label;
	}
	
	public long getCount() {
		return count;
	}

}
