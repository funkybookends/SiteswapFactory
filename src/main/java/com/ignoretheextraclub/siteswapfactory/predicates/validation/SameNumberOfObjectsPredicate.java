package com.ignoretheextraclub.siteswapfactory.predicates.validation;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;

import java.util.function.Predicate;

/**
 * Tests if all states have the same number of objects
 * @author Caspar Nonclercq
 */
public class SameNumberOfObjectsPredicate implements Predicate<State[]>
{
    private static SameNumberOfObjectsPredicate instance;

    private SameNumberOfObjectsPredicate()
    {
        // Singleton
    }

    public static SameNumberOfObjectsPredicate get()
    {
        if (instance == null)
        {
            instance = new SameNumberOfObjectsPredicate();
        }
        return instance;
    }

    @Override
    public boolean test(final State[] states)
    {
        for (final State state : states)
        {
            if (state.getNumObjects() != states[0].getNumObjects())
            {
                return false;
            }
        }

        return true;
    }

    public static boolean hasSameNumberOfObjects(final State[] states)
    {
        return get().test(states);
    }
}
