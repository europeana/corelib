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
package eu.europeana.corelib.lookup.utils;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Abstract class that reads a SIPCreator mapping
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public abstract class MappingParser {

	private static final Logger LOG = Logger.getLogger(MappingParser.class);

	protected String repository;
	/**
	 * Retrieve the field responsible for hash generation in SIPCreator
	 * @param collectionId The collectionID (each collection has a different mapping)
	 * @param fileName The complete filename of the collection
	 * @return The field that was used for the hash creation
	 */
	public abstract String getHashField(String collectionId, String fileName);
	
	/**
	 * Read a SIPCreator mapping file
	 * @param mappingFile
	 * @return
	 */
	public String readFile(String mappingFile) {
		if(mappingFile != null){
			StringBuilder strFileContents = new StringBuilder();
			try (FileInputStream fin = new FileInputStream(mappingFile);
				 BufferedInputStream bin = new BufferedInputStream(fin)){
				byte[] contents = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = bin.read(contents)) != -1) {
					strFileContents.append( new String(contents, 0, bytesRead));
				}
				fin.close();
				bin.close();
			} catch (IOException e) {
				LOG.error("Error reading mapping file" +mappingFile, e);
				return null;
			}
			return strFileContents.toString();
			}
		return null;
	}
	
	
}
