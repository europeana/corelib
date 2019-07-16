package eu.europeana.corelib.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import eu.europeana.corelib.utils.EuropeanaUriUtils;
import static org.junit.Assert.*;

/**
 * URI Schema util classes
 * @author Srishti Singh
 *
 */
public class URISchemaUtilsTest {
	
	@Test
	public void absoluteURITest() {
		assertTrue(EuropeanaUriUtils.isAbsoluteIRI("https://"));

		assertTrue(EuropeanaUriUtils.isAbsoluteIRI("http://"));

		assertTrue(EuropeanaUriUtils.isAbsoluteIRI("session://"));

		assertTrue( EuropeanaUriUtils.isAbsoluteIRI("bitcoin:"));

		assertFalse(EuropeanaUriUtils.isAbsoluteIRI("5fdh5672"));

		assertFalse( EuropeanaUriUtils.isAbsoluteIRI(""));

	}

	@Test
	public void RealtiveURITest() {
		assertTrue( EuropeanaUriUtils.isRelativeIRI("/proxy"));

		assertTrue( EuropeanaUriUtils.isRelativeIRI("../"));

		assertTrue( EuropeanaUriUtils.isRelativeIRI("#"));

		assertTrue( EuropeanaUriUtils.isRelativeIRI("./"));

		assertFalse( EuropeanaUriUtils.isRelativeIRI("&&"));

		assertFalse( EuropeanaUriUtils.isRelativeIRI("*^"));

		assertFalse( EuropeanaUriUtils.isRelativeIRI(""));

	}

}
