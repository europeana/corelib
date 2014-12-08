package eu.europeana.corelib.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Ignore;

@Ignore
public class JsonReader {

	
	public static List<Record> loadRecords(){
		
		try {
			RecordList records = new ObjectMapper().readValue(new File("src/test/resources/records.json"), RecordList.class);
			return records.getRecords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
