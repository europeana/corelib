package eu.europeana.corelib.tools.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashUtilsTest {

	private final static String HASH = "98D5BC61D9FDFD00B075E5B6899C55DB874AE7AD";
	@Test
	public void testHashCreation() {
		//Using real life data
		String identifier = "http://gallica.bnf.fr/ark:/12148/btv1b9059254q/f1.zoom";
		assertEquals(HASH, HashUtils.createHash(identifier));
	}

}
