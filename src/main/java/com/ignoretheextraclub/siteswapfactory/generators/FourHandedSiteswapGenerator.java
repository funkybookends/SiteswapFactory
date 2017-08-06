package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.SiteswapFactory;
import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NumObjectsException;
import com.ignoretheextraclub.siteswapfactory.exceptions.PeriodException;
import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.impl.StatePredicate;
import com.ignoretheextraclub.siteswapfactory.predicates.impl.ThrowCombinationPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 Created by caspar on 30/07/17.
 */
public class FourHandedSiteswapGenerator extends SiteswapGenerator
{
    private static final Logger LOG = LoggerFactory.getLogger(FourHandedSiteswapGenerator.class);

    public FourHandedSiteswapGenerator(final int maxPeriod,
                                        final SortingStrategy sortingStrategy,
                                        final boolean reduce)
    {
        super(maxPeriod, (State[] states) ->
        {
            LOG.debug("{}, {}", "trying", states);
            try
            {
                return SiteswapFactory.createFHS(VanillaStateUtils.castAllToVanillaState(states),
                        sortingStrategy,
                        reduce);
            }
            catch (final InvalidSiteswapException cause)
            {
                throw new IllegalArgumentException("Provided states were not valid.", cause);
            }
        });

        this.addPredicate(new ThrowCombinationPredicate(VanillaThro.getUnchecked(3)).negate());
        this.addPredicate(new ThrowCombinationPredicate(VanillaThro.getUnchecked(1)).negate());
    }

    public static SiteswapGenerator ground(final int numObjects, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return ground(numObjects, maxPeriod, SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY);
    }

    public static SiteswapGenerator ground(final int numObjects,
                                           final int maxPeriod,
                                           final SortingStrategy sortingStrategy) throws NumObjectsException, PeriodException
    {
        final FourHandedSiteswapGenerator generator = new FourHandedSiteswapGenerator(maxPeriod, sortingStrategy, true);

        generator.addStartingState(VanillaStateUtils.getGroundState(numObjects, maxPeriod));

        return generator;
    }

    public static SiteswapGenerator all(final int numObjects, final int maxPeriod) throws NumObjectsException, PeriodException
    {
        return all(numObjects, maxPeriod, SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY);
    }

    public static SiteswapGenerator all(final int numObjects,
                                        final int maxPeriod,
                                        final SortingStrategy sortingStrategy) throws NumObjectsException, PeriodException
    {
        final FourHandedSiteswapGenerator generator = new FourHandedSiteswapGenerator(maxPeriod,
                sortingStrategy,
                true);

        VanillaStateUtils.getAllStates(numObjects, maxPeriod).forEach(generator::addStartingState);

        return generator;
    }

    public static SiteswapGenerator excited(final int numObjects,
                                        final int maxPeriod) throws NumObjectsException, PeriodException
    {
        final FourHandedSiteswapGenerator generator = new FourHandedSiteswapGenerator(maxPeriod,
                SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY,
                true);

        final SequencePredicate banGroundState = new StatePredicate(VanillaStateUtils.getGroundState(numObjects,
                maxPeriod)).negate();

        VanillaStateUtils.getAllStates(numObjects, maxPeriod)
                         .filter(banGroundState::testSequence)
                         .forEach(generator::addStartingState);

        generator.addPredicate(banGroundState);

        return generator;
    }
}
