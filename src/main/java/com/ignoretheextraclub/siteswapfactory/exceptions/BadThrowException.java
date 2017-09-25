package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 * Exception indicating a {@link com.ignoretheextraclub.siteswapfactory.siteswap.Thro} cannot be constructed, or for the
 * current state the {@link com.ignoretheextraclub.siteswapfactory.siteswap.Thro} cannot be thrown.
 * @author Caspar Nonclercq
 */
public class BadThrowException extends InvalidSiteswapException
{
    public BadThrowException(final String message)
    {
        super(message);
    }

    public BadThrowException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
