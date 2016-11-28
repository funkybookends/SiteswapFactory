package com.ignoretheextraclub.vanillasiteswap.siteswap;

import com.ignoretheextraclub.vanillasiteswap.exceptions.BadThrowException;
import com.ignoretheextraclub.vanillasiteswap.exceptions.InvalidSiteswapException;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by caspar on 26/11/16.
 */
public class VanillaSiteswapTest
{
    private List<String> invalidSiteswaps = new LinkedList<>();

    @Before
    public void setUp() throws Exception
    {

        invalidSiteswaps.add("543");
        invalidSiteswaps.add("9768458");
//        invalidSiteswaps.add("975"); // to test that it will throw an exception if given a valid siteswap
    }

    @Test
    public void parseNoSort() throws Exception, InvalidSiteswapException
    {
        final String prefix = "";
        final boolean sorted = false;
        for (VanillaTestCase testCase : VanillaTestCase.getValidTestCases())
        {
            VanillaSiteswap vanillaSiteswap = new VanillaSiteswap(testCase.intSiteswap, sorted);
            testCase.verify("parseNoSort", prefix, sorted, vanillaSiteswap);
        }
    }

    @Test
    public void parseGlobalStringNoSort() throws InvalidSiteswapException, BadThrowException
    {
        final String prefix = "";
        final boolean sorted = false;
        for (VanillaTestCase testCase : VanillaTestCase.getValidTestCases())
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.unsortedStringSiteswap, sorted);
            testCase.verify("parseGlobalStringNoSort", prefix, sorted, vanillaSiteswap);
        }
    }

    @Test
    public void parseGlobalStringSorted() throws InvalidSiteswapException, BadThrowException
    {
        final boolean sorted = true;
        final String prefix = "";
        for (VanillaTestCase testCase : VanillaTestCase.getValidTestCases())
        {
            VanillaSiteswap vanillaSiteswap = VanillaSiteswap.create(testCase.unsortedStringSiteswap, sorted);
            testCase.verify("parseGlobalStringSorted", prefix, sorted, vanillaSiteswap);
        }
    }

    @Test
    public void invalidSiteswapsDontCompile() throws Exception
    {
        for (String invalidSiteswap : invalidSiteswaps)
        {
            try
            {
                VanillaSiteswap.create(invalidSiteswap, true);
                throw new RuntimeException(invalidSiteswap + " should've thrown an exception");
            }
            catch (InvalidSiteswapException e)
            {
                // do nothing
            }
        }


    }


}