package eu.europeana.corelib.tools.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SipCreatorUtils {

	private static String repository;
	private final static String INPUT_FOLDER = "/input_source/";
	private final static String SUFFIX = ".xml.mapping";
	private final static String BEGIN_HASH_FUNCTION = "createEuropeanaURI(input.";
	private final static String END_HASH_FUNCTION = ")";
	private String hash;
	
	public String getHashField(String collectionId, String fileName) {
		String inputString = readFile(repository + collectionId + INPUT_FOLDER
				+ fileName + SUFFIX);
		StringUtils.substringBetween(inputString, BEGIN_HASH_FUNCTION,
				END_HASH_FUNCTION);
		return "";
	}

	private String readFile(String mappingFile) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strFileContents;
	}

	public List<String> retrieveOldHashes(String value, String collectionId){
		List<String> oldHashes = new ArrayList<String>();
		if(hashExists(value, collectionId)){
			oldHashes.add(hash);
		}
		return oldHashes;
	}
	
	private boolean hashExists(String value, String collectionId){
		hash = "";
		return false;
	}
	
	
	private static class HashUtils {
		static final String HEX_STRING = "0123456789ABCDEF";

		private static String createHash(String field)  {
			StringBuilder hash = new StringBuilder();
			try {

				MessageDigest digest = MessageDigest.getInstance("SHA-1");
				hash = new StringBuilder();
				for (Byte b : digest.digest(field.getBytes())) {
					hash.append(HEX_STRING.charAt(((b & 0xF0) >> 4))).append(
							HEX_STRING.charAt(((b & 0x0F))));
				}
			} catch (NoSuchAlgorithmException ex) {
			}
			return hash.toString();
		}
		
		public static boolean equals(String value, String hash){
			return StringUtils.equals(createHash(value),hash);
		}
		
	}
}
