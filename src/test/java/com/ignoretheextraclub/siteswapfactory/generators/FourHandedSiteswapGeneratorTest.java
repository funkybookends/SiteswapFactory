package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.impl.RequiredStatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.impl.NoSortingStrategy;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 Created by caspar on 30/07/17.
 */
public class FourHandedSiteswapGeneratorTest
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGeneratorTest.class);

    @Rule public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void testGeneratorForTHSPeriod2() throws Exception
    {
        final TwoHandedSiteswapGenerator generator = new TwoHandedSiteswapGenerator(3, NoSortingStrategy.get(), false);
        final State groundState = VanillaStateUtils.getGroundState(5, 3);
        generator.addStartingState(groundState);
        final List<Siteswap> collect = generator.generate()
                                                .peek((s) -> LOG.info("{}\t{}", s.toString(), s.getStates()))
                                                .collect(Collectors.toList());
        final RequiredStatePredicate requiredStatePredicate = new RequiredStatePredicate(groundState);
        assertThat(collect).hasSize(9).allMatch((s) -> requiredStatePredicate.test(s.getStates()));
    }

    private TwoHandedVanillaSiteswap ths(final String s) throws InvalidSiteswapException
    {
        return SiteswapFactory.createTHS(s);
    }
}