package com.ignoretheextraclub.siteswapfactory.predicates.result;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;

/**
 * Created by caspar on 20/09/17.
 */
public class AmbidextrousPredicateTest
{
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void test() throws Exception
    {
        softly.assertThat(AmbidextrousPredicate.isAmbidextrous(SiteswapFactory.getTwoHandedSiteswap("534"))).isTrue();
        softly.assertThat(AmbidextrousPredicate.isAmbidextrous(SiteswapFactory.getTwoHandedSiteswap("53"))).isFalse();
        softly.assertThat(AmbidextrousPredicate.isAmbidextrous(SiteswapFactory.getFourHandedSiteswap("7"))).isTrue();
        softly.assertThat(AmbidextrousPredicate.isAmbidextrous(SiteswapFactory.getFourHandedSiteswap("97"))).isTrue();
        softly.assertThat(AmbidextrousPredicate.isAmbidextrous(SiteswapFactory.getFourHandedSiteswap("796686"))).isTrue();
        softly.assertThat(AmbidextrousPredicate.isAmbidextrous(SiteswapFactory.getFourHandedSiteswap("88578677"))).isFalse();
        // todo add more
    }
}