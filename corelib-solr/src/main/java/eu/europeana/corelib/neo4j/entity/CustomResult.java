package eu.europeana.corelib.neo4j.entity;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize
public class CustomResult {

	List<String> columns;
	List<Map<String,List<String>>> data;
	
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
