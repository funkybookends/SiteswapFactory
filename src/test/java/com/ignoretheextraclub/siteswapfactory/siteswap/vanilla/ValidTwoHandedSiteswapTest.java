package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import com.ignoretheextraclub.siteswapfactory.testutils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 Created by caspar on 25/06/17.
 */
@RunWith(Parameterized.class)
public class ValidTwoHandedSiteswapTest
{
    private static final String VALID_TWO_HANDED_SITESWAPS_LIST = "siteswapLists/THS-unsorted-valid.list";
    private final String stringSiteswap;

    public ValidTwoHandedSiteswapTest(final String stringSiteswap)
    {
        this.stringSiteswap = stringSiteswap;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
    {
        return Utils.load(VALID_TWO_HANDED_SITESWAPS_LIST);
    }

    @Test
    public void testCreateWithNoSortingStrategy() throws Exception
    {
        final TwoHandedVanillaSiteswap ths = SiteswapFactory.createTHS(stringSiteswap, NoSortingStrategy.get(), true);
        assertThat(ths.toString()).isEqualTo(stringSiteswap.toUpperCase());
    }

    @Test
    public void testCreateWithHighestThrowFirstStrategy() throws Exception
    {
        final TwoHandedVanillaSiteswap ths = SiteswapFactory.createTHS(stringSiteswap,
                HighestThrowFirstStrategy.get(),
                true);
    }
}