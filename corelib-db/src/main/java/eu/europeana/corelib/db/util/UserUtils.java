package eu.europeana.corelib.db.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * @author Willem-Jan Boogerd (www.eledge.net/contact).
 */
public class UserUtils {

    /**
     * Hashing password using ShaPasswordEncoder.
     *
     * @param password The password in initial form.
     * @return Hashed password as to be stored in database
     */
    public static String hashPassword(String password) {
        if (StringUtils.isNotBlank(password)) {
            return getPasswordEncoder().encodePassword(password, null);
    }
        return null;
    }

    public static BasePasswordEncoder getPasswordEncoder() {
        return new ShaPasswordEncoder();
    }

}
