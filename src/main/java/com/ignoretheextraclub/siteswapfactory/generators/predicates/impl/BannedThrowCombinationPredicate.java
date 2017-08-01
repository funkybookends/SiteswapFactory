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
public class BannedThrowCombinationPredicate implements SequencePredicate
{
    private final Thro[] btc;

    public BannedThrowCombinationPredicate(final Thro[] bannedThrowCombination)
    {
        Objects.requireNonNull(bannedThrowCombination[0], "The first throw must not be null");
        Objects.requireNonNull(bannedThrowCombination[bannedThrowCombination.length - 1],
                "The last throw must not be null");
        this.btc = bannedThrowCombination;
    }

    public BannedThrowCombinationPredicate(final Thro bannedThrow)
    {
        Objects.requireNonNull(bannedThrow);
        this.btc = new Thro[]{bannedThrow};
    }

    @Override
    public boolean test(final State[] states, final Boolean loops)
    {
        try
        {
            if (states.length < btc.length - 1)
            {
                return true;
            }

            if (!loops && states.length > 1)
            {
                final Thro[] thros = StateUtils.getAllThrows(states, loops);

                final ArrayLoopingIterator<Thro> looper = new ArrayLoopingIterator<>(thros, thros.length - btc.length);
                if (matches(looper, btc))
                {
                    return false;
                }
            }

            if (loops)
            {
                final Thro[] thros = StateUtils.getAllThrows(states, loops);

                for (int i = thros.length - btc.length + 1; i < thros.length; i++)
                {
                    if (matches(new ArrayLoopingIterator<>(thros, i), btc))
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        catch (final TransitionException cause)
        {
            throw new IllegalStateException("Could not check throw because transition is illegal", cause);
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
