package com.ignoretheextraclub.siteswapfactory.sorters;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;

/**
 Created by caspar on 26/07/17.
 */
public interface SiteswapSorter<State>
{
    int getWinningIndex() throws InvalidSiteswapException;

    State[] getWinningSort() throws InvalidSiteswapException;

    <T> T[] sortToMatch(T[] unsorted) throws InvalidSiteswapException;
}
