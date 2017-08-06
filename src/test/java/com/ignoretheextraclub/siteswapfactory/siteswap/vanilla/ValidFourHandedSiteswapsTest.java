package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import com.ignoretheextraclub.siteswapfactory.testutils.Utils;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
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
public class ValidFourHandedSiteswapsTest
{
    private static final String VALID_FOUR_HANDED_SITESWAPS_LIST = "siteswapLists/FHS-HTF-valid.list";
    private final String stringSiteswap;

    public ValidFourHandedSiteswapsTest(final String stringSiteswap)
    {
        this.stringSiteswap = stringSiteswap;
    }

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Parameterized.Parameters(name = "{0}")
    public static Collection getValidTwoHandedSiteswaps() throws FileNotFoundException
    {
        return Utils.load(VALID_FOUR_HANDED_SITESWAPS_LIST);
    }

    @Test
    public void testCreateWithNoSortingStrategy() throws Exception
    {
        final FourHandedSiteswap unsorted = SiteswapFactory.createFHS(stringSiteswap, NoSortingStrategy.get(), true);
        assertThat(unsorted.toString()).isEqualTo(stringSiteswap.toUpperCase());
        final FourHandedSiteswap hfsSorted = SiteswapFactory.createFHS(stringSiteswap, HighestThrowFirstStrategy.get(), true);
        assertThat(hfsSorted.toString()).isEqualTo(stringSiteswap.toUpperCase());
        assertThat(hfsSorted.same(unsorted)).isTrue();
        assertThat(unsorted.same(hfsSorted)).isTrue();
        assertThat(hfsSorted.equals(unsorted)).isTrue();
        assertThat(unsorted.equals(hfsSorted)).isTrue();
    }

    @Test
    public void testCreateWithFourHandedPassingStrategy() throws Exception
    {
        SiteswapFactory.createFHS(stringSiteswap, FourHandedPassingStrategy.get(), true);
    }
}