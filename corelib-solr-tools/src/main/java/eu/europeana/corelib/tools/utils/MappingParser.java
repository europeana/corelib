package eu.europeana.corelib.tools.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class MappingParser {
	/**
	 * Retrieve the field responsible for hash generation in SIPCreator
	 * @param collectionId The collectionID (each collection has a different mapping)
	 * @param fileName The complete filename of the collection
	 * @return The field that was used for the hash creation
	 */
	public abstract String getHashField(String collectionId, String fileName);
	
	public String readFile(String mappingFile) {
		String strFileContents = "";
		FileInputStream fin;
		try {
			fin = new FileInputStream(mappingFile);
			BufferedInputStream bin = new BufferedInputStream(fin);
			byte[] contents = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = bin.read(contents)) != -1) {
				strFileContents += new String(contents, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return strFileContents;
	}
	
	
}
