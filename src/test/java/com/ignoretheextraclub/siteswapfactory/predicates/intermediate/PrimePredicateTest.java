package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedSiteswapFactory;

/**
 * Created by caspar on 24/09/17.
 */
public class PrimePredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void name() throws Exception
    {
        softly.assertThat(PrimePredicate.isPrime(TwoHandedSiteswapFactory.getTwoHandedSiteswap("5"))).isTrue();
        softly.assertThat(PrimePredicate.isPrime(TwoHandedSiteswapFactory.getTwoHandedSiteswap("564"))).isFalse();
        //todo add More
    }
}