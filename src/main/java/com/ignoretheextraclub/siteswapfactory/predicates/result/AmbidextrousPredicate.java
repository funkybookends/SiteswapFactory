package com.ignoretheextraclub.siteswapfactory.predicates.result;

import java.util.function.Predicate;

import com.ignoretheextraclub.siteswapfactory.siteswap.Siteswap;

/**
 * Tests if a siteswap is ambidextrous. A siteswap is ambidextrous if all jugglers have an ambidextrous throw set. A throw set is ambidextrous if it has an odd period.
 * @author Caspar Nonclercq
 */
public class AmbidextrousPredicate implements Predicate<Siteswap>
{
    public static AmbidextrousPredicate INSTANCE;

    private AmbidextrousPredicate()
    {
        // Singleton
    }

    public static AmbidextrousPredicate get()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new AmbidextrousPredicate();
        }
        return INSTANCE;
    }

    @Override
    public boolean test(final Siteswap siteswap)
    {
        for (int juggler = 0; juggler < siteswap.getNumJugglers(); juggler++)
        {
            if (siteswap.getThrowsForJuggler(juggler).length % 2 == 0)
            {
                return false; // TODO make better, this is a primitive approach - should really reduce as well.
            }
        }
        return true;
    }

    public static boolean isAmbidextrous(final Siteswap siteswap)
    {
        return get().test(siteswap);
    }
}
