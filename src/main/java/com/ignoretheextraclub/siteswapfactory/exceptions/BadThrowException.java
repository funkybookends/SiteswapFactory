package com.ignoretheextraclub.siteswapfactory.exceptions;

/**
 Created by caspar on 26/11/16.
 */
public class BadThrowException extends InvalidSiteswapException
{
    public BadThrowException(String message)
    {
        super(message);
    }

    public BadThrowException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
