package com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.constructors;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StreamingFilteringReducer;
import com.ignoretheextraclub.siteswapfactory.factory.SiteswapRequest;
import com.ignoretheextraclub.siteswapfactory.graph.GeneralPath;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;

/**
 * Created by caspar on 09/10/17.
 */
public class StringToTwoHandedVanillaSiteswapConstructorTest
{
    @Test
    public void WHEN_noReduce_noSort_EXPECT_siteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = new SiteswapRequest("3");
        final VanillaState[] states = {XXX};

        final TwoHandedVanillaSiteswap result = StringToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedVanillaSiteswap expected = new TwoHandedVanillaSiteswap(GeneralPath.from(XXX, VanillaThro.get(3)).toGeneralCircuit());

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void WHEN_noReduce_sort_EXPECT_sortedSiteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = new SiteswapRequest("3");
        final VanillaState[] unsorted = {XXX};

        final TwoHandedVanillaSiteswap result = StringToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedVanillaSiteswap expected = new TwoHandedVanillaSiteswap(GeneralPath.from(XXX, VanillaThro.get(3)).toGeneralCircuit());

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void WHEN_reduce_noSort_EXPECT_Reducedsiteswap() throws Exception
    {
        final SiteswapRequest siteswapRequest = new SiteswapRequest("33", StreamingFilteringReducer.get(), null);

        final TwoHandedVanillaSiteswap result = StringToTwoHandedSiteswapConstructor.get().apply(siteswapRequest);
        final TwoHandedVanillaSiteswap expected = new TwoHandedVanillaSiteswap(GeneralPath.from(XXX, VanillaThro.get(3)).toGeneralCircuit());

        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testAccepts() throws Exception
    {
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(new State[]{})).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(new VanillaState[]{})).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(null)).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts(XXX__)).isFalse();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts("sdfdf")).isTrue();
        Assertions.assertThat(StringToTwoHandedSiteswapConstructor.get().accepts("")).isTrue();
    }
}