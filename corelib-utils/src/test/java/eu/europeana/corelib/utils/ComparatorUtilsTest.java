package eu.europeana.corelib.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComparatorUtilsTest {

    private static final String TESTING_VALUE_1 = "Testing";
    private static final String TESTING_VALUE_2 = "TEST-ing";
    private static final String TESTING_VALUE_3 = "test\\ING";
    private static final String TESTING_VALUE_4 = "Rembrandt Harmensz van Rijn";
    private static final String TESTING_VALUE_5 = "Rembrandt Harmensz .van Rijn";
    private static final String TESTING_VALUE_6 = "Rembrandt -Harmensz van Rijn";


    List<String> list = new ArrayList<>();

    @Before
    public void setup() {
        list.add(TESTING_VALUE_1);
        list.add(TESTING_VALUE_2);
        list.add(TESTING_VALUE_3);
        list.add(TESTING_VALUE_4);
        list.add(TESTING_VALUE_5);
        list.add(TESTING_VALUE_6);
    }

    @Test
    public void testPunctuation() {
       //stripPunctuation(String)
       Assert.assertTrue(StringUtils.equalsIgnoreCase(ComparatorUtils.stripPunctuation(TESTING_VALUE_2), TESTING_VALUE_1));
       Assert.assertTrue(StringUtils.equalsIgnoreCase(ComparatorUtils.stripPunctuation(TESTING_VALUE_3), TESTING_VALUE_1));
       Assert.assertTrue(StringUtils.equalsIgnoreCase(ComparatorUtils.stripPunctuation(TESTING_VALUE_5), TESTING_VALUE_4));
       Assert.assertTrue(StringUtils.equalsIgnoreCase(ComparatorUtils.stripPunctuation(TESTING_VALUE_6), TESTING_VALUE_4));

       //stripPunctuations(List)
       Assert.assertTrue(ComparatorUtils.stripPunctuations(list).size() == 6);
    }

    @Test
    public void testCompare() {
        //check the size before
        Assert.assertTrue(list.size() == 6);
        ComparatorUtils.removeDuplicates(list);
        //check size after
        Assert.assertTrue(list.size() == 2);
        //check the values in the list
        Assert.assertTrue(list.contains(TESTING_VALUE_1));
        Assert.assertTrue(list.contains(TESTING_VALUE_4));
    }

    @Test
    public void testSameValueWithoutSpace() {
        Assert.assertTrue(ComparatorUtils.sameValueWithoutSpace("Calamatta, Luigi (1801-1869)", "Calamatta, Luigi (1801 - 1869)"));
        Assert.assertTrue(ComparatorUtils.sameValueWithoutSpace("Leonardo da Vinci (1452 - 1519)", "Leonardo da Vinci (1452-1519)"));
        Assert.assertTrue(ComparatorUtils.sameValueWithoutSpace("TvB G 3674", "TvB G 3674"));
        Assert.assertFalse(ComparatorUtils.sameValueWithoutSpace("graveur", "Calamatta"));
    }
}
