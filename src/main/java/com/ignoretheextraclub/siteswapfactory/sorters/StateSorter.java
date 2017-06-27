package com.ignoretheextraclub.siteswapfactory.sorters;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

/**
 Created by caspar on 15/12/16.
 */
public interface StateSorter<Throw extends AbstractThro, State extends AbstractState<Throw>>
{
    /**
     Returns the simple name for this sorting strategy.

     @return the name.
     */
    String getName();

    /**
     The main method that needs to be implemented. When sorting, the state sorter will be asked
     which two state sequences is preferred.
     <p>
     In a tie break, the implementer can return either true or false.

     @param first  the first candidate.
     @param second the second candidate.

     @return if the first is preferred

     @throws InvalidSiteswapException      if the sorter does not think the siteswap is valid
     @throws UnsupportedOperationException if the sorter is unable to sort.
     */
    boolean takeFirst(State[] first, State[] second) throws InvalidSiteswapException, UnsupportedOperationException;

    /**
     A human friendly description of how this sorter sorts.

     @return a description.
     */
    String getDescription(); // todo localise
}
