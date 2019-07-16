package eu.europeana.corelib.utils;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * EuropeanaID creator class
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */

public class EuropeanaUriUtils {

	private final static String REPLACEMENT = "_";

	private static Charset UTF8  = Charset.forName("UTF-8");
	private static String  P_STR = "^([a-zA-Z][a-zA-Z+-.]*):.*$";

	public  static Pattern            PATTERN = Pattern.compile(P_STR);
	public  static Collection<String> SCHEMES = loadSchemes();

	private EuropeanaUriUtils() {

	}

	/**
	 * Create the EuropeanaID from the collection ID and record ID
	 * 
	 * @param collectionId
	 *            The collection ID
	 * @param recordId
	 *            The record ID (unique local identifier of a collection record)
	 * @return The Europeana compatible ID
	 */

	public static String createSanitizedEuropeanaId(String collectionId, String recordId) {
		return "/" + sanitizeCollectionId(collectionId) + "/" + sanitizeRecordId(recordId);
	}

	public static String createEuropeanaId(String collectionId, String recordId){
		return "/" + collectionId + "/" + recordId;
	}
	
	private static String sanitizeRecordId(String recordId) {

		recordId = StringUtils.startsWith(recordId, "http://") ? StringUtils
				.substringAfter(
						StringUtils.substringAfter(recordId, "http://"), "/")
				: recordId;
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9_]");
		Matcher matcher = pattern.matcher(recordId);
		recordId = matcher.replaceAll(REPLACEMENT);
		return recordId;
	}

	private static String sanitizeCollectionId(String collectionId) {
		Pattern pattern = Pattern.compile("[a-zA-Z]");
		Matcher matcher = pattern.matcher(collectionId.substring(
				collectionId.length() - 1, collectionId.length()));
		return matcher.find() ? StringUtils.substring(collectionId, 0,
				collectionId.length() - 1) : collectionId;
	}

	public static String encode(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return value;
	}

	public static String decode(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return value;
	}


	public static boolean isUri(String str) {
		return ( isRelativeIRI(str) || isAbsoluteIRI(str) );
	}

	//Loads the schemas from the  iri.schemes.cfg in resource folder

	private static Collection<String> loadSchemes()
	{
		Collection<String> schemes = new TreeSet<String>();
		try
		{
			URL url = EuropeanaUriUtils.class.getClassLoader().getResource("iri.schemes.cfg");
			CSVParser parser = CSVParser.parse(url, UTF8, CSVFormat.EXCEL);
			int i = 0;
			for ( CSVRecord record : parser.getRecords() )
			{
				if ( i++ == 0 ) { continue; }
				schemes.add(record.get(0));
			}
		}
		catch (IOException e) {}

		return schemes;
	}

	// will check is it's a absolute or relative URI

	public static boolean isAbsoluteIRI(String iri)
	{
		Matcher m = PATTERN.matcher(iri);
		return ( m.find() ? SCHEMES.contains(m.group(1)) : false );
	}

	public static boolean isRelativeIRI(String iri)
	{
		return ( iri.startsWith("/")   || iri.startsWith("#")
				|| iri.startsWith("../") || iri.startsWith("./") );
	}

}
