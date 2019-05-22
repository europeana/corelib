package eu.europeana.corelib.lookup.utils;

import java.io.UnsupportedEncodingException;
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
	/**
	 * Create a hash from a String based on the SHA-256 algorithm 
	 * @param uri The string used for the hash creation
	 * @return The hash produced
	 */
	  public static String createHashSHA256(String uri) {
		  try {
		  MessageDigest digest = MessageDigest.getInstance("SHA-256"); 
		  byte[] raw=digest.digest(uri.getBytes("UTF-8"));
	       
	        byte[] hex = new byte[2 * raw.length];
	        int index = 0;
	        for (byte b : raw) {
	            int v = b & 0xFF;
	            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
	            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
	        }
	      
	            return new String(hex, "ASCII");
	        }
	        catch (UnsupportedEncodingException e) {
	            e.printStackTrace(System.err);
	            throw new RuntimeException(e);
	        } catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return null;
	    }

	    private static final byte[] HEX_CHAR_TABLE = {
	            (byte) '0', (byte) '1', (byte) '2', (byte) '3',
	            (byte) '4', (byte) '5', (byte) '6', (byte) '7',
	            (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
	            (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F'
	    };

}