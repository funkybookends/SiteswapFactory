package com.ignoretheextraclub.siteswapfactory.generators.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.generators.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateUtils;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;

import java.util.Objects;

/**
 Created by caspar on 30/07/17.
 */
public class RequiredThrowCombinationPredicate implements SequencePredicate
{
    private final Thro[] required;

    public RequiredThrowCombinationPredicate(final Thro[] thros)
    {
        Objects.requireNonNull(thros[0], "The first element must not be null");
        Objects.requireNonNull(thros[thros.length], "The last element must not be null");
        this.required = thros;
    }
    
    public RequiredThrowCombinationPredicate(final Thro thro)
    {
        Objects.requireNonNull(thro, "Thro must not be null");
        this.required = new Thro[]{thro};
    }
        

    @Override
    public boolean test(final State[] states, final Boolean loops)
    {
        if (!loops)
        {
            return false;
        }

        try
        {
            final Thro[] thros = StateUtils.getAllThrows(states, true);

            for (int i = 0; i < states.length; i++)
            {
                if (matches(new ArrayLoopingIterator<>(thros, i), this.required))
                {
                    return true;
                }
            }

            return false;
        }
        catch (final TransitionException cause)
        {
            throw new IllegalStateException("Could not verify for illegal states", cause);
        }
    }

    private boolean matches(final ArrayLoopingIterator<Thro> looper, final Thro[] btc)
    {
        for (final Thro aBtc : btc)
        {
            if (aBtc != null && !looper.next().equals(aBtc))
            {
                return false;
            }
        }
        return true;
    }
}
