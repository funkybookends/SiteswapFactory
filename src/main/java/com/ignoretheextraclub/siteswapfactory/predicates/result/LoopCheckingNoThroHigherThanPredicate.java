package com.ignoretheextraclub.siteswapfactory.predicates.result;

import com.ignoretheextraclub.siteswapfactory.converter.vanilla.semantic.StatesToThrosConverter;
import com.ignoretheextraclub.siteswapfactory.predicates.intermediate.NoThroHigherThanPredicate;
import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;

/**
 * Uses the {@link StatesToThrosConverter} to get the throws and is therefore a loop checking
 * version of {@link NoThroHigherThanPredicate}.
 *
 * @author Caspar Nonclercq
 */
public class LoopCheckingNoThroHigherThanPredicate extends NoThroHigherThanPredicate
{
    public LoopCheckingNoThroHigherThanPredicate(final Thro maxThro)
    {
        super(maxThro);
    }

    @Override
    protected Thro[] getThros(final State[] states)
    {
        return StatesToThrosConverter.getThros(states);
    }
}
