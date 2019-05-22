package eu.europeana.corelib.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;

/**
 * Read Unit test records from file
 * @author YOrgos.Mamakis@ europeana.eu
 *
 */
@Ignore
public class JsonReader {

	/**
	 * Method that loads the unit test records for the unit tests
	 * @return
	 */
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
