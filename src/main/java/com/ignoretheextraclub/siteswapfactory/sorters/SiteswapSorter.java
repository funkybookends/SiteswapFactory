package com.ignoretheextraclub.siteswapfactory.sorters;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;

/**
 Created by caspar on 26/07/17.
 */
public interface SiteswapSorter
{
    void sort() throws InvalidSiteswapException;

    int getWinningIndex() throws InvalidSiteswapException;

    State[] getWinningSort() throws InvalidSiteswapException;

    <T> T[] sortToMatch(T[] unsorted) throws InvalidSiteswapException;
}
