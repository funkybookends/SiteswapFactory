package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 * Indicates that a provided {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap} did not conform to the
 * expectations of the interface, or that the provided constructor parameter could not be converted into a
 * {@link com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap}
 * @author Caspar Nonclercq
 */
public class InvalidSiteswapException extends Exception
{
    public InvalidSiteswapException(String message)
    {
        super(message);
    }

    public InvalidSiteswapException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
