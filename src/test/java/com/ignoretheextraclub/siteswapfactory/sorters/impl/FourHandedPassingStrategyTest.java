package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caspar on 15/01/17.
 */
public class FourHandedPassingStrategyTest
{
    private static final Map<String, String[]> correctToRotations = new HashMap<>();

    @Before
    public void setUp() throws Exception
    {
        correctToRotations.put("9A678", new String[]{"9A678", "A6789", "6789A", "789A6", "89A67"});
        correctToRotations.put("975", new String[]{"975", "759", "597"});
        correctToRotations.put("78686", new String[]{"78686", "86867", "68678", "86786", "67868"});
    }

    @Test
    public void test() throws Exception
    {
        for (String expected : correctToRotations.keySet())
        {
            for (String constructor : correctToRotations.get(expected))
            {
                FourHandedSiteswap fourHandedSiteswap = FourHandedSiteswap.create(constructor);
                Assert.assertEquals(constructor, expected, fourHandedSiteswap.toString());
            }
        }
    }
}