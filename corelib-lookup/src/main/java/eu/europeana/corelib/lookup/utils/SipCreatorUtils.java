package eu.europeana.corelib.lookup.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class to read mappings developed by SIPCreator
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class SipCreatorUtils extends MappingParser {

	private String repository;
	private final static String INPUT_FOLDER = "/input_source/";
	private final static String SUFFIX = ".xml.mapping";
	private final static String BEGIN_HASH_FUNCTION_RECORD = "createEuropeanaURI(input.record.";
	private final static String BEGIN_HASH_FUNCTION_NO_RECORD = "createEuropeanaURI(";
	private final static String END_HASH_FUNCTION = ")";

	@Override
	public String getHashField(String collectionId, String fileName) {

		String file = findFile(collectionId, fileName);

		String inputString = this.readFile(file);
		if (inputString != null) {

			return StringUtils.substringAfterLast(StringUtils.substringBetween(inputString, BEGIN_HASH_FUNCTION_NO_RECORD, END_HASH_FUNCTION), ".");

		}
		return null;
		// return inputString ==
		// null?null:(StringUtils.substringBetween(inputString,
		// BEGIN_HASH_FUNCTION_RECORD,
		// END_HASH_FUNCTION)==null?StringUtils.substringBetween(inputString,
		// BEGIN_HASH_FUNCTION_NO_RECORD,
		// END_HASH_FUNCTION):(StringUtils.substringBetween(inputString,
		// BEGIN_HASH_FUNCTION_RECORD,
		// END_HASH_FUNCTION)));
	}

	private String findFile(String collectionId, String fileName) {
		// String file = repository + collectionId + INPUT_FOLDER
		// + fileName + SUFFIX;
		// if(new File(file).exists()){
		// return file;
		// }
		// if (new File(repository + collectionId + INPUT_FOLDER).exists()){
		// for(File fFile:new File(repository + collectionId +
		// INPUT_FOLDER).listFiles()){
		// if (StringUtils.contains(fFile.getName(),fileName+"_")){
		// return fFile.getName();
		// }
		//
		// }
		// }
		// return null;
		String[] folders = new File(repository).list();
		for (String folder : folders) {
			if (StringUtils.contains(folder,
					StringUtils.substringBefore(collectionId, "_") + "_")) {
				return new File(repository + folder + INPUT_FOLDER).listFiles()[0]
						.getAbsolutePath();
			}
		}
		return null;
	}

	/**
	 * Specify the place the mappings are held
	 * 
	 * @param repository
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}

}
