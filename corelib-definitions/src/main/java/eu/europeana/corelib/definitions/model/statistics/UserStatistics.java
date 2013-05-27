package eu.europeana.corelib.definitions.model.statistics;

/**
 * User statistics contains user name (name) API key (apiKey) and count (count).
 *
 * @author peter.kiraly@kb.nl
 */
public class UserStatistics {

	private String name;
	private String apiKey;
	private long count;

	public UserStatistics(String name, String apiKey, long count) {
		super();
		this.name = name;
		this.apiKey = apiKey;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public String getApiKey() {
		return apiKey;
	}

	public long getCount() {
		return count;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setCount(long count) {
		this.count = count;
	}

	
}
