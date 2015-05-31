package pl.kalisz.kamil.preffer;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class SetUtilsTest extends TestCase
{
    private static final String PREFIX = "MyPrefix";

    public void testNulValueInSetWilNotNullPointerException()
    {
        Set<String> testSet = new HashSet<>();

        testSet.add("SomeValue");
        testSet.add(null);

        SetUtils.filterSetByPrefix(testSet, "SomePrefix");

    }

    public void testProperFilterValuesByPrefix()
    {
        Set<String> testSet = new HashSet<>();

        String keyWithPrefix1 = PREFIX + "key";
        testSet.add(keyWithPrefix1);
        testSet.add("key");
        String keyWithPrefix2 = PREFIX + "key2";
        testSet.add(keyWithPrefix2);
        String keyWithPrefix3 = PREFIX + "key3";
        testSet.add(keyWithPrefix3);
        testSet.add("key2");

        Set<String> filteredValues = SetUtils.filterSetByPrefix(testSet,PREFIX);

        Assert.assertTrue(filteredValues.contains(keyWithPrefix1));
        Assert.assertTrue(filteredValues.contains(keyWithPrefix2));
        Assert.assertTrue(filteredValues.contains(keyWithPrefix3));
        Assert.assertEquals(3,filteredValues.size());

    }

}