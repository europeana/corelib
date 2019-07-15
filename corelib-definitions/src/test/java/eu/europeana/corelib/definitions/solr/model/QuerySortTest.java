package eu.europeana.corelib.definitions.solr.model;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Patrick Ehlert
 * Created on 10-04-2018
 */
public class QuerySortTest {

    static final String SORT_RANDOM_WITH_SEED_PATTERN = "(?i)" +
            QuerySort.SORT_RANDOM + QuerySort.SORT_RANDOM_SEED_SEPARATOR +"[0-9a-zA-Z]+";

    /**
     * Test whether we recognize a sort order specification properly
     */
    @Test
    public void testSortOrder() {
        assertTrue(QuerySort.isSortOrder("ascending"));
        assertTrue(QuerySort.isAscending("ascending"));

        assertTrue(QuerySort.isSortOrder("DESCENDING"));
        assertFalse(QuerySort.isAscending("DESCENDING"));

        assertTrue(QuerySort.isSortOrder(" Asc "));
        assertTrue(QuerySort.isAscending(" Asc "));

        assertTrue(QuerySort.isSortOrder(" desC "));
        assertFalse(QuerySort.isAscending(" desC "));

        assertFalse(QuerySort.isSortOrder("descendingggg"));
        assertFalse(QuerySort.isSortOrder(" "));
        assertFalse(QuerySort.isSortOrder(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortOrderIllegal() {
        new QuerySort("test", "descendingggg");
    }

    /**
     * Test construction of a QuerySort object (containing sort field and sort order)
     */
    @Test
    public void testSortField() {
        QuerySort lowercase = new QuerySort("id asc");
        assertEquals("id", lowercase.getSortField());
        assertEquals(QuerySort.ORDER_ASC, lowercase.getSortOrder());

        QuerySort mixedCase = new QuerySort("COMPLETENESS+DEScENDING");
        assertEquals("COMPLETENESS", mixedCase.getSortField());
        assertEquals(QuerySort.ORDER_DESC, mixedCase.getSortOrder());

        QuerySort noOrder = new QuerySort("timestamp");
        assertEquals("timestamp", noOrder.getSortField());
        assertEquals(QuerySort.ORDER_DESC, noOrder.getSortOrder());

        QuerySort multipleSpaces = new QuerySort("test   ascending ");
        assertEquals("test", multipleSpaces.getSortField());
        assertEquals(QuerySort.ORDER_ASC, multipleSpaces.getSortOrder());
    }

    /**
     * Test if we properly recognize sorting functions for multi-value fields, for example field(proxy_dcterms_issued,min)
     */
    @Test
    public void testMultiValueFunctionSort() {
        QuerySort noOrder = new QuerySort("field(proxy_dcterms_issued,min)");
        assertEquals("field(proxy_dcterms_issued,min)", noOrder.getSortField());

        QuerySort withOrder = new QuerySort("FIELD(proxy_dcterms_issued,min)+ASC");
        assertEquals("FIELD(proxy_dcterms_issued,min)", withOrder.getSortField());
        assertEquals(QuerySort.ORDER_ASC, withOrder.getSortOrder());

        QuerySort withOrderAndSpaces = new QuerySort(" Field( proxy_dcterms_issued , min ) descending ");
        assertEquals("Field( proxy_dcterms_issued , min )", withOrderAndSpaces.getSortField());
        assertEquals(QuerySort.ORDER_DESC, withOrderAndSpaces.getSortOrder());
    }

    /**
     * Test the isRandomNoSeed() function
     */
    @Test
    public void testRandomNoSeed() {
        assertTrue(QuerySort.isRandomNoSeed("random"));
        assertTrue(QuerySort.isRandomNoSeed(" RanDom "));
        assertFalse(QuerySort.isRandomNoSeed("random_a7as6sd"));
        assertFalse(QuerySort.isRandomNoSeed("random1"));
        assertFalse(QuerySort.isRandomNoSeed(" this is just some random string "));
    }

    /**
     * Test if a QuerySort object is created properly when if it contains 'random'
     */
    @Test
    public void testRandom() {
        // was a seed generated and added?
        QuerySort randomNoSeed = new QuerySort("random");
        assertTrue(randomNoSeed.getSortField().matches(SORT_RANDOM_WITH_SEED_PATTERN));

        String randomSeed = "random_a7as6sd";
        QuerySort randomWithSeed = new QuerySort(randomSeed);
        assertEquals(randomSeed, randomWithSeed.getSortField());

        String noRandom = "random1";
        QuerySort noRandomSort = new QuerySort(noRandom);
        assertEquals(noRandom, noRandomSort.getSortField());
    }


}
