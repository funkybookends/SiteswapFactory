package com.ignoretheextraclub.siteswapfactory.sorters;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

/**
 * Created by caspar on 15/12/16.
 */
public interface StateSorter<Throw extends AbstractThro, State extends AbstractState<Throw>>
{
    String getName();

    boolean takeFirst(State[] first, State[] second) throws InvalidSiteswapException;
}