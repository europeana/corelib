package eu.europeana.corelib.tools.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashUtilsTest {

	private final static String HASH = "98D5BC61D9FDFD00B075E5B6899C55DB874AE7AD";
	//PRE-SIPCREATOR
	private final static String HASH2 = "D1F3E145D8B1B11804267529669CE0E97E48FF37";
	@Test
	public void testHashCreation() {
		//Using real life data
		String identifier = "http://gallica.bnf.fr/ark:/12148/btv1b9059254q/f1.zoom";
		
		String identifier2 = "http://www.landesarchiv-bw.de/plink/?f=5-170636";
		assertEquals(HASH, HashUtils.createHash(identifier));
		assertEquals(HASH2, HashUtils.createHash(identifier2));
	}

}
