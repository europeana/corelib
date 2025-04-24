package eu.europeana.corelib.search.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CachingRefinementUtilsTest {

    CachingRefinementUtils utils = new CachingRefinementUtils();

    private static String OR_VALUE_TAGGED      = "{!tag=filter_tags}(filter_tags:123 OR filter_tags:45)";
    private static String OR_AND_VALUE_TAGGED  = "{!tag=filter_tags}(filter_tags:54 OR filter_tags:4 AND filter_tags:87)";
    private static String OR_VALUE             = "(filter_tags:54 OR filter_tags:4)";
    private static String OR_AND_VALUE         = "(filter_tags:54 OR filter_tags:4 AND filter_tags:87)";
    private static String SINGLE_VALUE_TAGGED  = "{!tag=filter_tags}(filter_tags:445)";
    private static String SINGLE_VALUE         = "(filter_tags:445)";
    private static String FIELD_VALUES         = "contentTier:(1 OR 2 AND 3)";
    private static String FIELD_VALUES_2       = "filter_tags:(1 OR 2 AND 3)";

    private static String CACHED_OR_AND_VALUE  = "{!tag=filter_tags}filter(filter_tags:54) OR filter(filter_tags:4) AND filter(filter_tags:87)";
    private static String CACHED_VALUE_2       = "filter(filter_tags:54) OR filter(filter_tags:4) AND filter(filter_tags:87)";


    @Test
    public void ifBooleanFiltersPresent() {
        Assert.assertTrue(utils.ifBooleanFiltersPresent(OR_VALUE_TAGGED));
        Assert.assertTrue(utils.ifBooleanFiltersPresent(OR_AND_VALUE_TAGGED));
        Assert.assertTrue(utils.ifBooleanFiltersPresent(OR_AND_VALUE));
        Assert.assertTrue(utils.ifBooleanFiltersPresent(OR_VALUE));
        Assert.assertFalse(utils.ifBooleanFiltersPresent(SINGLE_VALUE_TAGGED));
        Assert.assertFalse(utils.ifBooleanFiltersPresent(SINGLE_VALUE));

    }

    @Test
    public void isBooleanParameter() {
        Assert.assertTrue(utils.isBooleanParameter("OR"));
        Assert.assertTrue(utils.isBooleanParameter("AND"));
        Assert.assertFalse(utils.isBooleanParameter("test"));
    }

    @Test
    public void isFieldValuesPattern() {
        Assert.assertTrue(utils.isFieldValuesPattern(FIELD_VALUES));
        Assert.assertFalse(utils.isFieldValuesPattern(OR_AND_VALUE_TAGGED));
    }

    @Test
    public void getCachedRefinements_TaggedTest() {
        List<String> refinements = Arrays.asList("{!tag=has_media}has_media:true", SINGLE_VALUE_TAGGED, OR_AND_VALUE_TAGGED);
        List<String> cachedRefinements =  Arrays.asList(utils.getCachedRefinements(refinements.toArray(new String[0])));
        Assert.assertTrue(cachedRefinements.contains(CACHED_OR_AND_VALUE));
    }

    @Test
    public void getCachedRefinements_NonTaggedTest() {
        List<String> refinements = Arrays.asList("{!tag=has_media}has_media:true", SINGLE_VALUE, OR_AND_VALUE);
        List<String> cachedRefinements =  Arrays.asList(utils.getCachedRefinements(refinements.toArray(new String[0])));
        Assert.assertTrue(cachedRefinements.contains(CACHED_VALUE_2));
    }

    @Test
    public void getCachedRefinements_OtherFieldsTest() {
        List<String> refinements = Arrays.asList("{!tag=has_media}has_media:true", FIELD_VALUES);
        List<String> cachedRefinements =  Arrays.asList(utils.getCachedRefinements(refinements.toArray(new String[0])));
        Assert.assertTrue(cachedRefinements.contains(FIELD_VALUES)); // nothing changed returns the original list
        Assert.assertTrue(cachedRefinements.size() == 2);
    }

    @Test
    public void getCachedRefinements_FieldValuesTest() {
        List<String> refinements = Arrays.asList("{!tag=has_media}has_media:true", FIELD_VALUES_2);
        List<String> cachedRefinements =  Arrays.asList(utils.getCachedRefinements(refinements.toArray(new String[0])));
        Assert.assertTrue(cachedRefinements.contains("filter(" + FIELD_VALUES_2 +")"));
        Assert.assertTrue(cachedRefinements.size() == 2);
    }
}
