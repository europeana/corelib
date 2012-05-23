package eu.europeana.corelib.tools.utils;

import org.apache.commons.lang.StringUtils;

public class SipCreatorUtils extends MappingParser{

	private static String repository;
	private final static String INPUT_FOLDER = "/input_source/";
	private final static String SUFFIX = ".xml.mapping";
	private final static String BEGIN_HASH_FUNCTION_RECORD = "createEuropeanaURI(input.record.";
	private final static String BEGIN_HASH_FUNCTION_NO_RECORD = "createEuropeanaURI(input.";
	private final static String END_HASH_FUNCTION = ")";
	
	
	//TODO: is that unique?
	
	@Override
	public String getHashField(String collectionId, String fileName) {
		String inputString = this.readFile(repository + collectionId + INPUT_FOLDER
				+ fileName + SUFFIX);
		
		return inputString == null?null:(StringUtils.substringBetween(inputString, BEGIN_HASH_FUNCTION_RECORD,
				END_HASH_FUNCTION)==null?StringUtils.substringBetween(inputString, BEGIN_HASH_FUNCTION_NO_RECORD,
						END_HASH_FUNCTION):(StringUtils.substringBetween(inputString, BEGIN_HASH_FUNCTION_RECORD,
								END_HASH_FUNCTION)));
	}

	public void setRepository(String repository) {
		SipCreatorUtils.repository = repository;
	}
	
	

}
