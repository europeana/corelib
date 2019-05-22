package eu.europeana.corelib.lookup.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class to read mapping files created before SIPCreator
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class PreSipCreatorUtils extends MappingParser {

	private String repository;
	private final static String INPUT_FOLDER = "/mappings/";
	private final static String SUFFIX = "_mapping.txt";
	private final static String IDENTIFIER = "(ID)";
	private final static String RECORD_SPLITTER = "=>";
	private final static String LINE_SPLITTER = "\n";

	@Override
	public String getHashField(String collectionId, String fileName) {
		String file = findFile(collectionId+"_",fileName);
		String inputString = this.readFile(file);
		if (inputString != null) {
			String[] lines = StringUtils.split(inputString, LINE_SPLITTER);
			for (String line : lines) {
				if (StringUtils.contains(line, IDENTIFIER)
						&& StringUtils.contains(line, RECORD_SPLITTER)) {
					return StringUtils.replace(
							StringUtils.substringBetween(line, RECORD_SPLITTER,
									IDENTIFIER).trim(), ":", "_");
				}
			}
		}
		return null;
	}
	private String findFile(String collectionId, String fileName) {
		String file = repository + collectionId + INPUT_FOLDER
		+ fileName + SUFFIX;
		if(new File(file).exists()){
			return file;
		}
		if (new File(repository + collectionId + INPUT_FOLDER).exists()){
		for(File fFile:new File(repository + collectionId + INPUT_FOLDER).listFiles()){
			if (StringUtils.contains(fFile.getName(),fileName+"_")){
				return fFile.getName();
			}
			
		}
		}
		return null;
	}
	/**
	 * Specify the place the mappings are held
	 * @param repository
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
}
