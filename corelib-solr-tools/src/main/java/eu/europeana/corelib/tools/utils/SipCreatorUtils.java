package eu.europeana.corelib.tools.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class SipCreatorUtils {

	private static String repository;
	private final static String INPUT_FOLDER = "/input_source/";
	private final static String SUFFIX = ".xml.mapping";
	private final static String BEGIN_HASH_FUNCTION = "createEuropeanaURI(input.record.";
	
	private final static String END_HASH_FUNCTION = ")";
	
	
	//TODO: is that unique?
	
	/**
	 * Retrieve the field responsible for hash generation in SIPCreator
	 * @param collectionId The collectionID (each collection has a different mapping)
	 * @param fileName The complete filename of the collection
	 * @return The field that was used for the hash creation
	 */
	public static String getHashField(String collectionId, String fileName) {
		String inputString = readFile(repository + collectionId + INPUT_FOLDER
				+ fileName + SUFFIX);
		return StringUtils.substringBetween(inputString, BEGIN_HASH_FUNCTION,
				END_HASH_FUNCTION);
	}

	private static String readFile(String mappingFile) {
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strFileContents;
	}
	
	public static void setRepository(String repository) {
		SipCreatorUtils.repository = repository;
	}

}
