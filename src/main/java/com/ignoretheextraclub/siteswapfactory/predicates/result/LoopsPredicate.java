package com.ignoretheextraclub.siteswapfactory.predicates.result;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.function.Predicate;

/**
 * Created by caspar on 14/09/17.
 */
public class LoopsPredicate implements Predicate<State[]>
{
    private static LoopsPredicate instance;

    private LoopsPredicate()
    {
        // Singleton
    }

    public static LoopsPredicate get()
    {
        if (instance == null)
        {
            instance = new LoopsPredicate();
        }
        return instance;
    }

    @Override
    public boolean test(final State[] states)
    {
        for (int i = 0; i < states.length; i++)
        {
            if (!states[i].canTransition(states[(i + 1) % states.length]))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean loops(final State[] states)
    {
        return get().test(states);
    }
}
