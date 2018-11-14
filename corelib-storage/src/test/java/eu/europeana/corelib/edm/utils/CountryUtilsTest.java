package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.jibx.Country;
import eu.europeana.corelib.definitions.jibx.CountryCodes;
import eu.europeana.corelib.edm.utils.EdmUtils;
import org.junit.Test;

import java.util.Locale;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Patrick Ehlert
 * Created on 28-08-2018
 */
public class CountryUtilsTest {

    @Test
    public void testCapitalizeCountry() {
        String test1 = "United Kingdom of Great Britain and Northern Ireland";
        assertEquals(test1, CountryUtils.capitalizeCountry(test1.toLowerCase(Locale.GERMANY)));
        assertEquals(test1, CountryUtils.capitalizeCountry(test1.toUpperCase(Locale.GERMANY)));

        String test2 = "Holy See (Vatican City State)";
        assertEquals(test2, CountryUtils.capitalizeCountry(test2.toLowerCase(Locale.GERMANY)));
        assertEquals(test2, CountryUtils.capitalizeCountry(test2.toUpperCase(Locale.GERMANY)));

        String test3 = "Macedonia (the former Yugoslav Republic of)";
        assertEquals(test3, CountryUtils.capitalizeCountry(test3.toLowerCase(Locale.GERMANY)));
        assertEquals(test3, CountryUtils.capitalizeCountry(test3.toUpperCase(Locale.GERMANY)));
    }

    /**
     * This tests the EdmUtils.convertToCountry function. The function should return the proper JIBX Country with filled
     * CountryCode when it's provided with a string representation of that CountryCode no matter the exact casing.
     * Otherwise it should return null
     */
    @Test
    public void testConvertToCountryCode() {
        for (CountryCodes countryCode : CountryCodes.values()) {
            String country = countryCode.xmlValue().toLowerCase(Locale.GERMANY);
            Country result = CountryUtils.convertToJibxCountry(country);
            assertNotNull(result);
            assertNotNull(result.getCountry());
            assertEquals(countryCode, result.getCountry());

            country = countryCode.xmlValue().toUpperCase(Locale.GERMANY);
            result = CountryUtils.convertToJibxCountry(country);
            assertNotNull(result);
            assertNotNull(result.getCountry());
            assertEquals(countryCode, result.getCountry());
        }

        assertNull(CountryUtils.convertToJibxCountry("Never never land"));
    }
}
