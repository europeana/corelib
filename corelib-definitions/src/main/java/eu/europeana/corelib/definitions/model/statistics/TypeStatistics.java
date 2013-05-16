package eu.europeana.corelib.definitions.model.statistics;

/**
 * Implementation of TypeStatistics.
 * 
 * @author peter.kiraly@kb.nl
 */
public class TypeStatistics {

	private String recordType;
	private String profile;
	private long count;

	public TypeStatistics(String recordType, String profile, long count) {
		super();
		this.recordType = recordType;
		this.profile = profile;
		this.count = count;
	}

	public String getRecordType() {
		return recordType;
	}

	public String getProfile() {
		return profile;
	}

	public long getCount() {
		return count;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
