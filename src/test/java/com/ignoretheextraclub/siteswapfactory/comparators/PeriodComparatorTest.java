package com.ignoretheextraclub.siteswapfactory.comparators;

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
public class PeriodComparatorTest
{
    private static final StringToTwoHandedSiteswapConverter SC = new StringToTwoHandedSiteswapConverter();
    private static final PeriodComparator PERIOD_COMPARATOR = PeriodComparator.get();

    @Test
    @Parameters
    public void testEqual(final Siteswap first, final Siteswap second) throws Exception
    {
        assertThat(PERIOD_COMPARATOR.compare(first, second)).isEqualTo(0);
        assertThat(PERIOD_COMPARATOR.compare(second, first)).isEqualTo(0);
    }

    public Object parametersForTestEqual()
    {
        return new Object[]{
                new Object[]{SC.apply("567"), SC.apply("975")},
                new Object[]{SC.apply("5"), SC.apply("9")},
                new Object[]{SC.apply("95"), SC.apply("42")},
        };
    }

    @Test
    @Parameters
    public void testLessThan(final Siteswap less, final Siteswap more) throws Exception
    {
        assertThat(PERIOD_COMPARATOR.compare(less, more)).isLessThan(0);
        assertThat(PERIOD_COMPARATOR.compare(more, less)).isGreaterThan(0);
    }

    public Object parametersForTestLessThan()
    {
        return new Object[]{
                new Object[]{SC.apply("567"), SC.apply("97577")},
                new Object[]{SC.apply("5"), SC.apply("99")},
                new Object[]{SC.apply("95"), SC.apply("423")},
        };
    }
}