package com.ignoretheextraclub.siteswapfactory.predicates.impl;

import com.ignoretheextraclub.siteswapfactory.exceptions.TransitionException;
import com.ignoretheextraclub.siteswapfactory.predicates.SequencePredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.utils.StateUtils;
import com.ignoretheextraclub.siteswapfactory.utils.ArrayLoopingIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 Created by caspar on 30/07/17.
 */
public class ThrowCombinationPredicate implements SequencePredicate
{
    private static final Logger LOG = LoggerFactory.getLogger(ThrowCombinationPredicate.class);

    private final Thro[] throComb;

    public ThrowCombinationPredicate(final Thro... throwCombination)
    {
        Objects.requireNonNull(throwCombination[0], "The first throw must not be null");
        Objects.requireNonNull(throwCombination[throwCombination.length - 1], "The last throw must not be null");
        this.throComb = throwCombination;
    }

    public ThrowCombinationPredicate(final Thro thro)
    {
        Objects.requireNonNull(thro);
        this.throComb = new Thro[]{thro};
    }

    private boolean test(final State[] states, final int lengthToCheck, final boolean loops)
    {
        try
        {
            if (states.length < throComb.length - 1)
            {
                LOG.debug("State length too small, cannot contain.");
                return false;
            }

            final Thro[] thros = StateUtils.getAllThrows(states, loops);

            for (int i = 0; i < lengthToCheck; i++)
            {
                final ArrayLoopingIterator<Thro> looper = new ArrayLoopingIterator<>(thros, i);

                if (matches(looper, throComb))
                {
                    return true;
                }
            }

            return false;
        }
        catch (final TransitionException cause)
        {
            LOG.warn("Error checking states {}, length: {}, loops: {}", states, lengthToCheck, loops);
            throw new IllegalStateException("Could not check " + (loops ? "loop" : "sequence") + " because transition is illegal",
                    cause);
        }
    }

    private boolean matches(final ArrayLoopingIterator<Thro> looper, final Thro[] btc)
    {
        for (final Thro thro : btc)
        {
            final Thro next = looper.next();
            if (thro != null && !next.equals(thro))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean testLoop(final State[] states)
    {
        return test(states, states.length, true);
    }

    @Override
    public boolean testSequence(final State[] states)
    {
        return states.length > 1 && test(states, states.length - throComb.length, false);
    }

    @Override
    public boolean supportsTestingLoops()
    {
        return true;
    }

    @Override
    public boolean supportsTestingSequences()
    {
        return true;
    }
}
