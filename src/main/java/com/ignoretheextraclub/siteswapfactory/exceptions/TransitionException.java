package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 Created by caspar on 26/11/16.
 */
public class TransitionException extends InvalidSiteswapException
{
    public TransitionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TransitionException(String message)
    {
        super(message);
    }
}
