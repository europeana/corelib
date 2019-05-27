package eu.europeana.corelib.neo4j.entity;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A class representing the Result from the neo4j call
 * @author ymamakis
 *
 */
@JsonSerialize
public class CustomResult {

	private List<String> columns;
	private List<Map<String,List<String>>> data;
	
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	public List<Map<String, List<String>>> getData() {
		return data;
	}
	public void setData(List<Map<String, List<String>>> data) {
		this.data = data;
	}
}
