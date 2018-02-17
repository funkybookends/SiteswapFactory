package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXXXXXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;

/**
 * Created by caspar on 09/10/17.
 */
public class StringToFourHandedSiteswapConstructorTest
{
    @Test
    public void WHEN_noReduce_noSort_EXPECT_siteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = new SiteswapRequest("8");
        final VanillaState[] states = {XXXXXXXX__};

        final FourHandedSiteswap result = StringToFourHandedSiteswapConstructor.get().apply(siteswapRequest);
        final FourHandedSiteswap expected = new FourHandedSiteswap(GeneralPath.from(XXXXXXXX__, FourHandedSiteswapThro.get(8)).toGeneralCircuit());

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test // TODO implement properly
    public void WHEN_noReduce_sort_EXPECT_sortedSiteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = new SiteswapRequest("8");
        final VanillaState[] unsorted = {XXXXXXXX__};

        final FourHandedSiteswap result = StringToFourHandedSiteswapConstructor.get().apply(siteswapRequest);
        final FourHandedSiteswap expected = new FourHandedSiteswap(GeneralPath.from(XXXXXXXX__, FourHandedSiteswapThro.get(8)).toGeneralCircuit());

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void WHEN_reduce_noSort_EXPECT_Reducedsiteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = new SiteswapRequest("88", StreamingFilteringReducer.get(), null);
        final FourHandedSiteswap result = StringToFourHandedSiteswapConstructor.get().apply(siteswapRequest);
        final FourHandedSiteswap expected = new FourHandedSiteswap(GeneralPath.from(XXXXXXXX__, FourHandedSiteswapThro.get(8)).toGeneralCircuit());

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testAccepts() throws Exception
    {
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(new State[]{})).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(new VanillaState[]{})).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(null)).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts(XXX__)).isFalse();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts("sdfdf")).isTrue();
        Assertions.assertThat(StringToFourHandedSiteswapConstructor.get().accepts("")).isTrue();
    }
}