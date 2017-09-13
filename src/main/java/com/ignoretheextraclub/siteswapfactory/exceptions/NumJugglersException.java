package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 * Indicates that the number of jugglers was not in the required bounds.
 * @author Caspar Nonclercq
 */
public class NumJugglersException extends InvalidSiteswapException
{
    public NumJugglersException(String message)
    {
        super(message);
    }
}
