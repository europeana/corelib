package eu.europeana.corelib.tools.utils;

import org.junit.Assert;
import org.junit.Test;

import eu.europeana.corelib.lookup.utils.PopularTermSearchWarmer;

public class PopularTermSearchgWarmerTest {

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
	
	@Test
	public void test(){
		String termWarmer = PopularTermSearchWarmer.createTermsSection("test");
		Assert.assertEquals(PREFIX+"test"+SUFFIX,termWarmer);
	}
}
