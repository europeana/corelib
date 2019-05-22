package eu.europeana.corelib.neo4j;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * Record list wrapper
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
@JsonSerialize
@JsonDeserialize
public class RecordList {

	@JsonProperty(value = "records")
	private List<Record> records;

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
	
}
