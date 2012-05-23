package eu.europeana.corelib.tools.utils;

import org.apache.commons.lang.StringUtils;

public class PreSipCreatorUtils extends MappingParser {

	private static String repository;
	private final static String INPUT_FOLDER = "/mappings/";
	private final static String SUFFIX = "_mapping.txt";
	private final static String IDENTIFIER = "(ID)";
	private final static String RECORD_SPLITTER = "=>";

	@Override
	public String getHashField(String collectionId, String fileName) {
		String inputString = this.readFile(repository + collectionId
				+ INPUT_FOLDER + fileName + SUFFIX);
		return inputString == null ? null : StringUtils.replace(StringUtils
				.substringBetween(inputString, RECORD_SPLITTER, IDENTIFIER)
				.trim(), ":", "_");
	}

}
