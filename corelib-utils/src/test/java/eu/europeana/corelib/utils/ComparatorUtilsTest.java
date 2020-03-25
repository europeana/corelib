package eu.europeana.corelib.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ComparatorUtilsTest {

    private static final String TESTING_VALUE_1 = "test.ing";
    private static final String TESTING_VALUE_2 = "Rembrandt Harmensz. van Rijn";
    private static final String TESTING_VALUE_3 = "Rembrandt -Harmensz van Rijn";

    List<String> list = new ArrayList<>();

    @Before
    public void setup() {
        list.add(TESTING_VALUE_1);
        list.add("TEST-ing");
        list.add("test.ING");
        list.add("test\\ing");
        list.add(TESTING_VALUE_2);
        list.add(TESTING_VALUE_3);
    }

    @Test
    public void testPunctuation() {
        List<String> newList = ComparatorUtils.stripPunctuations(list);
        Assert.assertTrue(list.size() == 6);
    }

    @Test
    public void testCompare() {
        List<String> newList = ComparatorUtils.removeDuplicates(list);
        Assert.assertTrue(newList.size() == 2);
        Assert.assertTrue(newList.contains(TESTING_VALUE_1));
        Assert.assertTrue(newList.contains(TESTING_VALUE_2) || newList.contains(TESTING_VALUE_3));
    }
}
