package eu.europeana.corelib.definitions.solr.model;

import java.security.SecureRandom;

/**
 * Generates a random alpha-numerical string (used in random seeds sent to Solr)
 * @author Patrick Ehlert
 * Created on 9-07-2019
 */
public class RandomSeed {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String randomString( int len ){
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ ) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }


}
