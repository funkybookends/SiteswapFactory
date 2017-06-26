package com.ignoretheextraclub.siteswapfactory.sorters.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.InvalidSiteswapException;
import com.ignoretheextraclub.siteswapfactory.exceptions.NoTransitionException;
import com.ignoretheextraclub.siteswapfactory.sorters.StateSorter;
import com.ignoretheextraclub.siteswapfactory.state.AbstractState;
import com.ignoretheextraclub.siteswapfactory.thros.AbstractThro;

/**
 * Created by caspar on 10/12/16.
 */
public class HighestThrowFirstStrategy implements StateSorter
{
    private static final String NAME = "HighestThrowFirst";

    private static StateSorter instance;

    private HighestThrowFirstStrategy()
    {
    }

    public static <Thro extends AbstractThro, State extends AbstractState<Thro>> StateSorter<Thro, State> get()
    {
        if (instance == null)
        {
            instance = new HighestThrowFirstStrategy();
        }
        return instance;
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean takeFirst(AbstractState[] first, AbstractState[] second) throws InvalidSiteswapException
    {
        try
        {
            for (int i = 0; i < first.length; i++)
            {
                AbstractThro ftran = first[i].getThrow(first[(i + 1) % first.length]);
                AbstractThro stran = second[i].getThrow(second[(i + 1) % second.length]);
                if (ftran.compareTo(stran) < 0) { return false; }
                else if (ftran.compareTo(stran) > 0) { return true; }
            }
            return true; //they are equivalent
        }
        catch (NoTransitionException e)
        {
            throw new InvalidSiteswapException("Could not determine transition", e);
        }
    }
}
