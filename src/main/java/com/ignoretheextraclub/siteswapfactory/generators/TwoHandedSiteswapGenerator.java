package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.impl.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by caspar on 30/07/17.
 */
public class TwoHandedSiteswapGenerator extends SiteswapGenerator
{
    private static final Logger LOG = LoggerFactory.getLogger(TwoHandedSiteswapGenerator.class);

    public TwoHandedSiteswapGenerator(final int maxPeriod,
                                      final SortingStrategy sortingStrategy,
                                      final boolean reduce)
    {
        super(maxPeriod, (State[] states) ->
        {
            try
            {
                return SiteswapFactory.createTHS(VanillaStateUtils.castAllToVanillaState(states), sortingStrategy, reduce);
            }
            catch (final InvalidSiteswapException cause)
            {
                throw new IllegalArgumentException("Provided states were invalid", cause);
            }
        });
    }

    public static SiteswapGenerator ground(final int numObjects, final int maxPeriod) throws InvalidSiteswapException
    {
        return ground(numObjects, maxPeriod, SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY);
    }

    public static SiteswapGenerator ground(final int numObjects, final int maxPeriod, final SortingStrategy sortingStrategy) throws InvalidSiteswapException
    {
        final TwoHandedSiteswapGenerator generator = new TwoHandedSiteswapGenerator(maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY,
                true);

        generator.addStartingState(VanillaStateUtils.getGroundState(numObjects, maxPeriod));

        return generator;
    }

    public static SiteswapGenerator all(final int numObjects, final int maxPeriod)
    {
        return all(numObjects, maxPeriod, SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY);
    }

    public static SiteswapGenerator all(final int numObjects,
                                        final int maxPeriod,
                                        final SortingStrategy sortingStrategy)
    {
        final TwoHandedSiteswapGenerator generator = new TwoHandedSiteswapGenerator(maxPeriod,
                sortingStrategy,
                true);

        VanillaStateUtils.getAllStates(numObjects, maxPeriod).forEach(generator::addStartingState);

        return generator;
    }

    public static SiteswapGenerator excited(final int numObjects,
                                            final int maxPeriod) throws NumObjectsException, PeriodException
    {
        final TwoHandedSiteswapGenerator generator = new TwoHandedSiteswapGenerator(maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY,
                true);

        final SequencePredicate banGroundState = new StatePredicate(VanillaStateUtils.getGroundState(numObjects,
                maxPeriod)).negate();

        VanillaStateUtils.getAllStates(numObjects, maxPeriod)
                         .filter(banGroundState::testSequence)
                         .forEach(generator::addStartingState);

        generator.addIntermediatePredicate(banGroundState);

        return generator;
    }
}
