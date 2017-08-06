package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.FourHandedPassingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.HighestThrowFirstStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 Created by caspar on 06/08/17.
 */
public class FourHandedSiteswapTest
{
    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    @Ignore("caused by fhs sorting strategy")
    public void testSame() throws Exception
    {
        final String[] siteswaps = {"6666004", "6660046", "6600466", "6004666", "0046666", "0466660", "4666600"};

        final Siteswap correctHfs = SiteswapFactory.createFHS("6666004", NoSortingStrategy.get(), true);

        for (final String siteswap : siteswaps)
        {
            final Siteswap hfs = SiteswapFactory.createFHS(siteswap,
                    HighestThrowFirstStrategy.get(),
                    true);

            final Siteswap pass = SiteswapFactory.createFHS(siteswap,
                    FourHandedPassingStrategy.get(),
                    true);

            softly.assertThat(hfs).as(siteswap + "hfs ne pass").isNotEqualTo(pass);
            softly.assertThat(pass).as(siteswap + "pass ne hfs").isNotEqualTo(hfs);
            softly.assertThat(hfs.same(pass)).as(siteswap + " hfs same pass").isTrue();
            softly.assertThat(pass.same(hfs)).as(siteswap + " pass same hfs").isTrue();
            softly.assertThat(hfs.toString()).as(siteswap + " hfs " + hfs.toString() + " == correct").isEqualTo(correctHfs.toString());
            softly.assertThat(pass.toString()).as(siteswap + " pass " + pass.toString() + " == correct").isEqualTo(correctHfs.toString());
        }
    }
}