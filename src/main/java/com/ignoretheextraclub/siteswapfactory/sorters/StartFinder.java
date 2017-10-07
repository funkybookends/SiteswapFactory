package com.ignoretheextraclub.siteswapfactory.sorters;

import java.util.function.Function;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.sorters.strategy.StartingStrategy;

/**
 * An interface to facilitate finding the best start to a Siteswap.
 *
 * @author Caspar Nonclercq
 */
public interface StartFinder
{
    /**
     * Returns an object that encapsulates the result of the the StartingStrategy being applied to the given state array
     *
     * @param stateArray       An array of states
     * @param startingStrategy A startingStrategy to use to find the start
     *
     * @return The result of the operation
     *
     * @throws InvalidSiteswapException If the given state array was not a valid siteswap and the sorter requires it to
     *                                  be one
     */
    StartFinderResult sort(State[] stateArray, StartingStrategy startingStrategy) throws InvalidSiteswapException;

    /**
     * Returns a new siteswap that is rotated according to the starting strategy.
     *
     * The siteswap returned will be the one from the function after being given the sorted states.
     *
     * @param siteswap The siteswap to sort
     * @param startingStrategy The starting strategy to sort the new siteswap to
     * @param siteswapConstructor A function that given a State[] will return the siteswap.
     * @return
     * @throws InvalidSiteswapException
     */
    default Siteswap sort(Siteswap siteswap, StartingStrategy startingStrategy, Function<State[], Siteswap> siteswapConstructor) throws InvalidSiteswapException
    {
        return siteswapConstructor.apply(sort(siteswap.getStates(), startingStrategy).getSorted());
    }
}
