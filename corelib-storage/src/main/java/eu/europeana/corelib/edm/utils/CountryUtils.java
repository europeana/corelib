package eu.europeana.corelib.edm.utils;

import eu.europeana.metis.schema.jibx.Country;
import eu.europeana.metis.schema.jibx.CountryCodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

/**
 * @author Patrick Ehlert
 * Created on 1-11-2018
 */
public class CountryUtils {

    private static final Logger LOG = LogManager.getLogger(CountryUtils.class);

    private CountryUtils() {
        // empty constructor to prevent initialization
    }

    /**
     * Return provided country name in correct capitalization (as it is used in JIBX and in Solr)
     * @param country
     * @return country name with camel-cased capitalization
     */
    public static String capitalizeCountry(String country) {
        if (StringUtils.isNotEmpty(country)) {
            StringBuilder sb = new StringBuilder();
            String[] splitCountry = country.trim().toLowerCase(Locale.GERMANY).split(" ");
            for (String countryWord : splitCountry) {
                // strip off leading (
                if (countryWord.charAt(0) == '(') {
                    sb.append('(');
                    countryWord = countryWord.substring(1);
                }
                // strip off trailing )
                boolean addTrailingBracket = false;
                if (countryWord.charAt(countryWord.length() - 1) == ')') {
                    addTrailingBracket = true;
                    countryWord = countryWord.substring(0, countryWord.length() - 1);
                }

                // don't capitalize words like 'and', 'of', 'the' and 'former'
                if (StringUtils.equalsIgnoreCase("and", countryWord) ||
                    StringUtils.equalsIgnoreCase("of", countryWord) ||
                    StringUtils.equalsIgnoreCase("the", countryWord) ||
                    StringUtils.equalsIgnoreCase("former", countryWord)) {
                    sb.append(countryWord.toLowerCase(Locale.GERMANY));
                } else {
                    sb.append(StringUtils.capitalize(countryWord));
                }

                if (addTrailingBracket) {
                    sb.append(')');
                }
                sb.append(" ");
            }
            return sb.toString().trim();
        }
        return country;
    }

    /**
     * JIBX uses equals function to generate a CountryCodes enum from a string, so that's why we need to match exactly
     * to the value defined in the JIBX CountryCodes class (i.e. we need to capitalize the first letter of each word)
     * @param country string with country name as it is in JIBX CountryCodes but without proper capitalization
     * @return Country object with filled country code, or null if conversion failed
     */
    public static Country convertToJibxCountry(String country) {
        Country result = null;
        if (StringUtils.isNotEmpty(country)) {
            String converted = capitalizeCountry(country);

            CountryCodes cc = CountryCodes.convert(converted);
            if (cc != null) {
                result = new Country();
                result.setCountry(cc);
            } else {
                LOG.error("Cannot convert country '{}' to JIBX country code! (converted country = {})", country, converted);
            }
        }
        return result;
    }
}
