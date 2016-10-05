package eu.europeana.corelib.web.model.rights;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RightReusabilityCategorizerTest {

	@Before
	public void before() {
		RightReusabilityCategorizer.setPermissionStrategy(RightReusabilityCategorizer.PERMISSION_STRATEGY_NEGATIVE_ALL);
	}

	/**
	 * Testing Open rights Solr query (getOpenStringRightsQuery())
	 */
	@Test
	public void testGetOpenRightsQuery() {
		String query = "RIGHTS:(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/*)";

		assertEquals(query, RightReusabilityCategorizer.getOpenRightsQuery());
	}

	/**
	 * Testing restricted rights Solr query (getRestrictedRightsQuery())
	 */
	@Test
	public void testGetRestrictedRightsQuery() {
		String query = "RIGHTS:(http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*)";

		assertEquals(query, RightReusabilityCategorizer.getRestrictedRightsQuery());
	}

	/**
	 * Testing all reusable rights Solr query (getAllRightsQuery())
	 */
	@Test
	public void testGetAllRightsQuery() {
		String query = "RIGHTS:(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*)";

		assertEquals(query, RightReusabilityCategorizer.getAllRightsQuery());
	}

	/**
	 * Testing permission query (getPermissionRightsQuery())
	 */
	@Test
	public void testGetPermissionRightsQuery() {
		assertEquals(RightReusabilityCategorizer.getPermissionStrategy(), RightReusabilityCategorizer.PERMISSION_STRATEGY_NEGATIVE_ALL);

		String query = "RIGHTS:(NOT(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*))";

		RightReusabilityCategorizer.setPermissionStrategy(RightReusabilityCategorizer.PERMISSION_STRATEGY_NEGATIVE_ALL);
		assertEquals(RightReusabilityCategorizer.getPermissionStrategy(), RightReusabilityCategorizer.PERMISSION_STRATEGY_NEGATIVE_ALL);
		assertEquals(query, RightReusabilityCategorizer.getPermissionRightsQuery());

		query = "RIGHTS:(NOT(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*))";

		RightReusabilityCategorizer.setPermissionStrategy(RightReusabilityCategorizer.PERMISSION_STRATEGY_NEGATIVE_WITH_RIGHTS);
		assertEquals(RightReusabilityCategorizer.getPermissionStrategy(), RightReusabilityCategorizer.PERMISSION_STRATEGY_NEGATIVE_WITH_RIGHTS);
		assertEquals(query, RightReusabilityCategorizer.getPermissionRightsQuery());

		query = "RIGHTS:(http\\:\\/\\/www.europeana.eu\\/rights\\/rr-f\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/rr-p\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/rr-r\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/unknown\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/test-orphan-work-test\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-OW-EU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/CNE\\/1.0\\/*)";

		RightReusabilityCategorizer.setPermissionStrategy(RightReusabilityCategorizer.PERMISSION_STRATEGY_POSITIVE);
		assertEquals(query, RightReusabilityCategorizer.getPermissionRightsQuery());

	}

	/**
	 * Testing the mapValueReplacements() function
	 */
	@Test
	public void testMapValueReplacementsFromApi() {
		String open = "{!tag=REUSABILITY}RIGHTS:(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/*)";

		String notOpen = "{!tag=REUSABILITY}RIGHTS:(NOT(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/*))";

		String restricted = "{!tag=REUSABILITY}RIGHTS:(http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*)";

		String notRestricted = "{!tag=REUSABILITY}RIGHTS:(NOT(http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*))";

		String permission = "{!tag=REUSABILITY}RIGHTS:(NOT(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*))";

		String openAndRestricted = "{!tag=REUSABILITY}RIGHTS:(http\\:\\/\\/creativecommons.org\\/publicdomain\\/mark\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/publicdomain\\/zero\\/1.0\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-sa\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nc-nd\\/* " +
				"OR http\\:\\/\\/creativecommons.org\\/licenses\\/by-nd\\/* " +
				"OR http\\:\\/\\/www.europeana.eu\\/rights\\/out-of-copyright-non-commercial\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/InC-EDU\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-NC\\/1.0\\/* " +
				"OR http\\:\\/\\/rightsstatements.org\\/vocab\\/NoC-OKLR\\/1.0\\/*)";

		String[] reusability;
		Map<String, String> valueReplacements;
		Map<String, String> expected;

		reusability = new String[]{"open"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:open", open);
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"REUSABILITY:open"};
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"restricted"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:restricted", restricted);
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);

		reusability = new String[]{"REUSABILITY:restricted"};
		assertEquals(expected, valueReplacements);
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"permission"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:permission", permission);
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"REUSABILITY:permission"};
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"open", "restricted"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:open", openAndRestricted);
		expected.put("REUSABILITY:restricted", "");
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"REUSABILITY:open", "REUSABILITY:restricted"};
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"open", "permission"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:open", notRestricted);
		expected.put("REUSABILITY:permission", "");
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"REUSABILITY:open", "REUSABILITY:permission"};
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"restricted", "permission"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:restricted", notOpen);
		expected.put("REUSABILITY:permission", "");
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"REUSABILITY:restricted", "REUSABILITY:permission"};
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"open", "restricted", "permission"};
		expected = new HashMap<String, String>();
		expected.put("REUSABILITY:open", "");
		expected.put("REUSABILITY:restricted", "");
		expected.put("REUSABILITY:permission", "");
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability, true);
		assertEquals(expected, valueReplacements);

		reusability = new String[]{"REUSABILITY:open", "REUSABILITY:restricted", "REUSABILITY:permission"};
		valueReplacements = RightReusabilityCategorizer.mapValueReplacements(reusability);
		assertEquals(expected, valueReplacements);
	}

	/**
	 * Testing the categorize() function
	 */
	@Test
	public void testCategorize() {
		Map<String, Integer> rightUrls = new LinkedHashMap<String, Integer>();
		rightUrls.put("", 1733097);
		rightUrls.put("RIGHTS:http://www.europeana.eu/rights/rr-f/", 617);
		rightUrls.put("TEXT", 1);
		rightUrls.put("http://creativecommons.org/licenses/by", 652);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/", 40275);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/1.0/", 92091);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/2.0/", 65092);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/2.0/at/legalcode", 174);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/2.0/es/", 972);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/2.0/uk/", 7);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/2.5/", 44);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/2.5/pl/", 3);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/", 275289);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/at/", 4712);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/de/", 1559);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/deed.el", 7888);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/es/", 112065);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/es/deed.ca", 12402);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/nl/", 62);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-nd/3.0/pl/", 11);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/", 81599);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/2.0/de/", 5);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/2.0/fr/", 11786);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/2.5/pl/", 25);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/3.0/", 435716);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/3.0/es/", 2248);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/3.0/ie/", 1246);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/3.0/lu/", 96);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/3.0/nl/", 1);
		rightUrls.put("http://creativecommons.org/licenses/by-nc-sa/3.0/pl/", 48);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/2.0/", 95464);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/2.0/at/legalcode", 191);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/2.0/uk/", 208);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/2.5/pl/", 11);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/2.5/se/", 60224);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/3.0/", 11370);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/3.0/pl/", 32);
		rightUrls.put("http://creativecommons.org/licenses/by-nc/3.0/pt/", 61);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/2.0/at/legalcode", 3);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/2.0/es/", 1);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/2.5/", 1208);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/2.5/,http://www.culture.si/en/Licenses#C", 14);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/2.5/pl/", 5);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/2.5/si/", 37);
		rightUrls.put("http://creativecommons.org/licenses/by-nd/3.0/", 7906);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/1.0/", 92167);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/2.0/de/", 4071);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/3.0", 34197);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/3.0/", 498214);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/3.0/nl/", 490888);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/3.0/pl/", 100);
		rightUrls.put("http://creativecommons.org/licenses/by-sa/3.0/us/", 1);
		rightUrls.put("http://creativecommons.org/licenses/by/2.5/pl/", 6);
		rightUrls.put("http://creativecommons.org/licenses/by/3.0/", 184278);
		rightUrls.put("http://creativecommons.org/licenses/by/3.0/nl/", 2);
		rightUrls.put("http://creativecommons.org/licenses/by/3.0/pl/", 2);
		rightUrls.put("http://creativecommons.org/licenses/publicdomain/zero/", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.0", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.0/", 4693832);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.1", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.10", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.100", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.101", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.102", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.103", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.104", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.105", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.106", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.107", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.108", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.109", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.11", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.110", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.111", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.112", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.113", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.114", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.115", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.116", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.117", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.118", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.119", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.12", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.120", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.121", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.122", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.123", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.124", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.125", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.126", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.127", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.128", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.129", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.13", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.130", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.131", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.132", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.133", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.134", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.135", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.136", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.137", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.138", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.139", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.14", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.140", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.141", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.142", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.143", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.144", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.145", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.146", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.147", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.148", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.149", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.15", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.150", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.151", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.152", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.153", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.154", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.155", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.156", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.157", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.158", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.159", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.16", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.160", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.161", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.162", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.163", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.164", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.165", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.166", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.167", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.168", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.169", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.17", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.170", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.171", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.172", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.173", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.174", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.175", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.176", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.177", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.178", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.179", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.18", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.180", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.181", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.182", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.183", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.184", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.185", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.186", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.187", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.188", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.189", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.190", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.191", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.192", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.193", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.194", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.195", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.196", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.197", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.198", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.199", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.2", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.200", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.201", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.202", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.203", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.204", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.205", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.206", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.207", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.208", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.209", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.21", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.210", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.211", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.212", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.213", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.214", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.215", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.216", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.217", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.218", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.219", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.22", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.220", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.221", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.222", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.223", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.224", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.225", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.226", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.227", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.228", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.229", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.23", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.230", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.231", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.232", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.233", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.234", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.235", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.236", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.237", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.238", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.239", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.24", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.240", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.241", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.242", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.243", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.244", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.245", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.246", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.247", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.248", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.249", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.25", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.250", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.251", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.252", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.253", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.254", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.255", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.256", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.257", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.258", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.259", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.26", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.260", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.261", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.27", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.28", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.3", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.30", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.31", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.32", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.33", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.34", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.35", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.36", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.37", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.38", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.4", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.40", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.41", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.42", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.43", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.44", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.45", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.46", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.47", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.48", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.49", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.5", 1);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.50", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.51", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.52", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.53", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.54", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.55", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.56", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.57", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.58", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.59", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.6", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.60", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.61", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.62", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.63", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.64", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.65", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.66", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.67", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.68", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.69", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.7", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.70", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.71", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.72", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.73", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.74", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.75", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.76", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.77", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.78", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.79", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.8", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.80", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.81", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.82", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.83", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.84", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.85", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.86", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.87", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.88", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.89", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.9", 2);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.90", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.91", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.92", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.93", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.94", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.95", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.96", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.97", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.98", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/mark/1.99", 3);
		rightUrls.put("http://creativecommons.org/publicdomain/zero/1.0/", 810182);
		rightUrls.put("http://www.europeana.eu/rights/out-of-copyright-non-commercial/", 1);
		rightUrls.put("http://www.europeana.eu/rights/rr-f/", 8635787);
		rightUrls.put("http://www.europeana.eu/rights/rr-f//", 596);
		rightUrls.put("http://www.europeana.eu/rights/rr-p/", 1310211);
		rightUrls.put("http://www.europeana.eu/rights/rr-r/", 1380853);
		rightUrls.put("http://www.europeana.eu/rights/test-orphan-work-test/", 1);
		rightUrls.put("http://www.europeana.eu/rights/unknown/", 413164);
		rightUrls.put("http://rightsstatements.org/vocab/InC/1.0/", 5);
		rightUrls.put("http://rightsstatements.org/vocab/InC-OW-EU/1.0/", 7);
		rightUrls.put("http://rightsstatements.org/vocab/InC-EDU/1.0/", 11);
		rightUrls.put("http://rightsstatements.org/vocab/NoC-NC/1.0/", 23);
		rightUrls.put("http://rightsstatements.org/vocab/NoC-OKLR/1.0/", 29);
		rightUrls.put("http://rightsstatements.org/vocab/CNE/1.0/", 31);

		RightReusabilityCategorizer categorizer = new RightReusabilityCategorizer();
		int total = 0;
		for (Entry<String, Integer> entry : rightUrls.entrySet()) {
			categorizer.categorize(entry.getKey(), entry.getValue());
			total += entry.getValue();
		}

		assertTrue(total > (categorizer.getNumberOfOpen() + categorizer.getNumberOfRestricted()));
		assertEquals(6808439, categorizer.getNumberOfOpen());
		assertEquals(1322215, categorizer.getNumberOfRestricted());

		// Testing whether the cache is working
		categorizer = new RightReusabilityCategorizer();
		total = 0;
		for (Entry<String, Integer> entry : rightUrls.entrySet()) {
			categorizer.categorize(entry.getKey(), entry.getValue());
			total += entry.getValue();
		}

		assertTrue(total > (categorizer.getNumberOfOpen() + categorizer.getNumberOfRestricted()));
		assertEquals(6808439, categorizer.getNumberOfOpen());
		assertEquals(1322215, categorizer.getNumberOfRestricted());
	}
}
