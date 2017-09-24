package com.ignoretheextraclub.siteswapfactory.comparators;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StringToFourHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.converter.vanilla.siteswap.StringToTwoHandedSiteswapConverter;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by caspar on 24/09/17.
 */
@RunWith(JUnitParamsRunner.class)
public class NumJugglersComparatorTest
{
    private static final StringToTwoHandedSiteswapConverter TWO_HANDED = new StringToTwoHandedSiteswapConverter();
    private static final StringToFourHandedSiteswapConverter FOUR_HANDED = new StringToFourHandedSiteswapConverter();
    private static final NumJugglersComparator COMPARATOR = NumJugglersComparator.get();

    @Test
    @Parameters
    public void testEqual(final Siteswap first, final Siteswap second) throws Exception
    {
        assertThat(COMPARATOR.compare(first, second)).isEqualTo(0);
        assertThat(COMPARATOR.compare(second, first)).isEqualTo(0);
    }

    public Object parametersForTestEqual()
    {
        return new Object[]{
                new Object[]{TWO_HANDED.apply("567"), TWO_HANDED.apply("6")},
                new Object[]{TWO_HANDED.apply("423"), TWO_HANDED.apply("42")},
                new Object[]{TWO_HANDED.apply("7868686"), TWO_HANDED.apply("7")},
                new Object[]{FOUR_HANDED.apply("567"), FOUR_HANDED.apply("6")},
                new Object[]{FOUR_HANDED.apply("89A"), FOUR_HANDED.apply("42")},
                new Object[]{FOUR_HANDED.apply("7868686"), FOUR_HANDED.apply("7")},
                };
    }

    @Test
    @Parameters
    public void testLessThan(final Siteswap less, final Siteswap more) throws Exception
    {
        assertThat(COMPARATOR.compare(less, more)).isLessThan(0);
        assertThat(COMPARATOR.compare(more, less)).isGreaterThan(0);
    }

    public Object parametersForTestLessThan()
    {
        return new Object[]{
                new Object[]{TWO_HANDED.apply("567"), FOUR_HANDED.apply("97577")},
                new Object[]{TWO_HANDED.apply("5"), FOUR_HANDED.apply("99")},
                new Object[]{TWO_HANDED.apply("423"), FOUR_HANDED.apply("678")},
                };
    }
}
