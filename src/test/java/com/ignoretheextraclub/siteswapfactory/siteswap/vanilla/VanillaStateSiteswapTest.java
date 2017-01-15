package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caspar on 15/01/17.
 */
public class VanillaStateSiteswapTest
{
    @Test
    public void getStartingNumberOfObjects() throws Exception
    {
        TwoHandedSiteswap five04 = TwoHandedSiteswap.create("504");
        int firstStartingHandObjects = five04.getFirstStartingHandObjects();
        int secondStartingHandObjects = five04.getSecondStartingHandObjects();

        Assert.assertEquals(2, firstStartingHandObjects);
        Assert.assertEquals(1, secondStartingHandObjects);
    }

}