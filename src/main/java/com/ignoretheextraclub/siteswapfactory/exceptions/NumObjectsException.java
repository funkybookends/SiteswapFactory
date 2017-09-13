package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 * Exception indicating an issue with the number of objects in a
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap} or between two
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}s or in the construction of a
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.State}.
 */
public class NumObjectsException extends InvalidSiteswapException
{
    public NumObjectsException(String message)
    {
        super(message);
    }
}
