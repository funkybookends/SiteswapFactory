package com.ignoretheextraclub.siteswapfactory.sorters;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

/**
 Created by caspar on 26/07/17.
 */
public interface SiteswapSorter<State>
{
    State[] sort() throws InvalidSiteswapException;

    int getWinningIndex();

    <T> T[] sortToMatch(T[] unsorted);
}
