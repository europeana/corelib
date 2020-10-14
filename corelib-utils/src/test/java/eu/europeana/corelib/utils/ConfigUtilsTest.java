package eu.europeana.corelib.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ConfigUtilsTest {

    @Test
    public void shouldMatchMapKeysUsingRegex() {
        String prefix1 = "api2_baseUrl";
        String prefix2 = "apiGateway_baseUrl";
        String prefix3 = "portal_baseUrl";

        String regex = String.format("^route\\d+_(%s|%s|%s)", prefix1, prefix2, prefix3);
        Map<String, String> searchMap = new HashMap<>();
        // simulates env variables used by Vcap properties loader
        searchMap.put("route1_api2_baseUrl", "");
        searchMap.put("route2_api2_baseUrl", "");
        searchMap.put("route2_portal_baseUrl", "");
        searchMap.put("route4_api2_baseUrl", "");
        searchMap.put("route8_apiGateway_baseUrl", "");
        //non-matches
        searchMap.put("route2_other_prop", "");
        searchMap.put("route_portal_baseUrl", "");
        searchMap.put("routes_apiGateway_baseUrl", "");

        List<String> matches = ConfigUtils.getMatchingKeys(searchMap, regex);

        assertEquals(5, matches.size());

    }
}