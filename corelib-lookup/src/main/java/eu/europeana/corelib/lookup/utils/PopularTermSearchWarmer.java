package eu.europeana.corelib.lookup.utils;

/**
 * Utility to create the static warmer section in SOLR according to Google Analytics term results.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class PopularTermSearchWarmer {

	private final static String PREFIX="<lst>\n\t<str name=\"q\">";
	private final static String SUFFIX="</str>\n\t<str name=\"sort\">score desc</str>" +
			"\n\t<str name=\"facet.field\">LANGUAGE</str>" +
			"\n\t<str name=\"facet.field\">TYPE</str>" +
			"\n\t<str name=\"facet.field\">YEAR</str>" +
			"\n\t<str name=\"facet.field\">PROVIDER</str>" +
			"\n\t<str name=\"facet.field\">DATA_PROVIDER</str>" +
			"\n\t<str name=\"facet.field\">COUNTRY</str>" +
			"\n\t<str name=\"facet.field\">RIGHTS</str>" +
			"\n\t<str name=\"facet.field\">UGC</str>" +
			"\n\t<str name=\"facet.limit\">750</str>"+
			"\n\t<str name=\"rows\">24</str>" +
			"\n\t<str name=\"start\">0</str>" +
			"\n\t<str name=\"facet\">true</str>\n"+
			"</lst>\n";
	
	/**
	 * @param term 
	 * 			The query that will be used in explicit warming
	 */
	public static String createTermsSection(String term){
		StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);
		sb.append(term);
		sb.append(SUFFIX);
		return sb.toString();
	}
}
