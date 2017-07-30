package com.ignoretheextraclub.siteswapfactory.generators;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 Created by caspar on 27/07/17.
 */
public class Transition
{
    private final State from;
    private final Thro throwing;
    private final State to;

    public Transition(final State from,
                      final Thro throwing,
                      final State to)
    {
        this.from = from;
        this.throwing = throwing;
        this.to = to;
    }

    public State getFrom()
    {
        return from;
    }

    public Thro getThrowing()
    {
        return throwing;
    }

    public State getTo()
    {
        return to;
    }
}
