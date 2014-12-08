/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
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
