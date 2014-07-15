package eu.europeana.corelib.neo4j.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class IndexObject {

	private long length;
	private long time;
	
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
	
}
