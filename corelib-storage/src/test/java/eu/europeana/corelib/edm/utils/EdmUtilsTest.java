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
 * <p>
 * Created on 28-08-2018
 */
public class EdmUtilsTest {

    /**
     * This tests the EdmUtils.convertToCountry function. The function should return the proper JIBX Country with filled
     * CountryCode when it's provided with a string representation of that CountryCode no matter the exact casing.
     * Otherwise it should return null
     */
    @Test
    public void testConvertToCountryCode() {
        for (CountryCodes countryCode : CountryCodes.values()) {
            String country = countryCode.xmlValue().toLowerCase(Locale.GERMANY);
            Country result = EdmUtils.convertToCountry(country);
            assertNotNull(result);
            assertNotNull(result.getCountry());
            assertEquals(countryCode, result.getCountry());

            country = countryCode.xmlValue().toUpperCase(Locale.GERMANY);
            result = EdmUtils.convertToCountry(country);
            assertNotNull(result);
            assertNotNull(result.getCountry());
            assertEquals(countryCode, result.getCountry());

            assertNull(EdmUtils.convertToCountry("Never never land"));
        }
    }
}
