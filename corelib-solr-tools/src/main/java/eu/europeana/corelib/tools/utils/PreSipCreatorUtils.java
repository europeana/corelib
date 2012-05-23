package eu.europeana.corelib.tools.utils;

import org.apache.commons.lang.StringUtils;

public class PreSipCreatorUtils extends MappingParser {

	private String repository;
	private final static String INPUT_FOLDER = "/mappings/";
	private final static String SUFFIX = "_mapping.txt";
	private final static String IDENTIFIER = "(ID)";
	private final static String RECORD_SPLITTER = "=>";
	private final static String LINE_SPLITTER = "\n";

	@Override
	public String getHashField(String collectionId, String fileName) {
		String inputString = this.readFile(repository + collectionId
				+ INPUT_FOLDER + fileName + SUFFIX);
		if (inputString != null) {
			String[] lines = StringUtils.split(inputString, LINE_SPLITTER);
			for (String line : lines) {
				if (StringUtils.isNotBlank(line)
						&& StringUtils.contains(line, IDENTIFIER)
						&& StringUtils.contains(line, RECORD_SPLITTER)) {
					return StringUtils.replace(StringUtils.substringBetween(line, RECORD_SPLITTER,
							IDENTIFIER).trim(),":","_");
				}
			}
		}
		return null;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
}
