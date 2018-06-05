package com.ignoretheextraclub.siteswapfactory.predicates.validation;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.factory.impl.TwoHandedSiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX_;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;

/**
 * Created by caspar on 24/09/17.
 */
public class SameNumberOfObjectsPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void name() throws Exception
    {
        softly.assertThat(SameNumberOfObjectsPredicate.get().test(TwoHandedSiteswapFactory.getTwoHandedSiteswap("534444555504").getStates())).isTrue();
        softly.assertThat(SameNumberOfObjectsPredicate.hasSameNumberOfObjects(new State[]{XXX__, XXX_})).isTrue();
        softly.assertThat(SameNumberOfObjectsPredicate.hasSameNumberOfObjects(new State[]{XXX__, XXXX_})).isFalse();
    }
}