package com.ignoretheextraclub.siteswapfactory.predicates.result;

import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

import java.util.function.Predicate;

/**
 * @author Caspar Nonclercq
 */
public class AmbidextrousPredicate implements Predicate<Thro[]>
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
    public boolean test(final Thro[] thros)
    {
        return thros.length % 2 != 0;
    }

    public static class FourHandedSiteswap implements Predicate<Thro[]>
    {
        public static FourHandedSiteswap INSTANCE;

        private FourHandedSiteswap()
        {
            // Singleton
        }

        public static FourHandedSiteswap get()
        {
            if (INSTANCE == null)
            {
                INSTANCE = new FourHandedSiteswap();
            }
            return INSTANCE;
        }

        @Override
        public boolean test(final Thro[] thros)
        {
            return thros.length % 2 != 0 || (thros.length / 2) % 2 != 0;
        }
    }
}
