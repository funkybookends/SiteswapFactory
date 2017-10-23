package com.ignoretheextraclub.siteswapfactory.predicates.intermediate;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ignoretheextraclub.siteswapfactory.siteswap.State;
import com.ignoretheextraclub.siteswapfactory.siteswap.Thro;
import com.ignoretheextraclub.siteswapfactory.siteswap.vanilla.thros.VanillaThro;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XXX__;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X;
import static com.ignoretheextraclub.siteswapfactory.siteswap.StateTestUtils.XX_X_;

@RunWith(JUnitParamsRunner.class)
public class NoThroHigherThanPredicateTest
{
    @Test
    @Parameters
    public void testApply(final Thro maxThro, final State[] states, final boolean expected) throws Exception
    {
        final NoThroHigherThanPredicate noThroHigherThanPredicate = new NoThroHigherThanPredicate(maxThro);

        Assertions.assertThat(noThroHigherThanPredicate.test(states)).isEqualTo(expected);
    }

    public Object parametersForTestApply()
    {
        return new Object[]{
            new Object[]{VanillaThro.get(3), new State[]{XXX__, XXX__}, true},
            new Object[]{VanillaThro.get(3), new State[]{XXX__, XX_X_}, false},
            new Object[]{VanillaThro.get(3), new State[]{XX_X, XXX__}, true},
            // TODO add more
        };
    }
}