package eu.europeana.corelib.tools.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash Util class
 * @author yorgos.mamakis@ kb.nl
 *
 */
public final class HashUtils {
	static final String HEX_STRING = "0123456789ABCDEF";

	/**
	 * Create a hash from a String based on the SHA-1 algorithm 
	 * @param field The string used for the hash creation
	 * @return The hash produced
	 */
	public static String createHash(String field) {
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

}