package com.ignoretheextraclub.siteswapfactory;

import com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration;
import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.FourHandedSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.TwoHandedVanillaSiteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaState;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.state.VanillaStateUtils;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.FourHandedSiteswapThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThroUtils;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.SortingStrategy;
import com.ignoretheextraclub.siteswapfactory.sorters.utils.SortingUtils;

import java.util.Arrays;
import java.util.regex.Pattern;

import static com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration.DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY;
import static com.ignoretheextraclub.siteswapfactory.configuration.SiteswapFactoryConfiguration.DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY;

/**
 Created by caspar on 29/07/17.
 */
public class SiteswapFactory
{
    private static final Pattern VANILLA_SITESWAP_FORMAT = Pattern.compile("^[\\da-zA-Z]+$");
    private final SiteswapFactoryConfiguration config;

    public SiteswapFactory(final SiteswapFactoryConfiguration config)
    {
        this.config = config;
    }

    /**
     Constructs a siteswap using the provided configuration.
     @param input
     @return
     */
    public Siteswap createSiteswap(final Object input)
    {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO implement
    }

    public Siteswap createSiteswap(final Object[] input)
    {
        throw new UnsupportedOperationException();
    }

    public Siteswap createSiteswap(final int[] input)
    {
        throw new UnsupportedOperationException();
    }

    // ============================== STATIC CONSTRUCTORS: These use the default configurations

    // ============= Four Handed Siteswap

    // Implementations

    public static Siteswap createFHS(final VanillaState[] states, final SortingStrategy<VanillaState> sortingStrategy, final boolean reduce) throws InvalidSiteswapException
    {
        final VanillaState[] reducedSiteswap = reduce ? SortingUtils.reduce(states) : states;
        final Thro[] thros;

        try
        {
            thros =  StateUtils.getAllThrows(reducedSiteswap);
        }
        catch (final TransitionException cause)
        {
            throw new InvalidSiteswapException("Not a valid siteswap", cause);
        }

        try
        {
            final FourHandedSiteswapThro[] fhsts = (FourHandedSiteswapThro[]) Arrays.stream(thros).map(vt -> FourHandedSiteswapThro.getUnchecked(vt.getNumBeats())).toArray();
            return new TwoHandedVanillaSiteswap(reducedSiteswap, fhsts, sortingStrategy);
        }
        catch (final IllegalArgumentException uncheckedBadThrowException)
        {
            throw new InvalidSiteswapException("Not a valid FHS", uncheckedBadThrowException);
        }
    }

    public static FourHandedSiteswap createFHS(final int[] siteswap, final SortingStrategy<VanillaState> sortingStrategy, final boolean reduce) throws InvalidSiteswapException
    {
        final int[] reducedSiteswap = reduce ? SortingUtils.reduce(siteswap) : siteswap;
        final FourHandedSiteswapThro[] thros = VanillaThroUtils.intArrayToFourHandedSiteswapThrowArray(reducedSiteswap);
        final VanillaState firstState = VanillaStateUtils.getFirstState(thros);
        final VanillaState[] allStates = StateUtils.getAllStates(firstState, thros);
        return new FourHandedSiteswap(allStates, thros, sortingStrategy);
    }

    public static FourHandedSiteswap createFHS(final String siteswap, final SortingStrategy<VanillaState> sortingStrategy, final boolean reduce) throws InvalidSiteswapException
    {
        InvalidSiteswapException cause = null;
        if (VANILLA_SITESWAP_FORMAT.matcher(siteswap).matches())
        {
            try
            {
                final char[] charArray = siteswap.toCharArray();
                final int[] intArray = new int[charArray.length];

                for (int i = 0; i < charArray.length; i++)
                {
                    intArray[i] = FourHandedSiteswapThro.get(charArray[i]).getNumBeats();
                }

                return createFHS(intArray, sortingStrategy, reduce);
            }
            catch (final InvalidSiteswapException ise)
            {
                cause = ise;
            }
        }

        throw new InvalidSiteswapException("Could not create siteswap from [" + siteswap + "]", cause);
    }

    // Default Providers

    public static FourHandedSiteswap createFHS(final String siteswap) throws InvalidSiteswapException
    {
        return createFHS(siteswap, DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY, true);
    }

    public static FourHandedSiteswap createFHS(final int[] siteswap) throws InvalidSiteswapException
    {
        return createFHS(siteswap, DEFAULT_FOUR_HANDED_SITESWAP_SORTING_STRATEGY, true);
    }

    // ============= Two Handed Siteswap

    // Implementations

    public static Siteswap createTHS(final VanillaState[] states, final SortingStrategy<VanillaState> sortingStrategy, final boolean reduce) throws InvalidSiteswapException
    {
        final VanillaState[] reducedSiteswap = reduce ? SortingUtils.reduce(states) : states;
        final VanillaThro[] thros;
        try
        {
            thros = (VanillaThro[]) StateUtils.getAllThrows(reducedSiteswap);
        }
        catch (final TransitionException cause)
        {
            throw new InvalidSiteswapException("Not a valid siteswap", cause);
        }
        return new TwoHandedVanillaSiteswap(reducedSiteswap, thros, sortingStrategy);
    }

    public static TwoHandedVanillaSiteswap createTHS(final int[] siteswap, final SortingStrategy<VanillaState> sortingStrategy, final boolean reduce) throws InvalidSiteswapException
    {
        final int[] reducedSiteswap = reduce ? SortingUtils.reduce(siteswap) : siteswap;
        final VanillaThro[] thros = VanillaThroUtils.intArrayToVanillaThrowArray(reducedSiteswap);
        final VanillaState firstState = VanillaStateUtils.getFirstState(thros);
        final VanillaState[] allStates = StateUtils.getAllStates(firstState, thros);
        return new TwoHandedVanillaSiteswap(allStates, thros, sortingStrategy);
    }

    public static TwoHandedVanillaSiteswap createTHS(final String siteswap, final SortingStrategy<VanillaState> sortingStrategy, final boolean reduce) throws InvalidSiteswapException
    {
        InvalidSiteswapException cause = null;
        if (VANILLA_SITESWAP_FORMAT.matcher(siteswap).matches())
        {
            try
            {
                return createTHS(VanillaThroUtils.stringToIntArray(siteswap), sortingStrategy, reduce);
            }
            catch (final InvalidSiteswapException ise)
            {
                cause = ise;
            }
        }

        throw new InvalidSiteswapException("Could not create siteswap from [" + siteswap + "]", cause);
    }

    // Default Providers

    public static TwoHandedVanillaSiteswap createTHS(final String siteswap) throws InvalidSiteswapException
    {
        return createTHS(siteswap, DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY, true);
    }

    public static TwoHandedVanillaSiteswap createTHS(final int[] siteswap) throws InvalidSiteswapException
    {
        return createTHS(siteswap, DEFAULT_TWO_HANDED_SITESWAP_SORTING_STRATEGY, true);
    }
}