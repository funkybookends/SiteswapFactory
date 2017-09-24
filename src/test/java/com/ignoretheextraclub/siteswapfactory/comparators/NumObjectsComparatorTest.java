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
public class NumObjectsComparatorTest
{
    private static final StringToTwoHandedSiteswapConverter SC = new StringToTwoHandedSiteswapConverter();
    private static final NumObjectsComparator COMPARATOR = NumObjectsComparator.get();

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
                new Object[]{SC.apply("567"), SC.apply("6")},
                new Object[]{SC.apply("423"), SC.apply("42")},
                new Object[]{SC.apply("7868686"), SC.apply("7")},
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
                new Object[]{SC.apply("567"), SC.apply("97577")},
                new Object[]{SC.apply("5"), SC.apply("99")},
                new Object[]{SC.apply("423"), SC.apply("534")},
                };
    }
}