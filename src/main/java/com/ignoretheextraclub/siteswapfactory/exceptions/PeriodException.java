package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 * Indicates an issue with the period of a {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap}
 */
public class PeriodException extends InvalidSiteswapException
{
    public PeriodException(String message)
    {
        super(message);
    }
}
