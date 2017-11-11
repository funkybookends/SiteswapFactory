package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;

/**
 * Created by caspar on 24/09/17.
 */
public class GroundedPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test() throws Exception
    {
        softly.assertThat(GroundedPredicate.get().test(new State[]{XXX__})).isTrue();
        softly.assertThat(GroundedPredicate.isGround(SiteswapFactory.getTwoHandedSiteswap("3"))).isTrue();
        softly.assertThat(GroundedPredicate.isGround(SiteswapFactory.getTwoHandedSiteswap("55500"))).as("55500").isTrue();
        softly.assertThat(GroundedPredicate.isGround(SiteswapFactory.getTwoHandedSiteswap("55050"))).as("55050").isFalse();
        // todo add more
    }
}