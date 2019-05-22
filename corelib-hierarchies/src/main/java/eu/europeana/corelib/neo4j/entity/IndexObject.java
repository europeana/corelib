package eu.europeana.corelib.neo4j.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A class representing the index of a node including time it took for retrieval
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
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
